/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import lombok.soplets.Beanable;
import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.text.TextComponent;

import org.hibernate.Session;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.gui.PDCheckBox;
import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MBaseWithTitle;
import com.maydesk.base.model.MDataLink;
import com.maydesk.base.widgets.PDTextField;


public class PDBinding {

	private Hashtable<IChangeSupportable, MyChangeData> changeData = new Hashtable<IChangeSupportable, MyChangeData>();
	private Hashtable<String, MyChangeData> changeDataByFieldName = new Hashtable<String, MyChangeData>();
	private MBase target;
	private Class<? extends MBase> targetClass;
	private ActionListener titleChangeListener;
	private ActionListener somethingChangedListener;
	private ActionListener saveDoneListener;
	private Set<PDBinding> children = new HashSet<PDBinding>();
	private MDataLink dataLink;	
	private boolean isDeleted;

	public void setStatusDeleted(boolean deleted) {
		isDeleted = deleted;
		fireChangeEvent();
	}

	public PDBinding(Class<? extends MBase> targetClass) {
		this.targetClass = targetClass;
	}

	public void setTitleChangeListener(ActionListener titleChangeListener) {
		this.titleChangeListener = titleChangeListener;
	}	

	public void setSaveDoneListener(ActionListener saveDoneListener) {
		this.saveDoneListener = saveDoneListener;
	}	

	public void setSomethingChangedListener(ActionListener somethingChangedListener) {
		this.somethingChangedListener = somethingChangedListener;
		for (PDBinding childBinding : children) {
			childBinding.setSomethingChangedListener(somethingChangedListener);
		}
	}	

	public void doChange(IChangeSupportable component, Object newValue) {
		if (target == null) return;
		
    	Object oldValue = null;
    	MyChangeData cd = changeData.get(component);
        try {
    		Component c = (Component)component;
    		//store the old value (or retrieve the original one)
        	oldValue = cd.getter.invoke(target, new Object[]{});
        	if (cd.originalValue == null) {
        		cd.originalValue = oldValue;
        	} else {
        		oldValue = cd.originalValue;
        	}
        	if (newValue == null && oldValue == null) {        		
        		return;	
        	} 
        	
        	//set the border of the component
        	if (newValue != null && newValue.equals(oldValue)) {
        		cd.changed = false;
        		component.setBorder(PDBorderFactory.getBorder());        		
        	} else {
        		cd.changed = true;
        		component.setBorder(PDBorderFactory.getBorderActive());
        	}
        	fireChangeEvent();
        	
        	//apply value to target entity
        	cd.setter.invoke(target, newValue);

        	//update the label of the list entry
        	if (titleChangeListener != null && cd.listen && target instanceof MBaseWithTitle) {
        		((MBaseWithTitle)target).setCachedTitle(((MBaseWithTitle)target).createCachedTitle());
        		((MBaseWithTitle)target).setCachedDescription(((MBaseWithTitle)target).createCachedDescription());
        		ActionEvent e = new ActionEvent(target, null);
        		titleChangeListener.actionPerformed(e);
        	}
        	
        	//display the flash label (top-right corner of workspace)
        	if (PDDesktop.getInstance() != null) {
        		PDDesktop.getInstance().showSaving();
        	}
        	
//        	MActionValueChange action = new MActionValueChange();
//        	action.setValueClass(cd.getter.getReturnType().getCanonicalName());
//        	action.setOldValue(oldValue);
//        	action.setNewValue(newValue);
//        	action.setUser(PDUserSession.getInstance().getUser());
//        	action.setChangeSupport(this);
//        	action.setTargetClass(target.getClass().getCanonicalName());
//        	action.setTargetId(target.getId());
//        	action.setTargetField(cd.fieldName);
//            PDActionManager.getInstance().addAction(action);        	        	
        } catch (Exception e1) {
        	System.out.println(oldValue + " -> " + newValue);
            e1.printStackTrace();
        }
	}

	public void fireChangeEvent() {
    	//activate the save/cancel button
    	if (somethingChangedListener != null) {
    		ActionEvent e = new ActionEvent(hasChanges(), null);
    		somethingChangedListener.actionPerformed(e);
    	}
    }

	public MBase getTarget() {
	    return target;
    }

	public Class getTargetClass() {
	    return targetClass;
    }

	public void read(MBase target) {
		this.target = target;
		if (target == null) {
			throw new IllegalArgumentException("Target is null");
		}
		for (IChangeSupportable component : changeData.keySet()) {
			MyChangeData cd = changeData.get(component);
			if (cd == null) return;
			try {
				Object value = cd.getter.invoke(target, new Object[0]);
				((IChangeSupportable)component).setValue(value);
	        } catch (Exception e1) {
	        	System.out.println("Field: " + cd.fieldName);
	        	e1.printStackTrace();
	        }
		}
		if (titleChangeListener != null) {
			ActionEvent e = new ActionEvent(target, null);
			titleChangeListener.actionPerformed(e);
		}
    }

//	public void redoChange(MActionValueChange action) {
//		MyChangeData cd = changeDataByFieldName.get(action.getTargetField());
//        Object newValue = action.getNewValue();
//        try {
//        	cd.setter.invoke(target, new Object[]{newValue});
//        	if (valueListener != null) { // && cd.listen) {
//        		valueListener.actionPerformed(null);
//        	}        	        	
//        	cd.component.setValue(newValue);
//        	ApplicationInstance.getActive().setFocusedComponent((Component)cd.component);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        PDHibernateFactory.getSession().update(target);		
//    }

	public <T extends IChangeSupportable<?>> T register(T component, Beanable sopletRef) {
		return register(component, sopletRef, false);
	}

	public <T extends IChangeSupportable<?>> T register(final T component, Beanable sopletRef, boolean listen) {
		String name = null;
		if (sopletRef instanceof SimpleName) {
			name = ((SimpleName)sopletRef).name();
		} else {
			name = ((Enum)sopletRef).name();
		}
		String fieldName2 = name.substring(0, 1).toUpperCase() + name.substring(1);
		
		MyChangeData cd = new MyChangeData();
		cd.listen = listen;
		cd.soplet = sopletRef;
		cd.fieldName = fieldName2;
		
		component.setChangeSupport(this);
		changeData.put(component, cd);
		changeDataByFieldName.put(fieldName2, cd);
    	try {
    		String prefix = component instanceof PDCheckBox ? "is" : "get";
    		cd.getter = targetClass.getMethod(prefix + fieldName2, new Class[]{});
	        
        	Class valueClass = cd.getter.getReturnType();        	
        	cd.setter = targetClass.getMethod("set" + fieldName2, new Class[]{valueClass});

	        if (component instanceof IChangeSupportableWithAction) {
	        	//immediate save on Enter or on Blur
	        	IChangeSupportableWithAction cswa = (IChangeSupportableWithAction)component;
	        	cswa.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						doChange(component, component.getValue());
                    }	        		
	        	});
	        }

	        if (component instanceof PDTextField) {
	        	if (sopletRef instanceof Enum) {
					Integer length = PDUtil.getOverrideInt((Enum)sopletRef, "length");
					if (length == null) {
						length = sopletRef.length();					
					}
	        		if (length != null && length > 0) {
	        			((PDTextField)component).setMaximumLength(length);
	        		}
	        	}
	        };
        } catch (Exception e) {
	        e.printStackTrace();
        }
	    return component;
    }

//	public void undoChange(MActionValueChange action) {
//		MyChangeData cd = changeDataByFieldName.get(action.getTargetField());
//		Object newValue = action.getOldValue();
//		Object oldValue = action.getNewValue();
//    	
//        try {
//        	cd.setter.invoke(target, new Object[]{newValue});
//        	if (valueListener != null) { // && cd.listen) {
//        		valueListener.actionPerformed(null);
//        	}        	        	
//        	cd.component.setValue(newValue);
//        } catch (Exception e1) {
//        	System.out.println("undo: " + oldValue + " -> " + newValue);
//            e1.printStackTrace();
//        }
//        PDHibernateFactory.getSession().update(target);		
//	}
	
	class MyChangeData {
		private Beanable soplet;
		private String fieldName;
		private Method getter;
		private boolean listen;
		private Method setter;
		private Object originalValue;
		private boolean changed;
	}

	public boolean applyChanges() {
		//validate values
		boolean validationOK = true;
		for (IChangeSupportable component : changeData.keySet()) {
			component.setBorder(PDBorderFactory.getBorder());
			if (component instanceof TextComponent) {
				((TextComponent)component).setToolTipText("");	//FIXME
			}

			MyChangeData cd = changeData.get(component);
			if (cd == null) continue;
			
			//validation
			try {
				if (!(cd.soplet instanceof Enum)) continue;
				Annotation sopletAnnotation = cd.soplet.getClass().getField(((Enum)cd.soplet).name()).getAnnotations()[0];
				if (sopletAnnotation == null) continue;
				Method m = sopletAnnotation.getClass().getMethod("validator");
				if (m == null) continue;
				Enum<?> validator = (Enum<?>)m.invoke(sopletAnnotation);
				if (validator == null) continue;
				Method validate = validator.getClass().getMethod("validate", Object.class);
				validate.setAccessible(true);
				Object value = component.getValue();
				Boolean validationResult = (Boolean)validate.invoke(validator, value);
				if (Boolean.TRUE.equals(validationResult)) continue;
				
				//validation error found
				component.setBorder(PDBorderFactory.getBorderError());
				if (component instanceof TextComponent) {
					Field field = validator.getDeclaringClass().getField(validator.name());
					Annotation ann = field.getAnnotations()[0];
					Method m2 = ann.getClass().getMethod("textEN");
					if (m2 != null) {
						String errorMessage = (String)m2.invoke(ann);
						((TextComponent)component).setToolTipText(errorMessage);
					}
				}
				validationOK = false;
			} catch (NoSuchMethodException ex) {
				//no validator
			} catch (InvocationTargetException ex) {
				//no validator
            } catch (Exception ex) {
	            ex.printStackTrace();
            }	
		}
		if (!validationOK) {
			return false;
		}
		
		if (hasChanges()) {
			if (target instanceof MBaseWithTitle) {
				((MBaseWithTitle)target).updateCachedValues();
			}
			Session session = PDHibernateFactory.getSession();
			if (target.getId() == 0) {
				session.save(target);
				if (dataLink != null) {
					session.flush();
					session.refresh(target);
					dataLink.setTargetId(target.getId());
					session.saveOrUpdate(dataLink);
				}				
			} else {
				session.update(target);
			}
		}
		for (PDBinding childBinding : children) {
			childBinding.applyChanges();
		}
		resetChanges();
		if (saveDoneListener != null) {
			ActionEvent e = new ActionEvent(target, null);
			saveDoneListener.actionPerformed(e);
		}
		return true;
    }

	public boolean hasChanges() {
		if (target == null) {
			return false;	
		} if (isDeleted && target.getId() > 0) {
			return true;  //deleted an (existing!) object
		} else if (target.getId() == 0) { 
			return true;  //newly created object
		}
		
		for (IChangeSupportable c : changeData.keySet()) {
	    	MyChangeData cd = changeData.get(c);
	    	if (cd.changed) {
	    		return true;
	    	}
		}
		for (PDBinding childBinding : children) {
			if (childBinding.hasChanges()) {
				return true;
			}
		}
	    return false;
    }

	public void resetChanges() {
		isDeleted = false;
		for (IChangeSupportable c : changeData.keySet()) {
   			c.setBorder(PDBorderFactory.getBorder());
	    	MyChangeData cd = changeData.get(c);
	    	cd.changed = false;
	    	cd.originalValue = null;
		}	    
		for (PDBinding childBinding : children) {
			childBinding.resetChanges();
		}		
    }

	public void addChild(PDBinding childBinding) {
		children.add(childBinding);
    }
	
	public void resetChildren() {
		children.clear();
    }
	
	public Set<PDBinding> getChildren() {
		return children;
    }

	public ActionListener getSomethingChangedListener() {
	   return somethingChangedListener;	    
    }

	public MBase readDataLink(MDataLink dataLink) {
		this.dataLink = dataLink;
		MBase model = null;
		if (dataLink.getTargetId() == -1) {
			try {
	            model = (MBase) Class.forName(dataLink.getTargetClass()).newInstance();
            } catch (Exception e) {
	            e.printStackTrace();
            }
		} else {
			model = MBase.loadByDataLink(dataLink);	
		}
		if (model != null) read(model);
	    return model;
    }
}

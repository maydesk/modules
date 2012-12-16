/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import java.util.List;
import java.util.Vector;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.dao.DaoUser;
import com.maydesk.base.model.MMnemonic;
import com.maydesk.base.util.IChangeSupportableWithAction;
import com.maydesk.base.util.PDBinding;
import com.maydesk.base.util.PDBorderFactory;
import com.maydesk.base.util.PDLookAndFeel;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDComboModel.MyListEntry;

import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.SelectField;
import nextapp.echo.app.list.ListModel;


public class PDCombo<T> extends SelectField implements IChangeSupportableWithAction<T> {

	private PDBinding changeSupport;
	private int serialId;
	
	public PDCombo(int serialId) {
		super(new PDComboModel<T>());
		init2();
	}

	public PDCombo() {
		super(new PDComboModel<T>());
		init2();
	}

	public PDCombo(T[] values) {
		this(values, false);
	}

	public PDCombo(T[] values, boolean doSort) {
		this(arrayToList(values), doSort);
	}

	public PDCombo(List<T> values, boolean doSort) {
		this(values, null, doSort);
	}

	public PDCombo(T[] values, Translatable emptyEntry, boolean doSort) {
		this(arrayToList(values), emptyEntry, doSort);
	}

	public PDCombo(List<T> values, Translatable emptyEntry) {
		this(values, emptyEntry, false);
	}

	public PDCombo(List<T> values, Translatable emptyEntry, boolean doSort) {
		super(new PDComboModel<T>());
		setValues(values, emptyEntry, doSort);
		init2();
	}
	
	public void setValues(List<T> values) {
		PDComboModel<T> model = getModel();
		model.setValues(values, false);
		setSelectedIndex(0);
	}

	public void setValues(List<T> values, Translatable emptyEntry, boolean doSort) {
		PDComboModel<T> model = getModel();
		if (emptyEntry == null) {
			model.setValues(values, doSort);
		} else {
			model.setValues(values, nls(emptyEntry), doSort);
		}
		setSelectedIndex(0);
	}

	@Deprecated
	public void setValues(List<T> values, String emptyEntry, boolean doSort) {
		PDComboModel<T> model = getModel();
		model.setValues(values, emptyEntry, doSort);
		setSelectedIndex(0);
	}


	private void init2() {
		setBorder(PDBorderFactory.getBorder());
		setDisabledBorder(new Border(1, Color.LIGHTGRAY, Border.STYLE_DOTTED));
		setDisabledBackground(PDLookAndFeel.DISABLED_BACKGROUND);
		setDisabledForeground(PDLookAndFeel.DISABLED_FOREGROUND);
		setWidth(new Extent(210));
	}

	// allow empty entries in the combo - a "null" entry would throw an exception
	@Override
	public T getSelectedItem() {
		if (super.getModel().size() == 0) {
			return null;
		}
		Object o = super.getSelectedItem();
		if (PDUtil.isEmpty(o)) {
			return null;
		}
		return (T)((MyListEntry)o).value;
	}
	
    public PDComboModel<T> getModel() {
        return (PDComboModel<T>)super.getModel();
    }    

    @Override
    public void setModel(ListModel lm) {
    	if (!(lm instanceof PDComboModel)) {
        	throw new IllegalArgumentException ("Model must be of type PDComboModel");
    	}
    	super.setModel(lm);
    }   
    
    @Override
    public void setSelectedItem(Object item) {
    	if (item == null) {
    		setSelectedIndex(0);
    	} else {
    		MyListEntry entry = getModel().getEntryIndex(item);
    		super.setSelectedItem(entry);
    		//super.setSelectedIndex(3);
    	}
    }
    
    private static <TT> List<TT> arrayToList(TT[] array) {
    	List<TT> list = new Vector<TT>();
    	for (TT o : array) {
    		list.add(o);
    	}
    	return list;
    	
    }
    
//	public void storeSelection(Object contextClazz, Class modelClass) {
//		String context = contextClazz.toString();
//		MBase model = (MBase)getSelectedItem();
//		MMnemonic mnemomic = DaoUser.findMnemonic(context, modelClass);
//		if (mnemomic == null) {
//			mnemomic = new MMnemonic();
//			mnemomic.setContext(context);
//			mnemomic.setModelClass(modelClass.getName());
//			mnemomic.setUser(PDUserSession.getInstance().getUser());
//		}
//		if (model != null) {
//			mnemomic.setModelId(model.getId());
//		} else {
//			mnemomic.setModelId(-1);
//		}
//		PDHibernateFactory.getSession().saveOrUpdate(mnemomic);
//	}
//
//	public void restoreSelection(Object key, Class modelClass) {
//		String context = key.toString();
//		MMnemonic mnemomic = DaoUser.findMnemonic(context, modelClass);
//		if (mnemomic == null) return;
//		try {
//			Class modelClazz = Class.forName(mnemomic.getModelClass());
//			T model = (T)MBase.loadById(modelClazz, mnemomic.getModelId());
//			setSelectedItem(model);			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}			
//	}

	public void storeSelection() {
		if (serialId == 0) {
			throw new IllegalArgumentException("PDCombo.storeSelection may only be used when serialID is set");
		}
		MMnemonic mnemomic = DaoUser.findMnemonic(serialId);
		if (mnemomic == null) {
			mnemomic = new MMnemonic();
			mnemomic.setSerialId(serialId);
			mnemomic.setUserRef(PDUserSession.getInstance().getUser());
		}
		mnemomic.setPosition(getSelectedIndex());
		PDHibernateFactory.getSession().saveOrUpdate(mnemomic);
	}

	public void restoreSelection() {
		if (serialId == 0) {
			throw new IllegalArgumentException("PDCombo.storeSelection may only be used when serialID is set");
		}
		MMnemonic mnemomic = DaoUser.findMnemonic(serialId);
		if (mnemomic == null) return;
		setSelectedItem(mnemomic.getPosition());			
	}

	public void setValue(T value) {
		setSelectedItem(value);
	}

	public T getValue() {
	    return getSelectedItem();
    }

	public PDBinding getChangeSupport() {
    	return changeSupport;
    }

	public void setChangeSupport(PDBinding changeSupport) {
    	this.changeSupport = changeSupport;
    }

	public String getPropertyName() {
	    return "selected";
    }
	
	//@Override
    public void setEditable(boolean editable) {
	    setEnabled(editable);	    
    }    
}

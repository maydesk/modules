package com.maydesk.base.widgets;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.model.MBase;
import com.maydesk.base.util.IChangeSupportableWithAction;
import com.maydesk.base.util.PDBinding;

import echopoint.AutoLookupSelectField;



public class PDAutoLookupField<T extends MBase> extends AutoLookupSelectField implements IChangeSupportableWithAction<T> {

	private PDBinding changeSupport;
	private Class modelClazz;
	
	public PDAutoLookupField(Class<T> modelClazz) {
		this.modelClazz = modelClazz;
	}
	
	public void setValue(T value) {
		setKey(value.getIdAsString());
		setText(value.toString());
    }

	public T getValue() {
		String idAsString = (String)super.getKey();
		if (idAsString == null) {
			return null;
		}
	    return (T)PDHibernateFactory.getSession().load(modelClazz, Integer.parseInt(idAsString));
    }

	public String getPropertyName() {
	    return "key";
    }

	public void setChangeSupport(PDBinding changeSupport) {
	    this.changeSupport = changeSupport;
    }

	public PDBinding getChangeSupport() {
	    return changeSupport;
    }
}

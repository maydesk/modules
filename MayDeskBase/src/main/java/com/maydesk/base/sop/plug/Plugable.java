package com.maydesk.base.sop.plug;



public @interface Plugable {

	public String plugTitle();
	public SopSocketType plugType();
	public Class modelClass() default java.lang.Object.class;
	public Class editorClass() default java.lang.Object.class;
	public Class configEditorClass() default java.lang.Object.class;
	public String icon() default "";
	
}

if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDText = Core.extend(MD.MDAbstractFigure, {

	getEditor: function() {
		return null;
	},
	
	$load : function() {
       	Echo.ComponentFactory.registerType("MDText", this);
	},
	componentType: "MDText"
});

 
MD.Sync.MDText = Core.extend(MD.Sync.MDAbstractFigure, {
    
    $load: function() {
        Echo.Render.registerPeer("MDText", this);
    },
    
    renderAdd2: function(canvas, x, y) {
     	var fig = new window.draw2d.shape.basic.Label();
     	fig.setText(this.component.render("text", "type here..."));
      	fig.setFontSize(this.component.render("size", 12));
      	fig.installEditor(new draw2d.ui.LabelInplaceEditor());
		fig.onClick = Core.method(this, this.onClick);
		var type = this.component.render("type");
		if (type == "banner") {
			if (fig.setFontFamily) fig.setFontFamily("Arial");
	   		fig.setBackgroundColor("#5b5b5b");        
    	  	fig.setStroke(1);
        	fig.setRadius(5);
			fig.setPadding(4);
	    	fig.setColor("#dd00ee");
    	  	fig.setFontColor("#dddddd");
		    if (fig.setFontWeight) fig.setFontWeight("bold");    //works currently only in playgroundJS!  	  	
		} else if (type == "header") {
			fig.setStroke(0);
		    if (fig.setFontFamily) fig.setFontFamily("fantasy");   //works currently only in playgroundJS!	 
      		fig.setFontColor("#111111");
		} else {
			fig.setStroke(0);
		}
      	canvas.addFigure(fig, x, y);    
      	
    }
});
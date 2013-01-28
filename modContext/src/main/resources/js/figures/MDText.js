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
     	var fig = new window.draw2d.shape.note.PostIt();
     	fig.setText("Hello");
    	fig.setColor("#dd00ee");
      	fig.setFontColor("#dddddd");
		fig.installEditor(new draw2d.ui.LabelInplaceEditor());
		fig.onClick = Core.method(this, this.onClick);
      	canvas.addFigure(fig, x, y);    
    }
});
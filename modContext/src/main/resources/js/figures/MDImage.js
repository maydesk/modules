if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDImage = Core.extend(MD.MDAbstractFigure, {

	getEditor: function() {
		return null;
	},
	
	$load : function() {
       	Echo.ComponentFactory.registerType("MDImage", this);
	},
	componentType: "MDImage"
});

 
MD.Sync.MDImage = Core.extend(MD.Sync.MDAbstractFigure, {
    
    $load: function() {
        Echo.Render.registerPeer("MDImage", this);
    },
    
    renderAdd2: function(canvas, x, y) {
 		var src = this.component.render("src", "");
 		var width = this.component.render("width", 100);
 		var height = this.component.render("height", 100);
     	var fig = new window.draw2d.shape.basic.Image(Echo.Sync.ImageReference.getUrl(src), width, height);
      	canvas.addFigure(fig, x, y);      	
    }
});
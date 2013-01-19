if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDText = Core.extend(Echo.Component, {
	$load : function() {
       	Echo.ComponentFactory.registerType("MDText", this);
	},
	componentType: "MDText"
});

 
MD.Sync.MDText = Core.extend(Echo.Render.ComponentSync, {
    
    $load: function() {
        Echo.Render.registerPeer("MDText", this);
    },
    
    renderAdd: function(update, parentNode) {

    },
    
     doLazyLoad: function(canvas) {
     	this.fig = new window.draw2d.shape.note.PostIt('Hello this is a note!');
    	this.fig.setColor("#dd00ee");
      	this.fig.setFontColor("#dddddd");
		//this.fig.installEditor(new draw2d.ui.LabelInplaceEditor());
      	canvas.addFigure(this.fig, 20, 150);    
    },
	
	/** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {       
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
        return false; // Child elements not supported: safe to return false.
    }	
});


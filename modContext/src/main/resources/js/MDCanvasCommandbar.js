if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDCanvasCommandbar = Core.extend(Echo.Component, {

	$load : function() {
       	Echo.ComponentFactory.registerType("MDCanvasCommandbar", this);
	},
	
	componentType: "MDCanvasCommandbar",
	
	
});

 
MD.Sync.MDCanvasCommandbar = Core.extend(Echo.Render.ComponentSync, {
    
    $load: function() {
        Echo.Render.registerPeer("MDCanvasCommandbar", this);
    },
    
    _node: null,
    
    renderAdd: function(update, parentElement) {

    	//the tool row
    	this._node = document.createElement("div");
    	this._node.style.position = "absolute";	
		parentElement.appendChild(this._node);
    	
    	var that = this;
		var btnZoomDecrease = new Echo.Button();
		btnZoomDecrease.application = this.component.application;
		btnZoomDecrease.set("icon", "img/remove.gif");
		btnZoomDecrease.addListener("action", function() {
			that._canvas.setZoom(that._canvas.getZoom() / 0.7, true);
       	});		
		this.component.toolRow.add(btnZoomDecrease);					

		var btnZoomIncrease = new Echo.Button();
		btnZoomIncrease.application = this.component.application;
		btnZoomIncrease.set("icon", "img/add.gif");
		btnZoomIncrease.addListener("action", function() {
			that._canvas.setZoom(that._canvas.getZoom() * 0.7, true);
       	});		
		this.component.toolRow.add(btnZoomIncrease);					
    	
		for (var i = 0; i < this.component.getComponentCount(); i++) {   
			Echo.Render.renderComponentAdd(update, this.component.getComponent(i), this._node);
		}
    },
    
	/** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._node = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
        return false; // only update Child elements
    }       
});

if (!Core.get(window, ["PD", "Sync"])) {
        Core.set(window, ["PD", "Sync"], {});
}
 
PD.PDSvgImage = Core.extend(Echo.Component, {
		$load : function() {
        	Echo.ComponentFactory.registerType("PDSvgImage", this);
        	Echo.ComponentFactory.registerType("raphael", this);
		},
		componentType: "PDSvgImage"
});
 
 
PD.Sync.PDSvgImage = Core.extend(Echo.Render.ComponentSync, {
    
    $load: function() {
        Echo.Render.registerPeer("PDSvgImage", this);
    },
    
    _svgData: null,
    _node: null,
    _containerElement: null,

	_addSvg: function() {
		var paper = Raphael("paper123", 600, 600);
        paper.rect(0, 0, 640, 480, 10).attr({fill: "#fff", stroke: "none"});
   	    paper.circle(320, 240, 60).animate({fill: "#223fa3", stroke: "#000", "stroke-width": 80, "stroke-opacity": 0.5}, 2000);			
    },

    renderAdd: function(update, parentElement) {
    	this._containerElement = parentElement;
		//the main node
        this._node = document.createElement("div");
        this._node.id = "paper123";
		this._node.style.position = "absolute";	
       	this._node.style.left = "50px";
		this._node.style.top = "80px";
		this._node.style.width = "500px";
		this._node.style.height ="500px";
		parentElement.appendChild(this._node);

		var that = this;
		var doActionDelayed = function() {
			that._addSvg();
    	};
		window.setTimeout(doActionDelayed, 200);  
		
		//this._svgData = this.component.render("svg_data");
	},
	
	/** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._node = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
    	if (this._node) {
            this._node.parentNode.removeChild(this._node);
        }
        // Note: this.renderDispose() is not invoked (it does nothing).
        this.renderAdd(update, this._containerElement);
        
        return false; // Child elements not supported: safe to return false.
    }   
});

if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}

 
MD.MDCanvas = Core.extend(Echo.Component, {
	$load : function() {
       	Echo.ComponentFactory.registerType("MDCanvas", this);
	},
	componentType: "MDCanvas"
});

 
MD.Sync.MDCanvas = Core.extend(Echo.Render.ComponentSync, {
    
    $load: function() {
        Echo.Render.registerPeer("MDCanvas", this);
    },
    
    _node: null,
    _containerElement: null,
   	_canvas: null,
    _processMouseClickRef: null,
    
    $construct: function() {
		this._processMouseClickRef = Core.method(this, this._loadStuff);      
    },
    
    renderAdd: function(update, parentElement) {
    	this._containerElement = parentElement;
    	
        var backlight = document.createElement("div");
		backlight.style.position = "absolute";	
       	backlight.style.width = "100%";
		backlight.style.height ="100%";
		backlight.style.opacity = 0.12;
		backlight.style.background ="white";
		parentElement.appendChild(backlight);
    	
		//the main node
        this._node = document.createElement("div");
        this._node.id = 'canvas_' + this.component.renderId.replace(".", "_");
		this._node.style.position = "absolute";	
       	this._node.style.width = "100%";
		this._node.style.height ="100%";
		parentElement.appendChild(this._node);

		Core.Web.Event.add(this._node, "click", this._processMouseClickRef, true)

		//this._loadStuff();
		
		var componentCount = this.component.getComponentCount();
        for (var i = 0; i < componentCount; i++) {     
            Echo.Render.renderComponentAdd(update, this.component.getComponent(i), this._node);
        }		
        
        //setTimeout(this._loadStuff(), 2500);
    },

    _loadStuff: function() {
    	if (!this._canvas) {
	   		this._canvas = new window.draw2d.Canvas(this._node.id);
			
			//remove, just for demo...
			var startCircle = new window.draw2d.shape.basic.Circle(55);
			startCircle.setColor("#cd1dcc");
			this._canvas.addFigure(startCircle, 220, 220);
		
			var componentCount = this.component.getComponentCount();
			for (var i = 0; i < componentCount; i++) {     
            	this.component.getComponent(i).peer.doLazyLoad(this._canvas);
	        }		
			
	    }
	    Core.Web.Event.remove(this._node, "click", this._processMouseClickRef, true);
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

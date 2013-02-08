if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDCanvas = Core.extend(Echo.Component, {

	$load : function() {
       	Echo.ComponentFactory.registerType("MDCanvas", this);
	},
	
	componentType: "MDCanvas",
	_currentTool: null,
	
	//called from MDToolEntry   
    setCurrentTool: function(cmpFig) {
	    this._currentTool = cmpFig;
    }    
});

 
MD.Sync.MDCanvas = Core.extend(Echo.Render.ComponentSync, {
    
    $load: function() {
        Echo.Render.registerPeer("MDCanvas", this);
    },
    
    _node: null,
    _containerElement: null,
   	_canvas: null,
    
    renderAdd: function(update, parentElement) {

    	this._containerElement = parentElement;
    	
        var backlight = document.createElement("img");
  		//backlight.src = "http://wakpaper.com/large/Graffiti_wallpapers_216.png";
  		//backlight.src = "http://img.wallpaperstock.net:81/ubuntu-graffiti-wallpapers_33371_1600x1200.jpg";
  		backlight.src = "http://static3.depositphotos.com/1001951/174/i/950/depositphotos_1746717-Graffiti-background.jpg";
  		
		backlight.style.position = "absolute";	
       	backlight.style.width = "100%";
		backlight.style.height ="100%";
		backlight.style.opacity = 0.9;
		parentElement.appendChild(backlight);
    	
		//the main node
        this._node = document.createElement("div");
        this._node.id = 'canvas_' + this.component.renderId.replace(".", "_");
		this._node.style.position = "relative";
		this._node.style.width = "100%";
		this._node.style.height ="100%";
		parentElement.appendChild(this._node);

        var componentCount = this.component.getComponentCount();
		for (var i = 0; i < componentCount; i++) {   
			var child = this.component.getComponent(i);
		  	Echo.Render.renderComponentAdd(update, child, this);
	    }
	    
	    //late-loading, otherwise there would pop-up strange errors...
	   	window.setTimeout(Core.method(this, this._loadCanvas), 800);
    },
    
    _loadCanvas: function() {
   		this._canvas = new MyCanvas(this);
		this._canvas.setZoom(1.5, false);
		
		var node = this._node;
		var MyDragPolicy = draw2d.policy.canvas.CanvasPolicy.extend({
		    onMouseDrag:function(canvas, dx, dy, dx2, dy2){
		    	//console.log( (node.style.top + dy2) + " :: " + dy2);
        		node.style.left = dx + "px";
        		node.style.top = dy + "px";
    		}		
		});		
    },

	/** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._node = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
		var addedChildren = update.getAddedChildren();
        if (addedChildren) {
	        for (i = 0; i < addedChildren.length; ++i) {
				Echo.Render.renderComponentAdd(update, addedChildren[i], this);
	        }
	    }
        return false; // only update Child elements
    }       
});

MyCanvas = draw2d.Canvas.extend({

    NAME : "MyCanvas",
   
    _peerCanvas: null,
    
    init : function(peerCanvas) {
		this._peerCanvas = peerCanvas;
    	this._super(peerCanvas._node.id);
    },

    onClick: function(x, y){
    	if (this._peerCanvas.component._currentTool) {
    		//add a new instance of the current tool to the canvas
		  	var newFig = this._peerCanvas.component._currentTool;
		   	var x = event.clientX - this.getAbsoluteX();
		  	var y = event.clientY - this.getAbsoluteY();
		  	newFig.set("positionX", x);
		  	newFig.set("positionY", y);
		  	this._peerCanvas.component.add(newFig);
			Echo.Render.processUpdates(this._peerCanvas.client);
			this._peerCanvas.component._currentTool = null;
	    }
	    this._super(x, y);
    } 
});
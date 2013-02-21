if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDCanvas = Core.extend(Echo.Component, {

	$load : function() {
       	Echo.ComponentFactory.registerType("MDCanvas", this);
	},
	
	componentType: "MDCanvas",
	
	_currentTool: null,
	
	fireClick: function() {
		this.fireEvent({type: "async_click", source: this});
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
		backlight.style.opacity = 0.7;
		backlight.style.background = 'white';
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
		
	    var zoom = this.component.render("zoom", 100);
	    this._canvas.setZoom(100 / zoom, true);
		
		//for dragging the viewport of a zoomed canvas
//		var node = this._node;
//		var MyDragPolicy = draw2d.policy.canvas.CanvasPolicy.extend({
//		    onMouseDrag:function(canvas, dx, dy, dx2, dy2){
//				console.log( (node.style.top + dy2) + " :: " + dy2);
//        		node.style.left = dx + "px";
//        		node.style.top = dy + "px";
//    		}
//		});	
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
	    
	    var zoom = this.component.render("zoom", 100);
	    this._canvas.setZoom(100 / zoom, true);
        return true;
    }       
});

MyCanvas = draw2d.Canvas.extend({

    NAME : "MyCanvas",
   
    _peerCanvas: null,
    
    init : function(peerCanvas) {
		this._peerCanvas = peerCanvas;
    	this._super(peerCanvas._node.id);
    
    	this._eventListener = new draw2d.command.CommandStackEventListener();
    	this._eventListener.stackChanged = Core.method(this, this.stackChanged);
    	this.getCommandStack().addEventListener(this._eventListener);
    },

    stackChanged: function(event) {
    	var command = event.getCommand(); 
    	if (!command || !(command instanceof draw2d.command.CommandResize)) {
    		return;
    	}
    	
    	var figure = command.figure;
    	var component = figure._parent.component;
    	component.set('positionX', figure.getX());
		component.set('positionY', figure.getY());
		component.set('width', figure.getWidth());
		component.set('height', figure.getHeight());
    	component.fireUpdatePropEvent();
    },
    
    onClick: function(x, y) {
    	
    	// XXX: FIX. It's going to the server on every click, it could be optimized just to fire it when adding a new element (for now at least).
    	this._peerCanvas.component.set("clickX", x);
    	this._peerCanvas.component.set("clickY", y);
    	this._peerCanvas.component.fireClick();
    	
//    	if (this._peerCanvas.component._currentTool) {
//    		//add a new instance of the current tool to the canvas
//		  	var newFig = this._peerCanvas.component._currentTool;
//		   	var x = event.clientX - this.getAbsoluteX();
//		  	var y = event.clientY - this.getAbsoluteY();
//		  	newFig.set("positionX", x);
//		  	newFig.set("positionY", y);
//		  	if (this._peerCanvas.client.addComponentListener) {
//		  		//console.log("register listener");
//		  		//this._peerCanvas.client.addComponentListener(newFig, "move");
//		  	}
//		  	this._peerCanvas.component.add(newFig);
//			Echo.Render.processUpdates(this._peerCanvas.client);
//			this._peerCanvas.component._currentTool = null;
//	    }
	    this._super(x, y);
    }
});
if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}

 
MD.MDCanvas = Core.extend(Echo.Component, {

	$load : function() {
       	Echo.ComponentFactory.registerType("MDCanvas", this);
	},
	
	componentType: "MDCanvas",
	editorRow: new Echo.Row(),
	toolRow: new Echo.Row(),
	_currentTool: null,
	    
    setCurrentTool: function(cmpFig) {
	    this._currentTool = cmpFig;
    },
    
    setEditor: function(figure) {
		this.editorRow.removeAll();
		var editor = figure.component.getEditor();
		if (editor) {
			this.editorRow.add(editor);					
		}
	    Echo.Render.processUpdates(this.peer.client);
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
    	
        var backlight = document.createElement("div");
		backlight.style.position = "absolute";	
		backlight.style.top = "25px";
       	backlight.style.width = "100%";
		backlight.style.height ="100%";
		backlight.style.opacity = 0.8;
		backlight.style.background ="blue";
		backlight.style.zIndex = parentElement.style.zIndex;
		parentElement.appendChild(backlight);
    	
		//the main node
        this._node = document.createElement("div");
        this._node.id = 'canvas_' + this.component.renderId.replace(".", "_");
		this._node.style.position = "absolute";	
		this._node.style.top = "25px";
       	this._node.style.width = "100%";
		this._node.style.height ="100%";
		this._node.style.zIndex = parentElement.style.zIndex + 1;
		parentElement.appendChild(this._node);

    	//the tool row
    	this.component.toolRow.parent = this.component;
    	this.component.toolRow.set("background", "black");
    	this.component.toolRow.set("height", "25");
    	//this.component.toolRow.set("opacity", "0.3");
    	this.component.editorRow.application = this.component.application;
		for (var i = this.component.getComponentCount(); i >= 0; i--) {   
			var child = this.component.getComponent(i);
			if (child instanceof MD.MDToolEntry) {
				this.component.remove(child);
				this.component.toolRow.add(child);				
				child.application = this.component.application;				
			}
		}
		this.component.toolRow.add(this.component.editorRow);
    	Echo.Render.renderComponentAdd(update, this.component.toolRow, parentElement);
    	
        var componentCount = this.component.getComponentCount();
		for (var i = 0; i < componentCount; i++) {   
			var child = this.component.getComponent(i);
			if (!(child instanceof MD.MDToolEntry)) {
			  	//Echo.Render.renderComponentAdd(update, child, this._node);
			}
	    }
	    
	    //late-loading, otherwise there would pop-up strange errors...
	   	window.setTimeout(Core.method(this, this._loadCanvas), 800);
    },
    
    _loadCanvas: function() {
   		this._canvas = new MyCanvas(this);
		//this._canvas.setZoom(0.5);
		
		//remove, just for testing...
		var startCircle = new window.draw2d.shape.basic.Circle(55);
		startCircle.setColor("#cd1dcc");
		this._canvas.addFigure(startCircle, 220, 220);
	
		var componentCount = this.component.getComponentCount();
		for (var i = 0; i < componentCount; i++) { 
			var child = this.component.getComponent(i);
			if (!(child instanceof MD.MDToolEntry) && child.peer) {
				child.peer.doLazyLoad(this._canvas, 50, 50);
			}
        }    
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


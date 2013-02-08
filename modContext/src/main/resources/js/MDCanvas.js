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
	zoomRow: new Echo.Row(),
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
    	
        var backlight = document.createElement("img");
        
  		//backlight.src = "http://wakpaper.com/large/Graffiti_wallpapers_216.png";
  		//backlight.src = "http://img.wallpaperstock.net:81/ubuntu-graffiti-wallpapers_33371_1600x1200.jpg";
  		backlight.src = "http://static3.depositphotos.com/1001951/174/i/950/depositphotos_1746717-Graffiti-background.jpg";
  		
		backlight.style.position = "absolute";	
		backlight.style.top = "25px";
       	backlight.style.width = "100%";
		backlight.style.height ="100%";
		backlight.style.opacity = 0.9;
		//backlight.style.background ="white";
		parentElement.appendChild(backlight);
    	
		//the main node
        this._node = document.createElement("div");
        this._node.id = 'canvas_' + this.component.renderId.replace(".", "_");
		this._node.style.position = "absolute";
		this._node.style.top = "25px";
       	this._node.style.width = "250%";
		this._node.style.height ="250%";
		parentElement.appendChild(this._node);

    	//the tool row
    	this.component.toolRow.parent = this.component;
    	this.component.toolRow.set("background", "blue");
    	this.component.toolRow.set("height", "25");
    	this.component.toolRow.set("opacity", "0.7");
    	this.component.editorRow.application = this.component.application;
		for (var i = this.component.getComponentCount(); i >= 0; i--) {   
			var child = this.component.getComponent(i);
			if (child instanceof MD.MDToolEntry) {
				//FIXME would be better having an own collection for tool entries, onstead of mixing them with regular children...
				this.component.remove(child);
				this.component.toolRow.add(child);		
				child.application = this.component.application;				
			}
		}
		this.component.toolRow.add(this.component.editorRow);
		this.component.toolRow.add(this.component.zoomRow);

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
    	
    	Echo.Render.renderComponentAdd(update, this.component.toolRow, parentElement);
    	
        var componentCount = this.component.getComponentCount();
		for (var i = 0; i < componentCount; i++) {   
			var child = this.component.getComponent(i);
			if (!(child instanceof MD.MDToolEntry)) {
			  	Echo.Render.renderComponentAdd(update, child, this);
			}
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
		//this._canvas.installEditPolicy(new MyDragPolicy());
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
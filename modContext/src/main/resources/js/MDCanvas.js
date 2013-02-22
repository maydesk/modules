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
    _divPosInner: null,
    
    renderAdd: function(update, parentElement) {

    	this._containerElement = parentElement;
    	
		//the main node
        this._node = document.createElement("div");
        this._node.id = 'canvas_' + this.component.renderId.replace(".", "_");
		this._node.style.position = "relative";
		this._node.style.width = "100%";
		this._node.style.height = "100%";
		this._node.style.background = 'white';
		parentElement.appendChild(this._node);

		//Core.Web.Event.add(this._divPosInner, "click", Core.method(this, this._panDown), true);
		
			
        var componentCount = this.component.getComponentCount();
		for (var i = 0; i < componentCount; i++) {   
			var child = this.component.getComponent(i);
		  	Echo.Render.renderComponentAdd(update, child, this);
	    }
	    
	    //late-loading, otherwise there would pop-up strange errors...
	   window.setTimeout(Core.method(this, this._loadCanvas), 800);
    },
    
    yPos: 0,

    _wheel: function() {
		console.log("wheel");
    },
    
    _loadCanvas: function() {
   		this._canvas = new MyCanvas(this);
   		
   		//this._canvas.paper.rect(0, 0, 1200, 1000).attr({
		//    fill: "url(http://static3.depositphotos.com/1001951/174/i/950/depositphotos_1746717-Graffiti-background.jpg)"
		//});
	    
		//credits: http://jsfiddle.net/9zu4U/9/
		var paper = this._canvas.paper;
		var canvas = this._canvas;
		var viewBoxWidth = paper.width;
		var viewBoxHeight = paper.height;
		var canvasID = "#" + this._node.id;
		var startX,startY;
		var mousedown = false;
		var dX,dY;
		var oX = 0, oY = 0;
		var viewBox = paper.setViewBox(oX, oY, viewBoxWidth, viewBoxHeight);
		viewBox.X = oX;
		viewBox.Y = oY;
		
		
		/** This is high-level function.
		 * It must react to delta being more/less than zero.
		 */
		function handleWheel(delta) {
		    vBHo = viewBoxHeight;
		    vBWo = viewBoxWidth;
		    if (delta < 0) {
			    viewBoxWidth *= 0.95;
			    viewBoxHeight*= 0.95;
		    } else {
		    	viewBoxWidth *= 1.05;
		    	viewBoxHeight *= 1.05;
		    }
		                    
			viewBox.X -= (viewBoxWidth - vBWo) / 2;
			viewBox.Y -= (viewBoxHeight - vBHo) / 2;          
			paper.setViewBox(viewBox.X,viewBox.Y,viewBoxWidth,viewBoxHeight);
			
			var zoom = viewBoxWidth / paper.width ;
			console.log(zoom);
			canvas.zoomFactor = zoom;
			
			
		}
		
		/** Event handler for mouse wheel event.
		 */
		function wheel(event){
			console.log("wheel");
	        var delta = 0;
	        if (!event) /* For IE. */
	                event = window.event;
	        if (event.wheelDelta) { /* IE/Opera. */
	                delta = event.wheelDelta/120;
	        } else if (event.detail) { /** Mozilla case. */
	                /** In Mozilla, sign of delta is different than in IE.
	                 * Also, delta is multiple of 3.
	                 */
	                delta = -event.detail/3;
	        }
	        
	        /** If delta is nonzero, handle it.
	         * Basically, delta is now positive if wheel was scrolled up,
	         * and negative, if wheel was scrolled down.
	         */
	        if (delta) {
	        	handleWheel(delta);
	        }
	        
	        /** Prevent default actions caused by mouse wheel.
	         * That might be ugly, but we handle scrolls somehow
	         * anyway, so don't bother here..
	         */
	        if (event.preventDefault) {
	        	event.preventDefault();
	        }
		    event.returnValue = false;
		}
		
		window.addEventListener('DOMMouseScroll', wheel, false);
		window.onmousewheel = document.onmousewheel = wheel;
		
		//Pane
		$(canvasID).mousedown(function(e){
		    if (paper.getElementByPoint( e.pageX, e.pageY ) != null) {return;}
		    mousedown = true;
		    startX = e.pageX; 
		    startY = e.pageY; 
		});
		
		$(canvasID).mousemove(function(e){
		    if (mousedown == false) {return;}
		    var zoom = viewBoxWidth / paper.width ;
		    dX = (startX - e.pageX) * zoom;
		    dY = (startY - e.pageY) * zoom;
		    paper.setViewBox(viewBox.X + dX, viewBox.Y + dY, viewBoxWidth, viewBoxHeight);
		})
		            
		$(canvasID).mouseup(function(e){
		    if ( mousedown == false ) return; 
		    viewBox.X += dX; 
		    viewBox.Y += dY; 
		    mousedown = false; 
		    
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
	    
	    var zoom = this.component.render("zoom", 50);

	    
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
    	
	    this._super(x, y);
    }
});





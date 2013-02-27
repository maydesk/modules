if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}

WebFontConfig = {
    google: { families: [ 'Cantarell', 'Knewave', 'Syncopate' ] }
};
(function() {
    var wf = document.createElement('script');
    wf.src = ('https:' == document.location.protocol ? 'https' : 'http') +
	    '://ajax.googleapis.com/ajax/libs/webfont/1/webfont.js';
	wf.type = 'text/javascript';
	wf.async = 'true';
	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(wf, s);
	//alert("loaded");
})();
 
MD.MDCanvas = Core.extend(Echo.Component, {

	$static: {
	},

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
		this._node.style.background = 'black';
		parentElement.appendChild(this._node);

		//var img = "http://static3.depositphotos.com/1001951/174/i/950/depositphotos_1746717-Graffiti-background.jpg";
		var img = "http://i.jootix.com/r/Grunge-Sky-grunge-sky-1920x1200.jpg";
		//img = "http://paper-backgrounds.com/textureimages/2012/12/blue-grunge-background-texture-hd.jpg";
		img = "http://fc07.deviantart.net/fs28/f/2008/088/d/0/Subway_Map_Wallpaper_by_Envirotechture.jpg";
		
		this._node.style.backgroundImage = "url(" + img + ")";
		this._node.style.backgroundRepeat = "no-repeat";
			
        var componentCount = this.component.getComponentCount();
		for (var i = 0; i < componentCount; i++) {   
			var child = this.component.getComponent(i);
		  	Echo.Render.renderComponentAdd(update, child, this);
	    }
	    
	    //late-loading, otherwise there would pop-up strange errors...
	   window.setTimeout(Core.method(this, this._loadCanvas), 800);
    },
    
     /** @see Echo.Render.ComponentSync#renderDisplay */
    renderDisplay: function() {
	    //resize paper when window resizes
	    if (!this._canvas) return;
	    var paper = this._canvas.paper;
    	paper.setSize(this._node.clientWidth, this._node.clientHeight);
    },
    
    _loadCanvas: function() {
   		this._canvas = new MyCanvas(this);
   		
   		var zoomable = this.component.render("zoomable", true);
   		if (!zoomable) return;
   		
		//credits to http://jsfiddle.net/9zu4U/9/
		var paper = this._canvas.paper;
		var canvas = this._canvas;
		var viewBoxWidth = paper.width;
		var viewBoxHeight = paper.height;
		var canvasID = "#" + this._node.id;
		var node = this._node;		
		var startX,startY;
		var mousedown = false;
		var dX,dY;
		var viewBox = paper.setViewBox(0, 0, viewBoxWidth, viewBoxHeight);
		viewBox.X = 0;
		viewBox.Y = 0;
		
		handleWheel("reset");
		
		/** This is high-level function.
		 * It must react to delta being more/less than zero.
		 */
		function handleWheel(delta) {
		    vBHo = viewBoxHeight;
		    vBWo = viewBoxWidth;
		    if (delta == "reset") {
			    viewBox.X = 0;
				viewBox.Y = 0;
			    viewBoxWidth *= 3;
			    viewBoxHeight *= 3;
		    } else if (delta < 0) {
			    viewBoxWidth *= 1.05;
			    viewBoxHeight *= 1.05;
			    viewBox.X -= (viewBoxWidth - vBWo) / 2;
				viewBox.Y -= (viewBoxHeight - vBHo) / 2;          
		    } else {
		    	viewBoxWidth *= 0.95;
		    	viewBoxHeight *= 0.95;
			    viewBox.X -= (viewBoxWidth - vBWo) / 2;
				viewBox.Y -= (viewBoxHeight - vBHo) / 2;          
		    }
			paper.setViewBox(viewBox.X,viewBox.Y,viewBoxWidth,viewBoxHeight);
			
			var zoom = viewBoxWidth / paper.width ;
			canvas.zoomFactor = zoom;

			var imageWidth = 1920;	//FIXME make dynamic		
		    node.style.backgroundSize = imageWidth / zoom + "px"; 
			var posX = (- viewBox.X) / zoom;
		    var posY = (- viewBox.Y) / zoom;
			node.style.backgroundPosition = posX + "px " + posY + "px";			
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
		    
		    if (isNaN(viewBox.X)) viewBox.X = 0;
		    if (isNaN(viewBox.Y)) viewBox.Y = 0;
		    paper.setViewBox(viewBox.X + dX, viewBox.Y + dY, viewBoxWidth, viewBoxHeight);
		    
		    var posX = (- viewBox.X -dX) / zoom;
		    var posY = (- viewBox.Y -dY) / zoom;
			node.style.backgroundPosition = posX + "px " + posY + "px";
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

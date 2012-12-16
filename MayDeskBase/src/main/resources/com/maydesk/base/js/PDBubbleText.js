 
if (!Core.get(window, ["PD", "Sync"])) {
        Core.set(window, ["PD", "Sync"], {});
}
 
PD.PDBubbleText = Core.extend(Echo.Component, {
	
	$load : function() {
       	Echo.ComponentFactory.registerType("PDBubbleText", this);
	},		

	componentType : "PDBubbleText"
});
 
 
PD.Sync.PDBubbleText = Core.extend(Echo.Render.ComponentSync, {
    
	$static: {
		BlinkAnimation: Core.extend(Extras.Sync.Animation, {
		
			_bubbleNode: null,	
			_counter: 0,
		
			$construct: function(bubbleNode) {
				this._bubbleNode = bubbleNode;
				this.runTime = 1200;
			},
			init: function() { 
			},
			
			step: function(progress) {			
				if (progress < 0.3) {
					this._bubbleNode.style.opacity = 0.5;					
				} else {
					this._bubbleNode.style.opacity = 1.0;					
				}

			},
			complete: function(abort) {	
				this._counter++;
				if (this._counter++ < 12 && !abort) {
					this.start();
				}
			}
		})
	},

	
    $load: function() {
        Echo.Render.registerPeer("PDBubbleText", this);
    },

    _containerElement: null,
    _node: null,
    _textNode: null,
    _text: null,
	_positionX: 0,
    _positionY: 0,
    _isMessage: false,
    _animation: null,
    _parentAvatar: null,
    	
    $construct: function() {
    },
    
    /** @see Echo.Render.ComponentSync#renderAdd */
    renderAdd: function(update, parentElement) {
        this._containerElement = parentElement;
        this._text = this.component.render("text");
		this._positionX = Echo.Sync.Extent.toPixels(this.component.render("positionX"));
		this._positionY = Echo.Sync.Extent.toPixels(this.component.render("positionY"));
        this._isMessage = this.component.render("isMessage");
        this._parentAvatar = this.component.render("avatar");
        	
		//the main node
        this._node = document.createElement("div");
		this._node.style.position = "absolute";
		//this._node.style.background = "#333333";
		this._node.style.cursor = "pointer";
		this._containerElement.appendChild(this._node);

	    //the text node        
        this._textNode = document.createElement("div");
		this._textNode.id = this.component.renderId;
		this._textNode.style.position = "absolute";
		this._textNode.style.top = "8px";
		this._textNode.style.left = "8px";
	   	this._textNode.style.textAlign = "center";
	    this._textNode.style.fontSize = "10px";
		this._textNode.style.color = "#00ffff";
		//this._textNode.style.background = "#00ddff";
		this._textNode.appendChild(document.createTextNode(this._text));
		this._node.appendChild(this._textNode);

		//timeout needed for correctly calculating clientWidth and height...	
		var that = this;
		var doActionDelayed = function() {
			//calculate width and height
			var calculatedRadius = 10;
			if (that._text.length > 60) {
				that._textNode.style.width = "200px";
				calculatedRadius = 15;
			} else if (that._text.length > 30) {
				that._textNode.style.width = "110px";
				calculatedRadius = 12;
			} else if (that._text.length > 20) {
				that._textNode.style.width = "80px";
			} else if (that._text.length > 12) {
				that._textNode.style.width = "60px";
			} else {
				that._textNode.style.width = "45px";				
			} 
			var calculatedWidth = that._textNode.clientWidth;
			that._textNode.style.width = calculatedWidth + "px";
			var calculatedHeight = that._textNode.clientHeight;
					
			//the canvas to draw the bubble
	    	that._canvas = document.createElement("canvas");
			that._node.appendChild(that._canvas);
		    var ctx = that._canvas.getContext('2d');
			that._canvas.width = calculatedWidth + 18;
			that._canvas.height = calculatedHeight + 30;
			var arrowX = that._positionX - calculatedWidth - 10 + calculatedRadius;
			var arrowY = that._positionY - calculatedHeight + 28;
			that._node.style.top = arrowY + "px";

			if (that._isMessage) {
				that._drawBubbleSimple(ctx, 6, 5, calculatedWidth + 10, calculatedHeight + 6, calculatedRadius);
				that._node.style.left = arrowX + "px";
			} else {
				that._drawBubbleCloudy(ctx, 0, 1, calculatedWidth + 16, calculatedHeight + 6 + calculatedRadius);
				that._node.style.left = "40px";
			}
			
			
		} 
		window.setTimeout(doActionDelayed, 0);

		if (this._isMessage) {
		//	this._animation = new PD.Sync.PDBubbleText.BlinkAnimation(this._node);
		//	this._animation.start();
		}
		
    	Core.Web.Event.add(this._node, "click", Core.method(this, this._processClick), true);
	},
	
	_processClick: function(echoEvent) {
		if (this._parentAvatar.acknowledge(this._isMessage)) {
			this._containerElement.removeChild(this._node);
		}		
	},

    /** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._containerElement = null;
        this._textNode = null;
        this._icon = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
	    if (this._node) {
			if (this._animation) {
				this._animation.abort();
			}
			this._node.parentNode.removeChild(this._node);
		}
        this.renderAdd(update, this._containerElement);
        return false;
    },

	_drawBubbleSimple: function(ctx, x, y, w, h, radius) {
	  	var r = x + w;
	  	var b = y + h;
	  	ctx.beginPath();
	  	ctx.strokeStyle="white";
	  	ctx.lineWidth="1";
	  
	  	//top line
	  	ctx.moveTo(x+radius, y);
	  	ctx.lineTo(r-radius, y);
	  	
	  	//top-right corner
		ctx.quadraticCurveTo(r, y, r, y+radius);
		
		//right side
	  	ctx.lineTo(r, y+h-radius);
	  	
		//bottom-right corner
	  	ctx.quadraticCurveTo(r, b, r-radius, b);
	  	
	  	//the arrow
		ctx.lineTo(r-radius, b+20);
		ctx.lineTo(r-radius-12, b);
	  	ctx.lineTo(x+radius, b);
	  	
	  	//bottom-left corner
	  	ctx.quadraticCurveTo(x, b, x, b-radius);
	  	
	  	//right side
	  	ctx.lineTo(x, y+radius);
	  	
	  	//top-left border
	  	ctx.quadraticCurveTo(x, y, x+radius, y);
	  	ctx.stroke();
	},
	
	_drawBubbleCloudy: function(ctx, x, y, w, h) {
	  	var r = x + w;
	  	var b = y + h;

	  	ctx.beginPath();
	  	ctx.strokeStyle="white";
	  	ctx.lineWidth="1";
	  	
	  	//draw beziers
	  	ctx.moveTo(x+1, y + (h/2));
	  	ctx.bezierCurveTo(x-5, y-4, r+5, y-4, r, y+(h/2));
	  	ctx.bezierCurveTo(r+5, b+4, x-5, b+4, x+1, y+(h/2));
	  	ctx.stroke();
	  	
	  	//draw small bubbles 
	  	ctx.beginPath();
	  	ctx.arc(x+10, b-2, 6, 0, 2*Math.PI);
	  	ctx.stroke();
	  	
	  	ctx.beginPath();
	  	ctx.arc(x+5, b+4, 4, 0, 2*Math.PI);
	  	ctx.stroke();

	  	ctx.beginPath();
	  	ctx.arc(x+3, b+9, 2, 0, 2*Math.PI);
	  	ctx.stroke();

	},

});

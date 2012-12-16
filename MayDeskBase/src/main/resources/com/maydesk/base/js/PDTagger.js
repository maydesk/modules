 
if (!Core.get(window, ["PD", "Sync"])) {
        Core.set(window, ["PD", "Sync"], {});
}
 
PD.PDTagger = Core.extend(Echo.Component, {
	
	$load : function() {
       	Echo.ComponentFactory.registerType("PDTagger", this);       	
	},
	
	$static: {
		taggers: new Array(),

		collectTag: function(dragEvent) {
			for (var i = 0; i < PD.PDTagger.taggers.length; i++) {
				var taggerSync = PD.PDTagger.taggers[i];
				if (!taggerSync.component) {
					//remove from list when the component has been unloaded
					Core.Arrays.remove(PD.PDTagger.taggers, taggerSync);
					continue;
				}
				if (taggerSync.checkMouseOver(dragEvent)) {
					return taggerSync.component.render("title");
				}
			}
			return null;
		}
	},

	componentType : "PDTagger"   
});
 
PD.Sync.PDTagger = Core.extend(PD.Sync.PDDesktopItem, {
            
    $load: function() {
        Echo.Render.registerPeer("PDTagger", this);
    },
    
    _containerElement: null,
    _canvas: null,
    
    
    /** @see Echo.Render.ComponentSync#renderAdd */
    renderAddImpl: function(update, parentElement) {

		PD.PDTagger.taggers.push(this);

	    this._width = 100;
    	this._height = 24;		
		this._rightAlign = true;

        this._containerElement = parentElement;

    	this._node = document.createElement("div");
		this._node.style.position = "absolute";	
		this._node.style.right = this._positionX + "px";
		this._node.style.top = this._positionY + "px";
		this._node.style.width = this._width + "px";
		this._node.style.height = this._height + "px";
		parentElement.appendChild(this._node);

		//the animation canvas
    	this._canvas = document.createElement("canvas");
		this._canvas.width = this._width;
		this._canvas.height = this._height;
		this._node.appendChild(this._canvas);
		
		this._enableRoundedCorner();
	
		//the rectangle
		var context = this._canvas.getContext('2d');
		context.strokeStyle = "rgb(234, 234, 234)";
		context.lineWidth  = 2;
  		context.roundRect(1, 1, 98, 20);
  
  		//the tag text
  		context.textBaseline = 'top';
  		context.textAlign = 'center';
		context.fillStyle = "rgb(244, 244, 244)";
		context.font = 'bold 12px sans-serif';
		context.fillText  (this._title, 50, 3);
    },


	_enableRoundedCorner: function() {
		CanvasRenderingContext2D.prototype.roundRect = 
			function(x, y, width, height, radius, fill, stroke) {
			if (typeof stroke == "undefined" ) {
				stroke = true;
			}
			if (typeof radius === "undefined") {
				radius = 8;
			}
			this.beginPath();
			this.moveTo(x + radius, y);
			this.lineTo(x + width - radius, y);
			this.quadraticCurveTo(x + width, y, x + width, y + radius);
			this.lineTo(x + width, y + height - radius);
			this.quadraticCurveTo(x + width, y + height, x + width - radius, y + height);
			this.lineTo(x + radius, y + height);
			this.quadraticCurveTo(x, y + height, x, y + height - radius);
			this.lineTo(x, y + radius);
			this.quadraticCurveTo(x, y, x + radius, y);
			this.closePath();
			if (stroke) {
				this.stroke();
			}
			if (fill) {
				this.fill();
			}        
		}	
	},
	
    /** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._containerElement = null;
        this._node = null;
        this._canvas = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
	    //if (this._node) {
		//	this._node.parentNode.removeChild(this._node);
	    //    this.renderAdd(update, this._containerElement);
		//}
        return false; // Child elements not supported: safe to return false.
    },
    
    /** @see PD.Sync.PDDesktopItem#onMouseOver */
    onMouseOver: function(mouseOver) {
    	if (mouseOver) {
	    	this._node.style.opacity = 0.4;
		} else {
	    	this._node.style.opacity = 1.0;
		}    
    }
});

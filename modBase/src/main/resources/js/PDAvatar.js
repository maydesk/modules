/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

/**
 * Component rendering peer: PDAvatar.
 */
 
if (!Core.get(window, ["PD", "Sync"])) {
        Core.set(window, ["PD", "Sync"], {});
}
 
PD.PDAvatar = Core.extend(Echo.Component, {
		$load : function() {
        	Echo.ComponentFactory.registerType("PDAvatar", this);
		},
		
		$virtual: {
			doAction: function() {
            	this.fireEvent({
            		type: "action", 
            		source: this, 
            		actionCommand: this.get("actionCommand")
            	});
			},
			onMouseUp: function() {
            	this.fireEvent({
            		type: "mouseUp", 
            		source: this, 
            		actionCommand: this.get("actionCommand")
            	});
			},
			acknowledgeMessage: function() {
            	this.fireEvent({
            		type: "acknowledgeMessage", 
            		source: this
            	});
			},
			acknowledgeStatus: function() {
            	this.fireEvent({
            		type: "acknowledgeStatus", 
            		source: this
            	});
			}		
    	},
    	
		componentType : "PDAvatar"		
});
 
 
PD.Sync.PDAvatar = Core.extend(PD.Sync.PDDesktopItem, {
    
    $load: function() {
        Echo.Render.registerPeer("PDAvatar", this);
    },

    _nodeParent: null,    
    _image: null,
	_tags: null,
	_tagContainer: null,
	_contextContainer: null,
	_bubbleNodeMessage: null,
	_bubbleNodeStatus: null,
	_color: null,
	

	$construct: function() {
   		PD.Sync.PDDesktopItem.call(this);
        this._tags = new Array();
    },
    
    /** @see Echo.Render.ComponentSync#renderAdd */
    renderAddImpl: function(update, parentElement) {
        this._containerElement = parentElement;
        var icon = this.component.render("icon");
		var bubbleMessage = this.component.render("bubbleMessage");
        var bubbleStatus = this.component.render("bubbleStatus");
        var statusImg = this.component.render("statusImg");
        var tooltip = this.component.render("tooltip");
        this._color = this.component.render("color");
	    this._height = 48;
		this._width = 48;
		
		//the main node
        this._nodeParent = document.createElement("div");
        this._nodeParent.id = this.component.renderId;
		this._nodeParent.style.position = "absolute";
		this._nodeParent.style.width = "60px";
		this._nodeParent.style.height ="60px";
		if (tooltip) {
			this._nodeParent.title = tooltip;
		}
		parentElement.appendChild(this._nodeParent);
		
		this._paintStatus(statusImg);

		//the animation canvas
    	this._node = document.createElement("canvas");
		this._node.width = 62;
		this._node.height = 62;
		this._node.style.cursor = "pointer";		
		this._nodeParent.appendChild(this._node);
		
		//the icon		
		this._image = new Image();		
		if (typeof(icon) == "object") {
 			this._image.src = icon.url;
        } else {
            this._image.src = icon;
        }
		var that = this;
   		this._image.onload = function(){
   			//wait for image to load
			var cContext = that._node.getContext('2d');
			that._drawRectangle(cContext, 1, 1, 60, 60, that._image, that._color);
		}

		//the message bubble (left)
		if (bubbleMessage) {
			this._bubbleNodeMessage = new PD.PDBubbleText();
			this._bubbleNodeMessage.parent = this.component; 
			this._bubbleNodeMessage.set("text", bubbleMessage, false);
			this._bubbleNodeMessage.set("positionX", 15, false);
			this._bubbleNodeMessage.set("positionY", -54, false);
			this._bubbleNodeMessage.set("isMessage", true);
			this._bubbleNodeMessage.set("avatar", this);
			Echo.Render.renderComponentAdd(update, this._bubbleNodeMessage, this._nodeParent);
	   	}

		//the status bubble (right, cloudy)
		if (bubbleStatus) {
			this._bubbleNodeStatus = new PD.PDBubbleText();
			this._bubbleNodeStatus.parent = this.component; 
			this._bubbleNodeStatus.set("text", bubbleStatus, false);
			this._bubbleNodeStatus.set("positionX", 150, false);
			this._bubbleNodeStatus.set("positionY", -54, false);
			this._bubbleNodeStatus.set("isMessage", false);
			this._bubbleNodeStatus.set("avatar", this);
			Echo.Render.renderComponentAdd(update, this._bubbleNodeStatus, this._nodeParent);			
	   	}
	   	
	   	//tag container
		this._tagContainer = document.createElement("div");
        this._tagContainer.style.position = "absolute";
        this._tagContainer.style.top = "0px";
		this._tagContainer.style.left = "0px";
	    this._tagContainer.style.background = "#ffff00";
	    this._tagContainer.style.opacity = 0.6;
		parentElement.appendChild(this._tagContainer);
    },
    
    acknowledge: function(isMessage) {
    	if (isMessage) {
        	this.component.acknowledgeMessage();
    	} else {
        	this.component.acknowledgeStatus();
    	}
    	return true;
    },
    
    _paintStatus: function(statusImg) {
    	var imgNode = document.createElement("img");
    	imgNode.style.position = "absolute";
    	imgNode.style.bottom = "0px";
    	imgNode.style.left = "0px";
        Echo.Sync.ImageReference.renderImg(statusImg, imgNode);
   		this._nodeParent.appendChild(imgNode);
    },

	onDoubleClick: function() {
		this.component.doAction();
	},

    
    /** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._containerElement = null;
        this._nodeParent = null;
        this._bubbleNode = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
	    if (this._nodeParent) {
			if (this._bubbleNode) {
				//this._nodeParent.removeChild(this._bubbleNode);
			}
			if (this._tagContainer) {
				this._nodeParent.parentNode.removeChild(this._tagContainer);
			}
			this._nodeParent.parentNode.removeChild(this._nodeParent);
			
	        this.renderAdd(update, this._containerElement);
	       // this._drawTags();
		}
        return false; // Child elements not supported: safe to return false.
    },
    
	/** see ... */ 
	redraw: function() {
	 	this._nodeParent.style.left = this._positionX + "px";
		this._nodeParent.style.top = this._positionY + "px";
		this._tagContainer.style.left = (this._positionX - 20) + "px";
		this._tagContainer.style.top = (this._positionY + 48) + "px";
	},
	    
    /** @see PD.Sync.PDDesktopItem#onMouseOver */
    onMouseOver: function(mouseOver) {
    	if (mouseOver) {
	    	this._nodeParent.style.opacity = 0.4;
		} else {
	    	this._nodeParent.style.opacity = 1.0;
		}    
    },
    
    _drawRectangle: function(ctx, x, y, w, h, image, color) {
        var radius = 17;
	  	var r = x + w;
	  	var b = y + h;
	  	ctx.beginPath();
	  	ctx.fillStyle = color; 
	  	
	  	ctx.lineWidth="6";
	  
	  	ctx.moveTo(x+radius, y);
	  	ctx.lineTo(r-radius, y);
		ctx.quadraticCurveTo(r, y, r, y+radius);
	  	ctx.lineTo(r, y+h-radius);
	  	ctx.quadraticCurveTo(r, b, r-radius, b);
	  	ctx.lineTo(x+radius, b);
	  	ctx.quadraticCurveTo(x, b, x, b-radius);
	  	ctx.lineTo(x, y+radius);
	  	ctx.quadraticCurveTo(x, y, x+radius, y);

		ctx.clip();
		ctx.drawImage(image, 0, 0);

	  	ctx.strokeStyle= "rgba(190, 190, 190, 0.7)";
	  	ctx.stroke();
	  	var colorInt = parseInt(color.substring(1), 16);
	  	var red = Math.floor(colorInt / 0x10000);
        var green = Math.floor(colorInt / 0x100) % 0x100;
        var blue = colorInt % 0x100;
		var transparentColor = "rgba(" + red + ", " + green + ", " + blue + ", 0.2)";
		ctx.fillStyle = transparentColor;			  	  
	  	ctx.fill();
		//this._addShine(ctx, w, h, radius, 0.2, true);
	},  
	
	
	_addGradient: function(ctx,x,y,w,h,color,opacity) {
		var tmp = ctx.createLinearGradient(x,y,w,h);
		var val = (color>0?0.25:0.2);
		tmp.addColorStop(0,'rgba('+color+','+color+','+color+',0.9)');
		tmp.addColorStop(val,'rgba('+color+','+color+','+color+','+opacity+')');
		tmp.addColorStop(0.75,'rgba('+color+','+color+','+color+',0)');
		tmp.addColorStop(1,'rgba('+color+','+color+','+color+',0)');
		return tmp;
	}
	
});

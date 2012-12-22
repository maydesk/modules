/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

/**
 * Component rendering peer: PDShortcut.
 */
 
if (!Core.get(window, ["PD", "Sync"])) {
        Core.set(window, ["PD", "Sync"], {});
}
 
PD.PDShortcut = Core.extend(Echo.Component, {
		$load : function() {
        	Echo.ComponentFactory.registerType("PDShortcut", this);
		},
		
		$static: {
			shortcuts: new Array(),
	
			check: function(sourceSync, dragEvent) {
				for (var i = 0; i < PD.PDShortcut.shortcuts.length; i++) {
					var shortcutSync = PD.PDShortcut.shortcuts[i];
					if (shortcutSync.component == null | shortcutSync._node == null) {
						//remove from list when the component has been unloaded
						Core.Arrays.remove(PD.PDShortcut.shortcuts, shortcutSync);
						continue;
					}
					if (sourceSync === shortcutSync) {
						continue;
					}
					if (shortcutSync.checkMouseOver(dragEvent)) {
						return shortcutSync;
					}
				}
				return null;
			}
		},
		
		componentType : "PDShortcut",
		
	    $virtual: {
			doAction: function() {
            	this.fireEvent({
            		type: "action", 
            		source: this, 
            		actionCommand: this.get("actionCommand")
            	});
			},
			onMouseUp: function(actionType) {
            	this.fireEvent({
            		type: "mouseUp", 
            		source: this, 
            		actionCommand: this.get("actionCommand")
            	});
			}
    	}
});
 
 
PD.Sync.PDShortcut = Core.extend(PD.Sync.PDDesktopItem, {
    
    	$static: {
			StoringAnimation: Core.extend(Extras.Sync.Animation, {
				_shortcut2: null,
							
				$construct: function(shortcut) {
					this._shortcut2 = shortcut;
					this.runTime = 2500;
				},
					 
				init: function() { 
					this._shortcut2._node.style.zIndex = 999;
					var cContext = this._shortcut2._canvas.getContext('2d');
					cContext.translate(30, 30);     // Ursprung verschieben
				}, 
				step: function(progress) {
					var cContext = this._shortcut2._canvas.getContext('2d');
					cContext.clearRect(-30, -30, 60, 60); // Rechteck löschen
					cContext.rotate(progress * Math.PI / 50);				
		   			var posX = this._shortcut2._image.width / 2;
					cContext.drawImage(this._shortcut2._image, posX, (20 * progress)-30, 40 * (1 - progress), 40 * (1 - progress));
				},			
				complete: function(abort) {
					this._shortcut2._node.parentNode.removeChild(this._shortcut2._tagContainer);
					this._shortcut2._node.parentNode.removeChild(this._shortcut2._node);
					this._shortcut2._containerElement = null;
			        this._shortcut2._tagContainer = null;
			        this._shortcut2._node = null;
				},
			}
		),
		
		PortAnimation: Core.extend(Extras.Sync.Animation, {
			_shortcut2: null,
						
			$construct: function(shortcut) {
				this._shortcut2 = shortcut;
				this.runTime = 2500;
			},
				 
			init: function() { 
				this._shortcut2._node.style.zIndex = 999;
			}, 
			step: function(progress) {
				var cContext = this._shortcut2._canvas.getContext('2d');
				//cContext.scale(1 - progress, 1 - progress);
				cContext.clearRect(0, 0, 60, 60); // Rechteck löschen
				cContext.drawImage(this._shortcut2._image, 30 -(16 * progress), 0, 32 * (1 - progress), 32 * (1 - progress));				
				cContext.fillText(this._title, 30, 40 - (35 * progress), 32 * (1 - progress), 32 * (1 - progress));
				
			},			
			complete: function(abort) {
				this._shortcut2._node.parentNode.removeChild(this._shortcut2._tagContainer);
				this._shortcut2._node.parentNode.removeChild(this._shortcut2._node);
				this._shortcut2._containerElement = null;
		        this._shortcut2._tagContainer = null;
		        this._shortcut2._node = null;
			},
		})
		
	},
    
    $load: function() {
        Echo.Render.registerPeer("PDShortcut", this);
    },

    _icon: null,
    _canvas: null,    
    _image: null,
	_tags: null,
	_tagContainer: null,
	_textNode: null,

	$construct: function() {
   		PD.Sync.PDDesktopItem.call(this);
        this._tags = new Array();
    },
    
    /** @see Echo.Render.ComponentSync#renderAdd */
    renderAddImpl: function(update, parentElement) {
    
    	PD.PDShortcut.shortcuts.push(this);
    
        this._containerElement = parentElement;
        var icon = this.component.render("icon");
	    this._height = 48;
		this._width = 48;
		
		//the main node
        this._node = document.createElement("div");
        this._node.id = this.component.renderId;
		this._node.style.position = "absolute";
		this._node.style.width = "60px";
		this._node.style.height ="60px";
		this._node.style.cursor = "pointer";
		parentElement.appendChild(this._node);

		//the text node
        this._textNode = document.createElement("div");
		this._textNode.style.position = "absolute";
		this._textNode.style.width = "120px";
		this._textNode.style.top = "45px";
		this._textNode.style.left = "-35px";
		this._textNode.style.fontSize = "13px";
		this._textNode.style.fontFamily = "Verdana";
		this._textNode.style.fontWeight = "900";
		this._textNode.style.color = "#00ffff";
		this._textNode.style.textAlign = "center";
		this._textNode.style.opacity = 0.72;		
		this._textNode.appendChild(document.createTextNode(this._title));
		this._node.appendChild(this._textNode);

		//the animation canvas
    	this._canvas = document.createElement("canvas");
		this._canvas.width = 120;
		this._canvas.height = 62;
		this._node.appendChild(this._canvas);
		
		var ctx = this._canvas.getContext('2d');
		this._drawRectangle(ctx, 1, 1, 45, 39);
		
		//the icon		
		this._image = new Image();
		this._image.src = icon;
		var that = this;
   		this._image.onload = function(){
   			//wait for image to load
   			var posX = 24 - (that._image.width / 2);
			var cContext = that._canvas.getContext('2d');
			cContext.drawImage(that._image, posX, 7);
		}
	   	
		this._tagContainer = document.createElement("div");
        this._tagContainer.style.position = "absolute";
        this._tagContainer.style.top = "0px";
		this._tagContainer.style.left = "0px";
	    this._tagContainer.style.background = "#ffff00";
	    this._tagContainer.style.opacity = 0.6;
		parentElement.appendChild(this._tagContainer);
    },

	onDoubleClick: function() {
		this.component.doAction();
	},

    
    /** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._containerElement = null;
        this._node = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
	    if (this._node) {
			this._node.parentNode.removeChild(this._tagContainer);
			this._node.parentNode.removeChild(this._node);
			
	        this.renderAdd(update, this._containerElement);
	        //this._drawTags();
		}
        return false; // Child elements not supported: safe to return false.
    },
    
	/** see ... */ 
	redraw: function() {
	 	this._node.style.left = this._positionX + "px";
		this._node.style.top = this._positionY + "px";
		this._tagContainer.style.left = (this._positionX - 20) + "px";
		this._tagContainer.style.top = (this._positionY + 48) + "px";
	},
	    
    /** @see PD.Sync.PDDesktopItem#onMouseOver */
    onMouseOver: function(mouseOver) {
    	if (mouseOver) {
	//    	this._textNode.style.opacity = 0.4;
		} else {
	 //   	this._textNode.style.opacity = 1.0;
		}    
    },
    
    _drawRectangle: function(ctx, x, y, w, h) {
        var radius = 10;
	  	var r = x + w;
	  	var b = y + h;
	  	ctx.beginPath();
	  	ctx.strokeStyle= "rgba(190, 190, 250, 0.7)"; //"white";
	  	ctx.fillStyle = "rgba(150, 250, 250, 0.4)";
	  	ctx.lineWidth="4";
	  
	  	ctx.moveTo(x+radius, y);
	  	ctx.lineTo(r-radius, y);
		ctx.quadraticCurveTo(r, y, r, y+radius);
	  	ctx.lineTo(r, y+h-radius);
	  	ctx.quadraticCurveTo(r, b, r-radius, b);
	  	ctx.lineTo(x+radius, b);
	  	ctx.quadraticCurveTo(x, b, x, b-radius);
	  	ctx.lineTo(x, y+radius);
	  	ctx.quadraticCurveTo(x, y, x+radius, y);
	  	ctx.stroke();
	  	ctx.fill();

	},  
});

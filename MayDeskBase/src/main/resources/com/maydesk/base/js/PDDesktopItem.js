/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

if (!Core.get(window, ["PD", "Sync"])) {
        Core.set(window, ["PD", "Sync"], {});
}

 
PD.Sync.PDDesktopItem = Core.extend(Echo.Render.ComponentSync, {	

 	$abstract: true,
 
	$load : function() {
       	Echo.Render.registerPeer("PDDesktopItem", this);       	
	},
	
	_processMouseMoveRef: null,
    _processMouseUpRef: null,
	_title: "Taggo",
	_positionX: 0,
    _positionY: 0,
    _width: 100,
    _height: 20,
    _rightAlign: false,
	_topOffset: 25,  //height of the menu bar?
	_node: null,
    _dragOriginX: null,
    _dragOriginY: null,
    _dragInitPositionX: null,
    _dragInitPositionY: null,
	_firstClickTime: null,
	_MOUSEDOWN: null,
	_MOUSEMOVE: null,
    _MOUSEUP: null,
    
	$construct: function() {
        this._processMouseMoveRef = Core.method(this, this._processMouseMove);
        this._processMouseUpRef = Core.method(this, this._processMouseUp);
        var isIPad = navigator.userAgent.match(/iPad/i) != null;
        this._MOUSEDOWN = isIPad ? 'touchstart' : 'mousedown';
        this._MOUSEMOVE = isIPad ? 'touchmove' : 'mousemove';
        this._MOUSEUP = isIPad ? 'touchend' : 'mouseup';
    },
	
	$abstract: {
		onMouseOver: function(mouseOver) { },		
		renderAddImpl: function (update, parentElement) { }
	},

	$virtual: {
		redraw: function () { },
		onDoubleClick: function () { }
	},
		
	checkMouseOver: function(dragEvent) {
		this.onMouseOver(false);
		
		var posX = this._positionX;
		if (this._rightAlign) {
			posX = window.innerWidth - posX - this._width;
		}
		var posY = this._positionY + this._topOffset; 
		
		if (dragEvent.clientX < posX) return false;
		if (dragEvent.clientX > posX + this._width) return false;
		if (dragEvent.clientY < posY) return false;
		if (dragEvent.clientY > posY + this._height) return false;
		this.onMouseOver(true);
		return true;
	},

	/** @see Echo.Render.ComponentSync#renderAdd */
    renderAdd: function(update, parentElement) {
		
		this._title = this.component.render("title");
		this._positionX = Echo.Sync.Extent.toPixels(this.component.render("positionX"));
		this._positionY = Echo.Sync.Extent.toPixels(this.component.render("positionY"));
		
		this.renderAddImpl(update, parentElement);
		
		this.redraw();
		//event listeners
		Core.Web.Event.add(this._node, this._MOUSEDOWN, Core.method(this, this._processMouseDown), true);
    	Core.Web.Event.add(this._node, "click", Core.method(this, this._processClick), true);	
    },
	
	_processClick: function(echoEvent) {
        if (!this.client || !this.client.verifyInput(this.component)) {
            return true;
        }
	    var d = new Date();
	   	var now = d.getTime();
	    if ((now - this._firstClickTime) < 450) {  //450ms timeout
	        //double click	
			this.onDoubleClick();  
	    } else {
	       //single click
			this._firstClickTime = d.getTime();
		}	
	},

	
    _processMouseDown: function(echoEvent) {
        if (!this.client || !this.client.verifyInput(this.component)) {
            return true;
        }
		var x;
		var y;
		if (echoEvent.touches) {
			x = echoEvent.touches[0].pageX;
			y = echoEvent.touches[0].pageY;
		} else {
			x = echoEvent.clientX;
			y = echoEvent.clientY;
		}        
	    this._dragInitPositionX = this._positionX ;
	    this._dragInitPositionY = this._positionY ;
	    this._dragOriginX = x; 
	    this._dragOriginY = y; 
	    
        // Prevent selections.
        Core.Web.dragInProgress = true;
        Core.Web.DOM.preventEventDefault(echoEvent);
		this._node.style.zIndex = 999;
        Core.Web.Event.add(document.body, this._MOUSEMOVE, this._processMouseMoveRef, true);
        Core.Web.Event.add(document.body, this._MOUSEUP, this._processMouseUpRef, true);
	},

	_processMouseMove: function(e) {
		var x;
        var y;
		if (echoEvent.touches) {
			x = echoEvent.touches[0].pageX;
			y = echoEvent.touches[0].pageY;
			if (echoEvent.touches.length > 1) {
				//multi-touch, resize as well
			    var w = echoEvent.touches[1].pageX - x;
		        var h = echoEvent.touches[1].pageY - y;
		        this._node.style.width = w + "px";
		        this._node.style.height = h + "px";
		    }
		} else {
			x = echoEvent.clientX;
			y = echoEvent.clientY;
		}
	
		var posX = this._dragInitPositionX + x - this._dragOriginX;
		var posY = this._dragInitPositionY + y - this._dragOriginY;
		this._positionX = posX;
		this._positionY = posY;		
	    this.redraw();
	    
	    //dim down over the recycle box or a port
		if (posX < 50 & posY < 50) {
			this._node.style.opacity = 0.4;
		} else {
			this._node.style.opacity = 1.0;
		}	
		//PD.PDShortcut.check(this, e);		
	},

	_processMouseUp: function(e) {
		Core.Web.dragInProgress = false;
        Core.Web.Event.remove(document.body, this._MOUSEMOVE, this._processMouseMoveRef, true);
        Core.Web.Event.remove(document.body, this._MOUSEUP, this._processMouseUpRef, true);
        this.component.set("positionX", this._positionX);
        this.component.set("positionY", this._positionY);
		this._node.style.zIndex = 0;
		this._checkSendToOtherShortcut(e);
		
		if (this.component.onMouseUp) {
			//moved? (otherwise might kill double-click event)
			if ((Math.abs(this._positionX - this._dragInitPositionX) > 8) &&
				 (Math.abs(this._positionY - this._dragInitPositionY) > 8)) {
				this.component.onMouseUp();
			}
		}		
	},

	_checkSendToOtherShortcut: function(e) {
//		var otherShortcut = PD.PDShortcut.check(this, e);
//		if (otherShortcut) {
//		    otherShortcut.onMouseOver(false);		
//			var storingAnimation = new PD.Sync.PDShortcut.StoringAnimation(this);
//		 	storingAnimation.start();  
//		}
	},
});
 
 

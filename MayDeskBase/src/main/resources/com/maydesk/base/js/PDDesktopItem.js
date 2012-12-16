 
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
		
	$construct: function() {
        this._processMouseMoveRef = Core.method(this, this._processMouseMove);
        this._processMouseUpRef = Core.method(this, this._processMouseUp);
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
		Core.Web.Event.add(this._node, "mousedown", Core.method(this, this._processMouseDown), true);
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
	    this._dragInitPositionX = this._positionX ;
	    this._dragInitPositionY = this._positionY ;
	    this._dragOriginX = echoEvent.clientX;
	    this._dragOriginY = echoEvent.clientY;
	    
        // Prevent selections.
        Core.Web.dragInProgress = true;
        Core.Web.DOM.preventEventDefault(echoEvent);
		this._node.style.zIndex = 999;
        Core.Web.Event.add(document.body, "mousemove", this._processMouseMoveRef, true);
        Core.Web.Event.add(document.body, "mouseup", this._processMouseUpRef, true);
	},

	_processMouseMove: function(e) {
		var posX = this._dragInitPositionX + e.clientX - this._dragOriginX;
		var posY = this._dragInitPositionY + e.clientY - this._dragOriginY;
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
        Core.Web.Event.remove(document.body, "mousemove", this._processMouseMoveRef, true);
        Core.Web.Event.remove(document.body, "mouseup", this._processMouseUpRef, true);
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
 
 

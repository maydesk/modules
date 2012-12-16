if (!Core.get(window, ["PD", "Sync"])) {
	Core.set(window, ["PD", "Sync"], {});
}

PD.PDContextMenu = Core.extend(Echo.Component, {
	$load : function() {
		Echo.ComponentFactory.registerType("PDContextMenu", this);
	},
	componentType : "PDContextMenu",
	
	_colMenu: null,
	_childComponent: null
});

PD.Sync.PDContextMenu = Core.extend(Echo.Render.ComponentSync, {	

	_processMouseMoveRef: null,
    _processMouseUpRef: null,
    _processMouseOutRef: null,
	_mainDiv: null,
	_componentDiv: null,
	_colMenuDiv: null,
	_hideDelay: null,
	
	$load : function() {
       	Echo.Render.registerPeer("PDContextMenu", this);       	
	},
		
	$construct: function() {
		this._processMouseMoveRef = Core.method(this, this._processMouseMove);
        this._processMouseUpRef = Core.method(this, this._processMouseUp);
        this._processMouseOutRef = Core.method(this, this._processMouseOut);
        this._mouseOutEvent = Core.Web.Env.PROPRIETARY_EVENT_MOUSE_ENTER_LEAVE_SUPPORTED ? "mouseleave" : "mouseout";
    },
			
	/** @see Echo.Render.ComponentSync#renderAdd */
    renderAdd: function(update, parentElement) {
    	// Creating main div
        this._mainDiv = document.createElement("div");
        parentElement.appendChild(this._mainDiv);
        this._mainDiv.id = this.component.renderId;
        //this._mainDiv.id = "mainDiv";
        this._mainDiv.tabIndex = "-1";
        this._mainDiv.style.outlineStyle = "none";

        // Component main div
        this.component._childComponent = this.component.getComponent(1);
        this._componentDiv = document.createElement("div");
        this._componentDiv.id = "menuComp." + this.component._childComponent.renderId;
        //this._componentDiv.id = "childComp";
        this._componentDiv.tabIndex = "-1";
        this._componentDiv.style.outlineStyle = "none";
    	
    	Echo.Render.renderComponentAdd(update, this.component._childComponent, this._componentDiv);
    	this._mainDiv.appendChild(this._componentDiv);
    	
    	this.component._colMenu = this.component.getComponent(0);
    	
    	// Div for the context menu
		this._colMenuDiv = document.createElement("div");
		this._colMenuDiv.id = "menu." + this.component.renderId;
		//this._colMenuDiv.id = "menuDiv";
		this._colMenuDiv.tabIndex = "-1";
		this._colMenuDiv.style.position = "absolute";
		this._colMenuDiv.style.opacity = 0.7;
		
    	Echo.Render.renderComponentAdd(update, this.component._colMenu, this._colMenuDiv);
		
    	var mouseOverEvent = Core.Web.Env.PROPRIETARY_EVENT_MOUSE_ENTER_LEAVE_SUPPORTED ? "mouseenter" : "mouseover"; 
    	
    	// event listeners
    	Core.Web.Event.add(this._mainDiv, "mousedown", Core.method(this, this._processMouseDown), true);
    	Core.Web.Event.add(this._mainDiv, mouseOverEvent, Core.method(this, this._processMouseOver), true);
    },
    
    // Setting up listeners in case child allows dragging
    // Must return true so that the event keeps bubbling up
    _processMouseDown: function(echoEvent) {
    	Core.Web.Event.add(document.body, "mousemove", this._processMouseMoveRef, true);
        Core.Web.Event.add(document.body, "mouseup", this._processMouseUpRef, true);
        return true;
    },
    
    // If dragging update the menu position
    // Must return true so that the event keeps bubbling up
    _processMouseMove: function(echoEvent) {
    	if (!Core.Web.dragInProgress) {
    		return;
    	}
    	
    	this._positionMenuDiv();
		return true;
    },
    
    // Removing listeners used for dragging
    // Must return true so that the event keeps bubbling up
    _processMouseUp: function(echoEvent) {
    	Core.Web.Event.remove(document.body, "mousemove", this._processMouseMoveRef, true);
        Core.Web.Event.remove(document.body, "mouseup", this._processMouseUpRef, true);
        return true;
    },
    
    // Since this the child component can have many children, mouseOver and mouseOut events
    // need to be pre-processed to know if we are still inside this component.
    // Returns true if we are in fact entering/leaving the main component
    _isValidMouseEvent: function(echoEvent, parent) {
    	var elem = echoEvent.relatedTarget || echoEvent.toElement || echoEvent.fromElement;
	
    	while (elem && elem !== parent) {
    		elem = elem.parentNode;
    	}

    	return elem !== parent;
    },
    
    _processMouseOver: function(echoEvent) {
    	if (!this.client || !this.client.verifyInput(this.component)) {
    		return true;
    	}	    

    	if (this._isValidMouseEvent(echoEvent, this._mainDiv)) {
    		this._setMenuVisible(true);
    	}
    	
    	return true;
    },

    // We must add the listener to the body because there are cases where we might be out
    // of the component and not have the listener fire (eg: A window appearing right on top).
    // We remove the listener for efficiency. 
    _processMouseOut: function(echoEvent) {
    	if (this._isValidMouseEvent(echoEvent, this._mainDiv)) {
    		Core.Web.Event.remove(document.body, this._mouseOutEvent, this._processMouseOutRef, true);
    		this._setMenuVisible(false);
    	}
    	return true;
    },
    
	_setMenuVisible: function(menuVisible) {
		if (menuVisible) {
			// Stop hiding the menu because the cursor got back in.
			clearTimeout(this._hideDelay);
			
			// Adding the listener to the body so we can hide the menu
			Core.Web.Event.add(document.body, this._mouseOutEvent, this._processMouseOutRef, true);
			if (this._colMenuDiv.parentNode !== this._mainDiv) {
				this._positionMenuDiv();
				this._mainDiv.appendChild(this._colMenuDiv);
			}
		} else if (this._colMenuDiv.parentNode === this._mainDiv) {
			// 1 second delay to hide the menu, saving reference in case we need to stop it
			this._hideDelay = setTimeout(this._hideMenu(this), 500);
		}
	},
	
	_hideMenu: function(pdContextMenu) {
		return function() {
			pdContextMenu._mainDiv.removeChild(pdContextMenu._colMenuDiv);
	    }
	},
	
	_positionMenuDiv: function() {
		var childComp = document.getElementById(this.component._childComponent.renderId);
		var bounds = new Core.Web.Measure.Bounds(childComp);
		
		this._colMenuDiv.style.left = (bounds.left + bounds.width + 4) + "px";
		
		// For some reason menus (like the one in MayDesk) add to the top of the 'bounds'
		// variable, resulting in the menu being displayed lower, using the style directly
		this._colMenuDiv.style.top = childComp.style.top;
	},
	
    renderDispose: function(update) {
    	Core.Web.Event.removeAll(this._mainDiv);
    	this._mainDiv = null;
    	
    	this._componentDiv = null;
    	this.component._childComponent = null;
		this.component._colMenu = null;
		this._colMenuDiv = null;
    },

    renderUpdate: function(update) {
    	Echo.Render.renderComponentDispose(update, update.parent);

    	var element = this._mainDiv;
		var containerElement = element.parentNode;
		containerElement.removeChild(element);
		this.renderAdd(update, containerElement);
		return true;
    }
});
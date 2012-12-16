/**
 * Component rendering peer: PDContext.
 */
 
if (!Core.get(window, ["PD", "Sync"])) {
        Core.set(window, ["PD", "Sync"], {});
}
 
PD.PDContext = Core.extend(Echo.Component, {

		$load : function() {
        	Echo.ComponentFactory.registerType("PDContext", this);
		},
		
		componentType : "PDContext",
		
	    $virtual: {
			doMouseUp: function(actionType) {
            	this.fireEvent({
            		type: "mouseUp", 
            		source: this, 
            		actionCommand: this.get("actionCommand")
            	});
			}
    	}
});
 
 
PD.Sync.PDContext = Core.extend(PD.Sync.PDDesktopItem, {

	$static: {
		ExpandAnimation: Core.extend(Extras.Sync.Animation, {
		
			_context2: null,						
		
			$construct: function(context) {
				this._context2 = context;
				this.runTime = 1600;
			},
			init: function() { 
				this._context2._container.style.visibility = "visible";
				this._context2._expanded = !this._context2._expanded;
				if (this._context2._expanded) {
					this._context2._container.style.width = "0px";
					this._context2._container.style.height = "20px";				
					this._context2._titleBar.style.left = "2px";
				}				
			},
			
			step: function(progress) {			
				var height = this._context2._height - 20;
				var width = this._context2._width;
				
				var p1 = progress * 2;
				var p2 = p1 - 1;
				if (this._context2._expanded) {
					//increasing
					if (p1 < 1) {
						width = width * p1;
						height = 20;
					} else {
						height = 20 + height * p2;
					}								
				} else {
					//decreasing
					if (p1 < 1) {
						height = 20 + height * (1 - p1);
					} else {
						width = width * (1 - p2);
						height = 20;
					}								
				} 
				this._context2._container.style.width = width + "px";
				this._context2._container.style.height = height + "px";
				
			},
			complete: function(abort) {			
				if (this._context2._expanded) {
					this._context2._container.style.visibility = "visible";
				} else {
					this._context2._container.style.visibility = "hidden";
					this._context2._titleBar.style.left = "-32px";
				}				
			}
		})
	},
    
    $load: function() {
        Echo.Render.registerPeer("PDContext", this);
    },
    
    _container: null,
    _icon: null,
	_expanded: true,
	_titleBar: null,
	
    $construct: function() {
        this._processMouseMoveRef = Core.method(this, this._processMouseMove);
        this._processMouseUpRef = Core.method(this, this._processMouseUp);
    },
    
    /** @see Echo.Render.ComponentSync#renderAdd */
    renderAddImpl: function(update, parentElement) {
        this._containerElement = parentElement;
        var icon = this.component.render("icon");
	   	this._width = Echo.Sync.Extent.toPixels(this.component.render("width")); 
		this._height = Echo.Sync.Extent.toPixels(this.component.render("height"));

		//the main node
        this._mainNode = document.createElement("div");
        this._mainNode.id = this.component.renderId;
		this._mainNode.style.position = "absolute";	
		this._mainNode.style.width = "48px";
		this._mainNode.style.height ="60px";
		this._mainNode.style.zIndex = 0;
		this._mainNode.style.cursor = "pointer";
		parentElement.appendChild(this._mainNode);
		
		//the icon
		this._node = document.createElement("img");
        Echo.Sync.ImageReference.renderImg(icon, this._node);
   		this._mainNode.appendChild(this._node);

		//the title text
	    this._titleBar = document.createElement("div");
	   	this._titleBar.style.position = "absolute";
	    this._titleBar.style.top = "22px";
		this._titleBar.style.left = "2px";
	    this._titleBar.style.height = "20px";
		this._titleBar.style.width = this._width + "px";
	    this._titleBar.style.fontSize = "9px";
	    this._titleBar.style.color = "#00ffff";
	    this._titleBar.appendChild(document.createTextNode(this._title));
	    this._mainNode.appendChild(this._titleBar);

		//the container
        this._container = document.createElement("div");
		this._container.style.position = "absolute";
       	this._container.style.left = "0px";
		this._container.style.top = "19px";
		this._container.style.overflow = "hidden";
		this._container.style.width = this._width + "px";
		this._container.style.height = this._height + "px";
		Echo.Sync.Border.render("1px dotted #eeeeee", this._container);
		this._mainNode.appendChild(this._container);

		//add child to container
		var componentCount = this.component.getComponentCount();
        if (componentCount == 1) {
            Echo.Render.renderComponentAdd(update, this.component.getComponent(0), this._container);
        } else if (componentCount > 1) {
            throw new Error("Too many children: " + componentCount);
        }
    },

	onDoubleClick: function() {
		var expander = new PD.Sync.PDContext.ExpandAnimation(this);
		expander.start();  
	},
    
    /** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._containerElement = null;
        this._mainNode = null;
        this._node = null;
        this._container = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
        return true;
    },
    
    /** see ... */
	redraw: function() {
	 	this._mainNode.style.left = this._positionX + "px";
		this._mainNode.style.top = this._positionY + "px";
	}, 
	
	/** @see PD.Sync.PDDesktopItem#onMouseOver */
    onMouseOver: function(mouseOver) {
		//not implemented
	}   
});

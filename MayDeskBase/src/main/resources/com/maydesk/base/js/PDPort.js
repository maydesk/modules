 
if (!Core.get(window, ["PD", "Sync"])) {
        Core.set(window, ["PD", "Sync"], {});
}
 
PD.PDPort = Core.extend(Echo.Component, {	
	$load : function() {
       	Echo.ComponentFactory.registerType("PDPort", this);       	
	},
	componentType : "PDPort",
	
	$static: {
		ports: new Array(),
		
		check: function(dragEvent) {
			for (var i = 0; i < PD.PDPort.ports.length; i++) {
				var portSync = PD.PDPort.ports[i];
				if (!portSync.component) {
					//remove from list when the component has been unloaded
					Core.Arrays.remove(PD.PDPort.ports, portSync);
					continue;
				}
				if (portSync.checkMouseOver(dragEvent)) {
					return portSync.component.render("title");
				}				
			}
			return null;
		}
	},
});
 
 
PD.Sync.PDPort = Core.extend(PD.Sync.PDDesktopItem, {
            
    $load: function() {
        Echo.Render.registerPeer("PDPort", this);
    },
    
    _containerElement: null,
	_imgNormal: null,
	_imgSelected: null,
	
    
    /** @see Echo.Render.ComponentSync#renderAdd */
    renderAddImpl: function(update, parentElement) {

		PD.PDPort.ports.push(this);
	
		this._imgNormal = this.client.getResourceUrl("PDPort", "timeline/highlight1.png");
		this._imgSelected = this.client.getResourceUrl("PDPort", "timeline/highlight2.png");
    	this._positionY = 0;
        this._containerElement = parentElement;

        this._node = document.createElement("div");
        this._node.id = this.component.renderId;
		this._node.style.position = "absolute";
       	this._node.style.left = this._positionX + "px";
		this._node.style.top = this._positionY + "px";
		this._node.style.width = "80px";
		this._node.style.height ="20px";
		this._node.style.cursor = "pointer";
		this._node.style.backgroundImage = "url(" + this._imgNormal + ")";
		this._node.style.backgroundRepeat = "repeat";
	   	this._node.style.textAlign = "center";
	    this._node.style.fontSize = "9px";
	    this._node.style.color = "#00ffff";
		this._node.appendChild(document.createTextNode(this._title));
		parentElement.appendChild(this._node);

    },
	
    /** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._containerElement = null;
        this._node = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
	    if (this._node) {
			this._node.parentNode.removeChild(this._node);
		}
        this.renderAdd(update, this._containerElement);
        return false; 
    },

   /** @see PD.Sync.PDDesktopItem#onMouseOver */
    onMouseOver: function(mouseOver) {
		if (mouseOver) {
			this._node.style.backgroundImage = "url(" + this._imgSelected + ")";
		} else {
			this._node.style.backgroundImage = "url(" + this._imgNormal + ")";
		}		
	}	    
});

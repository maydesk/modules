if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDCanvasCommandbar = Core.extend(Echo.Component, {

	$load : function() {
       	Echo.ComponentFactory.registerType("MDCanvasCommandbar", this);
	},
	
	componentType: "MDCanvasCommandbar",

	_lblZoom: null,
	
	$construct: function(canvas) {
		Echo.Component.call(this);  //call super constructor
		
		var row = new Echo.Row();
		row.set("insets", "3px");
		row.set("cellSpacing", "6px");		
		this.add(row);

		var that = this;
		var btnZoomDecrease = new Echo.Button();
		var imgLess = "/main?sid=Echo.Resource&pkg=MDCanvasCommandbar&res=editor/minus2.png"; //this.application.client.getResourceUrl("MDArrow", "editor/minus2.png");
		if (!imgLess) imgLess = "img/minus2.png";  //for local testing...
		btnZoomDecrease.set("icon", imgLess);
		btnZoomDecrease.addListener("action", function() {
			var zoomFactor = canvas.render("zoom", 50);
			zoomFactor = Math.round(zoomFactor * 0.8);
			canvas.set("zoom", zoomFactor);
			that._lblZoom.set("text", zoomFactor + "%"); 
       	});		
		row.add(btnZoomDecrease);					

		this._lblZoom = new Echo.Label({text: "Zoom:", foreground: "#fff"});
		row.add(this._lblZoom);

		var btnZoomIncrease = new Echo.Button();
		var imgPlus = "/main?sid=Echo.Resource&pkg=MDCanvasCommandbar&res=editor/plus2.png";  //XXX this.peer.client.getResourceUrl("MDArrow", "editor/plus2.png");
		if (!imgPlus) imgPlus = "img/plus2.png";  //for local testing...
		btnZoomIncrease.set("icon", imgPlus);
		btnZoomIncrease.addListener("action", function() {
			var zoomFactor = canvas.render("zoom", 50);
			zoomFactor = Math.round(zoomFactor / 0.8);
			canvas.set("zoom", zoomFactor);
			that._lblZoom.set("text", zoomFactor + "%"); 
       	});		
		row.add(btnZoomIncrease);
    },	
});

 
MD.Sync.MDCanvasCommandbar = Core.extend(Echo.Render.ComponentSync, {
    
    $load: function() {
        Echo.Render.registerPeer("MDCanvasCommandbar", this);
    },
    
    _node: null,
    
    renderAdd: function(update, parentElement) {
        this._node = document.createElement("div");
		this._node.style.position = "absolute";
       	this._node.style.left = "100px";
		this._node.style.top = "-7px";
		this._node.style.width = "100px";
		this._node.style.height = "22px";
		this._node.style.background = "#222";
		this._node.style["-webkit-transition"] = "opacity 2s" 
		Echo.Sync.Border.render("1px dotted #eeeeee", this._node);
		parentElement.appendChild(this._node);

		for (var i = 0; i < this.component.getComponentCount(); i++) {   
			Echo.Render.renderComponentAdd(update, this.component.getComponent(i), this._node);
		}
    },
    
	/** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._node = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
    	var visible = this.component.render("visible", true);
    	this._node.style.opacity = visible ? 1 : 0; 
        return false; // only update Child elements
    }       
});

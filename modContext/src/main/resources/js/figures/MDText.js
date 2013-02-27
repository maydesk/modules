if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDText = Core.extend(MD.MDAbstractFigure, {

	$static: {
		STYLES: [
			{text:"plain", color:"#ffffff", border:1}, 
			{text:"h1", color:"#ddffff", border:1, fontWeight:"bold", fontFamily: "Syncopate"}, 
			{text:"h2", color:"#000000", border:2, background: "#dddd66"} 
		],
		SIZES: [
			"10", "12", "14", "16", "20", "24", "32", "48", "64", "80", "120"
		]		
	},
	
	getEditor: function() {
		var row = new Echo.Row();

		var lblStyle = new Echo.Label({
			foreground: 'white',
			text: 'Style:'
		});
		row.add(lblStyle);

		var cboStyle = new Echo.SelectField();			
		cboStyle.set("items", MD.MDText.STYLES);	
		cboStyle.set("insets", "-2px");		
		cboStyle.that = this;
		var styleIndex = this.render("type", 0);
		cboStyle.set("selection", styleIndex);
		cboStyle.addListener("action", this._updateStyle);
		row.add(cboStyle);

		var lblSize = new Echo.Label({
			foreground: 'white',
			text: 'Size:'
		});
		row.add(lblSize);

		var cboSize = new Echo.SelectField();
		cboSize.set("items", MD.MDText.SIZES);	
		cboSize.set("insets", "-2px");		
		cboSize.that = this;
		cboSize.addListener("action", this._updateSize);
		row.add(cboSize);

		//find size entry
		var actualSize = this.render("size", 10);
		for (var i = 0; i < MD.MDText.SIZES.length; i++) {
			var size = MD.MDText.SIZES[i];
			if (actualSize <= size) {
				cboSize.set("selection", i);
				break;
			}		
		}
		
		return row;
	},
	
	 _updateStyle: function(event) {
		var selectedIndex = event.source.get("selection");
		var textComponent = event.source.that; 
		textComponent.peer.setTypeStyle(selectedIndex);
		textComponent.set("type", selectedIndex);
		textComponent.fireUpdatePropEvent();
	},

	 _updateSize: function(event) {
		var selectedIndex = event.source.get("selection");
		var textComponent = event.source.that;
		var size = MD.MDText.SIZES[selectedIndex];
		console.log(size);
		textComponent.set("size", size);
		textComponent.fireUpdatePropEvent();
	},
	
	$load : function() {
       	Echo.ComponentFactory.registerType("MDText", this);
	},
	componentType: "MDText"
});

 
MD.Sync.MDText = Core.extend(MD.Sync.MDAbstractFigure, {
    
    $load: function() {
        Echo.Render.registerPeer("MDText", this);
    },
    
    renderAdd2: function(canvas, x, y) {
     	this._figure = new window.draw2d.shape.basic.Label();
     	this._figure.setText(this.component.render("text", "type here..."));
      	this._figure.setFontSize(this.component.render("size", 14));
      	
      	var lblEditor = new MD.MDLabelEditor();
      	lblEditor.addEditListener(this);
      	this._figure.installEditor(lblEditor);
		this._figure.onClick = Core.method(this, this.onClick);
		this.setTypeStyle(this.component.render("type"), 0);
		this.installListeners(this._figure);
      	canvas.addFigure(this._figure, x, y);      	
    },
    
    setTypeStyle: function(styleIndex) {    
    	if (typeof styleIndex === "undefined") styleIndex = 0;
		var style = MD.MDText.STYLES[styleIndex];
		var f = this._figure;
		if (style.border) f.setStroke(style.border);
		f.setFontColor(style.color);
		f.setBackgroundColor(style.background);
		//hacked properties..
		if (f.setFontFamily) f.setFontFamily(style.fontFamily);
		if (f.setFontWeight) f.setFontWeight(style.fontWeight);      	  	
		if (styleIndex == 1) {
    	  	f.setStroke(4);
        	f.setRadius(15);
			f.setPadding(6);
		} else {
			f.setStroke(0);
			f.setPadding(0);
		}
    },
    
    renderUpdate: function(update) {
    	MD.Sync.MDAbstractFigure.prototype.renderUpdate.call(this, update);
		this._figure.setText(this.component.render("text", "type here..."));
		this._figure.setFontSize(this.component.render("size", 12));
		this.setTypeStyle(this.component.render("type"));		
		return false; // Child elements not supported: safe to return false.
    },
    
    labelEdited: function() {
    	this.component.set("text", this._figure.getText());
    	this.component.fireUpdatePropEvent();
    }
});
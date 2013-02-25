if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDText = Core.extend(MD.MDAbstractFigure, {

	$static: {
		STYLES: [
			{text:"plain", color:"#ffffff", border:1}, 
			{text:"h1", color:"#111111", border:1, background: "#5b5b5b", fontWeight: "bold", fontFamily: "Knewave"}, 
			{text:"h2", color:"#aaaadd", border:2} 
		]
	},
	
	getEditor: function() {
		var lblStyle = new Echo.Label({
			foreground: 'white',
			text: 'Style:'
		});

		var cboStyle = new Echo.SelectField();			
		cboStyle.set("items", MD.MDText.STYLES);	
		cboStyle.set("insets", "-2px");		
		cboStyle.that = this;
		cboStyle.addListener("action", this._updateFigure);

		var row = new Echo.Row();
		row.add(lblStyle);
		row.add(cboStyle);
		return row;
	},
	
	 _updateFigure: function(event) {
		var selectedIndex = event.source.get("selection");
		event.source.that.peer.setTypeStyle(selectedIndex);
		event.source.that.set("type", selectedIndex);
		event.source.that.fireUpdatePropEvent();
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
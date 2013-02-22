if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDText = Core.extend(MD.MDAbstractFigure, {

	$static: {
		_styles: [
			{text:"banner", color:"#aaaadd", border:2}, 
			{text:"header", color:"#111111", border:1}, 
			{text:"plain", color:"#ffffff", border:1} 
		]
	},
	
	getEditor: function() {
		var lblStyle = new Echo.Label({
			foreground: 'white',
			text: 'Style:'
		});

		var cboStyle = new Echo.SelectField();			
		cboStyle.set("items", MD.MDText._styles);	
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
		var text = event.source.that.peer._figure;
		var style = MD.MDText._styles[selectedIndex];
		text.setStroke(style.border);
		text.setFontColor(style.color);
		
		event.source.that.set("type", style.text);
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
      	this._figure.setFontSize(this.component.render("size", 12));
      	
      	var lblEditor = new MD.MDLabelEditor();
      	lblEditor.addEditListener(this);
      	this._figure.installEditor(lblEditor);
		this._figure.onClick = Core.method(this, this.onClick);
		this.setTypeStyle(this.component.render("type"));
		this.installListeners(this._figure);
      	canvas.addFigure(this._figure, x, y);      	
    },
    
    setTypeStyle: function(type) {
    	if (type == "banner") {
			if (this._figure.setFontFamily) this._figure.setFontFamily("Arial");
	   		this._figure.setBackgroundColor("#5b5b5b");        
	    	this._figure.setColor("#dd00ee");
    	  	this._figure.setFontColor("#dddddd");
    	  	this._figure.setStroke(1);
        	this._figure.setRadius(5);
			this._figure.setPadding(4);
		    if (this._figure.setFontWeight) this._figure.setFontWeight("bold");    //works currently only in playgroundJS!  	  	
		} else if (type == "header") {
			this._figure.setStroke(0);
		    if (this._figure.setFontFamily) this._figure.setFontFamily("fantasy");   //works currently only in playgroundJS!	 
      		this._figure.setFontColor("#111111");
		} else {
			this._figure.setStroke(0);
			this._figure.setFontColor("#ffffff");
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
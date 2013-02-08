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
		cboStyle.that = this;
		cboStyle.addListener("action", this._updateFigure);

		var row = new Echo.Row();
		row.add(lblStyle);
		row.add(cboStyle);
		return row;
	},
	
	 _updateFigure: function(event) {
		var selectedIndex = event.source.get("selection");
		var text = event.source.that.peer._text;
		var style = MD.MDText._styles[selectedIndex];
		text.setStroke(style.border);
		text.setFontColor(style.color);
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
    
    _text: null,
    
    renderAdd2: function(canvas, x, y) {
     	this._text = new window.draw2d.shape.basic.Label();
     	this._text.setText(this.component.render("text", "type here..."));
      	this._text.setFontSize(this.component.render("size", 12));
      	this._text.installEditor(new draw2d.ui.LabelInplaceEditor());
		this._text.onClick = Core.method(this, this.onClick);
		var type = this.component.render("type");
		if (type == "banner") {
			if (this._text.setFontFamily) this._text.setFontFamily("Arial");
	   		this._text.setBackgroundColor("#5b5b5b");        
	    	this._text.setColor("#dd00ee");
    	  	this._text.setFontColor("#dddddd");
    	  	this._text.setStroke(1);
        	this._text.setRadius(5);
			this._text.setPadding(4);
		    if (this._text.setFontWeight) this._text.setFontWeight("bold");    //works currently only in playgroundJS!  	  	
		} else if (type == "header") {
			this._text.setStroke(0);
		    if (this._text.setFontFamily) this._text.setFontFamily("fantasy");   //works currently only in playgroundJS!	 
      		this._text.setFontColor("#111111");
		} else {
			this._text.setStroke(0);
			this._text.setFontColor("#ffffff");
		}
      	canvas.addFigure(this._text, x, y);      	
    }
});
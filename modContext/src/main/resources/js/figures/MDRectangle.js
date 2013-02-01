if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDRectangle = Core.extend(MD.MDAbstractFigure, {
	$load : function() {
       	Echo.ComponentFactory.registerType("MDRectangle", this);
	},
	componentType: "MDRectangle",

	$static: {
		_styles: [
			{text:"filled", color:"#aaaadd", border:0}, 
			{text:"rect", color:null, border:5}, 
		]
	},

	getEditor: function() {
		var lblStyle = new Echo.Label({
			foreground: 'white',
			text: 'Style:'
		});

		var cboStyle = new Echo.SelectField();			
		cboStyle.set("items", MD.MDRectangle._styles);
		cboStyle.that = this;
		cboStyle.addListener("action", this._updateFigure);

		var row = new Echo.Row();
		row.add(lblStyle);
		row.add(cboStyle);
		return row;
	},

	 _updateFigure: function(event) {
		var selectedIndex = event.source.get("selection");
		var rectangle = event.source.that.peer._rectangle;
		var style = MD.MDRectangle._styles[selectedIndex];
		if (style.color) {
			var color2 = new draw2d.util.Color(style.color);
			rectangle.setBackgroundColor(color2);
		} else {
			//empty rectangle
			rectangle.setBackgroundColor(null);
		}
		rectangle.setStroke(style.border);
	}    
});


MD.Sync.MDRectangle = Core.extend(MD.Sync.MDAbstractFigure, {
    
    $load: function() {
        Echo.Render.registerPeer("MDRectangle", this);
    },
    
    renderAdd2: function(canvas, x, y) {
		var w = this.component.render("width");
		var h = this.component.render("height");
		var rectangle = new window.draw2d.shape.basic.Rectangle(w, h);
		rectangle.setRadius(5);
		rectangle.onClick = Core.method(this, this.onClick);
		canvas.addFigure(rectangle, x, y);
    }
});
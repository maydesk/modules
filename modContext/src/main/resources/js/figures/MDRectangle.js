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
		//alert(event.source.that.peer);
		var component = event.source.that;
		var rectangle = component.peer._figure;
		var style = MD.MDRectangle._styles[selectedIndex];
		if (style.color) {
			rectangle.setBackgroundColor(new draw2d.util.Color(style.color));
			component.set("background", style.color);
		} else {
			//empty rectangle
			rectangle.setBackgroundColor(null);
			component.set("background", "#transparent");
		}
		rectangle.setStroke(style.border);
		component.set("border", style.border);
		
		component.fireUpdatePropEvent();
	}
});


MD.Sync.MDRectangle = Core.extend(MD.Sync.MDAbstractFigure, {
    
    $load: function() {
        Echo.Render.registerPeer("MDRectangle", this);
    },
    
    renderAdd2: function(canvas, x, y) {
		var w = this.component.render("width");
		var h = this.component.render("height");
		this._figure = new window.draw2d.shape.basic.Rectangle(w, h);
		this._figure.setRadius(5);
		this._figure.setBackgroundColor(this.component.render("background"));
		this._figure.setStroke(this.component.render("border"));
		this.installListeners(this._figure);
		canvas.addFigure(this._figure, x, y);
    },
    
    renderUpdate: function(update) {
    	MD.Sync.MDAbstractFigure.prototype.renderUpdate.call(this, update);
		var color = this.component.render("background");
		if (color === "#transparent" || color === "#-1") {
			this._figure.setBackgroundColor(null);
		} else {
			this._figure.setBackgroundColor(color);
		}
		this._figure.setStroke(this.component.render("border"));
		
		return false; // Child elements not supported: safe to return false.
    }
    
});
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
    
    _rectangle: null,
    
    $load: function() {
        Echo.Render.registerPeer("MDRectangle", this);
    },
    
    renderAdd2: function(canvas, x, y) {
		this._rectangle = new MyRectangle(this);
		this._rectangle.setRadius(10);
		canvas.addFigure(this._rectangle, x, y);
    }
});


MyRectangle = window.draw2d.shape.basic.Rectangle.extend({
	
	_parent: null,

   	init: function(parent) {
        this._super();
        this._parent = parent;        
    },
    
    onClick: function(x, y) {
    	this._parent.onClick(x, y);
    }
});


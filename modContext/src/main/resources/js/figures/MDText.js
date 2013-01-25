if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDText = Core.extend(MD.MDAbstractFigure, {

	getEditor: function() {
		return null;
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
    
    doLazyLoad: function(canvas, x, y) {
	    this._canvas = canvas;
     	this.fig = new MyText(this);
     	this.fig.setText("Hello");
    	this.fig.setColor("#dd00ee");
      	this.fig.setFontColor("#dddddd");
		this.fig.installEditor(new draw2d.ui.LabelInplaceEditor());
      	canvas.addFigure(this.fig, x, y);    
    }	
});


MyText = window.draw2d.shape.note.PostIt.extend({
	
	_parent: null,

   	init: function(parent) {
        this._super();
        this._parent = parent;        
    },
    
    onClick: function(x, y) {
    	this._parent.onClick(x, y);
    }
});

if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDArrow = Core.extend(MD.MDAbstractFigure, {

   	_lblSize: 10,
    
	getEditor: function() {
		var imgLess = this.peer.client.getResourceUrl("MDArrow", "editor/back.gif");
		var btnLess = new Echo.Button({
			icon: imgLess
		});
		btnLess.that = this;
		btnLess.addListener("action", this._resize);

		this._lblSize = new Echo.Label({
			foreground: 'white',
			text: '10px'
		});

		var imgMore = this.peer.client.getResourceUrl("MDArrow", "editor/forward.gif");
		var btnMore = new Echo.Button({
			icon: imgMore
		});
		btnMore.increase = true;
		btnMore.that = this;
		btnMore.addListener("action", this._resize);

		var row = new Echo.Row();
		row.add(btnLess);
		row.add(this._lblSize);
		row.add(btnMore);
		return row;
	},

	_resize: function(event) {
		var that = event.source.that;
		var increase = event.source.increase;
		that.peer.fig.size *= increase ? 1.25 : 0.8;
		that.peer.fig.size = Math.round(that.peer.fig.size);
		that._lblSize.set("text", that.peer.fig.size + "px");
		that.peer.fig.repaint();
	},

	$load : function() {
       	Echo.ComponentFactory.registerType("MDArrow", this);
	},
	componentType: "MDArrow"
});

MD.Sync.MDArrow = Core.extend(MD.Sync.MDAbstractFigure, {
    
    $load: function() {
        Echo.Render.registerPeer("MDArrow", this);
    },
    
    startCircle : null,
	endCircle : null,
	fig : null,
    _lblSize: null,
    
    doLazyLoad: function(canvas, x, y) {
       	this._canvas = canvas;
    
	    this.fig = new MyArrow(this);		
    	this.fig.installEditPolicy(new window.draw2d.policy.figure.GlowSelectionFeedbackPolicy());
		canvas.addFigure(this.fig);
        
		this.startCircle = new window.draw2d.shape.basic.Circle(14);
		this.endCircle = new window.draw2d.shape.basic.Circle(14);
		this.startCircle.attachMoveListener(this);
		this.endCircle.attachMoveListener(this);
		canvas.addFigure(this.startCircle, x + 100, y);
		canvas.addFigure(this.endCircle, x, y + 100);    
    },

	//listener method for attachMoveListener(...)    
	onOtherFigureIsMoving : function () {	
  		this.fig.switchX = false;
   		this.fig.switchY = false;
		var x0 = this.startCircle.getAbsoluteX() + 7 ;
		var y0 = this.startCircle.getAbsoluteY() + 7;
		var x1 = this.endCircle.getAbsoluteX() + 7;
		var y1 = this.endCircle.getAbsoluteY() + 7;
		if (x0 > x1) {var xn = x0; x0 = x1; x1 = xn; this.fig.switchX = true;}
		if (y0 > y1) {var yn = y0; y0 = y1; y1 = yn; this.fig.switchY = true;}
		var xs = x1 - x0;
		var ys = y1 - y0;
		this.fig.setPosition(x0, y0);
		this.fig.setDimension(xs, ys);
	},
	
	//listener method for attachMoveListener(...)   
	relocate : function () {
	}    
});



MyArrow = window.draw2d.VectorFigure.extend({

		_parent: null,
		size: 10,

	   	init: function(parent) {
		   this._super();
		   this.setDimension(100,100);	  
		   this._parent = parent;
    	},
	  
	  	onClick: function(x, y) {
	      	this._parent.onClick(x, y);
    	},
    
	    /**
	     * @inheritdoc
	     **/
	    repaint : function(attributes) {
	        if(this.shape===null){
	            return;
	        }
	        
	        if (typeof attributes === "undefined") {
	            attributes = {};
	        }
	        
	        var arrowLength=30;
			var w = this.getWidth();
			var h = this.getHeight();
	        var length = Math.sqrt(w * w + h * h);
	        var ax = length * 0.25;  //length of the arrow point 
	        var ay = length * 0.3;  //height of the arrow point
	        var angle = Math.atan(h / w);
	        
	        var x0 = this.getAbsoluteX() - (length - w) / 2;
	        var y0 = this.getAbsoluteY() + h/2;
	        var x1 = ax;
	        var y1 = -ay;
	        var x2 = 0;
	        var y2 = ay - this.size/2;
	        var x3 = length - ax;
	        var y3 = 0;
	        var x4 = 0;
	        var y4 = this.size;
	        var x5 = ax - length;
	        var y5 = 0;
	        var x6 = 0;
	        var y6 = ay - this.size/2;
	        	
	
	        attributes.path  = ' M '+ x0 + ' ' + y0 +
	                           ' l '+ x1 + ' ' + y1 +
	                           ' l '+ x2 + ' ' + y2 +
	                           ' l '+ x3 + ' ' + y3 +
	                           ' l '+ x4 + ' ' + y4 +
	                           ' l '+ x5 + ' ' + y5 +
	                           ' l '+ x6 + ' ' + y6 +
	                           ' Z';
	                           
			
			angle = angle * 180 / Math.PI;
			if (this.switchX && this.switchY) {
				angle = 360 - angle;
			} if (this.switchX) {
				angle = 180 - angle;
			} else if (this.switchY) {
				angle = -angle;
			} 
			this.shape.transform("r" + angle);
	        
			//this.setRotationAngle(angle);
	        this._super(attributes);
	},
	
	   /**
	    * @inheritdoc
	    */
	createShapeElement : function() {
	     return this.canvas.paper.path("");
	}
});

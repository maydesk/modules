if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDArrow = Core.extend(MD.MDAbstractFigure, {

   	_lblSize: 10,
   	
	getEditor: function() {
		var imgLess = this.peer.client.getResourceUrl("MDCanvasCommandbar", "editor/minus2.png"); //XXX MDArrow doesnt work?...
		if (!imgLess) imgLess = "img/minus2.png";  //for local testing...
		console.log(imgLess);
		var btnLess = new Echo.Button({
			icon: imgLess
		});
		btnLess.that = this;
		btnLess.addListener("action", this._resize);

		this._lblSize = new Echo.Label({
			foreground: 'white',
			text: '10px'
		});

		var imgMore = this.peer.client.getResourceUrl("MDCanvasCommandbar", "editor/plus2.png");  //XXX
		if (!imgMore) imgMore = "img/plus2.png";  //for local testing...
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
		that.peer._figure.size *= increase ? 1.25 : 0.8;
		that.peer._figure.size = Math.round(that.peer._figure.size);
		that._lblSize.set("text", that.peer._figure.size + "px");
		that.peer._figure.repaint();
		that.set("size", that.peer._figure.size);
		that.fireUpdatePropEvent();
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
    _lblSize: null,
    
    renderAdd2: function(canvas, x, y) {
	    this._figure = new MyArrow(this);
    	this._figure.installEditPolicy(new window.draw2d.policy.figure.GlowSelectionFeedbackPolicy());
        
		this.startCircle = new window.draw2d.shape.basic.Circle(10);
		this.startCircle.setResizeable(false);
		this.startCircle.setBackgroundColor("ffffff");
		this.endCircle = new window.draw2d.shape.basic.Circle(10);
		this.endCircle.setResizeable(false);
		this.endCircle.setBackgroundColor("ffffff");
		this.startCircle.attachMoveListener(this);
		this.endCircle.attachMoveListener(this);
		var w = this.component.render("width");
		w = Echo.Sync.Extent.toPixels(w, true);
		
		var h = this.component.render("height");
		h = Echo.Sync.Extent.toPixels(h, false);
		
		canvas.addFigure(this.startCircle, x, y);
		canvas.addFigure(this.endCircle, x + w, y + h);
		
		this._figure.size = this.component.render("size");
		canvas.addFigure(this._figure);
		
		// XXX: MOVE. Attempt at moving the handles along. Kind of works but with weird behavior
		// this._figure.attachMoveListener(this);
    },
    
    renderUpdate: function(update) {
    	MD.Sync.MDAbstractFigure.prototype.renderUpdate.call(this, update);
		this._figure.size = this.component.render("size");
		
		x = this.component.render("startPosX", this.startCircle.getX());
		y = this.component.render("startPosY", this.startCircle.getY());
		this.startCircle.setPosition(x, y);
		
		x = this.component.render("endPosX", this.endCircle.getX());
		y = this.component.render("endPosY", this.endCircle.getX());
		this.endCircle.setPosition(x, y);
		
		return false; // Child elements not supported: safe to return false.
    },

	//listener method for attachMoveListener(...)    
	onOtherFigureIsMoving : function (figure) {
	// XXX: MOVE. Attempt at moving the handles along. Kind of works but with weird behavior
	//		if (figure instanceof MyArrow) {
	//			if (this._figure.dragging) {
	//				var w = this.component.render("width");
	//				var h = this.component.render("height");
	//				var x = this._figure.getAbsoluteX();
	//				var y = this._figure.getAbsoluteY();
	//				
	//				this.startCircle.setPosition(x - 3, y - 3);
	//				this.endCircle.setPosition(x + w, y + h);
	//			}
	//			return;
		//			return;
	//		} else if (this._figure.dragging) {
	//		}
		
  		this._figure.switchX = false;
   		this._figure.switchY = false;
		var x0 = this.startCircle.getAbsoluteX() + 3 ;
		var y0 = this.startCircle.getAbsoluteY() + 3;
		var x1 = this.endCircle.getAbsoluteX() + 3;
		var y1 = this.endCircle.getAbsoluteY() + 3;
		if (x0 > x1) {var xn = x0; x0 = x1; x1 = xn; this._figure.switchX = true;}
		if (y0 > y1) {var yn = y0; y0 = y1; y1 = yn; this._figure.switchY = true;}
		var xs = x1 - x0;
		var ys = y1 - y0;
		this._figure.setPosition(x0, y0);
		this._figure.setDimension(xs, ys);

		this.component.setPosition(x0, y0);
		this.component.set("startPosX", this.startCircle.getX());
		this.component.set("startPosY", this.startCircle.getY());
		
		this.component.set("endPosX", this.endCircle.getX());
		this.component.set("endPosY", this.endCircle.getY());
		
		if (!isNaN(xs) && !isNaN(ys)) {	
			this.component.setDimension(xs, ys);
		}
	},
	
	//listener method for attachMoveListener(...)   
	relocate : function () {
	}    
});


MyArrow = window.draw2d.VectorFigure.extend({

		_parent: null,
		size: 10,
		dragging: false,

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
	        var ax = this.size * 2; //length of the arrow point 
	        var ay = this.size * 1.6; //height of the arrow point
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
	
	// XXX: MOVE. Attempt at moving the handles along. Kind of works but with weird behavior
	//	onDragStart: function(relativeX, relativeY) {
	//		this.dragging = true;
	//		console.log("dragging");
	//		
	//		return true;
	//	},
	//	
	//	onDragEnd: function() {
	//		this.dragging = false;
	//		console.log("dragging false");
	//	},
	
	   /**
	    * @inheritdoc
	    */
	createShapeElement : function() {
	     return this.canvas.paper.path("");
	}
});

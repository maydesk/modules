if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDNewsTicker = Core.extend(MD.MDAbstractFigure, {

	getEditor: function() {
		return null;
	},
	
	$load : function() {
       	Echo.ComponentFactory.registerType("MDNewsTicker", this);
	},
	componentType: "MDNewsTicker"
});

MD.Sync.MDNewsTicker = Core.extend(MD.Sync.MDAbstractFigure, {
    
    $load: function() {
        Echo.Render.registerPeer("MDNewsTicker", this);
    },
    
	_counter: 0,
	_tickerText: null,
	_x: 0,
	_y: 0,
	_width: 800,
	_height: 45,
        
    renderAdd2: function(canvas, x, y) {
    	this._x = x;
    	this._y = y;
		this._figure = new window.draw2d.SetFigure();
		this._figure.onClick = Core.method(this, this.onClick);
		this._figure.createSet = Core.method(this, this._createSet);
		this._figure.onTimer = Core.method(this, this._onTimer);		
		//this._width = this.component.render("width", 800);
		//this._height = this.component.render("height", 45);
		this._figure.setDimension(this._width, this._height);
		this.installListeners(this._figure);
		canvas.addFigure(this._figure, x, y);
		this._figure.startTimer(100);
		
    },
    
    _onTimer:function(){
    	this._counter -= 5;
    	if (this._counter < -270) this._counter = 750;
    	this._tickerText.attr({'x':this._counter});
    	//this._tickerText.transform("t-4,0");
    },
    
	_createSet: function() {
		var paper = this._parent._canvas.paper; 
		var set = paper.set();

		var w = this._width;
		var h = this._height;
		var w1 = 160;  //width of the "UPDATE" text
		
		var rect = paper.rect(0, 0, w, h);
		rect.attr({fill:"#000",stroke:"#000",r:2});
		set.push(rect);

		var text = paper.text(10, 20, "UPDATE:");
       	text.attr({'font-size':30, fill:"#fff", 'text-anchor':"start"});
		set.push(text);

		var text = this.component.render("text");
		this._tickerText = paper.text(8, 22, text);
       	this._tickerText.attr({
       		'font-size':26, 
       		fill: "#fff", 
       		'text-anchor': "start", 
       		'font-family': "Sans-Serif", 
       		'clip-rect': (this._x + w1) + " 0 " + (w - w1 - 5) + " 1080"});
		set.push(this._tickerText);
		
		var glow = rect.glow();
		glow.attr({'color': "#000", 'stroke': '#ff7', 'opacity':0.3, 'width':5});
		set.push(glow);
			
        return set;
    }    
});

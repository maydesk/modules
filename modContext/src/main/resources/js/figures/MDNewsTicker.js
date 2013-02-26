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
        
    renderAdd2: function(canvas, x, y) {
		this._figure = new window.draw2d.SetFigure();
		this._figure.onClick = Core.method(this, this.onClick);
		this._figure.createSet = Core.method(this, this._createSet);
		this._figure.onTimer = Core.method(this, this._onTimer);
		this._figure.setDimension(600, 45);
		this.installListeners(this._figure);
		canvas.addFigure(this._figure, x, y);
		this._figure.startTimer(100);
		
    },
    
    _onTimer:function(){
    	this._counter -= 5;
    	if (this._counter < -270) this._counter = 550;
    	this._tickerText.attr({'x':this._counter});
    	//this._tickerText.transform("t-4,0");
    },
    
	_createSet: function() {
		var paper = this._parent._canvas.paper; 
		var set = paper.set();


		var rect = paper.rect(0, 0, 600, 30);
		rect.attr({fill:"#000",stroke:"#000",r:2});
		set.push(rect);

		var rect2 = paper.rect(480, 20, 120, 25);
		rect2.attr({fill:"#000",stroke:"000",r:2});
		set.push(rect2);

		var text = paper.text(8, 14, "UPDATE:");
       	text.attr({'font-size':20, fill:"#fff", 'text-anchor':"start"});
		set.push(text);

		var text = this.component.render("text");
		this._tickerText = paper.text(10, 14, text);
       	this._tickerText.attr({'font-size':14, fill:"#fff", 'text-anchor':"start", 'font-family':"Sans-Serif", 'clip-rect':"135 0 475 1000"});
		set.push(this._tickerText);
		
		var author = this.component.render("author");
		var authorText = paper.text(587, 36, author);
       	authorText.attr({'font-size':10, fill:"#fff", 'text-anchor':"end"});
		set.push(authorText);
		
		var glow = rect.glow();
		glow.attr({'color': "#000", 'stroke': '#ff7', 'opacity':0.3, 'width':5});
		set.push(glow);

		//var glow2 = rect2.glow();
		//glow2.attr({'color': "#000", 'stroke': '#ff7', 'opacity':0.3, 'width':3});
		//set.push(glow2);

			
        return set;
    }    
});

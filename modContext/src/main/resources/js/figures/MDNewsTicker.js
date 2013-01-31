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
		var figTicker = new window.draw2d.SetFigure();
		figTicker.onClick = Core.method(this, this.onClick);
		figTicker.createSet = Core.method(this, this._createSet);
		figTicker.onTimer = Core.method(this, this._onTimer);
        figTicker.setDimension(400, 45);
		canvas.addFigure(figTicker, x, y);
		figTicker.startTimer(100);
		
    },
    
    _onTimer:function(){
    	this._counter -= 5;
    	if (this._counter < -270) this._counter = 350;
    	this._tickerText.attr({'x':this._counter});
    	//this._tickerText.transform("t-4,0");
    },
    
	_createSet: function() {
		var paper = this._parent._canvas.paper; 
		var set = paper.set();

		var rect = paper.rect(0, 0, 400, 30);
		rect.attr({fill:"#3d3d6d",stroke:"#3d3d6d",r:2});
		set.push(rect);

		var rect = paper.rect(280, 20, 120, 25);
		rect.attr({fill:"#3d3d6d",stroke:"#3d3d6d",r:2});
		set.push(rect);

		var text = paper.text(8, 14, "UPDATE:");
       	text.attr({'font-size':20, fill:"#eeeeee", 'text-anchor':"start"});
		set.push(text);

		var rect2 = paper.rect(100, 3, 290, 24);
		rect2.attr({fill:"#eeeeee",stroke:"eeeeee"});
		set.push(rect2);

		var text = this.component.render("text");
		this._tickerText = paper.text(10, 14, text);
       	this._tickerText.attr({'font-size':14, fill:"#222222", 'text-anchor':"start", 'font-family':"Sans-Serif", 'clip-rect':"135 0 275 300"});
		set.push(this._tickerText);

		
		var author = this.component.render("author");
		var authorText = paper.text(387, 36, author);
       	authorText.attr({'font-size':10, fill:"#eeeeee", 'text-anchor':"end"});
		set.push(authorText);
			
        return set;
    }    
});
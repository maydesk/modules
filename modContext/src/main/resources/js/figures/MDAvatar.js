MD.MDAvatar = Core.extend(Echo.Component, {

	getEditor: function() {
		return null;
	},

	$load : function() {
       	Echo.ComponentFactory.registerType("MDAvatar", this);
	},
   	
	componentType : "MDAvatar"		
});
 
 
MD.Sync.MDAvatar = Core.extend(MD.Sync.MDAbstractFigure, {
    
    $load: function() {
        Echo.Render.registerPeer("MDAvatar", this);
    },

	renderAdd2: function(canvas, x, y) {
		this._figure = new window.draw2d.SetFigure();
		this._figure.onClick = Core.method(this, this.onClick);
		this._figure.createSet = Core.method(this, this._createSet);
        this._figure.setDimension(40, 40);
		canvas.addFigure(this._figure, x, y);
    },
    
    _createSet: function() {
		var paper = this._parent._canvas.paper; 
		var set = paper.set();

 		var src = this.component.render("src", "");
		var image = paper.image(src, 0, 0, 50, 50)
		set.push(image);

		var srcText = this.component.render("text");
		if (srcText && srcText != "") {
			var lineBreakText = this._lineBreak(srcText);
			var text = paper.text(0, 0, lineBreakText);
			var w = text.getBBox().width + 10;
			var h = text.getBBox().height + 5;
			text.attr({'x': 25 + w/2, 'y': -20 - h/2, 'stroke': '#000'});
			set.push(text);
	
			var p = "M30,0";
			p += "L35,-20";
			p += "L25,-20";
			p += "L25,-" + (20+h);
			p += "L" + (25 + w) + ",-" + (20+h);
			p += "L" + (25 + w) + ",-20";
			p += "L45,-20Z";		
			var path = paper.path(p);
			path.attr({stroke: '#ff7', 'fill': '#ff7', 'opacity':0.85});
			set.push(path);
			
			var glow = path.glow();
			glow.attr({'color': "#000", 'stroke': '#ff7', 'opacity':0.3, 'width':5});
			set.push(glow);
			
			var text2 = paper.text(0, 0, lineBreakText);
			text2.attr({'x': 25 + w/2, 'y': -20 - h/2, 'stroke': 'black'});
			set.push(text2);
		}		
        return set;
    },
    
    _lineBreak: function(text) {
    	var lineCount = Math.floor(Math.sqrt(text.length / 15)) + 1;
	    var words = text.match(/\w+/g);
    	var result = "";
    	var length = 0;
    	var avgLineLength = text.length / lineCount; 
	    for (var i = 0; i < words.length; i++) {
	        var newLength = length + words[i].length / 2 + 1;
	        if (lineCount > 1 && newLength > avgLineLength) {
	        	lineCount--;
	        	result += "\n";
	        	length = 0;
	        }
	        result += words[i] + " ";
	        length += words[i].length + 1;
	    }
    	return result;
	}
 });
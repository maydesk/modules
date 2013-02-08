MD.MDNewsBox = Core.extend(Echo.Component, {

	getEditor: function() {
		return null;
	},

	$load : function() {
       	Echo.ComponentFactory.registerType("MDNewsBox", this);
	},
   	
	componentType : "MDNewsBox"		
});
 
 
MD.Sync.MDNewsBox = Core.extend(MD.Sync.MDAbstractFigure, {
    
    $load: function() {
        Echo.Render.registerPeer("MDNewsBox", this);
    },

	_figSet: null,
	_width: null,
	_height: null,
	
	renderAdd2: function(canvas, x, y) {
		this._figSet = new window.draw2d.SetFigure();
		this._figSet.onClick = Core.method(this, this.onClick);
		this._figSet.createSet = Core.method(this, this._createSet);
		this._figSet.setBackgroundColor("#cccccc");
		this._width = this.component.render("width");
		this._height = this.component.render("height");
        this._figSet.setDimension(this._width, this._height);
		canvas.addFigure(this._figSet, x, y);
    },
    
    _createSet: function() {
		var paper = this._parent._canvas.paper; 
		var set = paper.set();
		
		var title = this.component.render("title"); 
		var titleText = paper.text(45, 70, title);
		titleText.attr({'font-size': 12, 'font-weight': 'bold'});
		set.push(titleText);

		var lineBreakText = this._lineBreak(this.component.render("text", null));
		if (lineBreakText) {
			var text = paper.text(60, 28, lineBreakText);
			set.push(text);
		}

 		var icon = this.component.render("icon", "");
 		if (icon) {
			var image = paper.image(Echo.Sync.ImageReference.getUrl(icon), 90, 40, 20, 40);
			set.push(image);
		}

        return set;
    },
    
    _lineBreak: function(text) {
    	if (!text) return null;
    	var lineCount = Math.floor(Math.sqrt(text.length / 15)) + 1;
	    var words = text.match(/\w+/g);
    	var result = "";
    	var length = 0;
    	var lineLength = 23; //this._width - 10;
	    for (var i = 0; i < words.length; i++) {
	        var newLength = length + words[i].length;
	        if (lineCount > 0 && newLength > lineLength) {
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

 
 
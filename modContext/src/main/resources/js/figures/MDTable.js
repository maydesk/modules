if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}

MD.MDTable = Core.extend(MD.MDAbstractFigure, {

	getEditor: function() {
		return null;
	},
	
	$load : function() {
       	Echo.ComponentFactory.registerType("MDTable", this);
	},
	componentType: "MDTable"
});

 
MD.Sync.MDTable = Core.extend(MD.Sync.MDAbstractFigure, {

    $load: function() {
        Echo.Render.registerPeer("MDTable", this);
    },
    
    renderAdd2: function(canvas, x, y) {
		var table = new window.draw2d.SetFigure();
		table.onClick = Core.method(this, this.onClick);
		table.createSet = Core.method(this, this._createSet);
        table.setDimension(180, 120);
		canvas.addFigure(table, x, y);
    },
    
        
	_createSet: function() {
		var paper = this._parent._canvas.paper; 
		var set = paper.set();	
	   	var names = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
	   	var status = ["Spinat-Lasagne", "Broccoli-Auflauf", "T-Bone Steak", "Schnitzel/Pommes", "Salmon + Gratin"]
	   	for (var i = 0; i < names.length; i++) {
			var rect = paper.rect(0, 23 * i, 90, 20);
			rect.attr({fill:"#3d3d6d",stroke:"#3d3d6d",r:5});
			set.push(rect);

			var rect = paper.rect(95, 23 * i, 110, 20);
			rect.attr({fill:"#3d3d6d",stroke:"#3d3d6d",r:5});
			set.push(rect);

			var text = paper.text(8, 9 + 23 * i, names[i]);
			var lattr = {};
			lattr["text-anchor"] = "start";
			lattr["font-size"] = 14;
			lattr["font-family"] = "Sans-Serif";
			lattr.fill = "#eeeeee";			
        	text.attr(lattr);
			set.push(text);
			
			text = paper.text(150, 10 + 23 * i, status[i]);
			var lattr = {};
			lattr["font-size"] = 11;
			lattr["font-family"] = "Sans-Serif";
			lattr.fill = "#eeeeee";			
			text.attr(lattr);
			set.push(text);
		}
        return set;
    }    
});




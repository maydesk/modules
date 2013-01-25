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
    
    doLazyLoad: function(canvas, x, y) {
	    this._canvas = canvas;  
		var table = new MyTable(this);
		canvas.addFigure(table, x, y);
    }
});



MyTable = window.draw2d.SetFigure.extend({

   	init: function(parent) {
        this._super();
        this._parent = parent;        
        this.setDimension(180, 66);
    },
    
	createSet: function(){
		var set = this.canvas.paper.set();
	
	   	var names = ["Juan Carlos", "Hector Garcia", "John Travolta"]
	   	var status = ["OK", "due 3 days!", "OK"]
	   	for (var i = 0; i < names.length; i++) {
			
			var rect = this.canvas.paper.rect(0, 23 * i, 120, 20);
			rect.attr({fill:"#3d3d6d",stroke:"#3d3d6d",r:5});
			set.push(rect);

			var rect = this.canvas.paper.rect(125, 23 * i, 70, 20);
			rect.attr({fill:"#3d3d6d",stroke:"#3d3d6d",r:5});
			set.push(rect);

			var text = this.canvas.paper.text(8, 9 + 23 * i, names[i]);
			var lattr = {};
			lattr["text-anchor"] = "start";
			lattr["font-size"] = 16;
			lattr["font-family"] = "Sans-Serif";
			lattr.fill = "#eeeeee";			
        	text.attr(lattr);
			set.push(text);
			
			text = this.canvas.paper.text(160, 10 + 23 * i, status[i]);
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

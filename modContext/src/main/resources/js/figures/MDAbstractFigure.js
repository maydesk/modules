if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}

 
MD.MDAbstractFigure = Core.extend(Echo.Component, {
	$abstract: {
		getEditor: function() { }
	},
});

   
MD.Sync.MDAbstractFigure = Core.extend(Echo.Render.ComponentSync, {

 	$abstract: true,
 	_parent: null,
 	
	$load : function() {
       	Echo.Render.registerPeer("MDAbstractFigure", this);       	
	},
		
	$construct: function() {
    },
	
	$abstract: {
		renderAdd2: function(canvas, x, y) { },
	},
	
    renderDispose: function(update) {       
    },
    
    renderAdd: function(update, parent) {
    	this._parent = parent;
    	var x = this.component.render("positionX");
		var y = this.component.render("positionY");
		
		if (parent._canvas) {
			this.renderAdd2(parent._canvas, x, y);
    	} else {
    		//wait for canvas to be fully loaded
	    	var that = this;
			var doActionDelayed = function() {
				that.renderAdd2(parent._canvas, x, y);
	    	};
	   		window.setTimeout(doActionDelayed, 1000);
		}    	
    },
    
    renderUpdate: function(update) {
        return false; // Child elements not supported: safe to return false.
    },
    
    onClick: function(x, y) {
    	this._parent.component.parent.setEditor(this);
    }
});

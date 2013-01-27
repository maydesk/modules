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
    	this.renderAdd2(parent._canvas, x, y);
    },
    
    renderUpdate: function(update) {
        return false; // Child elements not supported: safe to return false.
    },
    
    onClick: function(x, y) {
    	this._parent.component.setEditor(this);
    }
});

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
 
	$load : function() {
       	Echo.Render.registerPeer("MDAbstractFigure", this);       	
	},
		
	$construct: function() {
    },
	
	$abstract: {
		doLazyLoad: function(canvas, x, y) { },
	},
	
	_canvas: null,

    renderAdd: function(update, parentNode) {    	
    },
    
	
    renderDispose: function(update) {       
    },
    
    renderUpdate: function(update) {
        return false; // Child elements not supported: safe to return false.
    },
    
    onClick: function(x, y){
    	this._canvas.setEditor(this);
    },
    
    doLazyLoad2: function(canvas, x, y) {
    	this._canvas = canvas;
	    this.doLazyLoad(canvas, x, y);
    }
});

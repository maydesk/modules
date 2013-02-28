if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}

 
MD.MDAbstractFigure = Core.extend(Echo.Component, {
	$abstract: {
		getEditor: function() { }
	},
	
	fireMoveEvent: function() {
	    this.fireEvent({type: "move", source: this});
	},

	fireUpdatePropEvent: function() {
	    this.fireEvent({type: "async_updateProps", source: this});
	}
});

   
MD.Sync.MDAbstractFigure = Core.extend(Echo.Render.ComponentSync, {

 	$abstract: true,
 	_parent: null,
 	_commandEventListener: null,
 	_ignoreUpdatesTill: 0,  //time in milliseconds, helps avoiding flickers, 
 	
 	
 	$virtual: {
 		_figure: null
 	},
 	
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
			this._figure._parent = this;
    	} else {
    		//wait for canvas to be fully loaded
	    	var that = this;
			var doActionDelayed = function() {
				that.renderAdd2(parent._canvas, x, y);
				if (that._figure) {
					that._figure._parent = that;
				} else {
					//console.log("what? " + x + " :: " +  y);
				}
	    	};
	   		window.setTimeout(doActionDelayed, 1000);
		}    	
    },
    
    renderUpdate: function(update) {
    	if (this._ignoreUpdatesTill > new Date().getTime()) {
    		//update is most probably triggered by self, ignore for avoiding flicker
    		return;
    	}
    
    	var x = this.component.render("positionX");
		var y = this.component.render("positionY");
		this._figure.setPosition(x, y);
		
		var w = this.component.render("width");
		var h = this.component.render("height");
		this._figure.setDimension(Echo.Sync.Extent.toPixels(w, true), Echo.Sync.Extent.toPixels(h, false));
    	
		return false; // Child elements not supported: safe to return false.
    },
    
    onClick: function(x, y) {
    	//calls MDContext.setEditor()
    	this._parent.component.parent.setEditor(this);
    },
    
    onDrag: function(figure) {
		this._ignoreUpdatesTill = new Date().getTime() + 1000;
		this.component.set('positionX', figure.getX());
		this.component.set('positionY', figure.getY());
  		this.component.fireMoveEvent();
    },
    
    installListeners: function(figure) {
    	var that = this;
    	var MyDragDropPolicy = draw2d.policy.figure.DragDropEditPolicy.extend({
		    onDrag:function(canvas, figure){
				that.onDrag(figure);
    		}
		});
		figure.installEditPolicy(new MyDragDropPolicy());
		figure.onClick = Core.method(this, this.onClick);
	} 
});

if (!Core.get(window, ["PD", "Sync"])) {
	Core.set(window, ["PD", "Sync"], {});
}

PD.PDStatusLabel = Core.extend(Echo.Label,  {

	$load : function() {
	 	Echo.ComponentFactory.registerType("PDStatusLabel", this);
	},

	/** @see Echo.Component#focusable */
	focusable: false,
	componentType : "PDStatusLabel"	
});

PD.Sync.PDStatusLabel = Core.extend(Echo.Sync.Label, {
	$static: {
		StoringAnimation: Core.extend(Extras.Sync.Animation, {
			$construct: function(node) {				
				this._node2 = node;
				this.runTime = 3000;
			},
			_node2: null,	 
			init: function() { 
				this._node2.style.opacity = 0.96;
			},			
			step: function(progress) {
				//first 33% are constant, no fading
				if (progress < 0.33) return;
				progress = progress - 0.33;
				progress = progress * 3.0 / 2.0;
				this._node2.style.opacity = 1.0 - progress;
			},			
			complete: function(abort) {
				this._node2.style.opacity = 0.0;
			},
		})
	},
	
	$load: function() {
		Echo.Render.registerPeer("PDStatusLabel", this);
	},
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
    	//call the super method
		Echo.Sync.Label.prototype.renderUpdate.call(this, update);
        var storingAnimation = new PD.Sync.PDStatusLabel.StoringAnimation(this._node);
	 	storingAnimation.start();      
        return false; // Child elements not supported: safe to return false.
    }

});


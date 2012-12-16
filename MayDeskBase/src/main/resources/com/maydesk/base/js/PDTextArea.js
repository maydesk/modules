/**
 * Remote text area component.
 */
PD.Sync.RemoteTextArea = Core.extend(Echo.TextArea, {

    /** @see Echo.Component#componentType */
    componentType: "PD.RTA",
    
    $load: function() {
        Echo.ComponentFactory.registerType("PD.RTA", this);
    }
});

/**
 * Remote text area component synchronization peer.
 */
PD.Sync.RemoteTextArea.Sync = Core.extend(Echo.Sync.TextArea, {
    
    $load: function() {
        Echo.Render.registerPeer("PD.RTA", this);
    },
    
    $include: [ Echo.Sync.RemoteTextComponent._SyncMixins],
    
    /** Constructor. */
    $construct: function() {
        this._remoteInit();
    },
    
    /** @see Echo.Sync.TextComponent#getSupportedPartialProperties */
    getSupportedPartialProperties: function() {
        return this._remoteGetSupportedPartialProperties(
                Echo.Sync.TextArea.prototype.getSupportedPartialProperties.call(this));
    },
    
    /** @see Echo.Sync.TextComponent#processBlur */
    processBlur: function(e) {
        Echo.Sync.TextArea.prototype.processBlur.call(this, e);
        this._remoteBlur();
    },
    
    /** @see Echo.Sync.TextComponent#processFocus */
    processFocus: function(e) {
        Echo.Sync.TextArea.prototype.processFocus.call(this, e);
        this._remoteFocus();
    },
    
    /** @see Echo.Render.ComponentSync#renderAdd */
    renderAdd: function(update, parentElement) {
        Echo.Sync.TextArea.prototype.renderAdd.call(this, update, parentElement);
        this._remoteAdd();
    },
    
    /** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._remoteDispose();
        Echo.Sync.TextArea.prototype.renderDispose.call(this, update);
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
        this._remoteUpdate();
        Echo.Sync.TextArea.prototype.renderUpdate.call(this, update);
    },
    
	/** this part has been actually added.. */     
    clientKeyUp: function(e) {
        this._storeSelection();
        this._storeValue(e);
		var that = this.component;
		var doActionDelayed = function() {
			that.doAction();
		};
		window.setTimeout(doActionDelayed, 520);
        return true;
    },   
    

});
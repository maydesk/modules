MD.MDLabelEditor =  draw2d.ui.LabelInplaceEditor.extend({
    
	init: function() {
        this._super();
        this.editListenerList = new draw2d.util.ArrayList();
    },
	
    addEditListener: function(listener) {
    	if (listener === null) {
			return;
		}

		this.editListenerList.add(listener);
    }, 
    
    removeEditListener: function(listener) {
    	if (listener === null) {
			return;
		}

		this.editListenerList.remove(listener);
    },
    
    /**
     * @method
     * Transfer the data from the editor into the label.<br>
     * Remove the editor.<br>
     * @private
     */
    commit: function() {
    	this._super();
    	this.fireEdit();
    },
    
    fireEdit: function() {
    	this.editListenerList.each($.proxy(function(i, item) {
            item.labelEdited();
        }, this));
    }
});
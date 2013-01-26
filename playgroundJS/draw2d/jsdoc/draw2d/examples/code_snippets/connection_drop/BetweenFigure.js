
var BetweenFigure = draw2d.shape.node.Between.extend({

    init : function()
    {
        this._super();
        this.setBackgroundColor("#fdc11d");
    },

  
    /**
     * @method
     * Called if the user drop this element onto the dropTarget. 
     * 
     * In this Example we create a "smart insert" of an existing connection.
     * COOL and fast network editing.
     * 
     * @param {draw2d.Figure} dropTarget The drop target.
     * @private
     **/
    onDrop:function(dropTarget)
    {
    	// Activate a "smart insert" If the user drop this figure on connection
    	//
    	if(dropTarget instanceof draw2d.Connection){
    		var oldSource = dropTarget.getSource();
    		dropTarget.setSource(this.getOutputPort(0));
   		
    		var additionalConnection = new draw2d.Connection();
    		this.getCanvas().addFigure(additionalConnection);
    		additionalConnection.setSource(oldSource);
    		additionalConnection.setTarget(this.getInputPort(0));
    	}
    }

});

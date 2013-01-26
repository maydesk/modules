
var MyFigure = draw2d.shape.basic.Rectangle.extend({

    init : function()
    {
        this._super();
        
        this.setBackgroundColor("#FF765E");
        this.setRadius(5);
    },

    /**
     * @method
     * Change the corner radius if the user clicks on the element. 
     * quite simple....
     * 
     */
    onDoubleClick: function(){
    	this.setRadius( this.getRadius()===5?20:5);
    }

});

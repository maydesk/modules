
var TimerFigure = draw2d.shape.basic.Label.extend({

    init : function()
    {
        this._super();
        this.setDimension(100,100);
        this.counter = 0;
        
        this.startTimer(500);
        
        this.setText("Counter: 0");
    },

    /**
     * @method
     * private callback method for the internal timer.
     * 
     * @private
     */
    onTimer:function(){
        this.setText("Counter: "+(++this.counter));
    }

});

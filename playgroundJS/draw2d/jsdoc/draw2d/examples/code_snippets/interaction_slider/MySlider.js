
MySlider = draw2d.shape.widget.Slider.extend({

    NAME : "MySlider",
    
    init : function()
    {
        this._super();
        
        this.createPort("output");
        this.setDimension(120,20);
    },

    /**
     * @method
     * Called if the value of the slider has been changed.
     * 
     * @param {Number} value The new value of the slider in percentage [0..100]
     * @template
     */
    onValueChange:function(value){
        var connections = this.getOutputPort(0).getConnections();
        connections.each($.proxy(function(i, conn){
            var targetPort = conn.getTarget();
            targetPort.setValue(value);
        },this));
    }

});

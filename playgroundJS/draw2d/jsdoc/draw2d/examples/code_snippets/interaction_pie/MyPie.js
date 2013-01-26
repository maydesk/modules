
var MyPie = draw2d.shape.diagram.Pie.extend({

    NAME : "MyPie",
    
    init : function()
    {
        this._super();
        
        this.createPort("input");
        this.createPort("input");
        this.createPort("input");
        
        this.setDimension(200,200);
    },
    
    /**
     * @method
     * Called if the value of any port has been changed
     * 
     * @param {draw2d.Port} relatedPort
     * @template
     */
    onPortValueChanged: function(relatedPort){
        var data = [];
        this.getInputPorts().each(function(i,port){
            data.push(port.getValue());
        });
        this.setData(data);
    }

});

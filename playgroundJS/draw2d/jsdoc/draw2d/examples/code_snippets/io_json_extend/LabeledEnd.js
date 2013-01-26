var example = {};

/**
 * @class example.connection_locator.LabelConnection
 * 
 * A simple Connection with a label wehich sticks in the middle of the connection..
 *
 * @author Andreas Herz
 * @extend draw2d.Connection
 */
example.LabeledEnd = draw2d.shape.node.End.extend({
    
    init:function()
    {
      this._super();
    
      // Create any Draw2D figure as decoration for the connection
      //
      this.label = new draw2d.shape.basic.Label("I'm a Label too");
      this.label.setColor("#0d0d0d");
      this.label.setFontColor("#0d0d0d");
      
      // add the new decoration to the connection with a position locator.
      //
      this.addFigure(this.label, new draw2d.layout.locator.BottomLocator(this));
    },

    /**
     * @method 
     * Return an objects with all important attributes for XML or JSON serialization
     * 
     * @returns {Object}
     */
    getPersistentAttributes : function()
    {
        var memento = this._super();
        
        // add all decorations to the memento 
        //
        memento.labels = [];
        this.children.each(function(i,e){
            memento.labels.push({
                id:e.figure.getId(),
                label:e.figure.getText(),
                locator:e.locator.NAME
            });
        });
    
        return memento;
    },
    
    /**
     * @method 
     * Read all attributes from the serialized properties and transfer them into the shape.
     * 
     * @param {Object} memento
     * @returns 
     */
    setPersistentAttributes : function(memento)
    {
        this._super(memento);
        
        // remove all decorations created in the constructor of this element
        //
        this.resetChildren();
        
        // and restore all children of the JSON document instead.
        //
        $.each(memento.labels, $.proxy(function(i,e){
            var label = new draw2d.shape.basic.Label(e.label);
            var locator =  eval("new "+e.locator+"()");
            locator.setParent(this);
            this.addFigure(label, locator);
        },this));
    }
});

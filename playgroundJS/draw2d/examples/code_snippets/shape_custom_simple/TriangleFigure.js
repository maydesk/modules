
TriangleFigure = draw2d.VectorFigure.extend({

    init : function()
    {
        this._super();
        this.setDimension(100,100);
    },

  
    /**
     * @inheritdoc
     **/
    repaint : function(attributes)
    {
        if(this.shape===null){
            return;
        }
        
        if (typeof attributes === "undefined") {
            attributes = {};
        }

        attributes.path  = ' M '+this.getAbsoluteX()+' '+this.getAbsoluteY()+  // absolute move to upper left
                           ' l '+(this.getWidth()/2)+' '+this.getHeight()+     // relative line to bottom middle
                           ' l '+this.getWidth()/2+' -'+this.getHeight()+      // relative line to top right corner
                           ' Z';                                               // close the path

        this._super(attributes);
    },

    /**
     * @inheritdoc
     */
    createShapeElement : function()
    {
       return this.canvas.paper.path("");
    }

});

/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.shape.node.Hub
 * 
 * A hub is a shape with a special kind of port handling. The hole figure is a hybrid port. You can drag&drop a Port directly on
 * the figure.
 * 
 * See the example:
 *
 *     @example preview small frame
 *     
 *     var figure =  new draw2d.shape.node.Hub();
 *     
 *     canvas.addFigure(figure,50,10);
 *     
 * @extends draw2d.shape.basic.Rectangle
 */
draw2d.shape.node.Hub = draw2d.shape.basic.Rectangle.extend({

    NAME : "draw2d.shape.node.Hub",

    DEFAULT_COLOR : new draw2d.util.Color("#4DF0FE"),
    BACKGROUND_COLOR : new draw2d.util.Color("#29AA77"),

	/**
	 * 
	 * @param {Number} width initial width of the bus shape
	 * @param {Number} height height of the bus
	 */
	init : function(width, height, label)
    {
	    this.label = null;
	    
        this._super(width,height);
        
        var port = this.createPort("hybrid", new draw2d.layout.locator.CenterLocator(this));

        // redirect the glow effect and the hitTest for the port to the parent node
        //
        port.setGlow = $.proxy(this.setGlow,this);
        port.hitTest = $.proxy(this.hitTest,this);
        
        // provide a special connection anchor for this port. We use the bounding box of the
        // parent as connection border
        //
        port.setConnectionAnchor(new draw2d.layout.anchor.ShortesPathConnectionAnchor(port));
        port.setVisible(false);
        
        // set some good defaults
        //
        if(typeof height ==="undefined"){
            this.setDimension(150, 50);
        }
        
        // set the border of the rectangle a little bit darker than the 
        // inner part
        //
        this.setColor(this.DEFAULT_COLOR.darker());
        this.setBackgroundColor(this.BACKGROUND_COLOR);
        if(typeof label !== "undefined"){
            // Create any Draw2D figure as decoration for the connection
            //
            this.label = new draw2d.shape.basic.Label(label);
            this.label.setColor("#0d0d0d");
            this.label.setFontColor("#0d0d0d");
            this.label.setStroke(0);
            
            // add the new decoration to the connection with a position locator.
            //
            this.addFigure(this.label, new draw2d.layout.locator.CenterLocator(this));
        }
    },
    
      /**
      * @method
      * 
      * @param draggedFigure
      * @return {draw2d.Figure} the figure which should receive the drop event or null if the element didnt want a drop event
      */
     onDragEnter : function( draggedFigure )
     {
         // redirect the dragEnter handling to the hybrid port
         //
  		 return this.getHybridPort(0).onDragEnter(draggedFigure);
     },
     
     /**
      * @method
      * This value is relevant for the interactive resize of the figure.
      *
      * @return {Number} Returns the min. width of this object.
      */
     getMinWidth:function()
     {
         if(this.label!==null){
             return Math.max(this.label.getMinWidth(), this._super());
         }
         else{
             return this._super();
         }
     },
     

     /**
      * @inheritdoc
      * 
      * @param attributes
      */
     repaint:function(attributes)
     {
         if(this.repaintBlocked===true || this.shape===null){
             return;
         }

         if(typeof attributes === "undefined"){
             attributes= {};
         }
         
         // set some good defaults if the parent didn't
         if(typeof attributes.fill ==="undefined"){
             attributes.fill="90-"+this.bgColor.hash()+":5-"+this.bgColor.lighter(0.3).hash()+":95";
         }
         
        this._super(attributes);
     }
     

});

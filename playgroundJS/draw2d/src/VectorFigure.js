/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.VectorFigure
 * The base class for all vector based figures like {@link draw2d.shape.basic.Rectangle}  or {@link draw2d.shape.basic.Oval} 
 * inside a canvas.
 * 
 * @inheritable
 * @author Andreas Herz
 * @extends draw2d.shape.node.Node
 */
draw2d.VectorFigure = draw2d.shape.node.Node.extend({
    NAME : "draw2d.VectorFigure",

    /**
     * @constructor
     * Creates a new figure element which are not assigned to any canvas.
     * 
     */
    init : function()
    {
        this.stroke = 1;
        
        // memento for the stroke if we reset the glow effect of this shape
        //
        this.strokeBeforeGlow = this.stroke;
        this.glowIsActive = false;
        
        this._super();
    },
      
    
    /**
     * @method
     * Highlight the element or remove the highlighting
     * 
     * @param {Boolean} flag indicates glow/noGlow
     * @template
     */
    setGlow: function(flag){
        
        if(flag === this.glowIsActive) {
            // nothing todo
            return;
        }
        
        this.glowIsActive = flag;
        if(flag===true){
            this.strokeBeforeGlow = this.getStroke();
            this.setStroke(this.strokeBeforeGlow*2.5);
        }
        else {
            this.setStroke(this.strokeBeforeGlow);
        }
    },
    
   /**
    * @method
    * propagate all attributes like color, stroke,... to the shape element
    **/
    repaint : function(attributes)
    {
        if (this.repaintBlocked===true || this.shape === null){
            return;
        }

        if(typeof attributes === "undefined" ){
            attributes = {};
        }

        attributes.x = this.getAbsoluteX();
        attributes.y = this.getAbsoluteY();
        
        if(typeof attributes.stroke==="undefined"){
            if(this.color === null || this.stroke ===0){
                attributes.stroke = "none";
            }
            else {
                attributes.stroke = "#" + this.color.hex();
            }
        }
        
        attributes["stroke-width"] = this.stroke;
        
        if(typeof attributes.fill === "undefined"){
           if(this.bgColor!==null){
        	   attributes.fill = "#" + this.bgColor.hex();
           }
           else{
               attributes.fill ="none";
           }
        }

        this._super(attributes);
    },


   /**
    * @method
    * Set the new background color of the figure. It is possible to hands over
    * <code>null</code> to set the background transparent.
    *
    * @param {draw2d.util.Color} color The new background color of the figure
    **/
    setBackgroundColor : function(color)
    {
        if (color instanceof draw2d.util.Color) {
            this.bgColor = color;
        }
        else if (typeof color === "string") {
            this.bgColor = new draw2d.util.Color(color);
        }
        else {
            this.bgColor = null;
        }

        this.repaint();
    },

   /**
    * @method
    * The current used background color.
    * 
    * @return {draw2d.util.Color}
    */
   getBackgroundColor:function()
   {
     return this.bgColor;
   },

   /**
    * @method
    * Set the stroke to use.
    * 
    * @param {Number} w The new line width of the figure
    **/
   setStroke:function( w )
   {
     this.stroke=w;
     this.repaint();
   },

   /**
    * @method
    * The current use line width.
    * 
    * @type {Number}
    **/
   getStroke:function( )
   {
     return this.stroke;
   },

   /**
    * @mehod
    * Set the color of the line.
    * This method fires a <i>document dirty</i> event.
    * 
    * @param {draw2d.util.Color} color The new color of the line.
    **/
   setColor:function( color)
   {
     if(color instanceof draw2d.util.Color){
         this.color = color;
     }
     else if(typeof color === "string"){
         this.color = new draw2d.util.Color(color);
     }
     else{
         // set good default
         this.color = new draw2d.util.Color(0,0,0);
     }
     this.repaint();
   },

   /**
    * @method
    * The current used forground color
    * 
    * @returns {draw2d.util.Color}
    */
   getColor:function()
   {
     return this.color;
   }

});


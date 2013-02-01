/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.SetFigure
 * 
 * A SetFigure is a composition of different SVG elements.
 * 
 * @author Andreas Herz
 * @extends draw2d.shape.basic.Rectangle
 */
draw2d.SetFigure = draw2d.shape.basic.Rectangle.extend({
    
    NAME : "draw2d.SetFigure",

    /**
     * @constructor
     * Creates a new figure element which are not assigned to any canvas.
     * 
     */
    init: function( width, height) {
      // collection of SVG DOM nodes
      this.svgNodes=null;
      
      this.originalWidth = null;
      this.originalHeight= null;
      
      this.scaleX = 1;
      this.scaleY = 1;

      this._super( width, height);

      this.setStroke(0);
      this.setBackgroundColor(null); 
    },
    
    /**
     * @method
     * Set/Reset the canvas for the element.
     * 
     * @param {draw2d.Canvas} canvas the canvas to use
     */
    setCanvas: function( canvas )
    {
      // remove the shape if we reset the canvas and the element
      // was already drawn
      if(canvas===null && this.svgNodes!==null){
         this.svgNodes.remove();
         this.svgNodes=null;
      }
      
      this._super(canvas);
     },
 
     
     
    /**
     * @method
     * propagate all attributes like color, stroke,... to the shape element and
     * repaint them.
     * 
     **/
    repaint : function(attributes)
    {
        // repaint can be blocked during deserialization and if the shape
        // not part of any canvas.
        //
        if (this.repaintBlocked === true || this.shape === null) {
            return;
        }

        if (this.originalWidth !== null) {
        //XXX	this.scaleX = this.width / this.originalWidth;
        //XXX	this.scaleY = this.height / this.originalHeight;
        }
        
        if (typeof attributes === "undefined") {
            attributes = {};
        }

        if(this.visible===true){
            this.svgNodes.show();
        }
        else{
            this.svgNodes.hide();
        }
        
        this._super(attributes);
    },


    /**
     * @private
     */
    applyTransformation:function(){
        var s = 
        	"S"+this.scaleX+","+this.scaleY+",0,0 "+
        	"R"+this.rotationAngle+","+parseInt(this.getWidth()/2)+","+parseInt(this.getHeight()/2)+
        	"T" + this.getAbsoluteX() + "," + this.getAbsoluteY()+
            "";
    	this.svgNodes.transform(s);
        if(this.getRotationAngle()=== 90|| this.getRotationAngle()===270){
            var before  = this.svgNodes.getBBox(true);
            var ratio = before.height/before.width;
            var reverseRatio = before.width/before.height;
            var rs = "...S"+ratio+","+reverseRatio+","+(this.getAbsoluteX() +this.getWidth()/2)+","+(this.getAbsoluteY() +this.getHeight()/2);
        	this.svgNodes.transform(rs);
        }
    },
    
    /**
     * @private
     */
    createShapeElement : function()
    {
       // NOTE: don't change the order of the two calls. This defines the z-oder in the canvas.
       // The "set" must always be on top.
       var shape= this.canvas.paper.rect(this.getX(),this.getY(),this.getWidth(), this.getHeight());
       this.svgNodes = this.createSet();
       
       var bb = this.svgNodes.getBBox();
       this.originalWidth = bb.width;
       this.originalHeight= bb.height;
      
       return shape;
    },
    
    /**
     * @method
     * Override this method to add your own SVG elements. See {@link draw2d.shape.basic.Label} as example.
     * 
     * @template
     */
    createSet: function(){
    	return this.canvas.paper.set(); // return empty set as default;
    }
   
});
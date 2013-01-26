/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.shape.basic.PolyLine
 * 
 * A PolyLine is a line with more than 2 points.
 *
 * @inheritable
 * @author Andreas Herz
 * @extends draw2d.shape.basic.Line
 */
draw2d.shape.basic.PolyLine = draw2d.shape.basic.Line.extend({
    
	NAME : "draw2d.shape.basic.PolyLine",


    /**
     * @constructor
     * Creates a new figure element which are not assigned to any canvas.
     */
    init: function( ) {
        
      // internal status handling for performance reasons
      //
      this.svgPathString = null;
      this.oldPoint=null;
    
      // all line segments with start/end as simple object member
      this.lineSegments = new draw2d.util.ArrayList();
      this.basePoints = new draw2d.util.ArrayList();
      
      // all possible brides (line crossings)
      this.bridges = new draw2d.util.ArrayList();

      this._super();
    },
    
    /**
     * @method
     * Set the start point of the line.
     *
     * @param {Number} x the x coordinate of the start point
     * @param {Number} y the y coordinate of the start point
     **/
    setStartPoint:function( x, y){
        this.repaintBlocked=true;
        this._super(x,y);
        this.calculatePath();
        
        this.repaintBlocked=false;
        this.repaint();
    },

    /**
     * Set the end point of the line.
     *
     * @param {Number} x the x coordinate of the end point
     * @param {Number} y the y coordinate of the end point
     **/
    setEndPoint:function(x,  y){
        this.repaintBlocked=true;
        this._super(x,y);
        this.calculatePath();
        
        this.repaintBlocked=false;
        this.repaint();
    },

    /**
     * @method
     * Calculate the path of the polyline
     * 
     * @private
     */
    calculatePath: function(){
        
        if(this.shape===null){
            return;
        }
    
        this.svgPathString = null;
        
        // cleanup the routing cache
        //
        this.oldPoint=null;
        this.lineSegments = new draw2d.util.ArrayList();
        this.basePoints = new draw2d.util.ArrayList();
    
        // Use the internal router
        //
        this.router.route(this);
    },
    
    /**
     * @private
     **/
    repaint : function()
    {
      if(this.repaintBlocked===true || this.shape===null){
          return;
      }
      
      if(this.svgPathString===null || this.routingRequired===true){
          this.calculatePath();
      }
      
      this._super({path:this.svgPathString, "stroke-linejoin":"round"});
    },
    

    /**
     * @method
     * Called by the framework during drag&drop operations.
     * 
     * @param {draw2d.Figure} draggedFigure The figure which is currently dragging
     * 
     * @return {draw2d.Figure} the figure which should receive the drop event or null if the element didnt want a drop event
     * @template
     **/
    onDragEnter : function( draggedFigure ){
    	this.setGlow(true);
    	return this;
    },
 
    /**
     * @method
     * Called if the DragDrop object leaving the current hover figure.
     * 
     * @param {draw2d.Figure} draggedFigure The figure which is currently dragging
     * @template
     **/
    onDragLeave:function( draggedFigure ){
    	this.setGlow(false);
    },


    /**
     * @method
     * Returns the fulcrums of the connection
     *
     * @return {draw2d.util.ArrayList} an draw2d.util.ArrayList of type draw2d.Point
     **/
    getPoints:function()
    {
        return this.basePoints;
    },
    
    /**
     * @method
     * Return all line segments of the polyline.
     * 
     * @returns {draw2d.util.ArrayList}
     */
    getSegments: function(){
        return this.lineSegments;
    },
    
    /**
     * @method
     * used for the router to add the calculated points
     * 
     * @private
     *
     **/
    addPoint:function(/*:draw2d.geo.Point*/ p)
    {
      p = new draw2d.geo.Point(p.x, p.y);
      this.basePoints.add(p);

      if(this.oldPoint!==null){
        // store the painted line segment for the "mouse selection test"
        // (required for user interaction)
        this.lineSegments.add({start: this.oldPoint, end:p});
      }
      this.svgPathString=null;
      this.oldPoint = p;
    },

    /**
     * @see draw2d.Figure#onOtherFigureHasMoved
     **/
    onOtherFigureIsMoving:function(/*:draw2d.Figure*/ figure)
    {
      this.repaintBlocked=true;
      this._super(figure);
      this.calculatePath();
      
      this.repaintBlocked=false;
      this.repaint();
    },
    
    /**
     * @method
    * Checks if the hands over coordinate close to the line. The 'corona' is considered
    * for this test. This means the point isn't direct on the line. Is it only close to the
    * line!
    *
    * @param {Number} px the x coordinate of the test point
    * @param {Number} py the y coordinate of the test point
    * @return {boolean}
     **/
    hitTest:function( px, py)
    {
      for(var i = 0; i< this.lineSegments.getSize();i++){
         var line = this.lineSegments.get(i);
         if(draw2d.shape.basic.Line.hit(this.corona, line.start.x,line.start.y,line.end.x, line.end.y, px,py)){
           return true;
         }
      }
      return false;
    },

    /**
     * @method
     * Returns the Command to perform the specified Request or null.
      *
     * @param {draw2d.command.CommandType} request describes the Command being requested
     * @return {draw2d.command.Command}
     **/
    createCommand:function(request) 
    {
 
      if(request.getPolicy() === draw2d.command.CommandType.DELETE){
        if(this.isDeleteable()===true){
          return new draw2d.command.CommandDelete(this);
        }
      }
    
      return null;
    }
});


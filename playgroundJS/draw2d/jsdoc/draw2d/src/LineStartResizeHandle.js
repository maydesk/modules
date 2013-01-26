/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************//**
 * @class draw2d.shape.basic.LineStartResizeHandle
 * Selection handle for connections and normal lines.
 * 
 * TODO: Split the LineEndResizeHandle to ConnectionEndResizeHandle and LineEndResizeHandle!!!!
 *
 * @inheritable
 * @author Andreas Herz
 * @extends draw2d.shape.basic.LineResizeHandle 
 */
draw2d.shape.basic.LineStartResizeHandle = draw2d.shape.basic.LineResizeHandle.extend({
    NAME : "draw2d.shape.basic.LineStartResizeHandle",

    init: function( figure) {
        this._super(figure);
    },
    
    /**
     * @method
     * Return the Port below the ResizeHandle
     * 
     * @return {draw2d.Port}
     */
    getRelatedPort:function()
    {
       return this.owner.getSource();
    },
    
    /**
     * @method
     * Return the Port on the opposite side of the ResizeHandle
     * 
     * @returns
     */
    getOppositePort:function()
    {
         return this.owner.getTarget();
    },
    
    /**
     * @method
     * Called from the framework during a drag&drop operation
     * 
     * @param {Number} dx the x difference between the start of the drag drop operation and now
     * @param {Number} dy the y difference between the start of the drag drop operation and now
     * @return {boolean}
     **/
    onDrag:function( dx, dy)
    {
      var oldX = this.getX();
      var oldY = this.getY();
      this._super(dx, dy);
      var diffX = oldX-this.getX();
      var diffY = oldY-this.getY();
     
      var objPos = this.owner.getStartPoint();
    
      this.owner.setStartPoint(objPos.x-diffX, objPos.y-diffY);
      this.owner.isMoving = true;
      
      return true;
    },
    
    /**
     * @method
     * Resizehandle has been drop on a InputPort/OutputPort.
     * 
     * @param {draw2d.Port} dropTarget
     **/
    onDrop:function( dropTarget)
    {
    	this.owner.isMoving=false;
    
      // The ResizeHandle of a Connection has been droped on a Port
      // This will enforce a ReconnectCommand
      if(this.owner instanceof draw2d.Connection && this.command !==null) {
         this.command.setNewPorts(dropTarget, this.owner.getTarget());
         this.getCanvas().getCommandStack().execute(this.command);
      }
      this.command = null;
    }

});
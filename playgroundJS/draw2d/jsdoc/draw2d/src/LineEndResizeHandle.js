/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.shape.basic.LineEndResizeHandle
 * Selection handle for connections and normal lines.
 * 
 * TODO: Split the LineEndResizeHandle to ConnectionEndResizeHandle and LineEndResizeHandle!!!!
 *
 * @inheritable
 * @author Andreas Herz
 * @extends draw2d.shape.basic.LineResizeHandle 
 */
draw2d.shape.basic.LineEndResizeHandle = draw2d.shape.basic.LineResizeHandle.extend({
    NAME : "draw2d.shape.basic.LineEndResizeHandle",

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
         return this.owner.getTarget();
    },
    
    /**
     * @method
     * Return the Port on the opposite side of the ResizeHandle
     * 
     * @returns
     */
    getOppositePort:function()
    {
         return this.owner.getSource();
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
      this._super(dx,dy);
      var diffX = oldX-this.getX();
      var diffY = oldY-this.getY();
    
      var objPos = this.owner.getEndPoint();
    
      this.owner.setEndPoint(objPos.x-diffX, objPos.y-diffY);
      this.owner.isMoving = true;
      this.detachMoveListener(this.owner);
      
      return true;
    },
    
    /**
     * Resizehandle has been drop on a InputPort/OutputPort.
     * @private
     **/
    onDrop:function( dropTarget)
    {
    	this.owner.isMoving=false;
      
      if(this.owner instanceof draw2d.Connection && this.command !==null){
         this.command.setNewPorts(this.owner.getSource(),dropTarget);
         this.getCanvas().getCommandStack().execute(this.command);
      }
      this.command = null;
    }
});
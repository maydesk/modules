/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.policy.figure.SlimSelectionFeedbackPolicy
 * 
 * See the example:
 *
 *     @example preview small frame
 *       circle =new draw2d.shape.basic.Circle();
 *       circle.installEditPolicy(new draw2d.policy.SlimSelectionFeedbackPolicy());
 *       canvas.addFigure(circle,90,50);
 *
 *       canvas.addFigure(new draw2d.shape.basic.Label("Click on the circle to see the selection feedback"),20,10);
 *
 * @author Andreas Herz
 * @extends draw2d.policy.figure.RectangleSelectionFeedbackPolicy
 */
draw2d.policy.figure.SlimSelectionFeedbackPolicy = draw2d.policy.figure.RectangleSelectionFeedbackPolicy.extend({

    NAME : "draw2d.policy.figure.SlimSelectionFeedbackPolicy",
    
    /**
     * @constructor 
     * Creates a new Router object
     */
    init: function(){
        this._super();
   },
    

    /**
     * @method
     * Called by the framework of the Policy should show a resize handle for the given shape
     * 
     * @param {boolean} isPrimarySelection
     */
    onSelect: function(canvas,figure, isPrimarySelection){
        
        this._super(canvas,figure, isPrimarySelection);
        
        if(!figure.selectionHandles.isEmpty())
        {
            var box = new draw2d.shape.basic.Rectangle();
            box.setBackgroundColor(null);
            box.setColor("#3EB3F8");
            box.hide= function(){
                // don't add/remove this rectangle to the canvas resizeHandles. This rect isn't responsible for any hitTest or
                // dragDrop operation
                // canvas.resizeHandles.remove(box);
                box.setCanvas(null);
            };
            box.show= function(canvas){
                box.setCanvas(canvas);
                // don't add/remove this rectangle to the canvas resizeHandles. This rect isn't responsible for any hitTest or
                // dragDrop operation
                //canvas.resizeHandles.add(box);
                box.shape.toFront();
            };
            box.show(canvas);
            
            figure.selectionHandles.each(function(i,e){
                e.setDimension(6,6);
                e.setRadius(0);
                e.setColor("#3EB3F8");
                e.setBackgroundColor("#ffffff");
                e.shape.toFront();
             });

            figure.selectionHandles.add(box);
        }
        this.moved(canvas,figure);
   },
   /**
    * @method
    * Callback if the figure has been moved
    * 
    * @param figure
    * 
    * @template
    */
   moved: function(canvas, figure){
       if(figure.selectionHandles.isEmpty()){
           return; // silently
       }
       this._super(canvas, figure);
       
       var box= figure.selectionHandles.get(figure.selectionHandles.getSize()-1); 
       box.setPosition(figure.getPosition().translate(-4,-4));
       box.setDimension(figure.getWidth()+8, figure.getHeight()+8);
    }
   
});

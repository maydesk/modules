/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.policy.figure.LineSelectionFeedbackPolicy
 * 
 *
 * @author Andreas Herz
 * @extends draw2d.policy.figure.SelectionFeedbackPolicy
 */
draw2d.policy.figure.LineSelectionFeedbackPolicy = draw2d.policy.figure.SelectionFeedbackPolicy.extend({

    NAME : "draw2d.policy.figure.LineSelectionFeedbackPolicy",
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
    onSelect: function(canvas, figure, isPrimarySelection){
        if(figure.selectionHandles.isEmpty()){
            figure.selectionHandles.add( new draw2d.shape.basic.LineStartResizeHandle(figure));
            figure.selectionHandles.add( new draw2d.shape.basic.LineEndResizeHandle(figure));

            figure.selectionHandles.each(function(i,e){
                e.setDraggable(figure.isResizeable());
                e.show(canvas);
            });
        }
        this.moved(canvas, figure);
    },
    
    /**
     * @method
     * Callback method if the figure has been moved.
     * 
     * @template
     */
    moved: function(canvas,figure){
        if(figure.selectionHandles.isEmpty()){
            return; // silently
        }
        
        var resizeHandleStart = figure.selectionHandles.get(0);
        var resizeHandleEnd = figure.selectionHandles.get(1);

        var resizeWidthHalf = resizeHandleStart.getWidth()/2;
        var resizeHeightHalf= resizeHandleStart.getHeight()/2;
        
        var startPoint = figure.getStartPoint();
        var endPoint   = figure.getEndPoint();
        
        resizeHandleStart.setPosition(startPoint.x-resizeWidthHalf,startPoint.y-resizeHeightHalf);
        resizeHandleEnd.setPosition(endPoint.x-resizeWidthHalf,endPoint.y-resizeHeightHalf);
    }
    
});

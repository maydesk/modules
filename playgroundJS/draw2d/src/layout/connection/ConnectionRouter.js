/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.layout.connection.ConnectionRouter
 * Routes a {@link draw2d.Connection}, possibly using a constraint.
 *
 * @author Andreas Herz
 */
draw2d.layout.connection.ConnectionRouter = Class.extend({
    NAME : "draw2d.layout.connection.ConnectionRouter",

	/**
	 * @constructor 
	 * Creates a new Router object
	 */
    init: function(){
    },
    

    /**
     * @method
     * Returns the direction for the connection in relation to the given port and it's parent.
     * 
     * <p>
     * Possible values:
     * <ul>
     *   <li>up -&gt; 0</li>
     *   <li>right -&gt; 1</li>
     *   <li>down -&gt; 2</li>
     *   <li>left -&gt; 3</li>
     * </ul>
     * <p>
     * 
     * @param {draw2d.Connection} conn the connection with the end port to examine
     * 
     * @return {Number} the direction
     */
    getEndDirection:function( conn)
    {
       var p = conn.getEndPoint();
       var rect = conn.getTarget().getParent().getBoundingBox();
       
       return rect.getDirection(p);
    },
    
    
    /**
     * @method
     * Returns the **direction** for the connection in relation to the given port and it's parent.
     * 
     * <p>
     * Possible values:
     * <ul>
     *   <li>up -&gt; 0</li>
     *   <li>right -&gt; 1</li>
     *   <li>down -&gt; 2</li>
     *   <li>left -&gt; 3</li>
     * </ul>
     * <p>
     * 
     * @param {draw2d.Connection} conn the connection with the start port to examine
     * 
     * @return {Number} the direction.
     */
    getStartDirection:function( conn)
    {
       var p = conn.getStartPoint();
       var rect = conn.getSource().getParent().getBoundingBox();
       return rect.getDirection(p);
    },
    
    /**
     * @method
     * Routes the Connection.
     * 
     * @param {draw2d.Connection} connection The Connection to route
     * @template
     */
    route:function( connection)
    {
    	throw "subclasses must implement the method [ConnectionRouter.route]";
    }   
});
/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************//**
 * @class draw2d.layout.connection.SplineConnectionRouter 
 * 
 * A MannhattanConnectionRouter with an spline interpolation between the bend points.
 * 
 * @inheritable
 * @author Andreas Herz
 * @extends draw2d.layout.connection.ManhattanConnectionRouter
 */
draw2d.layout.connection.SplineConnectionRouter = draw2d.layout.connection.ManhattanConnectionRouter.extend({

	NAME : "draw2d.layout.connection.SplineConnectionRouter",

    /**
     * @constructor Creates a new Router object
     */
    init : function()
    {
//        this.spline = new draw2d.util.spline.CatmullRomSpline();
        this.spline = new draw2d.util.spline.CubicSpline();
//        this.spline = new draw2d.util.spline.BezierSpline();
        
        this.MINDIST = 50,
        this.cheapRouter = null;
    },

    route : function(conn)
    {
    	var i=0;
		var fromPt  = conn.getStartPoint();
		var fromDir = this.getStartDirection(conn);

		var toPt  = conn.getEndPoint();
		var toDir = this.getEndDirection(conn);

		// calculate the manhatten bend points between start/end.
		//
		this._route(conn, toPt, toDir, fromPt, fromDir);

        var ps = conn.getPoints();

        // align the point a little bit
        // (required for CatmullRomSpline)
        //
        /*
            var clone = new draw2d.util.ArrayList();
            var length = ps.getSize()-2;
            clone.add(ps.get(0));
            for(i=0 ; i<length; i++){
                var p1= ps.get(i);
                var p2= ps.get(i+1).clone();
                var p3= ps.get(i+2);
                if(p1.x != p2.x){
                    p2.x = p2.x + (p1.x-p2.x)/4;
                    p2.y = p2.y + (p3.y-p2.y)/4;
                }
                else{
                    p2.x = p2.x + (p3.x-p2.x)/4;
                    p2.y = p2.y + (p1.y-p2.y)/4;
                }
                clone.add(p2);
            }
            clone.add(ps.get(ps.getSize()-1));
            ps = clone;
        */
        
        conn.oldPoint=null;
        conn.lineSegments = new draw2d.util.ArrayList();
        conn.basePoints   = new draw2d.util.ArrayList();
 
        var splinePoints = this.spline.generate(ps,8);
        splinePoints.each(function(i,e){
            conn.addPoint(e);
        });
        
        // calculate the path string for the SVG rendering
        //
        var ps = conn.getPoints();
        length = ps.getSize();
        var p = ps.get(0);
        var path = ["M",p.x," ",p.y];
        for( i=1;i<length;i++){
              p = ps.get(i);
              path.push("L", p.x, " ", p.y);
        }
        conn.svgPathString = path.join("");
    }
});
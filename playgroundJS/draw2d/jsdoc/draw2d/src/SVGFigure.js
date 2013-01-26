/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.SVGFigure
 * Abstract class which can handle plain SVG content. Inherit class must override the method
 * <code>getSVG()</code>.
 * 
 * @author Andreas Herz
 * @extends draw2d.shape.basic.Rectangle
 */
draw2d.SVGFigure = draw2d.SetFigure.extend({
    
    NAME : "draw2d.SVGFigure",

    /**
     * @constructor
     * Creates a new figure element which are not assigned to any canvas.
     * 
     */
    init: function(width, height) {
      this._super(width, height);

    },

    /**
     * @private
     * @returns
     */
    createSet : function() {
		return this.importSVG(this.canvas, this.getSVG());
	},
    
    /**
     * @private
     */
    importSVG : function (canvas, rawSVG) {
      
      var set = canvas.paper.set();
       
      try {
        if (typeof rawSVG === 'undefined'){
          throw 'No data was provided.';
        }
        
        rawSVG = rawSVG.replace(/\n|\r|\t/gi, '');
        
        if (!rawSVG.match(/<svg(.*?)>(.*)<\/svg>/i)){
          throw "The data you entered doesn't contain valid SVG.";
        }
        
        // Override the dimension from the JSON if the SVG contains any
        //
        var findDim   = new RegExp('<svg width="(.*?)" height="(.*?)" .*?>','gi');
        var match=findDim.exec(rawSVG);
        if(match){
            this.setDimension(parseInt(match[1],10), parseInt(match[2],10));
        }
        
        var findAttr  = new RegExp('([a-z0-9\-]+)="(.*?)"','gi');
        var findStyle = new RegExp('([a-z0-9\-]+) ?: ?([^ ;]+)[ ;]?','gi');
        //var findNodes = new RegExp('<(line|rect|polyline|circle|ellipse|path|polygon|image|text).*?\/>','gi');
        var findNodes = new RegExp('<(line|rect|polyline|circle|ellipse|path|polygon|image|text).*?(\/>|.*</text>)','gi');
               
        while(match = findNodes.exec(rawSVG)){      
          var shape=null;
          var style=null;
          var attr = { };
          var node = RegExp.$1;
          while(findAttr.exec(match)){
            switch(RegExp.$1) {
              case 'stroke-dasharray':
                attr[RegExp.$1] = '- ';
              break;
              case 'style':
                style = RegExp.$2;
              break;
              default:
                attr[RegExp.$1] = RegExp.$2;
              break;
            }
          }
          
          
          if ( style !== null){
            while(findStyle.exec(style)){
              attr[RegExp.$1] = RegExp.$2;
            }
          }
          
          if (typeof attr['stroke-width'] === 'undefined'){
              attr['stroke-width'] = (typeof attr.stroke === 'undefined' ? 0 : 1);
          }
          
          switch(node) {
            case 'rect':
              shape = canvas.paper.rect();
              break;
            case 'circle':
              shape = canvas.paper.circle();
              break;
            case 'ellipse':
              shape = canvas.paper.ellipse();
              break;
            case 'path':
              attr.fill ="none";
              shape = canvas.paper.path(attr.d);
              break;
            case 'line':
              attr.d= "M "+attr.x1+" "+attr.y1+"L"+attr.x2+" "+attr.y2;
              attr.fill ="none";
              shape = canvas.paper.path(attr.d);
             break;
            case 'polyline':
              var path = attr.points;
              attr.d = "M "+path.replace(" "," L");
              shape = canvas.paper.path(attr.d);
              break;
            case 'polygon':
              shape = canvas.paper.polygon(attr.points);
              break;
            case 'image':
              shape = canvas.paper.image();
              break;
            case 'text':
              shape = canvas.paper.text();
              attr["text-anchor"] = "start";
              attr.y= parseInt(attr.y,10)+shape.getBBox().height/2;
              break;
          }
          if(shape!==null){
              shape.attr(attr);
              set.push(shape);
          }
        }
      } catch (error) {
        alert('The SVG data you entered was invalid! (' + error + ')');
      }
      return set;
    }

});
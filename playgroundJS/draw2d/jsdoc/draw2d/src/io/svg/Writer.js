/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/

/**
 * @class draw2d.io.svg.Writer
 * 
 * Serialize the canvas document into a SVG document.
 * 
 *      // Create a SVG writer and convert the canvas into a SVG document.
 *      //
 *      var writer = new draw2d.io.svg.Writer();
 *      var svg = writer.marshal(canvas);
 *      
 *      // insert the svg string into a DIV for preview or post
 *      // it via ajax to the server....
 *      $("#svg").text(svg);
 *
 * 
 * @author Andreas Herz
 * @extends draw2d.io.Writer
 */
draw2d.io.svg.Writer = draw2d.io.Writer.extend({
    
    init:function(){
        this._super();
    },
    
    /**
     * @method
     * Export the content of the canvas into SVG. The SVG document can be loaded with Inkscape or any other SVG Editor.
     * 
      * @param {draw2d.Canvas} canvas
     * @returns {String} the SVG document
     */
    marshal: function(canvas){
        var s =canvas.getCurrentSelection();
        canvas.setCurrentSelection(null);
        var svg = canvas.getHtmlContainer().html()
                     .replace(/>\s+/g, ">")
                     .replace(/\s+</g, "<");
        svg = this.formatXml(svg);
        svg = svg.replace(/<desc>.*<\/desc>/g,"<desc>Create with draw2d JS graph library and RaphaelJS</desc>");
        
        canvas.setCurrentSelection(s);
        return svg;
    }
});
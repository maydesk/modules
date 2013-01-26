/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/

/**
 * @class draw2d.io.png.Writer
 * Convert the canvas document into a PNG Image.
 * 
 *     // example how to create a PNG image and set an 
 *     // image src attribute.
 *     //
 *     var writer = new draw2d.io.png.Writer();
 *     var png = writer.marshal(canvas);
 *     $("#preview").attr("src",png);
 *
 * @author Andreas Herz
 * @extends draw2d.io.Writer
 */
draw2d.io.png.Writer = draw2d.io.Writer.extend({
    
    init:function(){
        this._super();
    },
    
    /**
     * @method
     * Export the content to the implemented data format. Inherit class implements
     * content specific writer.
     * 
     * @param {draw2d.Canvas} canvas
     * @returns {String} base64 formated image in the format <strong><code>data:image/png;base64,iVBORw0KGg...</code></strong>
     */
    marshal: function(canvas){
        
        var svg = canvas.getHtmlContainer().html().replace(/>\s+/g, ">").replace(/\s+</g, "<");

        var canvasDomNode = $('<canvas id="canvas" width="1000px" height="600px"></canvas>');
        $('body').append(canvasDomNode);
        canvg('canvas', svg, { ignoreMouse: true, ignoreAnimation: true});

        var img = document.getElementById('canvas').toDataURL("image/png");
        canvasDomNode.remove();
        return img;
    }
});
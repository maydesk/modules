/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.io.Writer
 * Serialize the canvas to an external format. This is only a template/interface class.
 * Inherit classes must implement the export format.
 * 
 * @author Andreas Herz
 */
draw2d.io.Writer = Class.extend({
    
    /**
     * @constructor
     * @private
     */
    init:function(){
        
    },
    
    /**
     * @method
     * Export the content to the implemented data format. Inherit class implements
     * content specific writer.
     * 
     * @template
     * @param {draw2d.Canvas} canvas
     * @returns {Object}
     */
    marshal: function(canvas){
        
        return "";
    },
    
    /**
     * @method
     * utility method to format a given XML string.
     * 
     * @param xml
     * @returns {String}
     */
    formatXml: function(xml) {
        var formatted = '';
        var reg = /(>)(<)(\/*)/g;
        xml = xml.replace(reg, '$1\r\n$2$3');
        var pad = 0;
        jQuery.each(xml.split('\r\n'), function(index, node) {
            var indent = 0;
            if (node.match( /.+<\/\w[^>]*>$/ )) {
                indent = 0;
            } else if (node.match( /^<\/\w/ )) {
                if (pad != 0) {
                    pad -= 1;
                }
            } else if (node.match( /^<\w[^>]*[^\/]>.*$/ )) {
                indent = 1;
            } else {
                indent = 0;
            }

            var padding = '';
            for (var i = 0; i < pad; i++) {
                padding += '  ';
            }

            formatted += padding + node + '\r\n';
            pad += indent;
        });

        return formatted;
    }
});
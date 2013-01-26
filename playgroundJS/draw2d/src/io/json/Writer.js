/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/

/**
 * @class draw2d.io.json.Writer
 * Serialize the canvas document into a JSON object which can be read from the corresponding
 * {@link draw2d.io.json.Reader}.
 * 
 *      // Create a JSON writer and convert it into a JSON-String representation.
 *      //
 *      var writer = new draw2d.io.json.Writer();
 *      var json = writer.marshal(canvas);
 *      
 *      // convert the json object into string repesentation
 *      var jsonTxt = JSON.stringify(json,null,2);
 *      
 *      // insert the json string into a DIV for preview or post
 *      // it via ajax to the server....
 *      $("#json").text(jsonTxt);
 *
 * 
 * @author Andreas Herz
 * @extends draw2d.io.Writer
 */
draw2d.io.json.Writer = draw2d.io.Writer.extend({
    
    init:function(){
        this._super();
    },
    
    /**
     * @method
     * Export the content to the implemented data format. Inherit class implements
     * content specific writer.
     * 
      * @param {draw2d.Canvas} canvas
     * @returns {Object}
     */
    marshal: function(canvas){
        
        var result = [];
        var figures = canvas.getFigures();
        var i =0;
        var f= null;
        
        // conventional iteration over an array
        //
        for(i=0; i< figures.getSize(); i++){
            f = figures.get(i);
            result.push(f.getPersistentAttributes());
        }
        
        // jQuery style to iterate
        //
        var lines = canvas.getLines();
        lines.each(function(i, element){
            result.push(element.getPersistentAttributes());
        });
        
        return result;
    }
});
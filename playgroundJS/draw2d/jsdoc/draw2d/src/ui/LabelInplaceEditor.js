/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************//**
 * @class draw2d.ui.LabelInplaceEditor
 * 
 * Inplace editor for draw2d.shape.base.Label 
 * 
  * @author Andreas Herz
 * @extends draw2d.ui.LabelEditor
*/

draw2d.ui.LabelInplaceEditor =  draw2d.ui.LabelEditor.extend({
    
    /**
     * @constructor
     * @private
     */
    init: function(){
        this._super();
    },
    
    /**
     * @method
     * Trigger the edit of the label text.
     * 
     * @param {draw2d.shape.basic.Label} label the label to edit
     */
    start: function( label){
        this.label = label;

        this.commitCallback = $.proxy(this.commit,this);
        
        // commit the editor if the user clicks anywhere in the document
        //
        $("body").bind("click",this.commitCallback);
      
        // append the input field to the document and register 
        // the ENTER and ESC key for commit /cancel the operation
        //
        this.html = $('<input id="inplaceeditor">');
        this.html.val(label.getText());
        this.html.hide();
        
        $("body").append(this.html);
        
        this.html.autoResize({animate:false});
        
        this.html.bind("keyup",$.proxy(function(e){
            switch (e.which) {
            case 13:
                 this.commit();
                 break;
            case 27:
                this.cancel();
                 break;
           }
         },this));
        
         this.html.bind("blur",this.commitCallback);
         
         // avoid commit of the operation if we click inside the editor
         //
         this.html.bind("click",function(e){
             e.stopPropagation();
             e.preventDefault();
         });

        // Position the INPUT and init the autoresize of the element
        //
        var canvas = this.label.getCanvas();
        var bb = this.label.getBoundingBox();

        bb.setPosition(canvas.fromCanvasToDocumentCoordinate(bb.x,bb.y));

        // remove the scroll from the body if we add the canvas directly into the body
        var scrollDiv = canvas.getScrollArea();
        if(scrollDiv.is($("body"))){
           bb.translate(canvas.getScrollLeft(), canvas.getScrollTop());
        }
        
        bb.translate(-1,-1);
        bb.resize(2,2);
        
        this.html.css({position:"absolute",top: bb.y, left:bb.x, "min-width":bb.w, height:bb.h});
        this.html.fadeIn($.proxy(function(){
            this.html.focus();
        },this));
    },
    
    /**
     * @method
     * Transfer the data from the editor into the label.<br>
     * Remove the editor.<br>
     * @private
     */
    commit: function(){
        this.html.unbind("blur",this.commitCallback);
        $("body").unbind("click",this.commitCallback);
        var label = this.html.val();
        this.label.setText(label);
        this.html.fadeOut($.proxy(function(){
            this.html.remove();
            this.html = null;
        },this));
    },
    
    /**
     * @method
     * Transfer the data from the editor into the label.<br>
     * Remove the editor.<br>
     * @private
     */
    cancel: function(){
        this.html.unbind("blur",this.commitCallback);
        $("body").unbind("click",this.commitCallback);
        this.html.fadeOut($.proxy(function(){
            this.html.remove();
            this.html = null;
            
        },this));
        
    }
});


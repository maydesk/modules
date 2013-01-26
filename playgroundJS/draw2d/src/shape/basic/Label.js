/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.shape.basic.Label
 * Implements a simple text label.
 * 
 * See the example:
 *
 *     @example preview small frame
 *     
 *     var shape =  new draw2d.shape.basic.Label("This is a simple label");
 *          
 *     canvas.addFigure(shape,40,10);
 *     
 * @author Andreas Herz
 * 
 * @class draw2d.SetFigure
 */
draw2d.shape.basic.Label= draw2d.SetFigure.extend({

	NAME : "draw2d.shape.basic.Label",

    /**
     * @constructor
     * Creates a new text element.
     * 
     * @param {String} [text] the text to display
     */
    init : function(text)
    {
        this._super();
        
        if(typeof text === "string"){
    		this.text = text;
    	}
    	else{
    		this.text = "";
    	}
    	
        // appearance of the shape
        //
        this.fontSize = 12;
        this.fontColor = new draw2d.util.Color("#080808");
        this.padding = 4;
        
        // set the border width
        this.setStroke(1);
        
        // behavior of the shape
        //
        this.editor = null;
        
        this.installEditPolicy(new draw2d.policy.figure.AntSelectionFeedbackPolicy());
    },
    
    /** 
     * @method
     * Creates the shape object for a text node.
     * 
     * @template
     **/
    createSet : function()
    {
    	return this.canvas.paper.text(0, 0, this.text);
    },

    /**
     * @method
     * Trigger the repaint of the element and transport all style properties to the visual representation.<br>
     * Called by the framework.
     * 
     * @template
     **/
    repaint: function(attributes)
    {
        if(this.repaintBlocked===true || this.shape===null){
            return;
        }

        if(typeof attributes === "undefined"){
            attributes = {};
        }
        
        // style the label
        var lattr = {};
        lattr.text = this.text;
        lattr.x = this.padding;
        lattr.y = this.getHeight()/2;
        lattr["text-anchor"] = "start";
        lattr["font-size"] = this.fontSize;
        lattr.fill = this.fontColor.hash();
        this.svgNodes.attr(lattr);

        this._super(attributes);
    },
    
    /**
     * @private
     */
    applyTransformation:function(){
        this.shape.transform(
                "R"+
                this.rotationAngle);
        this.svgNodes.transform(
                "R"+
                this.rotationAngle+
                "T" + this.getAbsoluteX() + "," + this.getAbsoluteY());
    },

    
    /**
     * @method
     * A Label is not resizeable. In this case this method returns always <b>false</b>.
     * 
     * @returns Returns always false in the case of a Label.
     * @type boolean
     **/
    isResizeable:function()
    {
      return false;
    },
       
    
    /**
     * @method
     * Set the new font size in [pt].
     *
     * @param {Number} size The new font size in <code>pt</code>
     **/
    setFontSize: function( size)
    {
      this.fontSize = size;
      this.repaint();
    },
    
    
    /**
     * @mehod
     * Set the color of the font.
     * 
     * @param {draw2d.util.Color/String} color The new color of the line.
     **/
    setFontColor:function( color)
    {
      if(color instanceof draw2d.util.Color){
          this.fontColor = color;
      }
      else if(typeof color === "string"){
          this.fontColor = new draw2d.util.Color(color);
      }
      else{
          // set good default
          this.fontColor = new draw2d.util.Color(0,0,0);
      }
      this.repaint();
    },

    /**
     * @method
     * The current used font color
     * 
     * @returns {draw2d.util.Color}
     */
    getFontColor:function()
    {
      return this.fontColor;
    },
    
    /**
     * @method
     * Set the padding of the element
     *
     * @param {Number} padding The new padding
     **/
    setPadding: function( padding)
    {
      this.padding = padding;
      this.repaint();
    },
    
    /**
     * @method
     * A Label did have "autosize". Do nothing at all.
     *
     **/
    setDimension:function(/*:int*/ w, /*:int*/ h)
    {
        // Don't call the _super method here.
        // Dimension of a Label is autocalculated. "set" is not possible
    },
    
    /**
     * @method
     * This value is relevant for the interactive resize of the figure.
     *
     * @return {Number} Returns the min. width of this object.
     */
    getMinWidth:function()
    {
        // the minimum width of a label is always the required width.
        return this.getWidth();
    },
    
    /**
     * @method
     * This value is relevant for the interactive resize of the figure.
     *
     * @return {Number} Returns the min. width of this object.
     */
    getMinHeight:function()
    {
        // the minimum height of a label is always the required height.
        return this.getHeight();
    },
    
    /**
     * @method
     * Set an editor for the label. This can be a dialog or inplace editor for the 
     * Text.<br>
     * The editor will be activated if you doubleClick on the label.
     * 
     * @param {draw2d.ui.LabelEditor} editor
     */
    installEditor: function( editor ){
      this.editor = editor;  
    },
    
    /**
     * @method
     * Called when a user dbl clicks on the element
     * 
     * @template
     */
    onDoubleClick: function(){
        if(this.editor!==null){
            this.editor.start(this);
        }
    },
    
    
    /**
     * @method
     * Returns the current text of the label.
     *
     * @returns the current display text of the label
     * @type String
     **/
    getText:function()
    {
      return this.text;
    },
    
    /**
     * @method
     * Set the text for the label. Use \n for multiline text.
     * 
     * @param {String} text The new text for the label.
     **/
    setText:function(/*:String*/ text )
    {
      this.text = text;
      
      this.fireMoveEvent();
      
      // Update the resize handles if the user change the position of the element via an API call.
      //
      this.editPolicy.each($.proxy(function(i,e){
         if(e instanceof draw2d.policy.figure.DragDropEditPolicy){
             e.moved(this.canvas, this);
         }
      },this));
      
      this.repaint();
    },
    
    /**
     * @method
     * Return the calculate width of the set. This calculates the bounding box of all elements.
     * 
     * @returns the calculated width of the label
     * @return {Number}
     **/
	getWidth : function() {
		if (this.shape === null) {
			return 0;
		}
		return this.svgNodes.getBBox(true).width+2*this.padding;
	},
    
    /**
     * @method
     * Return the calculated height of the set. This calculates the bounding box of all elements.
     * 
	 * @returns the calculated height of the label
	 * @return {Number}
	 */
    getHeight:function()
    {
        if (this.shape === null) {
            return 0;
        }
        return this.svgNodes.getBBox(true).height+2*this.padding;
    },

    hitTest: function(x, y) {
        // rotate the box with the currectn matrix of the
        // shape
        var matrix = this.shape.matrix;
        var points = this.getBoundingBox().getPoints();
        points.each(function(i,point){
            var x = matrix.x(point.x,point.y);
            var y = matrix.y(point.x,point.y);
            point.x=x;
            point.y=y;
        });

        var polySides=4;
        var i=0;
        var j=polySides-1 ;
        var oddNodes=false;

        for (i=0; i<polySides; i++) {
            var pi = points.get(i);
            var pj = points.get(j);
            if ((pi.y< y && pj.y>=y
            ||   pj.y< y && pi.y>=y)
            &&  (pi.x<=x || pj.x<=x)) {
              if (pi.x+(y-pi.y)/(pj.y-pi.y)*(pj.x-pi.x)<x) {
                oddNodes=!oddNodes; }}
            j=i; }
        return oddNodes; 
     },
     

     /**
      * @method 
      * Return an objects with all important attributes for XML or JSON serialization
      * 
      * @returns {Object}
      */
     getPersistentAttributes : function()
     {
         var memento = this._super();
         
         memento.text = this.text;
         
         return memento;
     },
     
     /**
      * @method 
      * Read all attributes from the serialized properties and transfer them into the shape.
      * 
      * @param {Object} memento
      * @returns 
      */
     setPersistentAttributes : function(memento)
     {
         this._super(memento);
         if(typeof memento.text !=="undefined"){
             this.setText(memento.text);
         }
     }

});





example.Toolbar = Class.extend({
	
	init:function(elementId, view){
        $( "#radio" ).buttonset();
        this.view = view;
        
        $('#radio>input').click(function() {
            var policy = eval("new "+$(this).data("policy")+"()");
            view.installEditPolicy(policy);
        });

        // Inject the DELETE Button
        //
        this.deleteButton  = $("<button>Delete</button>");
        $('#radio').append(this.deleteButton);
        this.deleteButton.button().click($.proxy(function(){
            var node = this.view.getCurrentSelection();
            var command= new draw2d.command.CommandDelete(node);
            this.view.getCommandStack().execute(command);
        },this)).button( "option", "disabled", true );
        

        // Register a Selection listener for the state hnadling
        // of the Delete Button
        //
        view.addSelectionListener(this);
        
	},
	
    
    /**
     * @method
     * Called if the selection in the cnavas has been changed. You must register this
     * class on the canvas to receive this event.
     * 
     * @param {draw2d.Figure} figure
     */
    onSelectionChanged : function(figure){
        this.deleteButton.button( "option", "disabled", figure===null );
    }
});
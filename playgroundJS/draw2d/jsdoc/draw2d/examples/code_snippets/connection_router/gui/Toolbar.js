
example.Toolbar = Class.extend({
	
	init:function(elementId, view){
        $( "#radio" ).buttonset();
        this.view = view;
       
        $('#radio>input').click(function() {

            var router = eval("new "+$(this).data("router")+"()");
          
            view.getLines().each(function(i,line){
                line.setRouter(router);
            });
        });
	}
});
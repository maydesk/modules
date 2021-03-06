if (!Core.get(window, ["MD", "Sync"])) {
        Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDToolEntry = Core.extend(Echo.Component, {

		$load : function() {
        	Echo.ComponentFactory.registerType("MDToolEntry", this);
		},		
		componentType : "MDToolEntry",		
});
 
 
PD.Sync.MDToolEntry = Core.extend(Echo.Sync.Button, {

    $load: function() {
        Echo.Render.registerPeer("MDToolEntry", this);        
    },
    
    $virtual: {
		doAction: function() {
			//add a new instance of the tool to the canvas
			//the element is only added as component (because of the Echo update life cycle)
			//but not visible yet on the canvas
			//this happens only when the user clicks somewhere inside the canvas
			var tool = this.component.render("tool");
			var component = eval("new " + tool + "();");			
			var canvas = this.component.parent.render("canvas");
			canvas.setCurrentTool(component);
        }
	}    
});

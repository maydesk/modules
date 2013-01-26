/*****************************************
 *   Library is under GPL License (GPL)
 *   Copyright (c) 2012 Andreas Herz
 ****************************************/
/**
 * @class draw2d.command.CommandCollection
 * 
 * A CommandCollection works a a single command. You can add more than one
 * Command to this CommandCollection and execute/undo them onto the CommandStack as a
 * single Command.
 *
 * @inheritable
 * @author Andreas Herz
 * 
 * @extends draw2d.command.Command
 */
draw2d.command.CommandCollection = draw2d.command.Command.extend({
    NAME : "draw2d.command.CommandCollection", 
    
    /**
     * @constructor
     * Create a new CommandConnect objects which can be execute via the CommandStack.
     *
     */
    init : function()
     {
       this._super("Execute Commands");
       
       this.commands = new draw2d.util.ArrayList();
    },
    
    
    /**
     * @method
     * Add a command to the collection.
     * 
     * @param {draw2d.command.Command} command
     */
    add: function(command){
    	this.commands.add(command);
    },
    
    /**
     * @method
     * Execute the command the first time
     * 
     **/
    execute:function()
    {
    	this.commands.each(function(i,cmd){
    	    cmd.execute();
    	});
    },
    
    /**
     * @method
     * Redo the command after the user has undo this command.
     *
     **/
    redo:function()
    {
        this.commands.each(function(i,cmd){
            cmd.redo();
        });
    },
    
    /** 
     * @method
     * Undo the command.
     *
     **/
    undo:function()
    {
        this.commands.each(function(i,cmd){
            cmd.undo();
        });
    }
});

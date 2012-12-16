/**
 * Command execution peer: Browser Redirect
 */
PDBrowserRedirectParent = Core.extend(Echo.RemoteClient.CommandExec, {
    
    $static: {
        /** @see Echo.RemoteClient.CommandExecProcessor#execute */
        execute: function(client, commandData) {
            if (!commandData.uri) {
                throw new Error("URI not specified in BrowserOpenWindowCommand.");
            }
            window.parent.location.href = commandData.uri;
        }
    },
    
    $load: function() {
        Echo.RemoteClient.CommandExecProcessor.registerPeer("net.profidesk.pdw.util.BrowserRedirectParentCommand", this);
    }                                                        
});

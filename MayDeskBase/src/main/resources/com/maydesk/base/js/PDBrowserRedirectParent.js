/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

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

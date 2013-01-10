/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

if (!Core.get(window, ["PD", "Sync"])) {
	Core.set(window, ["PD", "Sync"], {});
}

PD.PDChat = Core.extend(Echo.Component, {

	componentType: "PDChat",
	focusable: true,

	$load: function() {
		Echo.ComponentFactory.registerType("PDChat", this);
	},

	sendMessage: function() {
	    this.fireEvent({type: "sendMessage", source: this});
	}
});

PD.Sync.PDChat = Core.extend(Echo.Arc.ComponentSync, {

	$load: function() {
		Echo.Render.registerPeer("PDChat", this);
	},

	_colMessages: null,
	_txtMessage: null,

	createComponent: function() {
		// See below to see PDColumn's implementation
		this._colMessages = new PD.PDColumn({
			layoutData: {
				alignment: "bottom"
			}
		});
		this._addIncomingMessages();
		
		this._txtMessage = new Echo.TextArea({
			width: "100%",
			height: "4em", // This actually sets the height to approx. 3 lines.
			layoutData: {
				width: "100%"
			},
			events: {
				action: Core.method(this, this._sendMessage)
			}
		});

		return new Echo.SplitPane({
			orientation: Echo.SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP,
			separatorPosition: 60,
			children: [
				new Echo.Row({
					insets: "0px 6px 0px 0px",
					children: [
						new Echo.Label({
							icon: this.component.render("otherUserIcon")
						}), 
						this._txtMessage
					]
				}), 
				this._colMessages
			]
		});
	},

	_sendMessage: function(e) {
		var msg = this._txtMessage.get("text");
		this._txtMessage.set("text", "");
		this._appendMessage(msg, false);
		this.component.set("outgoingMsg", msg);
		this.component.sendMessage();
	},

	renderFocus: function() {
		var txtElem = document.getElementById(this._txtMessage.renderId);
		Core.Web.DOM.focusElement(txtElem);
	},

	_appendMessage: function(strMsg, isReceiving) {
		var currTime = new Date();

		var hours = currTime.getHours();
		hours = hours < 10 ? "0" + hours : hours;

		var mins = currTime.getMinutes();
		mins = mins < 10 ? "0" + mins : mins;

		var time = hours + ":" + mins;
		
		var row = new Echo.Row({
			cellSpacing: 6,
			insets: 1,
			children: [
				new Echo.Label({
					text: time,
					insets: "2px 0px",
					font: {
						typeface: "Verdana",
						style: "plain",
						size: 9,
					},
					layoutData: {
						alignment: "top"
					}
				}), 
				new Echo.Label({
					text: strMsg,
					lineWrap: true,
					font: {
						typeface: "Verdana",
						style: "plain",
						size: 11
					}
				})
			]
		});
		if (isReceiving) {
			row.set("background", Echo.Sync.Color.toHex(230, 230, 230));
		}
		this._colMessages.add(row);
	},
	
	_addIncomingMessages: function() {
		var incomingMessages = this.component.get("incomingMessages");
		if (!incomingMessages) {
			return;
		}

		var i = 0;
		do {
			this._appendMessage(incomingMessages[i], true);
		} while(incomingMessages[++i]);
		
		this.component.set("incomingMessages", null);
	},
	
	renderUpdate: function(update) {
		this._addIncomingMessages();
	}
});

// Dummy column implementation just for the purpose of updating the scrollbar's position 
// in the SplitPane. If the position is updated in the methods above the DOM is not up to date
// so it's not possible to set the scrollbar to the right position.
// I found no other way of achieving this.
PD.PDColumn = Core.extend(Echo.Column, {
	
	componentType: "PDColumn",
	
	$load: function() {
		Echo.ComponentFactory.registerType("PDColumn", this);
	}
});

PD.Sync.PDColumn = Core.extend(Echo.Sync.Column, {

    $load: function() {
        Echo.Render.registerPeer("PDColumn", this);
    },
    
    renderUpdate: function(update) {
    	Echo.Sync.ArrayContainer.prototype.renderUpdate.call(this, update);
    	var colMessagesDiv = document.getElementById(this.component.renderId);
		if (colMessagesDiv) {
			var messagesPaneDiv = colMessagesDiv.parentNode;
			messagesPaneDiv.scrollTop = messagesPaneDiv.scrollHeight;
		}
    }
});
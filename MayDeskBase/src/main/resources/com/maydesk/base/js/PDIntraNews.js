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
 
PD.PDIntraNews = Core.extend(Echo.Component, {

		$load : function() {
        	Echo.ComponentFactory.registerType("PDIntraNews", this);
		},		
		componentType : "PDIntraNews",		
});
 
 
PD.Sync.PDIntraNews = Core.extend(PD.Sync.PDDesktopItem, {

    $load: function() {
        Echo.Render.registerPeer("PDIntraNews", this);
    },
    
    _icon: null,
	_titleBar: null,
	_textArea: null,
	_toolbar: null,
	
    
    /** @see Echo.Render.ComponentSync#renderAdd */
    renderAddImpl: function(update, parentElement) {
        this._containerElement = parentElement;
        var icon = this.component.render("icon");
        this._title = this.component.render("title");
        var text = this.component.render("text");
	   	this._width = Echo.Sync.Extent.toPixels(this.component.render("width")); 
		this._height = Echo.Sync.Extent.toPixels(this.component.render("height"));

		//the main node
        this._node = document.createElement("div");
        this._node.id = this.component.renderId;
		this._node.style.position = "absolute";	
		this._node.style.width = "330px";
		this._node.style.height ="80px";
		this._node.style.zIndex = 0;
		//this._mainNode.style.cursor = "pointer";
		parentElement.appendChild(this._node);
		
		//the icon
		var nodeIcon = document.createElement("img");
	    nodeIcon.style.height = "30px";
		nodeIcon.style.width = "30px";
        Echo.Sync.ImageReference.renderImg(icon, nodeIcon);
   		this._node.appendChild(nodeIcon);

		//the title text
	    this._titleBar = document.createElement("div");
	   	this._titleBar.style.position = "absolute";
	    this._titleBar.style.top = "9px";
		this._titleBar.style.left = "35px";
	    this._titleBar.style.height = "20px";
		this._titleBar.style.width = "310px";
	    this._titleBar.style.fontSize = "20px";
	    this._titleBar.style.color = "#00ffff";
	    this._titleBar.appendChild(document.createTextNode(this._title));
	    this._node.appendChild(this._titleBar);

		//the mesage text
	    this._textArea = document.createElement("div");
	   	this._textArea.style.position = "absolute";
	    this._textArea.style.top = "36px";
		this._textArea.style.left = "0px";
	    this._textArea.style.height = "60px";
		this._textArea.style.width = "310px";
	    this._textArea.style.fontSize = "12px";
	    this._textArea.style.color = "#00ffff";
	    this._textArea.style.textAlign = "justify";
	    this._textArea.appendChild(document.createTextNode(text));
	    this._node.appendChild(this._textArea);

		//the toolbar
		this._toolbar = document.createElement("div");
	    this._toolbar.style.left = "40px";
		this._toolbar.style.width = "260px";
		this._toolbar.style.top = "0px";
        this._toolbar.style.textAlign = "right";
		this._toolbar.style.position = "absolute";
        var componentCount = this.component.getComponentCount();
        for (var i = 0; i < componentCount; ++i) {
            var child = this.component.getComponent(i);			
            Echo.Render.renderComponentAdd(update, child, this._toolbar);
        }
        this._node.appendChild(this._toolbar);
    },
    
    /** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {
        this._node = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
        return true;
    },
    
    /** see ... */
	redraw: function() {
	 	this._node.style.left = this._positionX + "px";
		this._node.style.bottom = this._positionY + "px";
	}, 
	
	/** @see PD.Sync.PDDesktopItem#onMouseOver */
    onMouseOver: function(mouseOver) {
		//not implemented
	}   
});

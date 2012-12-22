/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

/**
 * Component rendering peer: PDWindow.
 * This class should not be extended by developers, the implementation is subject to change.
 */
 
if (!Core.get(window, ["PD", "Sync"])) {
	Core.set(window, ["PD", "Sync"], {});
}
 
PD.PDWindowPane = Core.extend(Echo.WindowPane, {
	$load : function() {
		Echo.ComponentFactory.registerType("PDWindowPane", this);
	},
	componentType : "PDWindowPane",
	_sidebarDiv: null,
	_toolbarDiv: null,
	_sidebarShown: false,
	_sidebarExpanded: false,
	_sidebarWidth: null,
	_toolbar: null,
	_sidebar: null
});
 
 
PD.Sync.PDWindowPane = Core.extend(Echo.Sync.WindowPane, {

	$static: {
		SIDEBAR_PROPERTIES: { 
		  sidebarExpanded: true 
		},
		
		FadeAnimation: Core.extend(Extras.Sync.Animation, {
			$construct: function(c) {
				this._component = c;
				this.runTime = 600;
			},
			_component: null,	 
			/** @see Extras.Sync.Animation#init */
			init: function() { }, 
			step: function(progress) {
				var w = this._component._sidebarWidth;
				if (this._component._sidebarExpanded) {
					this._component._sidebarDiv.style.marginLeft = (0 - w + (progress * w)) + "px";
				} else {				
					this._component._sidebarDiv.style.marginLeft = (0 - (progress * w)) + "px";
				}
			},
			/** @see Extras.Sync.Animation#complete */
			complete: function(abort) {
				if (this._component._sidebarExpanded) {
					this._component._sidebarDiv.style.marginLeft = "0px";
				} else {				
					this._component._sidebarDiv.style.marginLeft = (-this._component._sidebarWidth) + "px";
				}
			}
		})
	},
	
	$load : function() {
		Echo.Render.registerPeer("PDWindowPane", this);
	},

/** this method overrides WindowPane.renderAdd */
	renderAdd: function(update, parentElement) {
		if(!this._toolbar){
			this._toolbar = this.component.getComponent(0);
			Core.Arrays.remove(this.component.children, this._toolbar);
			this._sidebar = this.component.getComponent(0);
			Core.Arrays.remove(this.component.children, this._sidebar);
		}
	
		//call the super method
		Echo.Sync.WindowPane.prototype.renderAdd.call(this, update, parentElement);
	
		//add the header toolbar
		this._toolbarDiv = document.createElement("div");
		this._toolbarDiv.style.cssText = "float:right;margin-right:12px;overflow:hidden;";
		this._toolbarDiv.style.zIndex = 10;
		this._controlDiv.appendChild(this._toolbarDiv);
		Echo.Render.renderComponentAdd(update, this._toolbar, this._toolbarDiv);			
		//work-around. otherwise the child events will not work,
		//as they are inhibited in _processTitleBarMouseDown()
		this._toolbarDiv._controlData = {};
	
		//add the sidebar	
		this._sidebarDiv = document.createElement("div");
		this._sidebarDiv.style.cssText = "margin-top:10px;position:absolute;z-index:11;overflow:hidden;";
		this._contentDiv.appendChild(this._sidebarDiv);
		Echo.Render.renderComponentAdd(update, this._sidebar, this._sidebarDiv);
				
		this._sidebarWidth = 200;
		var measuredWidth = new Core.Web.Measure.Bounds(this._sidebarDiv).width;
		if (measuredWidth && measuredWidth > 45) {
		this._sidebarWidth = measuredWidth - 22;
			this._sidebarDiv.style.marginLeft = (0 - this._sidebarWidth) + "px";
		} else {
			//sidebar is hidden
			this._sidebarDiv.style.marginLeft = "-100px";
		}		
		this._contentDiv.style.opacity = 0.96;
	},
		
	renderDisplay: function() {
		//call the super method
		Echo.Sync.WindowPane.prototype.renderDisplay.call(this);
		Echo.Render.renderComponentDisplay(this._toolbar);
		Echo.Render.renderComponentDisplay(this._sidebar);	
	},
		
		/** @see Echo.Render.ComponentSync#renderUpdate */
	renderUpdate: function(update) {
		if (update.hasAddedChildren() || update.hasRemovedChildren()) {
			// Children added/removed: perform full render.
		} else if (update.isUpdatedPropertySetIn(Echo.Sync.WindowPane.NON_RENDERED_PROPERTIES)) {
			// Do nothing.
			return false;
		} else if (update.isUpdatedPropertySetIn(Echo.Sync.WindowPane.PARTIAL_PROPERTIES_POSITION_SIZE)) {
			this._loadPositionAndSize();
			return false;
		} else if (update.isUpdatedPropertySetIn(Echo.Sync.WindowPane.PARTIAL_PROPERTIES)) {
			this._renderUpdateFrame();
			return false;
		} else if (update.isUpdatedPropertySetIn(PD.Sync.PDWindowPane.SIDEBAR_PROPERTIES)) {
			this._renderSidebarExpanded();
			return false;
		}

//      why do we need this stuff anyway??	
//		var element = this._div;
//		var containerElement = element.parentNode;
//		Echo.Render.renderComponentDispose(update, update.parent);
//		containerElement.removeChild(element);
//		this.renderAdd(update, containerElement);  //here occurs an error!!
		
		return true;
	},
		
	renderDispose: function(update) {
		this._overlayRemove();
		Core.Web.Event.removeAll(this._toolbarDiv);
		this._toolbarDiv = null;
		this._renderDisposeFrame();
		this._div = null;
		this._maskDiv = null;
		this._contentDiv = null;
		
		Echo.Render.renderComponentDispose(update, this._toolbar);
		this._toolbar.register(false); //unregister
		this._toolbar = null;
		
		Echo.Render.renderComponentDispose(update, this._sidebar);
		this._sidebar.register(false); //unregister
		this._sidebar = null;		
	},
	
	_renderSidebarExpanded: function() {
		this._sidebarExpanded = this.component.render("sidebarExpanded", false);			
		var fadeAnimation = new PD.Sync.PDWindowPane.FadeAnimation(this);
	 	fadeAnimation.start(); 	 	
	}
});

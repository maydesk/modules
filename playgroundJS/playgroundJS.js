/**
 * Echo.Application implementation.
 * Root namespace.
 */
MyDemoApp = Core.extend(Echo.Application, {

    $static: {
    
        /**
         * Global initialization method.  Creates/starts client/application in "rootArea" element of document.
         */
        init: function() {
            Core.Web.init();
            if (Echo.DebugConsole) {
                Echo.DebugConsole.install();
            }
            var app = new MyDemoApp();
            var client = new Echo.FreeClient(app, document.getElementById("rootArea"));
            client.addResourcePath("Echo", "lib/echo/");
            client.addResourcePath("Extras", "lib/extras/");
            client.init();
        },        
    },



    /** @see Echo.Application#init */
    init: function() {
    	
		var content2 = new Echo.ContentPane({
			backgroundImage : { 
				url: "img/back3.jpg", 
				repeat: "repeat-x" 
			}
		});
		this.rootComponent.add(content2);

		var canvas = new MD.MDCanvas({
			children: [
				new MD.MDToolEntry({
					icon: "img/text16.png",
					tool: "MD.MDText"
				}),
				new MD.MDToolEntry({
					icon: "img/arrow16.png",
					tool: "MD.MDArrow"
				}),
				new MD.MDToolEntry({
					icon: "img/rectangle16.png",
					tool: "MD.MDRectangle"
				}),
				new MD.MDToolEntry({
					icon: "img/Icon24Preferences.png",
					tool: "MD.MDTable"
				}),
				new MD.MDRectangle({

				})
			]
		});
		
		
		this.rootComponent.add(new MD.MDContext({
			title: "My Activity",
			icon: "img/ampel.png",
			positionX: "300px",
			positionY: "130px",
			height: "400px",
			width: "500px",			
			children: [
				canvas
			]			
		}));
		
		var shortcut = new PD.PDShortcut({
			title: "Release 0.3 Info",
			icon: "img/test10a.png",
			positionX: "150px",
			positionY: "110px"
		});
		this.rootComponent.add(shortcut);

		var shortcut = new PD.PDShortcut({
			title: "Shopping list for Marc",
			icon: "img/test8a.png",
			positionX: "180px",
			positionY: "210px"
		});
		this.rootComponent.add(shortcut);


		var shortcut = new PD.PDAvatar({
			title: "A. Nast",
			icon: "img/nast_01.jpg",
			positionX: "140px",
			positionY: "410px",
			color: "rgba(200, 0, 250, 0.15)",
			bubbleStatus: "Rnaana!",
			bubbleMessage: "Hi dude, how you doing?"
		});
		this.rootComponent.add(shortcut);

		
		this.rootComponent.add(new PD.PDIntraNews({
			icon: "img/petra.jpg",
			title: "CloudDesk 0.3 released!",
			text: "Hi Clouderos, we just released CloudDesk version 0.3!!! Now you can make live video chat with your friends and collegues! Check it out, just look for the new video chat icon in the Avatar context menu.",
			positionX: "60px",
			positionY: "90px",
		}));
		
    }
});

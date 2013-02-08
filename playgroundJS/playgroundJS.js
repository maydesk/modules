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
				new MD.MDText({
					positionX: 40,
					positionY: 20,
					size: 40,
					text: "Welcome to Barco Karlsruhe R&D",
					type: "banner"
				}),
				new MD.MDNewsTicker({
					positionX: 30,
					positionY: 670,
					text: "Aquariva presentation at AV Fair Amsterdam was a big success!",
					author: "Robert, 2 hours ago"
				}),
				new MD.MDText({
					positionX: 20,
					positionY: 65,
					size: 16,
					text: "Joke of the day"
				}),
				new MD.MDImage({
					positionX: 10,
					positionY: 90,
					src: "img/dilbert.png",
					width: 260,
					height: 90
				}),
				new MD.MDAvatar({
					positionX: 310,
					positionY: 140,
					src: "img/alex.png",
					text: "why 'Joke'? - we call that 'Release Planning' ... ;)" 
				}),
				new MD.MDArrow({
					positionX: 270,
					positionY: 110,
					width: 50,
					height: 20,
					size: 2
				}),
				
				new MD.MDAvatar({
					positionX: 200,
					positionY: 300,
					src: "img/petra.png",
					text: "I have birthday today! join breakfast at 9 o'clock at the tee kitchen" 
				}),

				new MD.MDImage({
					positionX: 250,
					positionY: 300,
					src: "img/birthday.png",
					width: 80,
					height: 80
				}),
				new MD.MDText({
					positionX: 850,
					positionY: 10,
					size: 14,
					text: "This week at Chez Andrea",
					type: "header"
				}),
				new MD.MDTable({
					positionX: 825,
					positionY: 35
				}),
				new MD.MDText({
					positionX: 860,
					positionY: 170,
					size: 14,
					text: "Team Activities",
					type: "banner"
				}),
				new MD.MDNewsBox({
					positionX: 790,
					positionY: 205,
					width: 120,
					height: 80,
					icon: "img/dept_manag.png",
					title: "Management",
					text: "Working on the next generation random buzzword generator"
				}),
				new MD.MDNewsBox({
					positionX: 915,
					positionY: 205,
					width: 120,
					height: 80,
					icon: "img/dept_tech.png",
					title: "BCMC",
					text: "Pushing hard for MVD release, targeting mid of February for delivery"
				}),				
				new MD.MDNewsBox({
					positionX: 790,
					positionY: 290,
					width: 120,
					height: 80,
					icon: "img/dept_sales.png",
					title: "Sales",
					text: "We are receiving many pre-orders for 2x2 MVD panels, so lets just get them delivered"
				}),
				new MD.MDNewsBox({
					positionX: 915,
					positionY: 290,
					width: 120,
					height: 80,
					icon: "img/dept_dev.png",
					title: "Service",
					text: "The liquid cooling problem seems to be under control - thanks God!"
				}),
				new MD.MDImage({
					positionX: 920,
					positionY: 600,
					src: "img/barco.png",
					width: 120,
					height: 120
				}),
				
				//About this wall
				
				//This wall is intended for exploring new appplication cases
				//for 
				
				//Hardware is getting more and more commoditized. That is
				//why Barco needs to focus more on services, system integration
				//and innovation beyond the product   
			]
		});
		
		var toolbar = new MD.MDCanvasToolbar({
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
			]
		});
		
		this.rootComponent.add(new MD.MDContext({
			title: "My Activity",
			icon: "img/ampel.png",
			positionX: "60px",
			positionY: "60px",
			width: "700px",			
			height: "520px",
			children: [
				canvas,
				toolbar,
				new Echo.Row()
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

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
				new MD.MDText({
					positionX: 40,
					positionY: 20,
					size: 20,
					text: "Welcome to Barco Karlsruhe R&D",
					type: "banner"
				}),
				new MD.MDNewsTicker({
					positionX: 30,
					positionY: 415,
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
				new MD.MDText({
					positionX: 560,
					positionY: 10,
					size: 14,
					text: "Cantine menu",
					type: "header"
				}),
				new MD.MDTable({
					positionX: 475,
					positionY: 35
				}),
				
				new MD.MDAvatar({
					positionX: 100,
					positionY: 300,
					src: "img/petra.png",
					text: "I have birthday today! join breakfast at 9 o'clock at the tee kitchen" 
				}),

				new MD.MDImage({
					positionX: 150,
					positionY: 300,
					src: "http://openclipart.org/image/250px/svg_to_png/14860/nicubunu_Chocolate_birthday_cake.png",
					width: 80,
					height: 80
				}),
				
				new MD.MDImage({
					positionX: 590,
					positionY: 370,
					src: "img/barco.png",
					width: 100,
					height: 100
				}),

				new MD.MDText({
					positionX: 520,
					positionY: 165,
					size: 14,
					text: "Team Activities",
					type: "banner"
				}),

				new MD.MDNewsBox({
					positionX: 440,
					positionY: 195,
					width: 120,
					height: 80,
					icon: "img/dept_manag.png",
					title: "Management",
					text: "Working on the next generation random buzzword generator"
				}),
				new MD.MDNewsBox({
					positionX: 565,
					positionY: 195,
					width: 120,
					height: 80,
					icon: "img/dept_tech.png",
					title: "BCMC",
					text: "Pushing hard for MVD release, targeting mid of February for delivery"
				}),				
				new MD.MDNewsBox({
					positionX: 440,
					positionY: 280,
					width: 120,
					height: 80,
					icon: "img/dept_sales.png",
					title: "Sales",
					text: "We are receiving many pre-orders for 2x2 MVD panels, so lets just get them delivered"
				}),
				new MD.MDNewsBox({
					positionX: 565,
					positionY: 280,
					width: 120,
					height: 80,
					icon: "img/dept_dev.png",
					title: "Service",
					text: "The liquid cooling problem seems to be under control - thanks God!"
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

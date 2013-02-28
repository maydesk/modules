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
			zoom: 50,
			children: [
				new MD.MDText({
					positionX: 30,
					positionY: 45,
					size: 20,
					text: "Reception",
					type: 1
				}),
				new MD.MDImage({
					positionX: 30,
					positionY: 110,
					src: "http://www.telassistant.com/blog/wp-content/uploads/receptionist_full.jpg",
					width: 240,
					height: 226
				}),
				new MD.MDText({
					positionX: 340,
					positionY: 45,
					size: 58,
					text: "Welcome to BARCO Karlsruhe R&D",
					type: 1
				}),
				new MD.MDText({
					positionX: 20,
					positionY: 365,
					size: 32,
					text: "Press here for info!",
					type: 2
				}),
				
				new MD.MDImage({
					positionX: 160,
					positionY: 490,
					src: "http://www.broadcastandcablesat.co.in/images/stories/Articles/2009/08/barco_untitled-3.gif",
					width: 520,
					height: 392
				}),
								
				new MD.MDNewsTicker({
					positionX: 60,
					positionY: 970,
					width: 800,
					height: 45,
					text: "Aquariva presentation at AV Fair Amsterdam was a big success!",
					author: "Robert, 2 hours ago"
				}),
				
				new MD.MDText({
					positionX: 1110,
					positionY: 190,
					size: 22,
					text: "Community Area",
					type: 1
				}),				
				new MD.MDRectangle({
					positionX: 950,
					positionY: 250,
					width: 600,
					height: 750,
					background: "#ffffff",
					alpha: 0.3
				}),
				
				new MD.MDAvatar({
					positionX: 1200,
					positionY: 500,
					src: "img/petra.png",
					text: "I have birthday today! join breakfast at 9 o'clock at the tee kitchen" 
				}),
				new MD.MDImage({
					positionX: 1260,
					positionY: 510,
					src: "http://3.bp.blogspot.com/-EmYIhesj4ZQ/UMCFvC-fznI/AAAAAAAACso/wy8ZlXr2U_Y/s1600/birthday+cake+choclate.jpg",
					width: 140,
					height: 100
				}),

				new MD.MDImage({
					positionX: 1200,
					positionY: 850,
					src: "img/dilbert.png",
					width: 260,
					height: 90
				}),
				new MD.MDArrow({
					positionX: 1180,
					positionY: 900,
					width: -80,
					height: -50,
					size: 16
				}),
				new MD.MDAvatar({
					positionX: 1050,
					positionY: 790,
					src: "img/alex.png",
					text: "why 'Joke'? - we call that 'Release Planning' ... ;)" 
				}),
				
				
				new MD.MDText({
					positionX: 1560,
					positionY: 190,
					size: 22,
					text: "What's up at Barco?",
					type: 1
				}),				
				new MD.MDNewsBox({
					positionX: 1640,
					positionY: 255,
					width: 200,
					height: 120,
					icon: "img/dept_manag.png",
					title: "Management",
					text: "Working on the next generation random buzzword generator"
				}),
				new MD.MDNewsBox({
					positionX: 1640,
					positionY: 385,
					width: 200,
					height: 120,
					icon: "img/dept_tech.png",
					title: "Research",
					text: "Pushing hard for MVD release, targeting mid of February for delivery"
				}),				
				new MD.MDNewsBox({
					positionX: 1640,
					positionY: 515,
					width: 200,
					height: 120,
					icon: "img/dept_sales.png",
					title: "Sales",
					text: "We are receiving many pre-orders for 2x2 MVD panels, so lets just get them delivered"
				}),
				new MD.MDNewsBox({
					positionX: 1640,
					positionY: 645,
					width: 200,
					height: 120,
					icon: "img/dept_dev.png",
					title: "Service",
					text: "The liquid cooling problem seems to be under control - thanks God!"
				}),
				
				
	
	
	
				new MD.MDText({
					positionX: 850,
					positionY: 10,
					size: 14,
					text: "This week at Chez Andrea",
					type: 2
				}),
				new MD.MDTable({
					positionX: 825,
					positionY: 35
				}),

				new MD.MDImage({
					positionX: 1701,
					positionY: 840,
					src: "img/barco.png",
					width: 209,
					height: 210
				})
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
		
		var board = new MD.MDContext({
			title: "My Activity",
			icon: "img/ampel.png",
			positionX: "90px",
			positionY: "40px",
			width: "640px",			
			height: "360px",
			zoom: 0.3333,
			children: [
				canvas,
				toolbar
			]			
		});
		this.rootComponent.add(board);
		
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

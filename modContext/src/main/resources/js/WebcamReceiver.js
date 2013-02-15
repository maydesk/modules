if (!Core.get(window, ["MD", "Sync"])) {
	Core.set(window, ["MD", "Sync"], {});
}
 
MD.WebcamReceiver = Core.extend(MD.MDAbstractFigure, {
	
	getEditor: function() {
		return null;
	},
	
	$load : function() {
       	Echo.ComponentFactory.registerType("WebcamReceiver", this);
	},
	
	fireConnectEvent: function() {
	    this.fireEvent({type: "connect", source: this});
	},
	
	componentType: "WebcamReceiver"
});

 
MD.Sync.WebcamReceiver = Core.extend(MD.Sync.MDAbstractFigure, {
    
    $load: function() {
        Echo.Render.registerPeer("WebcamReceiver", this);
    },
    
    
    renderAdd2: function(canvas, x2, y2) {

		var infobox = new Infobox(canvas.paper, {x:x2,y:y2, width:150, height:120});

		var html = "<video width='100%' height='100%' id='video1'/>";
		infobox.div.html(html);
		
		var webcamUrl = "http://www.visitmix.com/content/files/HTML5.mp4";	
		var videoElement = document.getElementById("video1");
		if (webcamUrl) {
			alert(webcamUrl);
			videoElement.src = webcamUrl;
			videoElement.autoplay = true;
		} else {
			var that = this;		
			function onSuccess(stream) {
				videoElement.autoplay = true;
				var streamSourceURL = webkitURL.createObjectURL(stream);
				videoElement.src = streamSourceURL;
				console.log('Streaming from: ' + streamSourceURL);
				that.component.set('url', streamSourceURL);
				that.component.fireConnectEvent();
			};
			function onError(err) {
			    alert("Error, you are running this probably from local file system, try running in an web app container instead!");
			};
	    	
		    navigator.webkitGetUserMedia({ video: true, audio: false }, onSuccess, onError);
		}
    }
});


	
//borrowed from https://github.com/kreynolds/RaphaelJS-Infobox/blob/master/raphaeljs-infobox.js
function Infobox(r, options, attrs) {
    options = options || {};
    attrs = attrs || {};
    this.paper = r;
    this.x = options.x || 0;
    this.y = options.y || 0;
    this.width = options.width || this.paper.width;
    this.height = options.height || this.paper.height;
    this.rounding = options.rounding || 0;
    this.show_border = options.with_border || true;
    this.container = this.paper.rect(this.x, this.y, this.width, this.height, this.rounding).attr(attrs);
    var container_id = this.container.node.parentNode.parentNode.id;
    container_id = container_id || this.container.node.parentNode.parentNode.parentNode.id;
    this.raph_container = jQuery('#' + container_id);
    
    if (!this.show_border) { this.container.hide(); }
    
    this.div = jQuery('<div style="position: absolute; overflow: auto; left: 0; top: 0; width: 0; height: 0;"></div>').insertAfter(this.raph_container);
    jQuery(document).bind('ready', this, function(event) { event.data.update(); });
    jQuery(window).bind('resize', this, function(event) { event.data.update(); });
  }

  Infobox.prototype.update = function() {
    var offset = this.raph_container.offset();
    this.div.css({
      'top': (this.y + (this.rounding)) + 'px',
      'left': (this.x + (this.rounding)) + 'px',
      'height': (this.height - (this.rounding*2) + 'px'),
      'width': (this.width - (this.rounding*2) + 'px')
    });
  }
  
  // Note that the fadein/outs for the content div are at double speed. With frequent animations, it gives the best behavior
  Infobox.prototype.show = function() {
    this.container.animate({opacity: 1}, 400, ">");
    this.div.fadeIn(200);
  }

  Infobox.prototype.hide = function() {
    this.container.animate({opacity: 0}, 400, ">");
    this.div.fadeOut(200);
};
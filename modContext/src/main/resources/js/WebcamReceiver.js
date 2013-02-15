 
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
	
	componentType : "WebcamReceiver",
	
	onConnect: function(sdpString) {
		this.fireEvent({
        	type: "onConnect", 
        	source: this,
        	data: sdpString
       	});
	},
	
	onCandidate: function(candidateString) {
		this.fireEvent({
        	type: "onCandidate", 
        	source: this,
        	data: candidateString
       	});
	}
});
 
 
MD.Sync.WebcamReceiver = Core.extend(MD.Sync.MDAbstractFigure, {
   
	$load : function() {
       	Echo.Render.registerPeer("WebcamReceiver", this);       	
	},
	
	_video: null,
    _localStream: null,
    _peerConnection: null,
    _initalized: false,
     
     renderAdd2: function(canvas, x2, y2) {
     
     	var videoId = "video_" + Math.floor(Math.random() * (10000));
     	console.log("videoId: " + videoId);
     	
		var infobox = new Infobox(canvas.paper, {x:x2,y:y2, width:150, height:120});
		var html = "<video width='100%' height='100%' id='" + videoId + "'/>";
		infobox.div.html(html);
		this._video = document.getElementById(videoId);
		this._video.autoplay = true;
		this._video.src = "http://www.visitmix.com/content/files/HTML5.mp4";		
    },
	
	renderUpdate: function(update) {
		var signalSdp = update.getUpdatedProperty("signalSdp");
		if (signalSdp) {
			this._initalized = true;
			this._startReceiving();
		} else if (this._initalized) {
			signalCandidate = this.component.render("signalCandidate");
			var signal = JSON.parse(signalCandidate);
			var x = new RTCIceCandidate(signal.candidate);
			this._peerConnection.addIceCandidate(x);
        }        
        return false;
    },

  	_startReceiving: function() {
		var that = this;		
		function iceCallback(event) {
			if (!event.candidate) return;
			var candidateString = JSON.stringify({ "candidate": event.candidate });
			that.component.onCandidate(candidateString);
		};
		function gotRemoteStream(event) {
			var url = URL.createObjectURL(event.stream);
			console.log("URL: " + url);
			
			setTimeout(function() { that._video.src = url; }, 5000);
			//that._video.src = url;
		};
		function gotDescription(desc) {
		  	that._peerConnection.setLocalDescription(desc);
		  	var sdpString = JSON.stringify({ "sdp": desc });
			that.component.onConnect(sdpString);
		};
		
		//var servers = null;
		var pc_config = {"iceServers": [{"url": "stun:stun.l.google.com:19302"}]};
    	var pc_constraints = {"optional": [{"DtlsSrtpKeyAgreement": true}]};
    	that._peerConnection = new webkitRTCPeerConnection(pc_config, pc_constraints);
		this._peerConnection.onicecandidate = iceCallback;		
		this._peerConnection.onaddstream = gotRemoteStream;		
		signalSdp = this.component.render("signalSdp");
		var signal = JSON.parse(signalSdp);
		this._peerConnection.setRemoteDescription(new RTCSessionDescription(signal.sdp));
		this._peerConnection.createAnswer(gotDescription);
    },    
    
    renderDispose: function(update) {
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
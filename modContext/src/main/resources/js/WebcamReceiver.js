 
if (!Core.get(window, ["MD", "Sync"])) {
        Core.set(window, ["MD", "Sync"], {});
}
 
MD.WebcamReceiver = Core.extend(Echo.Component, {
	
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
 
 
MD.Sync.WebcamReceiver = Core.extend(Echo.Render.ComponentSync, {
   
	$load : function() {
       	Echo.Render.registerPeer("WebcamReceiver", this);       	
	},
	
	_video: null,
    _localStream: null,
    _peerConnection: null,
    _initalized: false,
     
    renderAdd: function(update, parentElement) {
        this._container = document.createElement("div");
		this._container.style.position = "absolute";
       	this._container.style.left = "250px";
		this._container.style.top = "20px";
       	this._container.style.width = "200px";
       	this._container.style.height = "120px";
		Echo.Sync.Border.render("4px dotted #666666", this._container);
		parentElement.appendChild(this._container);    
    
	   	this._video = document.createElement("video");
		this._video.id = "vid2";
		this._video.style.width = "100%";
		this._video.style.height = "100%";
		this._video.autoplay = true;
		this._video.style.cursor = "move";
		this._container.appendChild(this._video);
		
		//this._video.src = "http://www.visitmix.com/content/files/HTML5.mp4";		
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
		var servers = null;
		this._peerConnection = new webkitRTCPeerConnection(servers);
		
		var that = this;
		
		function iceCallback(event) {
			if (!event.candidate) return;
			var candidateString = JSON.stringify({ "candidate": event.candidate });
			that.component.onCandidate(candidateString);
		};
		this._peerConnection.onicecandidate = iceCallback;		
		
		function gotRemoteStream(event) {
			var url = URL.createObjectURL(event.stream);
			that._video.src = url;
		};
		this._peerConnection.onaddstream = gotRemoteStream;		
	
		signalSdp = this.component.render("signalSdp");
		var signal = JSON.parse(signalSdp);
		this._peerConnection.setRemoteDescription(new RTCSessionDescription(signal.sdp));
		var that = this;
        function gotDescription(desc) {
		  	that._peerConnection.setLocalDescription(desc);
		  	var sdpString = JSON.stringify({ "sdp": desc });
			that.component.onConnect(sdpString);
		};
		this._peerConnection.createAnswer(gotDescription);
    },    
    
    renderDispose: function(update) {
    }    
});
 

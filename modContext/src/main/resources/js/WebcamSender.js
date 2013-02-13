 
if (!Core.get(window, ["MD", "Sync"])) {
        Core.set(window, ["MD", "Sync"], {});
}
 
MD.WebcamSender = Core.extend(Echo.Component, {

	
	$load : function() {
       	Echo.ComponentFactory.registerType("WebcamSender", this);
	},
	
	componentType : "WebcamSender",
	
	onStart: function(sdpString) {
		this.fireEvent({
        	type: "startsending", 
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
 
 
MD.Sync.WebcamSender = Core.extend(Echo.Render.ComponentSync, {
   
	$load : function() {
       	Echo.Render.registerPeer("WebcamSender", this);       	
	},
	
	_video: null,
    _localStream: null,
    _peerConnection: null,
    
     
    renderAdd: function(update, parentElement) {
	    this._container = document.createElement("div");
		this._container.style.position = "absolute";
       	this._container.style.left = "20px";
		this._container.style.top = "60px";
       	this._container.style.width = "200px";
       	this._container.style.height = "120px";
		Echo.Sync.Border.render("1px dotted #eeeeee", this._container);
		parentElement.appendChild(this._container);
    
	   	this._video = document.createElement("video");
		this._video.id = "vid1";
		this._video.style.width = "100%";
		this._video.style.height = "100%";
		this._video.autoplay = true;
		this._container.appendChild(this._video);

	    var startDiv = document.createElement("div");
		startDiv.style.position = "absolute";
       	startDiv.style.bottom = "0px";
		startDiv.style.left = "0px";
       	startDiv.style.width = "50px";
       	startDiv.style.height = "16px";
       	startDiv.style.background = "#bbbbbb";
		this._container.appendChild(startDiv);

		Core.Web.Event.add(startDiv, "click", Core.method(this, this._startSending), true);

    },
    
    _startSending: function() {
		function onError(err) {
		    alert("Error, you are running this probably from local file system, try running in an web app container instead!");
		};
		var that = this;
		function onSuccess(stream) {
			that._video.src = webkitURL.createObjectURL(stream);
			that._localStream = stream;
			
			var servers = null;
			that._peerConnection = new webkitRTCPeerConnection(servers);
			that._peerConnection.onicecandidate = iceCallback1;
			that._peerConnection.addStream(stream);
			that._peerConnection.createOffer(gotDescription1);
		};
		function iceCallback1(event) {
			if (!event.candidate) return;
			var candidateString = JSON.stringify({ "candidate": event.candidate });
			that.component.onCandidate(candidateString);
		};		
		function gotDescription1(desc){
			that._peerConnection.setLocalDescription(desc);			
			var sdpString = JSON.stringify({ "sdp": desc });
			that.component.onStart(sdpString);		
		};
		navigator.webkitGetUserMedia({ video: true, audio: false }, onSuccess, onError);    
    },


	renderUpdate: function(update) {
		var value = this.component.render("value");
		
		var signal = JSON.parse(value);		
		if (signal.sdp) {
			this._peerConnection.setRemoteDescription(new RTCSessionDescription(signal.sdp));
		} else {
			this._peerConnection.addIceCandidate(new RTCIceCandidate(signal.candidate));
        }
        return false;
    },
    
    renderDispose: function(update) {
    },
    
});
 

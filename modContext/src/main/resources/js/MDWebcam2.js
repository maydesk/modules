 
if (!Core.get(window, ["MD", "Sync"])) {
		Core.set(window, ["MD", "Sync"], {});
}
 
MD.MDWebcam2 = Core.extend(Echo.Component, {

	
	$load : function() {
	   	Echo.ComponentFactory.registerType("MDWebcam2", this);
	},
	
	componentType : "MDWebcam2",
	
	sendMessage: function(msgString) {
		this.fireEvent({
			type: "sendMessage", 
			source: this,
			data: msgString
	   	});
	}	
});
 
 
MD.Sync.MDWebcam2 = Core.extend(Echo.Render.ComponentSync, {
   
	$load : function() {
	   	Echo.Render.registerPeer("MDWebcam2", this);	   	
	},
	
	_localStream: null,
	_peerConnection: null,
	_isSender: false,
	_remoteVideo: null,
	_localVideo: null,
	_miniVideo: null,
	 
	renderAdd: function(update, parentElement) {
		this._isSender = this.component.render("isSender");
     	
		this._container = document.createElement("div");
		this._container.style.position = "absolute";
	   	this._container.style.left = this._isSender ? "20px" : "240px";
		this._container.style.top = "60px";
	   	this._container.style.width = "200px";
	   	this._container.style.height = "120px";
		Echo.Sync.Border.render("1px dotted #eeeeee", this._container);
		parentElement.appendChild(this._container);
	
	   	this._remoteVideo = document.createElement("video");
	   	this._remoteVideo.style.position = "absolute";
		this._remoteVideo.style.width = "100%";
		this._remoteVideo.style.height = "100%";
		this._remoteVideo.autoplay = true;
		this._remoteVideo.style.opacity = "0";
		this._container.appendChild(this._remoteVideo);

	   	this._miniVideo = document.createElement("video");
		this._miniVideo.style.position = "absolute";
		this._miniVideo.style.width = "30%";
		this._miniVideo.style.height = "30%";
		this._miniVideo.style.right = "0px";
		this._miniVideo.style.bottom = "0px";
		this._miniVideo.autoplay = true;
		this._miniVideo.muted = true;
		this._miniVideo.style.opacity = "0";
		this._container.appendChild(this._miniVideo);

	   	this._localVideo = document.createElement("video");
		this._localVideo.style.position = "absolute";
		this._localVideo.style.width = "100%";
		this._localVideo.style.height = "100%";
		this._localVideo.style.left = "0px";
		this._localVideo.autoplay = true;
		this._localVideo.muted = true;
		this._container.appendChild(this._localVideo);

		var startDiv = document.createElement("div");
		startDiv.style.position = "absolute";
	   	startDiv.style.bottom = "0px";
		if (this._isSender) {
			startDiv.style.left = "0px";
		} else {
			startDiv.style.right = "0px";
		}	
	   	startDiv.style.width = "50px";
	   	startDiv.style.height = "16px";
	   	startDiv.style.background = "#bbbbbb";
		this._container.appendChild(startDiv);

		Core.Web.Event.add(startDiv, "click", Core.method(this, this._startSending), true);

	},
	
	///https://apprtc.appspot.com/?r=21841861
	
	_startSending: function() {
		this.initialize2();
	},

	renderUpdate: function(update) {
		var value = this.component.render("value");
		this.processSignalingMessage(value);
		return false;
	},
	
	renderDispose: function(update) {
	},


	remoteStream: null,
	channel: null,
	pc: null,
	started: false,
	// Set up audio and video regardless of what devices are present.
	sdpConstraints: {'mandatory': {
							'OfferToReceiveAudio':true, 
							'OfferToReceiveVideo':true }},
	isVideoMuted: false,
	isAudioMuted: false,
	
	initialize2: function() {
		this.doGetUserMedia();
	},
	
	
	doGetUserMedia: function() {
		// Call into getUserMedia via the polyfill (adapter.js).
		var constraints = {"mandatory": {}, "optional": []}; 
		try {
			MD.getUserMedia({'audio':true, 'video':constraints}, 
				Core.method(this, this.onUserMediaSuccess), 
				Core.method(this, this.onUserMediaError));
		} catch (e) {
			alert("getUserMedia() failed. Is this a WebRTC capable browser?");
			console.log("getUserMedia failed with exception: " + e.message);
		}
	},
	
	createPeerConnection: function() {
		var pc_config = {"iceServers": [{"url": "stun:stun.l.google.com:19302"}]};
		var pc_constraints = {"optional": [{"DtlsSrtpKeyAgreement": true}]};
		// Force the use of a number IP STUN server for Firefox.
		if (MD.webrtcDetectedBrowser == "firefox") {
			pc_config = {"iceServers":[{"url":"stun:23.21.150.121"}]};
		}
		try {
			// Create an RTCPeerConnection via the polyfill (adapter.js).
			this.pc = new MD.RTCPeerConnection(pc_config, pc_constraints);
			this.pc.onicecandidate = Core.method(this, this.onIceCandidate);
			console.log("Created RTCPeerConnnection with:\n" + 
					  "  config: \"" + JSON.stringify(pc_config) + "\";\n" + 
					  "  constraints: \"" + JSON.stringify(pc_constraints) + "\".");
		} catch (e) {
			console.log("Failed to create PeerConnection, exception: " + e.message);
			alert("Cannot create RTCPeerConnection object; WebRTC is not supported by this browser.");
			return;
		}
		this.pc.onaddstream = Core.method(this, this.onRemoteStreamAdded);
		this.pc.onremovestream = Core.method(this, this.onRemoteStreamRemoved);
	},
	
	maybeStart: function() {
		if (!this.started && this._localStream) {
			this.createPeerConnection();
			this.pc.addStream(this._localStream);
			this.started = true;
			// Caller initiates offer to peer.
			if (this._isSender) {
				this.doCall();
			}
		}
	},
	
	
	doCall: function() {
		var constraints = {"optional": [], "mandatory": {"MozDontOfferDataChannel": true}};
		// temporary measure to remove Moz* constraints in Chrome
		if (MD.webrtcDetectedBrowser === "chrome") {
			for (prop in constraints.mandatory) {
				if (prop.indexOf("Moz") != -1) {
			 		delete constraints.mandatory[prop];
				}
			}
		 }
		constraints = this.mergeConstraints(constraints, this.sdpConstraints);
		console.log("Sending offer to peer, with constraints: \n" +
					"  \"" + JSON.stringify(constraints) + "\".")
		this.pc.createOffer(Core.method(this, this.setLocalAndSendMessage), null, constraints); 
	},
	
	doAnswer: function() {
		console.log("Sending answer to peer.");
		this.pc.createAnswer(Core.method(this, this.setLocalAndSendMessage), null, this.sdpConstraints);
	},
	
	mergeConstraints: function(cons1, cons2) {
		var merged = cons1;
		for (var name in cons2.mandatory) {
			merged.mandatory[name] = cons2.mandatory[name];
		}
		merged.optional.concat(cons2.optional);
		return merged;
	},
	
	setLocalAndSendMessage: function(sessionDescription) {
		// Set Opus as the preferred codec in SDP if Opus is present.
		sessionDescription.sdp = this.preferOpus(sessionDescription.sdp);
		this.pc.setLocalDescription(sessionDescription);
		this.sendMessage(sessionDescription);
	},
	
	sendMessage: function(message) {
		var msgString = JSON.stringify(message);
		this.component.sendMessage(msgString);
	},
	
	processSignalingMessage: function(message) {
		var msg = JSON.parse(message);		
		if (msg.type === 'offer') {
			// Callee creates PeerConnection
			if (!this._isSender && !this.started) this.maybeStart();
			this.pc.setRemoteDescription(new RTCSessionDescription(msg));
			this.doAnswer();
		} else if (msg.type === 'answer' && this.started) {
			this.pc.setRemoteDescription(new RTCSessionDescription(msg));
		} else if (msg.type === 'candidate' && this.started) {
			var candidate = new RTCIceCandidate({sdpMLineIndex:msg.label, candidate:msg.candidate});
			this.pc.addIceCandidate(candidate);
		} else if (msg.type === 'bye' && this.started) {
			this.onRemoteHangup();
		}
	},
	
	onUserMediaSuccess: function(stream) {
		// Call the polyfill wrapper to attach the media stream to this element.
		MD.attachMediaStream(this._localVideo, stream);
		this._localVideo.style.opacity = 1;
		this._localStream = stream;
		// Caller creates PeerConnection.
		if (this._isSender) {
			this.maybeStart();
		}	
	},
	
	onUserMediaError: function(error) {
		console.log("Failed to get access to local media. Error code was " + error.code);
		alert("Failed to get access to local media. Error code was " + error.code + ".");
	},
	
	onIceCandidate: function(event) {
		if (event.candidate) {
			this.sendMessage({type: 'candidate',
					   label: event.candidate.sdpMLineIndex,
					   id: event.candidate.sdpMid,
					   candidate: event.candidate.candidate});
		} else {
			console.log("End of candidates.");
		}
	},
	
	onRemoteStreamAdded: function(event) {
		console.log("Remote stream added." + this._miniVideo + " ::: " + this._localVideo); 
		MD.reattachMediaStream(this._miniVideo, this._localVideo);
		MD.attachMediaStream(this._remoteVideo, event.stream);
		this.remoteStream = event.stream;
		this.waitForRemoteVideo();  
	},
	
	onRemoteStreamRemoved: function(event) {
		console.log("Remote stream removed.");
	},
	
	onHangup: function() {
		console.log("Hanging up.");
		this.transitionToDone();
		this.stop();
		// will trigger BYE from server
	},
	   
	onRemoteHangup: function() {
		console.log('Session terminated.');
		this.transitionToWaiting();
		stop();
	},
	
	stop: function() {
		this.started = false;
		this.isAudioMuted = false;
		this.isVideoMuted = false;
		this.pc.close();
		this.pc = null;
	},
	
	waitForRemoteVideo: function() {
		// Call the getVideoTracks method via adapter.js.
		this.videoTracks = this.remoteStream.getVideoTracks();
		if (this.videoTracks.length === 0 || this._remoteVideo.currentTime > 0) {
		  this.transitionToActive();
		} else {
		  setTimeout(Core.method(this, this.waitForRemoteVideo), 100); 
		}
	},
	
	transitionToActive: function() {
		this._remoteVideo.style.opacity = 1;
		this._container.style.webkitTransform = "rotateY(180deg)";
		that = this;
		setTimeout(function() { that._localVideo.src = ""; }, 500);
		setTimeout(function() { that._miniVideo.style.opacity = 1; }, 1000);
		console.log("onHangup()");
	},
	
	transitionToWaiting: function() {
		this._container.style.webkitTransform = "rotateY(0deg)";
		var that = this;
		setTimeout(function() {
					 that._localVideo.src = this._miniVideo.src;
					 that._miniVideo.src = "";
					 that._remoteVideo.src = "" }, 500);
		this._miniVideo.style.opacity = 0;
		this._remoteVideo.style.opacity = 0;
	},
	
	transitionToDone: function() {
		this._localVideo.style.opacity = 0;
		this._remoteVideo.style.opacity = 0;
		this._miniVideo.style.opacity = 0;
		console.log("You have left the call.");
	},
	
	enterFullScreen: function() {
		container.webkitRequestFullScreen();
	},
	
	// Set Opus as the default audio codec if it's present.
	preferOpus: function(sdp) {
		var sdpLines = sdp.split('\r\n');
		// Search for m line.
		for (var i = 0; i < sdpLines.length; i++) {
			if (sdpLines[i].search('m=audio') !== -1) {
				var mLineIndex = i;
				break;
			} 
		}
		if (mLineIndex === null) return sdp;
		// If Opus is available, set it as the default in m line.
		for (var i = 0; i < sdpLines.length; i++) {
			if (sdpLines[i].search('opus/48000') !== -1) {		
				var opusPayload = this.extractSdp(sdpLines[i], /:(\d+) opus\/48000/i);
				if (opusPayload) {
					sdpLines[mLineIndex] = this.setDefaultCodec(sdpLines[mLineIndex], opusPayload);
				}
				break;
		  	}
		}
		// Remove CN in m line and sdp.
		sdpLines = this.removeCN(sdpLines, mLineIndex);
		sdp = sdpLines.join('\r\n');
		return sdp;
	},
	
	extractSdp: function(sdpLine, pattern) {
		var result = sdpLine.match(pattern);
		return (result && result.length == 2)? result[1]: null;
	},
	
	// Set the selected codec to the first in m line.
	setDefaultCodec: function(mLine, payload) {
		var elements = mLine.split(' ');
		var newLine = new Array();
		var index = 0;
		for (var i = 0; i < elements.length; i++) {
			if (index === 3) {  // Format of media starts from the fourth.
				newLine[index++] = payload; // Put target payload to the first.
			}
			if (elements[i] !== payload) {
				newLine[index++] = elements[i];
			}
		}
		return newLine.join(' ');
	},
	
	// Strip CN from sdp before CN constraints is ready.
	removeCN: function(sdpLines, mLineIndex) {
		var mLineElements = sdpLines[mLineIndex].split(' ');
		// Scan from end for the convenience of removing an item.
		for (var i = sdpLines.length-1; i >= 0; i--) {
			var payload = this.extractSdp(sdpLines[i], /a=rtpmap:(\d+) CN\/\d+/i);
			if (payload) {
				var cnPos = mLineElements.indexOf(payload);
				if (cnPos !== -1) {
					// Remove CN payload from m line.
			 		mLineElements.splice(cnPos, 1);
				}
				// Remove CN line in sdp
				sdpLines.splice(i, 1);
			}
		}
		sdpLines[mLineIndex] = mLineElements.join(' ');
		return sdpLines;
	}	
});
 

 


MD.RTCPeerConnection = null;
MD.getUserMedia = null;
MD.attachMediaStream = null;
MD.reattachMediaStream = null;
MD.webrtcDetectedBrowser = null;

if (navigator.mozGetUserMedia) {
  console.log("This appears to be Firefox");

  MD.webrtcDetectedBrowser = "firefox";

  // The RTCPeerConnection object.
  MD.RTCPeerConnection = mozRTCPeerConnection;

  // The RTCSessionDescription object.
  MD.RTCSessionDescription = mozRTCSessionDescription;

  // The RTCIceCandidate object.
  MD.RTCIceCandidate = mozRTCIceCandidate;

  // Get UserMedia (only difference is the prefix).
  // Code from Adam Barth.
  MD.getUserMedia = navigator.mozGetUserMedia.bind(navigator);

  // Attach a media stream to an element.
  MD.attachMediaStream = function(element, stream) {
    console.log("Attaching media stream");
    element.mozSrcObject = stream;
    element.play();
  };

  MD.reattachMediaStream = function(to, from) {
    console.log("Reattaching media stream" + to + " :::: " + from );
    to.mozSrcObject = from.mozSrcObject;
    to.play();
  };

  // Fake get{Video,Audio}Tracks
 MediaStream.prototype.getVideoTracks = function() {
    return [];
  };

  MediaStream.prototype.getAudioTracks = function() {
    return [];
  };
} else if (navigator.webkitGetUserMedia) {
//  console.log("This appears to be Chrome");

  MD.webrtcDetectedBrowser = "chrome";

  // The RTCPeerConnection object.
  MD.RTCPeerConnection = webkitRTCPeerConnection;
  
  // Get UserMedia (only difference is the prefix).
  // Code from Adam Barth.
  MD.getUserMedia = navigator.webkitGetUserMedia.bind(navigator);

  // Attach a media stream to an element.
  MD.attachMediaStream = function(element, stream) {
  	console.log("attachMediaStream " + element);
    element.src = webkitURL.createObjectURL(stream);
  };

  MD.reattachMediaStream = function(to, from) {
    console.log("Reattaching media stream" + to + " :::: " + from );
    to.src = from.src;
  };

  // The representation of tracks in a stream is changed in M26.
  // Unify them for earlier Chrome versions in the coexisting period.
  if (!webkitMediaStream.prototype.getVideoTracks) {
    webkitMediaStream.prototype.getVideoTracks = function() {
      return this.videoTracks;
    };
    webkitMediaStream.prototype.getAudioTracks = function() {
      return this.audioTracks;
    };
  }

  // New syntax of getXXXStreams method in M26.
  if (!webkitRTCPeerConnection.prototype.getLocalStreams) {
    webkitRTCPeerConnection.prototype.getLocalStreams = function() {
      return this.localStreams;
    };
    webkitRTCPeerConnection.prototype.getRemoteStreams = function() {
      return this.remoteStreams;
    };
  }
} else {
  console.log("Browser does not appear to be WebRTC-capable");
}

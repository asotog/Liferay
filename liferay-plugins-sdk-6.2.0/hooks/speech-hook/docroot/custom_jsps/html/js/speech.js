YUI().add('speech-recognition', function (Y) {
	var recognition = null;
	
	var commandsMap;
	var commandsKey;
	var keyWord = 0;
	var userSetupComplete;

	var final_transcript = '';
	var recognizing = false;
	var ignore_onend = false;
	var start_timestamp;
	
	Y.namespace('Speech');
	
	Y.Speech.SpeechRecognition = Y.Base.create('speech-recognition', Y.Base,[], {
		initializer: function () {
			
			commandsMap = this.get('commandsMap');
			commandsKey = this.get('commandsKey');
			keyWord = this.get('keyWord');
			userSetupComplete = this.get('userSetupComplete');
			
			if(userSetupComplete) {
				if (!('webkitSpeechRecognition' in window)) {
	  				upgrade();
				} else {
	  				start_button.style.display = 'inline-block';
	  				recognition = new webkitSpeechRecognition();
	  				recognition.continuous = true;
	  				recognition.interimResults = false;
	
	  				recognition.onstart = function() {
	    				recognizing = true;
	    				console.log('onstart /speech-hook/image/mic-animate.gif');
	    				start_img.src = '/speech-hook/image/mic-animate.gif';
	  				};
	
	  				recognition.onerror = function(event) {
		  				console.log('ERROR: ' + event.error);
						if (event.error == 'no-speech') {
	      					console.log('onerror no-speech /speech-hook/image/mic.gif');
	      					start_img.src = '/speech-hook/image/mic.gif';
	      					ignore_onend = true;
	    				}
	    				if (event.error == 'audio-capture') {
	      					console.log('onerror audio-capture /speech-hook/image/mic.gif');
	      					start_img.src = '/speech-hook/image/mic.gif';
	      					ignore_onend = true;
	    				}
	    				if (event.error == 'not-allowed') {
	      					console.log('onerror not-allowed /speech-hook/image/mic.gif');
	      					ignore_onend = true;
	    				}
	    				localStorage.setItem("speech-active", 0);
	  				};
	
	  				recognition.onend = function() {
	    				recognizing = false;
	    				if (ignore_onend) {
	      					return;
	    				}
	    				console.log('onend /speech-hook/image/mic.gif');
	    				start_img.src = '/speech-hook/image/mic.gif';
	    				if (!final_transcript) {
	      					return;
	    				}
	  				};
	
	  				recognition.onresult = function(event) {
	    				var interim_transcript = '';
	    				if (event.results[(event.results.length - 1)].isFinal) {
	      					final_transcript = event.results[(event.results.length - 1)][0].transcript;
	    				} else {
	      					interim_transcript = event.results[(event.results.length - 1)][0].transcript;
	    				}
	    
	    				final_transcript = final_transcript.trim();
	    				final_span.innerHTML = linebreak(final_transcript);
	    				interim_span.innerHTML = linebreak(interim_transcript);
	    				showToolTips(final_transcript);
	  				};
	  				
	  				start();
				}
			}
        }

	},{
		ATTRS: {
			commandsMap: {
				value: null
			},
			commandsKey: {
				value: null
			},
			keyWord: {
				value: null
			},
			userSetupComplete: {
				value: null
			}
		}
	});
	
	Y.Speech.upgrate = function() {
		start_button.style.visibility = 'hidden';
	};
	
	var two_line = /\n\n/g;
	var one_line = /\n/g;
	Y.Speech.linebreak = function (s) {
		  return s.replace(two_line, '<p></p>').replace(one_line, '<br>');
	};
	
	Y.Speech.startButton = function(event) {
		if (recognizing) {
			localStorage.setItem("speech-active", 0);
			recognition.stop();
		    return;
		}
		localStorage.setItem("speech-active", 1);
		final_transcript = '';
		recognition.lang = 'en-US';
		recognition.start();
		ignore_onend = false;
		final_span.innerHTML = '';
		interim_span.innerHTML = '';
		start_img.src = '/speech-hook/image/mic-slash.gif';

		start_timestamp = event.timeStamp;
	};
	
	Y.Speech.start = function(event) {
		var speech_active = false;
		var object = localStorage.getItem("speech-active");
		if(object != null) {
			speech_active = new Boolean(Number(object));
		}
		if(speech_active == true) {
			localStorage.setItem("speech-active", 1);
			recognition.lang = 'en-US';
			recognition.start();
			ignore_onend = false;
			start_img.src = '/speech-hook/image/mic-slash.gif';
		}
	};
	
	Y.Speech.showToolTips = function(speechCommand) {
		var index = 0;
		if(speechCommand.toLowerCase() ==  'show') {
			index = showTTDockBar(index);
		} else {
			var number;
			/*try {
				number = Number(speechCommand);
			} catch(error) {
				number = -1;
			}*/
			if(!isNaN(speechCommand)) {
				number = Number(speechCommand);
				console.log("Is Number: " + speechCommand);
				clickDockBarItem(number);
			} else {
				var redirect = readCustomCommands(speechCommand);
				console.log("readCustomCommandResult: " + redirect);
				if(redirect != null && redirect.length > 0) {
					window.location.assign(redirect);
				}
			}
			console.log(speechCommand);
		}
		console.log(index);
	};
	
	//Show the index numbers of DockBar 
	Y.Speech.showTTDockBar = function(index) {
		YUI().use('aui-tooltip', 'node', function(Y) {
			var dockBar = Y.one("ul[aria-label=Dockbar].nav-account-controls");
			if(Y.Lang.isObject(dockBar)) {
				var items = Y.all('li.dockbar-item');
				items.each(function(item, i){
					item.setAttribute('data-title', index);
					new Y.Tooltip({
				        trigger: item,
				        position: 'left',
				        zIndex: Liferay.zIndex.TOOLTIP,
				        opacity: 0.9,
				        visible: true
				    }).render();
					index++;
				});
			}
		});
		return index;
	};
	
	//Show the menu options on DockBar
	Y.Speech.clickDockBarItem = function(index) {
		AUI().use('aui-tooltip', 'node', 'node-event-simulate', function(Y) {
			var dockBar = Y.one("ul[aria-label=Dockbar].nav-account-controls");
			if(Y.Lang.isObject(dockBar)) {
				var search = 'li[data-title='+ index +'].dropdown.dockbar-item';
				console.log(search);
				var item = dockBar.one(search);
				if(Y.Lang.isObject(item)) {
					dockBar.all('li.dropdown.dockbar-item').removeClass('open');
					item.addClass('open');
				} else {
					console.log("Not found item with " + index + " index.");
				}
			}
		});
	};
	
	//Read custom commands
	Y.Speech.readCustomCommands = function(speechCommand) {
		var result = null;
		var keyWordLength = keyWord.length;
		var indexOf = speechCommand.indexOf(keyWord);
		
		if(indexOf > -1) {
			speechCommand = speechCommand.substring((keyWordLength + 1),speechCommand.length);
			speechCommand = speechCommand.trim();
		}
		for(var i = 0; i < commands.length; i++) {
			if(speechCommand == commands[i]) {
				result = map[speechCommand];
				break;
			}
		}
		return result;
	};
});
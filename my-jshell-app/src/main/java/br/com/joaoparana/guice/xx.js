// Register the namespace
var parseNamespace = parseNamespace || function(root, ns) {
	var nsParts = ns.split(".");
	for (var i = 0; i < nsParts.length; i++) {
		if (typeof root[nsParts[i]] == "undefined") {
			root[nsParts[i]] = new Object();
		}
		root = root[nsParts[i]];
	}
};
var JDM = {
	EventBus : {}
};
parseNamespace(window, "JDM.EventBus");
// Main namespace and class
JDM.EventBus = {
	// Listener object, contains actual listener references and methods for
	// adding/removing listeners as well as binding the listeners to their
	// appropriate
	// triggers at run-time.
	Listeners : {
		// Instantiates the listener object - every event handler is registered
		// and
		// listed in this object.
		List : {},
		// Adds a function with an associated handler nickName and execution
		// priority to
		// the list of listeners.
		Add : function(eventName, funcName, fn, priority) {
			parseNamespace(this.List, eventName + "." + priority + "."
					+ funcName);
			this.List[eventName][priority][funcName] = fn;
		},
		// Removes the function associated with a particular event listener
		// nickName.
		// The event listener will still be registered with the system,
		// but the trigger function will be triggering a null function,
		// so it won't do anything.
		Remove : function(eventName, nickName) {
			for ( var priority in this.List[eventName]) {
				this.List[eventName][priority][id] = null;
			}
		}
	},
	// Checks for priority settings, if none given, add a listener to the list
	// with
	// a very low priority
	Subscribe : function(eventName, functionName, fn, priority) {
		if (!priority) {
			priority = 10;
		}
		this.Listeners.Add(eventName, functionName, fn, priority);
	},
	// Trigger an event
	Broadcast : function(eventName, args) {
		if (!this.Listeners.List[eventName]) {
			return;
		}
		for (var i = 0; i <= 10; i++) {
			var funcHolder = this.Listeners.List[eventName][i];
			if (funcHolder) {
				for ( var fn in funcHolder) {
					if (funcHolder[fn]) {
						if (args) {
							funcHolder[fn](args.eventArgs);
						} else {
							funcHolder[fn]();
						}
					}
				}
			}
		}
	},
	// Remove an event listener
	Unsubscribe : function(eventName, nn) {
		this.Listeners.Remove(eventName, nn);
	}
};

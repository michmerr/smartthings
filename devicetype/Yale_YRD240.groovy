metadata {
	// Automatically generated. Make future change here.
    /*
    Based on Z-Wave Lock by SmartThings which looks like it's based on
    	Custom Z-Wave Lock by Yves Racine,
    and Custom Z-Wave Lock by bigpunk6
        	    
    */
	definition (name: "Yale Touchscreen Lock", namespace: "michmerr.smartthings.devicetype", author: "Michael Merrell") {
		capability "Actuator"
		capability "Lock"
		capability "Polling"
		capability "Refresh"
		capability "Sensor"
		capability "Lock Codes"
		capability "Battery"
        capability "Configuration"
           
        attribute "oneTouchLocking", "string"
        attribute "audioMode", "string"
        attribute "autoRelock", "string"
        attribute "relockTime", "number"
        attribute "wrongCodeEntryLimit", "number"
        attribute "shutdownTime", "number"
        attribute "language", "string"
        attribute "insideIndicatorLight", "string"
        attribute "operatingMode", "string"

		command "unlockwtimeout"
        
        command "toggleOneTouchLocking"
        command "enableOneTouchLocking"
        command "disableOneTouchLocking"
        command "toggleAutoRelock"
        command "enableAutoRelock"
        command "disableAutoRelock"
        command "toggleInsideIndicatorLight" 
        command "enableInsideIndicatorLight" 
        command "disableInsideIndicatorLight"  
        
        command "increaseRelockTime"
        command "decreaseRelockTime"
        command "setRelockTime", ["number"]
        command "increaseWrongCodeEntryLimit"
        command "decreaseWrongCodeEntryLimit"
        command "setWrongCodeEntryLimit", ["number"]
        command "increaseShutdownTime"
        command "decreaseShutdownTime"
        command "setShutdownTime", ["number"]
        
        command "setAudioMode", ["enum"]
        command "nextAudioMode" 
        command "previousAudioMode"         
        command "setLanguage", ["enum"]
        command "nextLanguage" 
        command "previousLanguage"          
        command "setOperatingMode", ["enum"]
        command "nextOperatingMode" 
        command "previousOperatingMode"  
        
        command "getValues", ["string"]
        
		fingerprint deviceId: "0x4003", inClusters: "0x72, 0x86, 0x98"
	}
       
    /*
    
    Intuitively, the functionality would be here in preferences, but the scope of preferences in
    device types is limited to settings for the device type instance, and not the configuration
    settings for the device being controlled. While the settings values are updated and readable
    by the device type and device type handler, it is not possible to set those variables with
    values queried from the device, nor is there an opportunity to send these values back to
    the device after they are changed by the user. (refreshAfterSelection doesn't work for inputs
    in device types, likely because it's supported by dynamic pages.
    
    ╯°□°）╯︵-┻━┻
    
    */
    
	simulator {
		status "locked": "command: 9881, payload: 00 62 03 FF 00 00 FE FE"
		status "unlocked": "command: 9881, payload: 00 62 03 00 00 00 FE FE"

		reply "9881006201FF,delay 4200,9881006202": "command: 9881, payload: 00 62 03 FF 00 00 FE FE"
		reply "988100620100,delay 4200,9881006202": "command: 9881, payload: 00 62 03 00 00 00 FE FE"
	}

	tiles {
		standardTile("toggle", "device.lock", width: 2, height: 2) {
			state "locked", label:'locked', action:"lock.unlock", icon:"st.locks.lock.locked", backgroundColor:"#79b821", nextState:"unlocking"
			state "unlocked", label:'unlocked', action:"lock.lock", icon:"st.locks.lock.unlocked", backgroundColor:"#ffffff", nextState:"locking"
			state "unknown", label:"unknown", action:"lock.lock", icon:"st.locks.lock.unknown", backgroundColor:"#ffffff", nextState:"locking"
			state "locking", label:'locking', icon:"st.locks.lock.locked", backgroundColor:"#79b821"
			state "unlocking", label:'unlocking', icon:"st.locks.lock.unlocked", backgroundColor:"#ffffff"
		}
		standardTile("lock", "device.lock", inactiveLabel: false, decoration: "flat") {
			state "default", label:'lock', action:"lock.lock", icon:"st.locks.lock.locked", nextState:"locking"
		}
		standardTile("unlock", "device.lock", inactiveLabel: false, decoration: "flat") {
			state "default", label:'unlock', action:"lock.unlock", icon:"st.locks.lock.unlocked", nextState:"unlocking"
		}
		valueTile("battery", "device.battery", inactiveLabel: false, decoration: "flat") {
			state "battery", label:'${currentValue}% battery', unit:""
		}
        standardTile("refresh", "device.lock", inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
		}
        
        /*
        
        Configuration Tiles
        
        */
        
        // Toggles
        
		standardTile("oneTouchLocking", "device.oneTouchLocking", inactiveLabel: false, decoration: "flat", height: 1, width: 1) {
			state "On", label:"One Touch Locking: On", action:"toggleOneTouchLocking", icon:"st.Health & Wellness.health6", nextState:"turningOff"
			state "Off", label:"One Touch Locking: Off", action:"toggleOneTouchLocking", icon:"st.Health & Wellness.health6", nextState:"turningOn"
			state "turningOn", label:"One Touch Locking: enabling...", icon:"st.Health & Wellness.health6"
			state "turningOff", label:"One Touch Locking: disabling...", icon:"st.Health & Wellness.health6"
		}
        
		standardTile("audioMode", "device.audioMode", inactiveLabel: false, decoration: "flat", height: 1, width: 1) {
			state "Silent", label:'Audio: Silent', action:"nextAudioMode", icon:"st.custom.sonos.muted", nextState: "..."
			state "Low", label:'Audio: Low', action:"nextAudioMode", icon:"st.custom.sonos.unmuted", nextState: "..."
			state "High", label:'Audio: High', action:"nextAudioMode", icon:"st.custom.sonos.unmuted", nextState: "..."
			state "...", label:'...', action:"", icon:"st.custom.sonos.unmuted"
        }
        
		standardTile("autoRelock", "device.autoRelock", inactiveLabel: false, 
                     canChangeIcon: true, icon:"st.contact.contact.closed", 
                     canChangeBackground: true, background: "#1F45FC",
                     decoration: "flat") {
			state "On", label:"Auto Relock: On", action:"toggleAutoRelock", nextState:"turningOff", icon:"st.contact.contact.closed"
			state "Off", label:"Auto Relock: Off", action:"toggleAutoRelock",  nextState:"turningOn", icon:"st.contact.contact.closed"
			state "turningOn", label:"Auto Relock enabling...", action:"", icon:"st.contact.contact.closed"
			state "turningOff", label:"Auto Relock disabling...", action:"", icon:"st.contact.contact.closed"
		}
        
       // Value selectors using arrows, since the sliders are hard-coded to a range of 1-100  ╯°□°）╯︵-┻━┻
        
		valueTile("relockTime", "device.relockTime", inactiveLabel: false, decoration: "flat", height: 1, width: 1) {
			state "relockTime", label:'after ${currentValue} seconds', icon:"st.secondary.secondary"
		}
		standardTile("relockTimeUp", "device.relockTime", inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"increaseRelockTime", icon:"st.thermostat.thermostat-right"
		}
        standardTile("relockTimeDown", "device.relockTime", inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"decreaseRelockTime", icon:"st.thermostat.thermostat-left"
		}
        
        valueTile("wrongCodeEntryLimit", "device.wrongCodeEntryLimit", inactiveLabel: false, decoration: "flat", height: 1, width: 1) {
			state "wrongCodeEntryLimit", label:'${currentValue}', icon:"st.secondary.secondary"
		}
        standardTile("wrongCodeEntryLimitUp", "device.wrongCodeEntryLimit", inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"increaseWrongCodeEntryLimit", icon:"st.thermostat.thermostat-right"
		}
        standardTile("wrongCodeEntryLimitDown", "device.wrongCodeEntryLimit", inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"decreaseWrongCodeEntryLimit", icon:"st.thermostat.thermostat-left"
		}
        
        valueTile("shutdownTime", "device.shutdownTime", inactiveLabel: false, decoration: "flat", height: 1, width: 1) {
			state "shutdownTime", label:'${currentValue} sec', icon:"st.secondary.secondary"
		}
        standardTile("shutdownTimeUp", "device.shutdownTime", inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"increaseShutdownTime", icon:"st.thermostat.thermostat-right"
		}
        standardTile("shutdownTimeDown", "device.shutdownTime", inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"decreaseShutdownTime", icon:"st.thermostat.thermostat-left"
		}
        
        standardTile("language", "device.language", inactiveLabel: false, decoration: "flat", height: 1, width: 1) {
			state "English", label:'English', action:"nextLanguage", icon:"st.secondary.secondary", nextState: "..."
			state "Spanish", label:'Spanish', action:"nextLanguage", icon:"st.secondary.secondary", nextState: "..."
			state "French", label:'French', action:"nextLanguage", icon:"st.secondary.secondary", nextState: "..."
			state "...", label:'...', action:"", icon:"st.secondary.secondary"
        }
        
        standardTile("operatingMode", "device.operatingMode", inactiveLabel: false, decoration: "flat", height: 1, width: 1) {
			state "Normal", label:'Mode: Normal', action:"nextOperatingMode", icon:"st.secondary.secondary", nextState: "..."
			state "Vacation", label:'Mode: Vacation', action:"nextOperatingMode", icon:"st.secondary.secondary", nextState: "..."
			state "Privacy", label:'Mode: Privacy', action:"nextOperatingMode", icon:"st.secondary.secondary", nextState: "..."
			state "...", label:'...', action:"", icon:"st.secondary.secondary"
        }
        
        standardTile("insideIndicatorLight", "device.insideIndicatorLight", inactiveLabel: false, decoration: "flat") {
			state "On", label:'Indicator On', action:"toggleInsideIndicatorLight", icon:"st.illuminance.illuminance.light", nextState:"turningOff"
			state "Off", label:'Indicator Off', action:"toggleInsideIndicatorLight", icon:"st.illuminance.illuminance.dark", nextState:"turningOff"
			state "turningOn", label:"Enabling indicator...", action:"", icon:"st.illuminance.illuminance.light"
			state "turningOff", label:"Disabling indicator...", action:"", icon:"st.illuminance.illuminance.dark"
		}
        
        valueTile("blank", "device.name", inactiveLabel: true, label:'', icon:"st.secondary.secondary")
        
		main "toggle"
		details(["toggle", "lock", "unlock", 
        		 "battery", "refresh", "blank",                  
                 "autoRelock", "oneTouchLocking", "insideIndicatorLight",
                 "relockTimeDown", "relockTime", "relockTimeUp",
                 "wrongCodeEntryLimitDown", "wrongCodeEntryLimit", "wrongCodeEntryLimitUp",
                 "shutdownTimeDown", "shutdownTime", "shutdownTimeUp",
                 "language", "operatingMode", "audioMode"])
	}
}

import physicalgraph.zwave.commands.doorlockv1.*
import physicalgraph.zwave.commands.usercodev1.*

def parse(String description) {
	def result = null
	if (description.startsWith("Err")) {
		if (state.sec) {
			result = createEvent(descriptionText:description, displayed:false)
		} else {
			result = createEvent(
				descriptionText: "This lock failed to complete the network security key exchange. If you are unable to control it via SmartThings, you must remove it from your network and add it again.",
				eventType: "ALERT",
				name: "secureInclusion",
				value: "failed",
				displayed: true,
			)
		}
	} else {
		def cmd = zwave.parse(description, [ 0x98: 1, 0x72: 2, 0x85: 2 ])
		if (cmd) {
        	log.debug("special: $cmd");
			result = zwaveEvent(cmd)
		}
	}
	log.debug "\"$description\" parsed to ${result.inspect()}"
	result
}

def zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityMessageEncapsulation cmd) {
	def encapsulatedCommand = cmd.encapsulatedCommand([0x62: 1, 0x71: 2, 0x80: 1, 0x85: 2, 0x63: 1, 0x98: 1])
	// log.debug "encapsulated: $encapsulatedCommand"
	if (encapsulatedCommand) {
		zwaveEvent(encapsulatedCommand)
	}
}

def zwaveEvent(physicalgraph.zwave.commands.securityv1.NetworkKeyVerify cmd) {
	createEvent(name:"secureInclusion", value:"success", descriptionText:"Secure inclusion was successful")
}

def zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityCommandsSupportedReport cmd) {
	log.debug("SecurityCommandsSupportedReport: $cmd")
	state.sec = cmd.commandClassSupport.collect { String.format("%02X ", it) }.join()
	if (cmd.commandClassControl) {
		state.secCon = cmd.commandClassControl.collect { String.format("%02X ", it) }.join()
	}
	log.debug "Security command classes: $state.sec"
	createEvent(name:"secureInclusion", value:"success", descriptionText:"Lock is securely included")
}

def zwaveEvent(DoorLockOperationReport cmd) {
	def result = []
	def map = [ name: "lock" ]
	if (cmd.doorLockMode == 0xFF) {
		map.value = "locked"
	} else if (cmd.doorLockMode >= 0x40) {
		map.value = "unknown"
	} else if (cmd.doorLockMode & 1) {
		map.value = "unlocked with timeout"
	} else {
		map.value = "unlocked"
		if (state.assoc != zwaveHubNodeId) {
			log.debug "setting association"
			result << response(secure(zwave.associationV1.associationSet(groupingIdentifier:1, nodeId:zwaveHubNodeId)))
			result << response(zwave.associationV1.associationSet(groupingIdentifier:2, nodeId:zwaveHubNodeId))
			result << response(secure(zwave.associationV1.associationGet(groupingIdentifier:1)))
		}
	}
	result ? [createEvent(map), *result] : createEvent(map)
}

def zwaveEvent(physicalgraph.zwave.commands.alarmv2.AlarmReport cmd) {
	def result = []
	def map = null
	if (cmd.zwaveAlarmType == 6) {
		if (1 <= cmd.zwaveAlarmEvent && cmd.zwaveAlarmEvent < 10) {
			map = [ name: "lock", value: (cmd.zwaveAlarmEvent & 1) ? "locked" : "unlocked" ]
		}
		switch(cmd.zwaveAlarmEvent) {
			case 1:
				map.descriptionText = "$device.displayName was manually locked"
				break
			case 2:
				map.descriptionText = "$device.displayName was manually unlocked"
				break
			case 5:
				if (cmd.eventParameter) {
					map.descriptionText = "$device.displayName was locked with code ${cmd.eventParameter.first()}"
					map.data = [ usedCode: cmd.eventParameter[0] ]
				}
				break
			case 6:
				if (cmd.eventParameter) {
					map.descriptionText = "$device.displayName was unlocked with code ${cmd.eventParameter.first()}"
					map.data = [ usedCode: cmd.eventParameter[0] ]
				}
				break
			case 9:
				map.descriptionText = "$device.displayName was autolocked"
				break
			case 7:
			case 8:
			case 0xA:
				map = [ name: "lock", value: "unknown", descriptionText: "$device.displayName was not locked fully" ]
				break
			case 0xB:
				map = [ name: "lock", value: "unknown", descriptionText: "$device.displayName is jammed" ]
				break
			case 0xC:
				map = [ name: "codeChanged", value: "all", descriptionText: "$device.displayName: all user codes deleted", displayed: true ]
				allCodesDeleted()
				break
			case 0xD:
				if (cmd.eventParameter) {
					map = [ name: "codeReport", value: cmd.eventParameter[0], data: [ code: "" ], displayed: true ]
					map.descriptionText = "$device.displayName code ${map.value} was deleted"
					map.isStateChange = (state["code$map.value"] != "")
					state["code$map.value"] = ""
				} else {
					map = [ name: "codeChanged", descriptionText: "$device.displayName: user code deleted", displayed: true ]
				}
				break
			case 0xE:
				map = [ name: "codeChanged", value: cmd.alarmLevel,  descriptionText: "$device.displayName: user code added", displayed: true ]
				if (cmd.eventParameter) {
					map.value = cmd.eventParameter[0]
					result << response(requestCode(cmd.eventParameter[0]))
				}
				break
			default:
				map = map ?: [ descriptionText: "$device.displayName: alarm event $cmd.zwaveAlarmEvent", display: false ]
				break
		}
	} else switch(cmd.alarmType) {
		case 21:  // Manually locked
		case 18:  // Locked with keypad
		case 24:  // Locked by command (Kwikset 914)
		case 27:  // Autolocked
			map = [ name: "lock", value: "locked" ]
			break
		case 16:  // Note: for levers this means it's unlocked, for non-motorized deadbolt, it's just unsecured and might not get unlocked
		case 19:
			map = [ name: "lock", value: "unlocked" ]
			if (cmd.alarmLevel) {
				map.descriptionText = "$device.displayName was unlocked with code $cmd.alarmLevel"
				map.data = [ usedCode: cmd.alarmLevel ]
			}
			break
		case 22:
		case 25:  // Kwikset 914 unlocked by command
			map = [ name: "lock", value: "unlocked" ]
			break
		case 9:
		case 17:
		case 23:
		case 26:
			map = [ name: "lock", value: "unknown", descriptionText: "$device.displayName bolt is jammed" ]
			break
		case 13:
			map = [ name: "codeChanged", value: cmd.alarmLevel, descriptionText: "$device.displayName code $cmd.alarmLevel was added", displayed: true ]
			result << response(requestCode(cmd.alarmLevel))
			break
		case 32:
			map = [ name: "codeChanged", value: "all", descriptionText: "$device.displayName: all user codes deleted", displayed: true ]
			allCodesDeleted()
		case 33:
			map = [ name: "codeReport", value: cmd.alarmLevel, data: [ code: "" ], displayed: true ]
			map.descriptionText = "$device.displayName code $cmd.alarmLevel was deleted"
			map.isStateChange = (state["code$cmd.alarmLevel"] != "")
			state["code$cmd.alarmLevel"] = ""
			break
		case 112:
			map = [ name: "codeChanged", value: cmd.alarmLevel, descriptionText: "$device.displayName code $cmd.alarmLevel changed", displayed: true ]
			result << response(requestCode(cmd.alarmLevel))
			break
		case 130:  // Yale YRD batteries replaced
			map = [ descriptionText: "$device.displayName batteries replaced", displayed: true ]
			break
		case 131:
			map = [ /*name: "codeChanged", value: cmd.alarmLevel,*/ descriptionText: "$device.displayName code $cmd.alarmLevel is duplicate", displayed: false ]
		case 161:
			if (cmd.alarmLevel == 2) {
				map = [ descriptionText: "$device.displayName front escutcheon removed", isStateChange: true ]
			} else {
				map = [ descriptionText: "$device.displayName detected failed user code attempt", isStateChange: true ]
			}
			break
		case 167:
			if (!state.lastbatt || (new Date().time) - state.lastbatt > 12*60*60*1000) {
				map = [ descriptionText: "$device.displayName: battery low", displayed: true ]
				result << response(secure(zwave.batteryV1.batteryGet()))
			} else {
				map = [ name: "battery", value: device.currentValue("battery"), descriptionText: "$device.displayName: battery low", displayed: true ]
			}
			break
		case 168:
			map = [ name: "battery", value: 1, descriptionText: "$device.displayName: battery level critical", displayed: true ]
			break
		case 169:
			map = [ name: "battery", value: 0, descriptionText: "$device.displayName: battery too low to operate lock", isStateChange: true ]
			break
		default:
			map = [ displayed: false, descriptionText: "$device.displayName: alarm event $cmd.alarmType level $cmd.alarmLevel" ]
			break
	}
	result ? [createEvent(map), *result] : createEvent(map)
}

def zwaveEvent(UserCodeReport cmd) {
	def result = []
	def name = "code$cmd.userIdentifier"
	def code = cmd.code
	def map = [:]
	if (cmd.userIdStatus == UserCodeReport.USER_ID_STATUS_OCCUPIED ||
		(cmd.userIdStatus == UserCodeReport.USER_ID_STATUS_STATUS_NOT_AVAILABLE && cmd.user && code != "**********"))
	{
		if (code == "**********") {  // Schlage locks send us this instead of the real code
			state.blankcodes = true
			code = state["set$name"] ?: state[name] ?: code
			state.remove("set$name".toString())
		}
		if (!code && cmd.userIdStatus == 1) {  // Schlage touchscreen sends blank code to notify of a changed code
			map = [ name: "codeChanged", value: cmd.userIdentifier, displayed: true, isStateChange: true ]
			map.descriptionText = "$device.displayName code $cmd.userIdentifier " + (state[name] ? "changed" : "was added")
			code = state["set$name"] ?: state[name] ?: "****"
			state.remove("set$name".toString())
		} else {
			map = [ name: "codeReport", value: cmd.userIdentifier, data: [ code: code ] ]
			map.descriptionText = "$device.displayName code $cmd.userIdentifier is set"
			map.displayed = (cmd.userIdentifier != state.requestCode && cmd.userIdentifier != state.pollCode)
			map.isStateChange = (code != state[name])
		}
		result << createEvent(map)
	} else {
		map = [ name: "codeReport", value: cmd.userIdentifier, data: [ code: "" ] ]
		if (state.blankcodes && state["reset$name"]) {  // we deleted this code so we can tell that our new code gets set
			map.descriptionText = "$device.displayName code $cmd.userIdentifier was reset"
			map.displayed = map.isStateChange = false
			result << createEvent(map)
			state["set$name"] = state["reset$name"]
			result << response(setCode(cmd.userIdentifier, state["reset$name"]))
			state.remove("reset$name".toString())
		} else {
			if (state[name]) {
				map.descriptionText = "$device.displayName code $cmd.userIdentifier was deleted"
			} else {
				map.descriptionText = "$device.displayName code $cmd.userIdentifier is not set"
			}
			map.displayed = (cmd.userIdentifier != state.requestCode && cmd.userIdentifier != state.pollCode)
			map.isStateChange = state[name] as Boolean
			result << createEvent(map)
		}
		code = ""
	}
	state[name] = code

	if (cmd.userIdentifier == state.requestCode) {  // reloadCodes() was called, keep requesting the codes in order
		if (state.requestCode + 1 > state.codes) {
			state.remove("requestCode")  // done
		} else {
			state.requestCode = state.requestCode + 1  // get next
			result << response(requestCode(state.requestCode))
		}
	}
	if (cmd.userIdentifier == state.pollCode) {
		if (state.pollCode + 1 > state.codes) {
			state.remove("pollCode")  // done
		} else {
			state.pollCode = state.pollCode + 1
		}
	}
	log.debug "code report parsed to ${result.inspect()}"
	result
}

def zwaveEvent(UsersNumberReport cmd) {
	def result = []
	state.codes = cmd.supportedUsers
	if (state.requestCode && state.requestCode <= cmd.supportedUsers) {
		result << response(requestCode(state.requestCode))
	}
	result
}

def zwaveEvent(physicalgraph.zwave.commands.associationv2.AssociationReport cmd) {
	def result = []
	if (cmd.nodeId.any { it == zwaveHubNodeId }) {
		state.remove("associationQuery")
		log.debug "$device.displayName is associated to $zwaveHubNodeId"
		result << createEvent(descriptionText: "$device.displayName is associated")
		state.assoc = zwaveHubNodeId
		if (cmd.groupingIdentifier == 2) {
			result << response(zwave.associationV1.associationRemove(groupingIdentifier:1, nodeId:zwaveHubNodeId))
		}
	} else if (cmd.groupingIdentifier == 1) {
		result << response(secure(zwave.associationV1.associationSet(groupingIdentifier:1, nodeId:zwaveHubNodeId)))
	} else if (cmd.groupingIdentifier == 2) {
		result << response(zwave.associationV1.associationSet(groupingIdentifier:2, nodeId:zwaveHubNodeId))
	}
	result
}

def zwaveEvent(physicalgraph.zwave.commands.timev1.TimeGet cmd) {
	def result = []
	def now = new Date().toCalendar()
	if(location.timeZone) now.timeZone = location.timeZone
	result << createEvent(descriptionText: "$device.displayName requested time update", displayed: false)
	result << response(secure(zwave.timeV1.timeReport(
		hourLocalTime: now.get(Calendar.HOUR_OF_DAY),
		minuteLocalTime: now.get(Calendar.MINUTE),
		secondLocalTime: now.get(Calendar.SECOND)))
	)
	result
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicSet cmd) {
	// The old Schlage locks use group 1 for basic control - we don't want that, so unsubscribe from group 1
	def result = [ createEvent(name: "lock", value: cmd.value ? "unlocked" : "locked") ]
	result << response(zwave.associationV1.associationRemove(groupingIdentifier:1, nodeId:zwaveHubNodeId))
	if (state.assoc != zwaveHubNodeId) {
		result << response(zwave.associationV1.associationGet(groupingIdentifier:2))
	}
	result
}

def zwaveEvent(physicalgraph.zwave.commands.batteryv1.BatteryReport cmd) {
	def map = [ name: "battery", unit: "%" ]
	if (cmd.batteryLevel == 0xFF) {
		map.value = 1
		map.descriptionText = "$device.displayName has a low battery"
	} else {
		map.value = cmd.batteryLevel
	}
	state.lastbatt = new Date().time
	createEvent(map)
}

def zwaveEvent(physicalgraph.zwave.commands.manufacturerspecificv2.ManufacturerSpecificReport cmd) {
	def result = []

	def msr = String.format("%04X-%04X-%04X", cmd.manufacturerId, cmd.productTypeId, cmd.productId)
	log.debug "msr: $msr"
	updateDataValue("MSR", msr)

	result << createEvent(descriptionText: "$device.displayName MSR: $msr", isStateChange: false)
	result
}

def zwaveEvent(physicalgraph.zwave.commands.versionv1.VersionReport cmd) {
	def fw = "${cmd.applicationVersion}.${cmd.applicationSubVersion}"
	updateDataValue("fw", fw)
	def text = "$device.displayName: firmware version: $fw, Z-Wave version: ${cmd.zWaveProtocolVersion}.${cmd.zWaveProtocolSubVersion}"
	createEvent(descriptionText: text, isStateChange: false)
}

def zwaveEvent(physicalgraph.zwave.commands.applicationstatusv1.ApplicationBusy cmd) {
	def msg = cmd.status == 0 ? "try again later" :
	          cmd.status == 1 ? "try again in $cmd.waitTime seconds" :
	          cmd.status == 2 ? "request queued" : "sorry"
	createEvent(displayed: false, descriptionText: "$device.displayName is busy, $msg")
}


def zwaveEvent(physicalgraph.zwave.commands.configurationv2.ConfigurationReport cmd) {
	log.debug("configurationReportHandler: report: ${cmd}")
    def value = cmd.configurationValue[0]
    def settingsVar = getSettingsVar(cmd.parameterNumber);
    def current = device.currentValue(settingsVar.key)
    def currentValue = current
    def newValue = value
    log.debug("configurationReportHandler: current value = $current")
    
    if (settingsVar.value.containsKey("options")) {
    	def options = settingsVar.value["options"]
    	log.debug("options = $options")
        log.debug("current = $current")
    	currentValue = options[current]
        newValue = options.find( { it.value == value } ).key
        log.debug("newValue = $newValue")
    }
    
    device.each( { log.debug(it) })
    def description = "${settingsVar.key} is set to $value"
    def stateChange = false
    if (value != currentValue) {
    	stateChange = true
        description = "${settingsVar.key} changed to $value"
    }
    
    def result = createEvent(name: settingsVar.key, value: newValue, displayed: stateChange, descriptionText: description, isStateChange: stateChange)
    log.debug("configurationReportHandler: result = ${result.inspect()}")
    return result;
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
	def evt = createEvent(displayed: false, descriptionText: "$device.displayName: $cmd")
    log.debug(evt)
    return evt
}

def lockAndCheck(doorLockMode) {
	secureSequence([
		zwave.doorLockV1.doorLockOperationSet(doorLockMode: doorLockMode),
		zwave.doorLockV1.doorLockOperationGet()
	], 4200)
}

def lock() {
	lockAndCheck(DoorLockOperationSet.DOOR_LOCK_MODE_DOOR_SECURED)
}

def unlock() {
	lockAndCheck(DoorLockOperationSet.DOOR_LOCK_MODE_DOOR_UNSECURED)
}

def unlockwtimeout() {
	lockAndCheck(DoorLockOperationSet.DOOR_LOCK_MODE_DOOR_UNSECURED_WITH_TIMEOUT)
}

def refresh() {
	def cmds = [secure(zwave.doorLockV1.doorLockOperationGet())]
	if (state.assoc == zwaveHubNodeId) {
		log.debug "$device.displayName is associated to ${state.assoc}"
	} else if (!state.associationQuery) {
		log.debug "checking association"
		cmds << "delay 4200"
		cmds << secure(zwave.associationV1.associationGet(groupingIdentifier:1))
		state.associationQuery = new Date().time
	} else if (new Date().time - state.associationQuery.toLong() > 9000) {
		log.debug "setting association"
		cmds << "delay 6000"
		cmds << secure(zwave.associationV1.associationSet(groupingIdentifier:1, nodeId:zwaveHubNodeId))
		cmds << secure(zwave.associationV1.associationGet(groupingIdentifier:1))
		state.associationQuery = new Date().time
	}
    cmds << getConfiguration()
	log.debug "refresh sending ${cmds.inspect()}"
	cmds
}

def poll() {
	def cmds = []
	if (state.assoc != zwaveHubNodeId && secondsPast(state.associationQuery, 19 * 60)) {
		log.debug "setting association"
		cmds << secure(zwave.associationV1.associationSet(groupingIdentifier:1, nodeId:zwaveHubNodeId))
		cmds << "delay 6000"
		cmds << secure(zwave.associationV1.associationGet(groupingIdentifier:1))
		cmds << "delay 6000"
		state.associationQuery = new Date().time
	} else {
		// Only check lock state if it changed recently or we haven't had an update in an hour
		def latest = device.currentState("lock")?.date?.time
		if (!latest || !secondsPast(latest, 6 * 60) || secondsPast(state.lastPoll, 67 * 60)) {
			cmds << secure(zwave.doorLockV1.doorLockOperationGet())
			state.lastPoll = (new Date()).time
		} else if (!state.codes) {
			state.pollCode = 1
			cmds << secure(zwave.userCodeV1.usersNumberGet())
		} else if (state.pollCode && state.pollCode <= state.codes) {
			cmds << requestCode(state.pollCode)
		} else if (!state.lastbatt || (new Date().time) - state.lastbatt > 53*60*60*1000) {
			cmds << secure(zwave.batteryV1.batteryGet())
		}
		if(cmds) cmds << "delay 6000"
	}
	log.debug "poll is sending ${cmds.inspect()}, state: ${state.inspect()}"
	device.activity()  // workaround to keep polling from being shut off
	cmds ?: null
}

def requestCode(codeNumber) {
	secure(zwave.userCodeV1.userCodeGet(userIdentifier: codeNumber))
}

def reloadAllCodes() {
	def cmds = []
	if (!state.codes) {
		state.requestCode = 1
		cmds << secure(zwave.userCodeV1.usersNumberGet())
	} else {
		if(!state.requestCode) state.requestCode = 1
		cmds << requestCode(codeNumber)
	}
	cmds
}

def setCode(codeNumber, code) {
	def strcode = code
	log.debug "setting code $codeNumber to $code"
	if (code instanceof String) {
		code = code.toList().findResults { if(it > ' ' && it != ',' && it != '-') it.toCharacter() as Short }
	} else {
		strcode = code.collect{ it as Character }.join()
	}
	if (state.blankcodes) {
		if (state["code$codeNumber"] != "") {  // Can't just set, we won't be able to tell if it was successful
			if (state["setcode$codeNumber"] != strcode) {
				state["resetcode$codeNumber"] = strcode
				return deleteCode(codeNumber)
			}
		} else {
			state["setcode$codeNumber"] = strcode
		}
	}
	secureSequence([
		zwave.userCodeV1.userCodeSet(userIdentifier:codeNumber, userIdStatus:1, user:code),
		zwave.userCodeV1.userCodeGet(userIdentifier:codeNumber)
	], 7000)
}

def deleteCode(codeNumber) {
	log.debug "deleting code $codeNumber"
	secureSequence([
		zwave.userCodeV1.userCodeSet(userIdentifier:codeNumber, userIdStatus:0),
		zwave.userCodeV1.userCodeGet(userIdentifier:codeNumber)
	], 7000)
}

def updateCodes(codeSettings) {
	if(codeSettings instanceof String) codeSettings = util.parseJson(codeSettings)
	def set_cmds = []
	def get_cmds = []
	codeSettings.each { name, updated ->
		def current = state[name]
		if (name.startsWith("code")) {
			def n = name[4..-1].toInteger()
			log.debug "$name was $current, set to $updated"
			if (updated?.size() >= 4 && updated != current) {
				def cmds = setCode(n, updated)
				set_cmds << cmds.first()
				get_cmds << cmds.last()
			} else if ((current && updated == "") || updated == "0") {
				def cmds = deleteCode(n)
				set_cmds << cmds.first()
				get_cmds << cmds.last()
			} else if (updated && updated.size() < 4) {
				// Entered code was too short
				codeSettings["code$n"] = current
			}
		} else log.warn("unexpected entry $name: $updated")
	}
	if (set_cmds) {
		return response(delayBetween(set_cmds, 2200) + ["delay 2200"] + delayBetween(get_cmds, 4200))
	}
}

def getCode(codeNumber) {
	state["code$codeNumber"]
}

def getAllCodes() {
	state.findAll { it.key.startsWith 'code' }
}

def update() {
	getConfiguration()
}

def configure() {
	getConfiguration()
}

private getConfiguration() {
	// Pull configuration values FROM the device to populate initial values for settings tiles.
    log.debug("getConfiguration: sending queries to retrieve configuration values.")
    
    def parameters = getSettingsMap().collect({ m -> m.value["parameterNumber"] })
    def cmds = secureSequence(parameters.collect({ p -> zwave.configurationV1.configurationGet(parameterNumber: p) }), 4200)
    cmds
}


private secure(physicalgraph.zwave.Command cmd) {
	zwave.securityV1.securityMessageEncapsulation().encapsulate(cmd).format()
}

private secureSequence(commands, delay=4200) {
	delayBetween(commands.collect{ secure(it) }, delay)
}

private Boolean secondsPast(timestamp, seconds) {
	if (!(timestamp instanceof Number)) {
		if (timestamp instanceof Date) {
			timestamp = timestamp.time
		} else if ((timestamp instanceof String) && timestamp.isNumber()) {
			timestamp = timestamp.toLong()
		} else {
			return true
		}
	}
	return (new Date().time - timestamp) > (seconds * 1000)
}

private allCodesDeleted() {
	if (state.codes instanceof Integer) {
		(1..state.codes).each { n ->
			if (state["code$n"]) {
				result << createEvent(name: "codeReport", value: n, data: [ code: "" ], descriptionText: "code $n was deleted",
					displayed: false, isStateChange: true)
			}
			state["code$n"] = ""
		}
	}
}

/*
	Map of configuration attributes to corresponding command parameter numbers, string to command value relationships,
    and valid value ranges.
    
    (Note that numeric ranges are "bare". If wrapped in braces [1..7] it will return a Range inside an ArrayList.)

*/

private getSettingsMap() {
	[ "audioMode" : [ "parameterNumber" : 1, "options" : [ "Silent":1, "Low":2, "High":3] ],
   	  "autoRelock" : [ "parameterNumber" : 2, "options" : [ "Off":0x00, "On":0xFF ] ],
      "relockTime" : [ "parameterNumber" : 3, "range" : 5..255 ], 
      "wrongCodeEntryLimit" : [ "parameterNumber" : 4, "range" : 1..7 ],
      "language" : [ "parameterNumber" : 5, "options" : [ "English":1, "Spanish":2, "French":3] ],
      "shutdownTime" : [ "parameterNumber" : 7, "range" : 1..255 ],
      "operatingMode" : [ "parameterNumber" : 8, "options" : [ "Normal":0, "Vacation":1, "Privacy":2] ],
      "oneTouchLocking" : [ "parameterNumber" : 11, "options" : [ "Off":0x00, "On":0xFF ] ],
      "insideIndicatorLight": [ "parameterNumber" : 13, "options" : [ "Off":0x00, "On":0xFF ] ] ]
}

def getSettingsVar(parameterNumber) {
    def map = getSettingsMap()
    return map.find( { it.value.parameterNumber == parameterNumber })
}


private setConfiguration(parameterNumber, value) {
	secureSequence([ zwave.configurationV2.configurationSet(parameterNumber: parameterNumber, size: 1, configurationValue: [ value ]),
    			     zwave.configurationV2.configurationGet(parameterNumber: parameterNumber) ], 4200)
}

private toggleSetting(String name) {	
    def map = getSettingsMap()[name]
    def parameterNumber = map.parameterNumber
    def current = device.currentValue(name)
    log.debug("current $name = $current");
    def next = map.options.find( { it.key != current }).value
    setConfiguration(parameterNumber, next)
}

def toggleOneTouchLocking() {
	toggleSetting("oneTouchLocking")
}

def enableOneTouchLocking() {
	setValue("oneTouchLocking", "On")
}

def disableOneTouchLocking() {
	setValue("oneTouchLocking", "Off")
}

def toggleAutoRelock() {
	toggleSetting("autoRelock")
}

def enableAutoRelock() {
	setValue("autoRelock", "On")
}

def disableAutoRelock() {
	setValue("autoRelock", "Off")
}

def toggleInsideIndicatorLight() {
	toggleSetting("insideIndicatorLight")
}

def enableInsideIndicatorLight() {
	setValue("insideIndicatorLight", "On")
}

def disableInsideIndicatorLight() {
	setValue("insideIndicatorLight", "Off")
}

private setValue(String name, Integer value) {
	def map = getSettingsMap()[name]
    def parameterNumber = map["parameterNumber"]
    def next = value
    
    if (map.containsKey("range")) {
    	def range = map["range"]
        log.debug("range = $range")
        if (next <= range.getFrom()) { 
        	next = range.getFrom()    
        } else if (next >= range.getTo()) {
        	next = range.getTo()
        }
    }
    setConfiguration(parameterNumber, next)
}

private setValue(String name, String value) {
	if (value.isInteger()){
    	return setValue(name, value.toInteger())
    }
    
    def map = getSettingsMap()[name]
    
    if (!map.containsKey("options")) {
    	log.error("Numeric value could not be found $name = $value")
        return
    }
    def options = map["options"]
    log.debug("options = $options")
    Integer next = options[value]   
    log.debug("next = $next")
    
    def parameterNumber = map["parameterNumber"]
    setConfiguration(parameterNumber, next)
}

private toInteger(name) {
	def current = device.currentValue("relockTime")
    def value
    
	log.debug("toInteger: $name = $current")
    if (current == null) {
    	value = null
    } else if (current instanceof String) {
        if (!current.isInteger()) {
            log.error("Could not determine how to find the next value for $name")
            value = null
        } else {
        	value = current.toInteger()
        }
    } else if (current instanceof Number) {
        value = current.intValue()
    }
	log.debug("toInteger: value = $value")
    
    return value
}

private increase(name) {
	log.debug("increase $name")
	def value = toInteger(name)
    if (value != null)
		setValue(name, value + 1)
}

private decrease(name) {
	def value = toInteger(name)
    if (value != null)
		setValue(name, value - 1)
}

def setRelockTime(Integer value) {
	setValue("relockTime", value)
}

def increaseRelockTime() {
	log.debug("increaseRelockTime")
	increase("relockTime")
}

def decreaseRelockTime() {
	decrease("relockTime")
}

def setWrongCodeEntryLimit(Integer value) {
	setValue("wrongCodeEntryLimit", value)
}

def increaseWrongCodeEntryLimit() {
	increase("wrongCodeEntryLimit")
}

def decreaseWrongCodeEntryLimit() {
	decrease("wrongCodeEntryLimit")
}

def setShutdownTime(Integer value) {
	setValue("shutdownTime", value)
}

def increaseShutdownTime() {
	increase("shutdownTime")
}

def decreaseShutdownTime() {
	decrease("shutdownTime")
}

def previousAudioMode() {
	cycleMode("audioMode", true)
}

def previousLanguage() {
	cycleMode("language", true)
}

def previousOperatingMode() {
	cycleMode("operatingMode", true)
}

def nextAudioMode() {
	cycleMode("audioMode")
}

def nextLanguage() {
	cycleMode("language")
}

def nextOperatingMode() {
	cycleMode("operatingMode")
}

def setAudioMode(mode) {
	setValue("audioMode", mode)
}

def setLanguage(mode) {
	setValue("language", mode)
}

def setOperatingMode(mode) {
	setValue("operatingMode", mode)
}

def getValues(name) {
	def map = getSettingsMap()
    if (!map.containsKey(name)) {
    	log.error("Values requested for an unsupported attribute: $name")
        return null
    }    
    def result
    def entry = map[name]
    if (entry.containsKey("options")) {
		result = entry.options.collect({ it.key })
    } else if (entry.containsKey("range")) {
    	result = entry.range
    }
    return null
}

def cycleMode(String name, reverse = false) {
	def current = device.currentValue(name)
    log.debug("current $name = $current");
    def map = getSettingsMap()[name]
    
    if (!options.containsKey("options")) {
    	return reverse ? decrease(name) : increase(name)
    }
    
    def options = map["options"].entrySet.toArray()
    
    def next = null
    def first = null
    def found = false
    def start = 0
    def limit = options.size() - 1
    def increment = 1
    if (reverse) {
    	start = options.size()
        limit = 0
        increment = -1
    }
    
    for (def i = start; i != limit; i+=increment) {    
    	def e = options.get(i)
        if (found) {
        	next = e.value
    		log.debug("set next = $next")
            break;
        }
    	if (first == null) {
        	first = e.value
        }
        if (e.key == current) {
        	found = true
    		log.debug("found $current")
        }
    }
        
    if (next == null)
    	next = first
        
    
    def parameterNumber = map["parameterNumber"]
    log.debug("next = $next")
	setConfiguration(parameterNumber, next)

}

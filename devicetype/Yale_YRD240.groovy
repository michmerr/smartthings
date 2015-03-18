package michmerr.smartthings.devicetype

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
        capability "Refresh"
        capability "Sensor"
        capability "Lock Codes"
        capability "Battery"
        capability "Configuration"
        
        attribute "fault", "enum", getSupportedFaultTypes()

		/*
         * For enums, pull the options from the same map used to map Configuration Report parameter numbers
         * back to attributes and numeric value to enum string values. Changes made to that map will then
         * be reflected everywhere.
         *
         */
        attribute "oneTouchLocking", "enum", getSupportedSettingsValues("oneTouchLocking")
        attribute "audioMode", "enum", getSupportedSettingsValues("audioMode")
        attribute "autoRelock", "enum", getSupportedSettingsValues("autoRelock")
        attribute "relockTime", "number"
        attribute "wrongCodeEntryLimit", "number"
        attribute "shutdownTime", "number"
        attribute "language", "enum", getSupportedSettingsValues("language")
        attribute "insideIndicatorLight", "enum", getSupportedSettingsValues("insideIndicatorLight")
        attribute "operatingMode", "enum", getSupportedSettingsValues("operatingMode")

        attribute "schedule", "string"
        attribute "scheduleTypes", "string"

        command "unlockwtimeout"

        /*
         *
         * Commands related to device configuration.
         *
         */
         
        // Toggles
        
        command "toggleOneTouchLocking"
        command "enableOneTouchLocking"
        command "disableOneTouchLocking"
        command "toggleAutoRelock"
        command "enableAutoRelock"
        command "disableAutoRelock"
        command "toggleInsideIndicatorLight"
        command "enableInsideIndicatorLight"
        command "disableInsideIndicatorLight"

		// Number range controls
        
        command "increaseRelockTime"
        command "decreaseRelockTime"
        command "setRelockTime", ["number"]
        command "increaseWrongCodeEntryLimit"
        command "decreaseWrongCodeEntryLimit"
        command "setWrongCodeEntryLimit", ["number"]
        command "increaseShutdownTime"
        command "decreaseShutdownTime"
        command "setShutdownTime", ["number"]

		// Enum controls
        
        command "setAudioMode", ["enum"]
        command "nextAudioMode"
        command "previousAudioMode"
        command "setLanguage", ["enum"]
        command "nextLanguage"
        command "previousLanguage"
        command "setOperatingMode", ["enum"]
        command "nextOperatingMode"
        command "previousOperatingMode"

        command "getSupportedSettingsValues", ["enum"]

        /*
         *
         * Commands related to the Schedule Entry Lock Command Class (V3)
         *
         */


        command "clearAllUserSchedules", [ "number" ]
        command "clearAllSchedules"
        command "enableAllSchedules"
        command "enableSchedule", ["number"]
        command "disableAllSchedules"
        command "disableSchedule", ["number"]
        
        command "clearDailyRepeatingSchedule", [ "number", "number" ]
        command "clearUserDailyRepeatingSchedules", [ "number" ]
        command "clearAllDailyRepeatingSchedules"
        command "getDailyRepeatingSchedule", ["number", "number" ]
        command "setDailyRepeatingSchedule", ["number", "number", "number", "number", "number", "number", "number"]
        
        /*
         * The Yale lock doesn't support WeekDay and YearDay schedules,
         * but these are implemented anyway to uniformly present an interface
         * to the Schedule Entry Lock Command Class. Since the implementation 
         * checks for supported schedule types, these commands result in a noop,
         * but will work if copied to a device supporting the schedule type.
         *
         */
        command "clearWeekDaySchedule", [ "number", "number" ]
        command "clearUserWeekDaySchedules", [ "number" ]
        command "clearAllWeekDaySchedules"
        command "getWeekDaySchedule", ["number", "number" ]
        command "setWeekDaySchedule", ["number", "number", "number", "number", "number", "number", "number", "number"]
        
        command "clearYearDaySchedule", [ "number", "number" ]
        command "clearUserYearDaySchedules", [ "number" ]
        command "clearAllYearDaySchedules"
        command "getYearDaySchedule", ["number", "number" ]
        command "setYearDaySchedule", ["number", "number", "number", "number", "number", "number", "number", "number", "number", "number", "number", "number"]

        
        command "getSupportedFaultTypes"
        command "getFault", ["string" ]
        command "getFaults", ["string"]
	    command "clearFault", [ "string" ]
	    command "clearAllFaults"

        fingerprint deviceId: "0x4003", inClusters: "0x72, 0x86, 0x98"
    }

    /*

    Intuitively, the functionality would be here in preferences, but the scope of preferences in
    device types is limited to settings for the device type instance, and not the configuration
    settings for the device being controlled. While the settings values are updated and readable
    by the device type and device type handler, it is not possible to set those variables with
    values queried from the device.

    ╯°□°）╯︵-┻━┻

    */

    simulator {
        status "locked": "command: 9881, payload: 00 62 03 FF 00 00 FE FE"
        status "unlocked": "command: 9881, payload: 00 62 03 00 00 00 FE FE"

        reply "9881006201FF,delay state.const.DELAY,9881006202": "command: 9881, payload: 00 62 03 FF 00 00 FE FE"
        reply "988100620100,delay state.const.DELAY,9881006202": "command: 9881, payload: 00 62 03 00 00 00 FE FE"
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
import static java.util.Calendar.*

/**
 * Set [pseudo]constants in state for this device.
 *
 * TODO: Decide whether to have one of these per lock type in distinct device type scripts,
 *       or return different values based on fingerprint (or something).
 *
 */
def setConstants() {
    state.const = [
            DELAY : 4200,
            ASSOCIATION_DELAY 	: 6000,
            CODE_UPDATE_DELAY 	: 2200,
            CODE_LENGTH 		: 4..8,
            CODE_CHANGE_CHANGED : "changed",
            CODE_CHANGE_ADDED 	: "added",
            CODE_CHANGE_DELETED : "deleted"
    ]
}


/**
 * Map of configuration attributes to corresponding command parameter numbers, string to command value relationships,
 * and valid value ranges.
 *
 * (Note that numeric ranges are "bare". If wrapped in braces [1..7] it will return a Range inside an ArrayList.)
 * @return
 */
private getSettingsMap() {
    [ audioMode            : [ parameterNumber : 1,  options : [ "Silent":1, "Low":2, "High":3] ],
      autoRelock           : [ parameterNumber : 2,  options : [ "Off":0x00, "On":0xFF ] ],
      relockTime           : [ parameterNumber : 3,  range   : 5..255 ],
      wrongCodeEntryLimit  : [ parameterNumber : 4,  range   : 1..7 ],
      language             : [ parameterNumber : 5,  options : [ "English":1, "Spanish":2, "French":3] ],
      shutdownTime         : [ parameterNumber : 7,  range   : 1..255 ],
      operatingMode        : [ parameterNumber : 8,  options : [ "Normal":0, "Vacation":1, "Privacy":2] ],
      oneTouchLocking      : [ parameterNumber : 11, options : [ "Off":0x00, "On":0xFF ] ],
      insideIndicatorLight : [ parameterNumber : 13, options : [ "Off":0x00, "On":0xFF ] ] ]
}

/**
 * Parse messages from the hub.
 *
 * @param message The raw message from the hub.
 * @return an array of events and/or response commands.
 */
def parse(String message) {
    log.debug("parse($message)")
    def result = null
    if (message.startsWith("Err")) {
        if (state.sec) {
            result = createEvent(descriptionText:message, displayed:false)
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
        def cmd = zwave.parse(message, [ 0x98: 1, 0x72: 2, 0x85: 2 ])
        if (cmd) {
            result = zwaveEvent(cmd)
        }
    }
    log.debug "\"$message\" parsed to ${result.inspect()}"
    result
}


/**
 * Refresh device state.
 *
 * @return
 */
def refresh() {
    def cmds = checkAssociation()
    cmds << configure()
    log.debug "refresh sending ${cmds.inspect()}"
    cmds
}

/**
 * Poll the device for current state.
 *
 * @return
 */
def poll() {
    def cmds = []

    if (secondsPast(state.lastPoll, 60 * 60 * 10)) {
        cmds << getBatteryState()
        cmds << getLockState()
        state.lastPoll = (new Date()).time
    }

    if (cmds) {
        log.debug "poll is sending ${cmds.inspect()}, state: ${state.findAll({ !it.key.startsWith('code')}).collect()}"
        delayBetween(cmds, state.const.DELAY)
    } else {
        null
    }
}

/**
 *
 * @return
 */
def checkAssociation() {
    log.debug "checking association"
    def cmds = []
    if (state.assoc != zwaveHubNodeId) {
        if (!state.associationQuery) {
            cmds = [ secure(zwave.associationV1.associationGet(groupingIdentifier:1)), "delay ${state.const.ASSOCIATION_DELAY}" ]
        } else if (secondsPast(state.associationQuery, 9)) {
            cmds = setAssociation()
        }
    }
    state.associationQuery = new Date().time
    cmds
}

/**
 *
 * @return
 */
def setAssociation() {
    log.debug "setting association"
    state.associationQuery = new Date().time
    secureSequence([zwave.associationV1.associationSet(groupingIdentifier:1, nodeId:zwaveHubNodeId),
            zwave.associationV1.associationGet(groupingIdentifier:1)], state.const.ASSOCIATION_DELAY, false, true)
}

/**
 *
 * @return
 */
def update() {
    refresh()
}

/**
 *
 * @return
 */
def configure() {
	setConstants()
    def cmds = getConfiguration()
    cmds << getScheduleEntryTypeSupported()
    cmds << setTime()
    cmds << getBatteryState()
    cmds << getLockState()
    state.lastPoll = (new Date()).time
    delayBetween(cmds, state.const.DELAY)
}

/*
 *
 * Type-specific event handlers/parsers
 *
 */

/**
 * Handle SecurityMessageEncapsulation commands from the hub.
 *
 * @param cmd a securityv1.SecurityMessageEncapsulation command.
 * @return the result of the handling of the encapsulated command.
 */
def zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityMessageEncapsulation cmd) {
    def encapsulatedCommand = cmd.encapsulatedCommand([0x62: 1, 0x71: 2, 0x80: 1, 0x85: 2, 0x63: 1, 0x98: 1])
    // log.debug "encapsulated: $encapsulatedCommand"
    if (encapsulatedCommand) {
        zwaveEvent(encapsulatedCommand)
    }
}

/**
 * Handle NetworkKeyVerify commands from the hub.
 *
 * @param cmd a securityv1.NetworkKeyVerify command.
 * @return an array containing an event signaling the success of device inclusion.
 */
def zwaveEvent(physicalgraph.zwave.commands.securityv1.NetworkKeyVerify cmd) {
    createEvent(name:"secureInclusion", value:"success", descriptionText:"Secure inclusion was successful")
}

/**
 * Handle SecurityCommandsSupportedReport commands from the hub.
 *
 * Handler state.sec and state.secCon are set to the list of commands and control commands
 * supported by the device. This list is longer than the non-secure list used when matching
 * fingerprints.
 *
 * @param cmd a securityv1.SecurityCommandsSupportedReport command.
 * @return an event signaling the success of device inclusion.
 */
def zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityCommandsSupportedReport cmd) {
    log.debug("SecurityCommandsSupportedReport: $cmd")
    state.sec = cmd.commandClassSupport.collect { String.format("%02X ", it) }.join()
    if (cmd.commandClassControl) {
        state.secCon = cmd.commandClassControl.collect { String.format("%02X ", it) }.join()
    }
    log.debug "Security command classes: $state.sec"
    createEvent(name:"secureInclusion", value:"success", descriptionText:"Lock is securely included")
}

/**
 * Handle DoorLockOperationReport commands from the hub.
 *
 * @param cmd a doorlockv1.DoorLockOperationReport command.
 * @return an array containing an event signaling the lock state of the device.
 */
def zwaveEvent(DoorLockOperationReport cmd) {
    log.debug("DoorLockOperationReport $cmd")

    def map = [ name: "lock" ]
    if (cmd.doorLockMode == 0xFF) {
        map.value = "locked"
    } else if (cmd.doorLockMode >= 0x40) {
        map.value = "unknown"
    } else if (cmd.doorLockMode & 1) {
        map.value = "unlocked with timeout"
    } else {
        map.value = "unlocked"
    }
    [createEvent(map), poll()]
}

/**
 * Handle DoorLockConfigurationReport commands from the hub.
 *
 * We're using the Configuration commands to set/get these values,
 * so this is a redundant handler.
 *
 * @param cmd a doorlockv1.DoorLockConfigurationReport command.
 * @return an array containing an event signaling the configuration state of the device.
 */
def zwaveEvent(DoorLockConfigurationReport cmd) {
    log.debug("DoorLockConfigurationReport $cmd")

    def result = []
    result << createEvent(name: "autoRelock", value: (cmd.operationType == DoorLockConfigurationReport.OPERATION_TYPE_TIMED_OPERATION))
    result << createEvent(name: "relockTime", value: cmd.lockTimeoutSeconds)
    return result;
}

def alarmEventLock(physicalgraph.zwave.commands.alarmv2.AlarmReport cmd) {
    def map = [ name: "lock", isPhysical: (0x11..0x16).contains(cmd.alarmType) ]
    switch(cmd.alarmType) {
        case 0x12:
            map.value = "locked"
            map.descriptionText = "$device.displayName locked by user ${cmd.alarmLevel}"
            map.data = [ userIdentifier: cmd.alarmLevel, method: "keypad" ]
            break
        case 0x13:
            map.value = "unlocked"
            map.descriptionText = "$device.displayName unlocked by user ${cmd.alarmLevel}"
            map.data = [ userIdentifier: cmd.alarmLevel, method: "keypad"]
            break
        case 0x15:
            map.value = "locked"
            switch (cmd.alarmLevel) {
                case 0x01:
                    map.descriptionText = "$device.displayName manually locked"
                    map.data = [ method: "manual" ]
                    break
                case 0x02:
                    map.descriptionText = "$device.displayName locked with one-touch locking"
                    map.data = [ method: "touch"]
                    break
                default:
                    map["descriptionText"] = "$device.displayName locked (event code ${cmd.alarmLevel})"
                    break
            }
            break
        case 0x16:
            map.value = "unlocked"
            map.description = "$device.displayName manually unlocked"
            map.data = [ method: "manual"]
            break
        case 0x18:
            map.value = "locked"
            map.descriptionText = "$device.displayName locked via remote command"
            map.data = [ method: "wireless" ]
            break
        case 0x19:
            map.value = "unlocked"
            map.descriptionText = "$device.displayName unlocked via remote command"
            map.data = [ method: "wireless" ]
            break
        case 0x1B:  // Autolocked
            map.value = "locked"
            map.descriptionText = "$device.displayName locked automatically"
            map.data = [ method: "auto" ]
            break
        default:
            map = [ displayed: false, descriptionText: "$device.displayName: alarm event $cmd.alarmType level $cmd.alarmLevel" ]
            break
    }
    map
}

/**
 * Handle AlarmReport commands from the hub.
 *
 * @param cmd a alarmv2.AlarmReport command.
 * @return an array containing events and/or response commands which vary depending on the details of the report.
 */
def zwaveEvent(physicalgraph.zwave.commands.alarmv2.AlarmReport cmd) {
    def result = []
    def map = null
    if (cmd.alarmType > 0x11 && cmd.alarmType < 0x1C) {
        map = alarmEventLock(cmd)
    } else {
        switch (cmd.alarmType) {
            case 0x09:
            	map  = addFault( type : "jammed",
                                 level: "critical",
                                 descriptionText: "Bolt jammed")
                break
            case 0x21:
            	def isPhysical = !state.pending?.delete?."$cmd.alarmLevel"
                if (!isPhysical) state.pending.delete.remove("$cmd.alarmLevel".toString())
                
                map = [ name: "codeChanged", 
                		value: cmd.alarmLevel, 
                        displayed: true, 
                        isStateChange: true, 
                        isPhysical: isPhysical,
                        descriptionText: "$device.displayName code $cmd.alarmLevel was deleted",
                        data: [ change: state.const.CODE_CHANGE_DELETED, 
                        		method: isPhysical ? "keypad" : "wireless" ] ] 
                                
                state.remove("code$cmd.alarmLevel".toString())
                break
            case 0x70:            
            	def isPhysical = !state.pending?.change?."$cmd.alarmLevel"
                // let the handler for the requestCode response clear the pending.change flag.
                
                def change = state.containsKey("code$cmd.alarmLevel".toString()) ? state.const.CODE_CHANGE_CHANGED : state.const.CODE_CHANGE_ADDED
                map = [ name: "codeChanged", 
                		value: cmd.alarmLevel, 
                        displayed: true, 
                        isStateChange: true,  
                        isPhysical: isPhysical,
                        descriptionText: "$device.displayName code $cmd.alarmLevel $change, querying for new code value.",
                        data: [ change: change, 
                        		method: isPhysical ? "keypad" : "wireless" ] ]

                result << response(requestCode(cmd.alarmLevel))
                break
            case 0x82:
                map = [ descriptionText: "$device.displayName power restored", displayed: true, isPhysical: true ]
                result << response(configure())
                break
            case 0x71:
                map = [ descriptionText: "$device.displayName code entered for user $cmd.alarmLevel is already in use", displayed: true ]
                break
            case 0xA1:
            	map = (cmd.alarmLevel == 2) ?
                	addFault(type : "tamper",
                        	 level: "critical",
                        	 descriptionText: "front escutcheon removed") :
                    addFault(type : "user",
                			 level: "warning",
                             descriptionText: "exceeded failed user code attempt limit")
                break
            case 0xA7:
                result << response(poll())
                map = addFault(type : "battery",
                			   level: "warning",
                               descriptionText: "Low battery")
                break
            case 0xA8:
                result << setBatteryLevel(0xFF)
                break
            case 0xA9:
                result << setBatteryLevel(0)
                break
            default:
                map = [ displayed: false, descriptionText: "$device.displayName: alarm event $cmd.alarmType level $cmd.alarmLevel" ]
                break
        }
    }
    
    if (map)
    	result ? [createEvent(map), *result] : createEvent(map)
    else if (result)
    	result
}

/**
 * Handle UserCodeReport commands from the hub.
 *
 * @param cmd a usercodev1.UserCodeReport command.
 * @return an array containing events and/or response commands which vary depending on the details of the report.
 */
def zwaveEvent(UserCodeReport cmd) {
    log.debug("UserCodeReport: $cmd")
    def result = []
    def name = "code$cmd.userIdentifier"
    def code = cmd.code
    
    def isPhysical = !state.pending?.change?."$cmd.userIdentifier"
    if (!isPhysical) state.pending.change.remove("$cmd.userIdentifier".toString())
    
    def map = [
            name: "codeReport",
            value: cmd.userIdentifier,
            displayed : cmd.userIdentifier != state.requestCode,
            isStateChange: true,
            isPhysical: isPhysical,
            data: [ code : code ]
    ]

    def reason = ""
    if (cmd.userIdStatus == UserCodeReport.USER_ID_STATUS_OCCUPIED)
    {
        reason="is set"
        state[name] = code
    } else {
        map.data.code = ""
        if (state[name]) {
            reason = "was deleted"
            state.remove(name)
        } else {
            reason = "is not set"
            map.isStateChange = false
        }
    }

    map.descriptionText = "$device.displayName code for user $cmd.userIdentifier $reason."
    result << createEvent(map)

    if (cmd.userIdentifier == state.requestCode) {  // reloadCodes() was called, keep requesting the codes in order
        if (state.requestCode + 1 > state.codes) {
            state.remove("requestCode")  // done
        } else {
            state.requestCode = state.requestCode + 1  // get next
            result << response(requestCode(state.requestCode))
        }
    }
    log.debug "code report parsed to ${result.inspect()}"
    result
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(UsersNumberReport cmd) {
    def result = []
    state.codes = cmd.supportedUsers
    result
}

/**
 *
 * @param cmd
 * @return
 */
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

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicSet cmd) {
    // The old Schlage locks use group 1 for basic control - we don't want that, so unsubscribe from group 1
    def result = [ createEvent(name: "lock", value: cmd.value ? "unlocked" : "locked") ]
    result << response(zwave.associationV1.associationRemove(groupingIdentifier:1, nodeId:zwaveHubNodeId))
    if (state.assoc != zwaveHubNodeId) {
        result << response(zwave.associationV1.associationGet(groupingIdentifier:2))
    }
    result
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.batteryv1.BatteryReport cmd) {
    setBatteryLevel(cmd.batteryLevel)
}

/**
 * Create events to change the battery level attribute and low battery faults,
 * if necessary.
 *
 * @return an event or pair of created events to signal battery level change and low level fault (if necessary).
 */
def setBatteryLevel(level) {
	def result = []
    def map = [ name: "battery", unit: "%" ]
    
    if (level <= 1 || level == 0xFF) {
    		def description = "Battery ${level == 0 ? 'dead' : 'critical'"
    		def fault = addFault(type : "battery",
                                 level: level == 0 ? "critical" : "error",
                                 descriptionText: description )
            if (fault)
                result << createEvent(fault)
            map.descriptionText = description
            map.displayed = true        
            map.value = (level == 0xFF) ? 1 : level
    } else {
    		map.value = level
    }
    
    state.lastbatt = new Date().time
    result << createEvent(map)
    
    result
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.manufacturerspecificv2.ManufacturerSpecificReport cmd) {
    def result = []

    def msr = String.format("%04X-%04X-%04X", cmd.manufacturerId, cmd.productTypeId, cmd.productId)
    log.debug "msr: $msr"
    updateDataValue("MSR", msr)

    result << createEvent(descriptionText: "$device.displayName MSR: $msr", isStateChange: false)
    result
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.versionv1.VersionReport cmd) {
    def fw = "${cmd.applicationVersion}.${cmd.applicationSubVersion}"
    updateDataValue("fw", fw)
    def text = "$device.displayName: firmware version: $fw, Z-Wave version: ${cmd.zWaveProtocolVersion}.${cmd.zWaveProtocolSubVersion}"
    createEvent(descriptionText: text, isStateChange: false)
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.applicationstatusv1.ApplicationBusy cmd) {
    def msg = cmd.status == 0 ? "try again later" :
        cmd.status == 1 ? "try again in $cmd.waitTime seconds" :
            cmd.status == 2 ? "request queued" : "sorry"
    createEvent(displayed: false, descriptionText: "$device.displayName is busy, $msg")
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.configurationv2.ConfigurationReport cmd) {
    log.debug("configurationReportHandler: report: ${cmd}")
    def value = cmd.configurationValue[0]
    def settingsVar = getSettingsVar(cmd.parameterNumber);
    def current = device.currentValue(settingsVar.key)
    def currentValue = current
    def newValue = value

    if (settingsVar.value.containsKey("options")) {
        def options = settingsVar.value["options"]
        currentValue = options[current]
        newValue = options.find( { it.value == value } ).key
    }

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

/*
 *
 * Schedule Entry Lock Command Class (V3)
 *
 */

/**
 *
 * @param userIdentifier
 * @param slot
 * @param map
 * @param type
 * @return
 */
private scheduleEvent(userIdentifier, slot, Map map, String type) {
    if (state.codeSchedule == null) {
        state.codeSchedule = [:]
    }
    if (state.codeSchedule."${type}" == null) {
        state.codeSchedule."${type}" = [:]
    }
    if (state.codeSchedule."${type}"."${userIdentifier}" == null) {
        state.codeSchedule."${type}"."${userIdentifier}" = [:]
    }
    state.codeSchedule."${type}"."${userIdentifier}"."${slot}" = map

    createEvent(name: "schedule", value: state.codeSchedule, data: [ type: type, userChanged: userIdentifier, slotChanged : slot ])
}

/**
 *
 * @param userIdentifier
 * @param slot
 * @param map
 * @param type
 * @return
 */
private scheduleEventDelete(userIdentifier, slot, String type) {
    log.debug("Processing deletion of $type schedule in slot $slot for user number $userIdentifier.")

    if (state.codeSchedule."${type}"?."${userIdentifier}"?."${slot}") {
        // toString(), in case slot is not a string, INCLUDING a Gstring.
        state.codeSchedule."${type}"."${userIdentifier}".remove(slot.toString())
    }

    createEvent(name: "schedule", value: state.codeSchedule, data: [ type: type, userChanged: userIdentifier, slotChanged : slot ])
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.scheduleentrylockv3.ScheduleEntryLockDailyRepeatingReport cmd) {
/*
Short	durationHour
Short	durationMinute
Short	scheduleSlotId
Short	startHour
Short	startMinute
Short	userIdentifier
Short	weekDayBitmask
*/
    log.debug("ScheduleEntryLockDailyRepeatingReport $cmd")
    if (cmd.startHour == 0xFF) {
        scheduleEventDelete(cmd.userIdentifier, cmd.scheduleSlotId, "dailyRepeating")
    } else {
        def map = [ durationHour: cmd.durationHour,
                durationMinute: cmd.durationMinute,
                startHour: cmd.startHour,
                startMinute: cmd.startMinute,
                userIdentifier: cmd.userIdentifier,
                weekDayBitmask: cmd.weekDayBitmask ]
        scheduleEvent(cmd.userIdentifier, cmd.scheduleSlotId, map, "dailyRepeating")
    }
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.scheduleentrylockv3.ScheduleEntryLockWeekDayReport  cmd) {
/*
Short	dayOfWeek
Short	scheduleSlotId
Short	startHour
Short	startMinute
Short	stopHour
Short	stopMinute
Short	userIdentifier
*/
    log.debug("ScheduleEntryLockWeekDayReport $cmd")
    def map = [ dayOfWeek: cmd.dayOfWeek,
            startHour: cmd.startHour,
            startMinute: cmd.startMinute,
            stopHour: cmd.stopHour,
            stopMinute: cmd.stopMinute,
            userIdentifier: cmd.userIdentifier ]
    scheduleEvent(cmd.userIdentifier, cmd.scheduleSlotId, map, "weekDay")
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.scheduleentrylockv3.ScheduleEntryLockYearDayReport  cmd) {
/*
Short	scheduleSlotId
Short	startDay
Short	startHour
Short	startMinute
Short	startMonth
Short	startYear
Short	stopDay
Short	stopHour
Short	stopMinute
Short	stopMonth
Short	stopYear
Short	userIdentifier
*/
    log.debug("ScheduleEntryLockYearDayReport $cmd")
    def map = [ startDay: cmd.startDay,
            startHour: cmd.startHour,
            startMinute: cmd.startMinute,
            startMonth: cmd.startMonth,
            startYear: cmd.startYear,
            stopDay: cmd.stopDay,
            stopHour: cmd.stopHour,
            stopMinute: cmd.stopMinute,
            stopMonth: cmd.stopMonth,
            stopYear: cmd.stopYear,
            userIdentifier: cmd.userIdentifier ]
    scheduleEvent(cmd.userIdentifier, cmd.scheduleSlotId, map, "yearDay")
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.scheduleentrylockv3.ScheduleEntryLockTimeOffsetReport cmd) {
/*
Short	hourTzo
Short	minuteOffsetDst
Short	minuteTzo
Boolean	signOffsetDst
Boolean	signTzo
*/
    log.debug("ScheduleEntryLockTimeOffsetReport $cmd")
    if (state.time == null) {
        state.time = [:]
    }
    state.time.offset = [ hourTzo: cmd.hourTzo,
            minuteOffsetDst: cmd.minuteOffsetDst,
            minuteTzo: cmd.minuteTzo,
            signOffsetDst: cmd.signOffsetDst,
            signTzo: cmd.signTzo ]

    def result = []
    if (state.time.delta == null) {
        result << response(getTimeParameters())
    }
    result
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.scheduleentrylockv3.ScheduleEntryTypeSupportedReport cmd) {
/*
Short	numberOfSlotsDailyRepeating
Short	numberOfSlotsWeekDay
Short	numberOfSlotsYearDay
*/
    log.debug("ScheduleEntryTypeSupportedReport: $cmd")
    if (state.codeSchedule == null) {
        state.codeSchedule = [:]
    }
    if (state.codeSchedule.numberOfSlots == null) {
        state.codeSchedule.numberOfSlots = [:]
    }

    state.codeSchedule.numberOfSlots.dailyRepeating = cmd.numberOfSlotsDailyRepeating
    state.codeSchedule.numberOfSlots.weekDay = cmd.numberOfSlotsWeekDay
    state.codeSchedule.numberOfSlots.yearDay = cmd.numberOfSlotsYearDay

    createEvent(name: "scheduleTypes", value: state.codeSchedule.numberOfSlots)
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.commands.timeparametersv1.TimeParametersReport cmd) {
/*
Short	day
Short	hourUtc
Short	minuteUtc
Short	month
Short	secondUtc
Integer	year
*/

    // Track the delta between the time in the report and the current time,
    // so that we can display device time without constantly querying for
    // the current value.

    def now = new Date()

    log.debug("TimeParametersReport: $cmd")
    def map = [ year   : now[YEAR]         - cmd.year,
            month  : now[MONTH]        - cmd.month,
            day    : now[DAY_OF_MONTH] - cmd.day,
            hour   : now[HOUR_OF_DAY]  - cmd.hourUtc,
            minute : now[MINUTE]       - cmd.minuteUtc,
            second : now[SECOND]       - cmd.secondUtc ]
    if (state.time == null) {
        state.time = [:]
    }

    state.time.delta = map

    def result = []
    if (state.time.offset == null) {
        result << response(getScheduleTimeOffset())
    }
    result
}

/**
 *
 * @param cmd
 * @return
 */
def zwaveEvent(physicalgraph.zwave.Command cmd) {
    log.debug("Unhandled command: $cmd")
    def evt = createEvent(displayed: false, descriptionText: "$device.displayName: $cmd")
    log.debug(evt)
    return evt
}

/*
 *
 * Lock Command Class (V1)
 *
 */

def getLockState() {
    secure(zwave.doorLockV1.doorLockOperationGet())
}

/**
 *
 * @param doorLockMode
 * @return
 */
def lockAndCheck(doorLockMode) {
    secureSequence([
            zwave.doorLockV1.doorLockOperationSet(doorLockMode: doorLockMode),
            zwave.doorLockV1.doorLockOperationGet()
    ], state.const.DELAY)
}

/**
 *
 * @return
 */
def lock() {
    lockAndCheck(DoorLockOperationSet.DOOR_LOCK_MODE_DOOR_SECURED)
}

/**
 *
 * @return
 */
def unlock() {
    lockAndCheck(DoorLockOperationSet.DOOR_LOCK_MODE_DOOR_UNSECURED)
}

/**
 *
 * @return
 */
def unlockwtimeout() {
    lockAndCheck(DoorLockOperationSet.DOOR_LOCK_MODE_DOOR_UNSECURED_WITH_TIMEOUT)
}

/*
 *
 * Battery Command Class (V1)
 *
 */

/**
 * Get the current charge level of the device battery.
 *
 * @return a secure batteryV1.batteryGet command
 */
def getBatteryState() {
    secure(zwave.batteryV1.batteryGet())
}

/*
 *
 * User Code Command Class (V1)
 *
 */

/**
 * Query the lock for a specific user.
 *
 * @param userIdentifier
 * @return
 */
def requestCode(userIdentifier) {
    userIdentifier = userIdentifier.toInteger()
    def cachedCode = getCode(userIdentifier)

    cachedCode ? sendEvent(name: "codeReport", value: userIdentifier, data: [ code: cachedCode ]) : secure(zwave.userCodeV1.userCodeGet(userIdentifier: userIdentifier))
}

/**
 * Query the lock for all codes.
 *
 * @return
 */
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

/**
 * Set a user code.
 *
 * @param userIdentifier
 * @param code
 * @return
 */
def setCode(userIdentifier, code) {
    userIdentifier = userIdentifier.toInteger()
    if (code instanceof Number) code = code.toInteger().toString()
    if (!(code instanceof String && code.isNumber())) {
        log.error("'$code' must be a string composed of numbers.")
        return
    }
    if (code instanceof String && !code.isInteger()) {
    	code = code.substring(0, code.indexOf('.'))
    }

    log.debug "setting code $userIdentifier to $code"
    if (!state.pending) state.pending = [:]
    if (!state.pending.change) state.pending.change = [:]
    state.pending.change."$userIdentifier" = true
    secure(zwave.userCodeV1.userCodeSet(userIdentifier:userIdentifier, userIdStatus:UserCodeSet.USER_ID_STATUS_OCCUPIED, user:code))
}

/**
 * Delete a user.
 *
 * @param userIdentifier
 * @return
 */
def deleteCode(userIdentifier) {
	userIdentifier = userIdentifier.toInteger()
    log.debug "deleting code $userIdentifier"
    if (!state.pending) state.pending = [:]
    if (!state.pending.delete) state.pending.delete = [:]
    state.pending.delete."$userIdentifier" = true
    secure(zwave.userCodeV1.userCodeSet(userIdentifier:userIdentifier, userIdStatus: UserCodeSet.USER_ID_STATUS_AVAILABLE_NOT_SET))
}

/**
 * Apply updated user codes to the lock.
 *
 * @param codeSettings
 * @return
 */
def updateCodes(codeSettings) {
    if(codeSettings instanceof String) codeSettings = util.parseJson(codeSettings)
    def set_cmds = []
    def get_cmds = []
    codeSettings.each { name, updated ->
        def current = state[name]
        if (name.startsWith("code")) {
            def n = name[4..-1].toInteger()
            log.debug "$name was $current, set to $updated"
            if (state.const.CODE_LENGTH.contains(updated?.size()) && updated != current) {
                def cmds = setCode(n, updated)
                set_cmds << cmds.first()
                get_cmds << cmds.last()
            } else if ((current && updated == "") || updated == "0") {
                def cmds = deleteCode(n)
                set_cmds << cmds.first()
                get_cmds << cmds.last()
            } else if (updated && !state.const.CODE_LENGTH.contains(updated.size())) {
                // Entered code was out of range.
                log.warn("Code for user $n is ${updated.size()}, supported range is ${state.const.CODE_LENGTH}")
                codeSettings."code$n" = current
            }
        } else log.warn("unexpected entry $name: $updated")
    }
    if (set_cmds) {
        return response(delayBetween([delayBetween(set_cmds, state.const.CODE_UPDATE_DELAY), delayBetween(get_cmds, state.const.DELAY)], state.const.CODE_UPDATE_DELAY))
    }
}

/**
 * Get the cached code for a user.
 *
 * @param userIdentifier
 * @return
 */
def getCode(userIdentifier) {
    state["code${userIdentifier}"]
}

/*
 *
 * Schedule Entry Lock Command Class (V3)
 *
 */


/**
 * Check to see if a schedule type and slot number is supported.
 *
 * @param slot
 * @param type
 * @return
 */
private Boolean supported(Number slot, String type) {
    def supported = state.codeSchedule?.numberOfSlots?."${type}" ?: 0
    Boolean result = (slot <= supported )
    if (!result) {
        if (supported < 1) {
            log.warning("This device does not support the $type.capitalize() schedule type.")
        } else {
            log.warning("Slot $slot request, but this device only supports $supported $type schedules.")
        }
    }
    result
}

/**
 * Clear a schedule slot.
 *
 * @param userIdentifier
 * @param slot
 * @param type
 * @return
 */
def clearSchedule(userIdentifier, slot, type) {
    slot = slot.toInteger()
    userIdentifier = userIdentifier.toInteger()

    if (supported(slot, type)) {
        log.debug("Clearing $type schedule slot $slot on user $userIdentifier")
        secureSequence([zwave.scheduleEntryLockV3."scheduleEntryLock${type.capitalize()}Set"(userIdentifier: userIdentifier, scheduleSlotId: slot, setAction: 0),
                zwave.scheduleEntryLockV3."scheduleEntryLock${type.capitalize()}Get"(userIdentifier: userIdentifier, scheduleSlotId: slot)])
    }
}

/**
 * Clear all user schedules of a specific type.
 *
 * @param userIdentifier
 * @param type
 * @return
 */
def clearUserSchedules(userIdentifier, type) {

    def cmds = state.codeSchedule?."$type"?."$userIdentifier"?.collect({ clearSchedule(userIdentifier, it.key, type) })
    cmds ? delayBetween(cmds, state.const.DELAY) : null
}

/**
 * Clear all schedules of a specific type.
 *
 * @param type
 * @return
 */
def clearAllSchedules(type) {
    def cmds = state.codeSchedule?."$type"?.collect(clearUserSchedules(it.key, type))
    cmds ? delayBetween(cmds, state.const.DELAY) : null
}

/**
 * Clear a daily repeating schedule slot for a user.
 *
 * @param userIdentifier
 * @param slot
 * @return
 */
def clearDailyRepeatingSchedule(userIdentifier, slot) {
    clearSchedule(userIdentifier, slot, "dailyRepeating")
}

/**
 * Clear all daily repeating schedule slots for a user.
 *
 * @param userIdentifier
 * @return
 */
def clearUserDailyRepeatingSchedules(userIdentifier) {
    clearUserSchedules(userIdentifier, "dailyRepeating")
}

/**
 * Clear all daily repeating schedules for all users.
 *
 * @return
 */
def clearAllDailyRepeatingSchedules() {
    clearAllSchedules("dailyRepeating")
}

/**
 * Clear a week day schedule slot for a user.
 *
 * @param userIdentifier
 * @param slot
 * @return
 */
def clearWeekDaySchedule(userIdentifier, slot) {
    clearSchedule(userIdentifier, slot, "weekDay")
}

/**
 * Clear all week day schedules for a user.
 *
 * @param userIdentifier
 * @return
 */
def clearUserWeekDaySchedules(userIdentifier) {
    clearUserSchedules(userIdentifier, "weekDay")
}

/**
 * Clear all week day schedules for all users.
 *
 * @return
 */
def clearAllWeekDaySchedules() {
    clearAllSchedules("weekDay")
}

/**
 * Clear all types of schedules for all users.
 *
 * @return
 */
def clearAllSchedules() {
    def cmds = state.codeSchedule?.collect({ clearAllSchedules(it.key)})
    cmds ? delayBetween(cmds, state.const.DELAY) : null
}

/**
 * Get an existing schedule.
 *
 * @param userIdentifier
 * @param slot
 * @param type
 * @return
 */
def getSchedule(userIdentifier, slot, type) {
    userIdentifier = userIdentifier.toInteger()
    slot = slot.toInteger()
    secure(zwave.scheduleEntryLockV3."scheduleEntryLock${type.capitalize()}Get"(userIdentifier: userIdentifier, scheduleSlotId: slot))
}

/**
 * Get an existing daily repeating schedule.
 *
 * @param userIdentifier
 * @param slot
 * @return
 */
def getDailyRepeatingSchedule(userIdentifier, slot) {
    getSchedule(userIdentifier, slot, "dailyRepeating")
}

/**
 * Create/edit a daily repeating schedule.
 *
 * @param userIdentifier
 * @param slot
 * @param durationHour
 * @param durationMinute
 * @param startHour
 * @param startMinute
 * @param weekDayBitmask
 * @return
 */
def setDailyRepeatingSchedule(userIdentifier,
                              slot,
                              durationHour,
                              durationMinute,
                              startHour,
                              startMinute,
                              weekDayBitmask) {
/*
Short	durationHour
Short	durationMinute
Short	scheduleSlotId
Short	setAction  // 0 erase, 1 set
Short	startHour
Short	startMinute
Short	userIdentifier
Short	weekDayBitmask Sa Fr Th W Tu M Su
                       0  0  0  0 0  0 0
*/
    if (userIdentifier instanceof String)
        userIdentifier = userIdentifier.toInteger()
    if (slot instanceof String)
        slot = slot.toInteger()
    if (weekDayBitmask instanceof String)
        weekDayBitmask = weekDayBitmask.toInteger()
    if (startHour instanceof String) startHour = startHour.toShort()
    if (startMinute instanceof String) startMinute = startMinute.toShort()
    if (durationHour instanceof String) durationHour = durationHour.toShort()
    if (durationMinute instanceof String) durationMinute = durationMinute.toShort()

    def cmd = null
    if (supported(slot, "dailyRepeating")) {
        def map = [ userIdentifier : userIdentifier,
                scheduleSlotId : slot,
                durationHour:durationHour,
                durationMinute:durationMinute,
                setAction:1,
                startHour:startHour,
                startMinute:startMinute,
                weekDayBitmask:weekDayBitmask]
        log.debug("set schedule with $map")
        cmd = secureSequence([zwave.scheduleEntryLockV3.scheduleEntryLockDailyRepeatingSet(map),
                zwave.scheduleEntryLockV3.scheduleEntryLockDailyRepeatingGet(userIdentifier: userIdentifier, scheduleSlotId: slot)])
    }
    cmd
}

/**
 * Get an existing week day schedule.
 *
 * @param userIdentifier
 * @param slot
 * @return
 */
def getWeekDaySchedule(userIdentifier, slot) {
    getSchedule(userIdentifier, slot, "weekDay")
}

/**
 * Define/edit a week day schedule.
 *
 * @param userIdentifier
 * @param slot
 * @param dayOfWeek
 * @param startHour
 * @param startMinute
 * @param stopHour
 * @param stopMinute
 * @return
 */
def setWeekDaySchedule(userIdentifier, slot, dayOfWeek,
                       startHour, startMinute,
                       stopHour, stopMinute) {


    if (startHour instanceof String) startHour = startHour.toShort()
    if (startMinute instanceof String) startMinute = startMinute.toShort()
    if (stopHour instanceof String) stopHour = stopHour.toShort()
    if (stopMinute instanceof String) stopMinute = stopMinute.toShort()
    if (dayOfWeek instanceof String) dayOfWeek = dayOfWeek.toShort()
    if (slot instanceof String) slot = slot.toShort()
    if (userIdentifier instanceof String) userIdentifier = userIdentifier.toShort()

    def cmd = null
    if (supported(slot, "weekDay")) {
        log.debug("setWeekDaySchedule userId: $userIdentifier, slot: $slot, day: ${dayOfWeek}, start: ${startHour}:${startMinute}, stop: ${stopHour}:${stopMinute}")

        map = [ scheduleSlotId : slot,
                startHour: startHour,
                startMinute: startMinute,
                stopHour: stopHour,
                stopMinute: stopMinute,
                dayOfWeek: dayOfWeek,
                setAction: 1,
                userIdentifier: userIdentifier ]

        cmd = secureSequence([zwave.scheduleEntryLockV3.scheduleEntryLockWeekDaySet(map),
                zwave.scheduleEntryLockV3.scheduleEntryLockWeekDayGet()])
    }
    cmd
}

/**
 * Get a year day schedule.
 *
 * @param userIdentifier
 * @param slot
 * @return
 */
def getYearDaySchedule(userIdentifier, slot) {
    getSchedule(userIdentifier, slot, "yearDay")
}

/**
 * Define/edit a year day schedule.
 *
 * @param userIdentifier
 * @param slot
 * @param startDay
 * @param startHour
 * @param startMinute
 * @param startMonth
 * @param startYear
 * @param stopDay
 * @param stopHour
 * @param stopMinute
 * @param stopMonth
 * @param stopYear
 * @return
 */
def setYearDaySchedule(userIdentifier, slot,
                       startDay, startHour, startMinute, startMonth, startYear,
                       stopDay, stopHour, stopMinute, stopMonth, stopYear) {
/*
Short	scheduleSlotId
Short	startDay
Short	startHour
Short	startMinute
Short	startMonth
Short	startYear
Short	stopDay
Short	stopHour
Short	stopMinute
Short	stopMonth
Short	stopYear
Short	userIdentifier
*/
    log.debug("setYearDaySchedule userId: $userIdentifier, slot: $slot, start: ${startHour}:${startMinute} ${startMonth}/${startDay}/${startYear}, stop: ${stopHour}:${stopMinute} ${stopMonth}/${stopDay}/$stopYear} ")

    if (startDay instanceof String) startDay = startDay.toShort()
    if (startHour instanceof String) startHour = startHour.toShort()
    if (startMinute instanceof String) startMinute = startMinute.toShort()
    if (startMonth instanceof String) startMonth = startMonth.toShort()
    if (startYear instanceof String) startYear = startYear.toShort()
    if (stopDay instanceof String) stopDay = stopDay.toShort()
    if (stopHour instanceof String) stopHour = stopHour.toShort()
    if (stopMinute instanceof String) stopMinute = stopMinute.toShort()
    if (stopMonth instanceof String) stopMonth = stopMonth.toShort()
    if (stopYear instanceof String) stopYear = stopYear.toShort()
    if (userIdentifier instanceof String) userIdentifier = userIdentifier.toShort()

    def cmd = null
    if (supported(slot, "yearDay")) {
        def map = [ startDay: startDay,
                startHour: startHour,
                startMinute: startMinute,
                startMonth: startMonth,
                startYear: startYear,
                stopDay: stopDay,
                stopHour: stopHour,
                stopMinute: stopMinute,
                stopMonth: stopMonth,
                stopYear: stopYear,
                setAction: 1,
                userIdentifier: userIdentifier ]

        cmd = secureSequence([zwave.scheduleEntryLockV3.scheduleEntryLockYearDaySet(map),
                zwave.scheduleEntryLockV3.scheduleEntryLockYearDayGet()])
    }
    cmd
}

/**
 * Get the current schedule time offset (timezone) setting from the device.
 *
 * @return
 */
def getScheduleTimeOffset() {
    log.debug("getScheduleTimeOffset()")
    def cmd = secure(zwave.scheduleEntryLockV3.scheduleEntryLockTimeOffsetGet())
    log.debug("getScheduleTimeOffset: $cmd")
    cmd
}

/**
 * Set the schedule time offset (timezone) on the device.
 *
 * @return
 */
def setScheduleTimeOffset() {
    log.debug("setScheduleTimeOffset()")

    def hourTzo = (location.timeZone.rawOffset / (1000 * 60 * 60)).intValue()
    def minuteOffsetDst = (location.timeZone.dstSavings / (1000 * 60)).intValue()
    def minuteTzo = ((location.timeZone.rawOffset - (hourTzo * (1000 * 60 * 60))) / (1000 * 60)).intValue()
    def signOffsetDst = location.timeZone.dstSavings > 0
    def signTzo = location.timeZone.rawOffset > 0

    def map = [ hourTzo			: hourTzo.abs(),
            minuteOffsetDst	: minuteOffsetDst.abs(),
            minuteTzo		: minuteTzo.abs(),
            signOffsetDst	: signOffsetDst,
            signTzo 		: signTzo ]

    log.debug("setTimeOffset: $map")
    secure(zwave.scheduleEntryLockV3.scheduleEntryLockTimeOffsetSet(map))
}

private setEnable(Short userIdentifier, boolean enabled) {
    secure(zwave.scheduleEntryLockV3.scheduleEntryLockEnableSet(userIdentifier: userIdentifier, enabled: enabled ? 1 : 0))
}

private setEnableAll(boolean enabled) {
    secure(zwave.scheduleEntryLockV3.scheduleEntryLockEnableAllSet(enabled: enabled ? 1 : 0))
}

/**
 * Enable a user's schedule(s)
 *
 * @param userIdentifier
 * @return
 */
def enableSchedule(userIdentifier) {
    if (userIdentifier instanceof String) userIdentifier = userIdentifier.toInteger()
    setEnable(userIdentifier, true)
}

/**
 * Enable schedules for all users.
 *
 * @return
 */
def enableAllSchedules() {
    setEnableAll(true)
}

/**
 * Disable schedule(s) for a user.
 *
 * @param userIdentifier
 * @return
 */
def disableSchedule(userIdentifier) {
    if (userIdentifier instanceof String) userIdentifier = userIdentifier.toInteger()
    setEnable(userIdentifier, false)
}

/**
 * Disable schedules for all users.
 *
 * @return
 */
def disableAllSchedules() {
    setEnableAll(false)
}

/**
 * Get the types off schedules supported and how many for each user.
 *
 * @return
 */
def getScheduleEntryTypeSupported() {
    secure(zwave.scheduleEntryLockV3.scheduleEntryTypeSupportedGet())
}

/*
 *
 * Time Parameters Command Class (V1)
 *
 */

/**
 * Get the date and time settings from the device.
 *
 * @return
 */
def getTimeParameters() {
    log.debug("getTimeParameters()")
    secure(zwave.timeParametersV1.timeParametersGet())
}

/**
 * Set the [UTC] date and time on the device.
 * @return
 */
def setTimeParameters() {
/*
Short	day
Short	hourUtc
Short	minuteUtc
Short	month
Short	secondUtc
Integer	year
*/

    log.debug("setTimeParameters()")
    def now = new Date()
    def map = [ day : now[DAY_OF_MONTH],
            hourUtc : now[HOUR_OF_DAY],
            minuteUtc : now[MINUTE],
            month : now[MONTH],
            secondUtc : now[SECOND],
            year : now[YEAR] ]


    secure(zwave.timeParametersV1.timeParametersSet(map))
}

/**
 * Set the time on the device to the current time and timezone on the hub.
 *
 * @return
 */
def setTime() {
    // The null state.time.delta will cause the handler for the ScheduleTimeOffsetReport
    // to send the getTimeParameters query.
    state.time = null
    delayBetween([setTimeParameters(), setScheduleTimeOffset(), getScheduleTimeOffset() ], state.const.DELAY)
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
 *
 *
 * Configuration commands
 *
 *
 */


/**
 * Get the name of the settings variable for a given report parameter number.
 *
 * @param parameterNumber
 * @return
 */
def getSettingsVar(parameterNumber) {
    def map = getSettingsMap()
    return map.find( { it.value.parameterNumber == parameterNumber })
}


/**
 * Read the configuration from the device to provide the initial state for the device type.
 *
 * @return
 */
private getConfiguration() {
    // Pull configuration values FROM the device to populate initial values for settings tiles.
    log.debug("getConfiguration: sending queries to retrieve configuration values.")

    def parameters = getSettingsMap().collect({ m -> m.value["parameterNumber"] })
    def cmds = secureSequence(parameters.collect({ p -> zwave.configurationV1.configurationGet(parameterNumber: p) }), state.const.DELAY)
    cmds
}

private setConfiguration(parameterNumber, value) {
    secureSequence([ zwave.configurationV2.configurationSet(parameterNumber: parameterNumber, size: 1, configurationValue: [ value ]),
            zwave.configurationV2.configurationGet(parameterNumber: parameterNumber) ], state.const.DELAY)
}

private toggleSetting(String name) {
    def map = getSettingsMap()[name]
    def parameterNumber = map.parameterNumber
    def current = device.currentValue(name)
    log.debug("current $name = $current");
    def next = map.options.find( { it.key != current }).value
    setConfiguration(parameterNumber, next)
}

/**
 * Toggle the one-touch locking feature on/off.
 *
 * @return
 */
def toggleOneTouchLocking() {
    toggleSetting("oneTouchLocking")
}

/**
 * Enable one-touch locking.
 *
 * @return
 */
def enableOneTouchLocking() {
    setValue("oneTouchLocking", "On")
}

/**
 * Disable one-touch locking.
 *
 * @return
 */
def disableOneTouchLocking() {
    setValue("oneTouchLocking", "Off")
}

/**
 * Toggle the auto-relock feature.
 *
 * @return
 */
def toggleAutoRelock() {
    toggleSetting("autoRelock")
}

/**
 * Enable the auto-relock feature.
 *
 * @return
 */
def enableAutoRelock() {
    setValue("autoRelock", "On")
}

/**
 * Disable the auto-relock feature.
 *
 * @return
 */
def disableAutoRelock() {
    setValue("autoRelock", "Off")
}

/**
 * Toggle the inside indicator light feature.
 *
 * @return
 */
def toggleInsideIndicatorLight() {
    toggleSetting("insideIndicatorLight")
}

/**
 * Enable the inside indicator light.
 *
 * @return
 */
def enableInsideIndicatorLight() {
    setValue("insideIndicatorLight", "On")
}

/**
 * Disable the inside indicator light.
 *
 * @return
 */
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

/**
 * Set the auto-relock delay time.
 *
 * @param value
 * @return
 */
def setRelockTime(Integer value) {
    setValue("relockTime", value)
}

/**
 * Increment the auto-relock delay time by one second.
 *
 * @return
 */
def increaseRelockTime() {
    log.debug("increaseRelockTime")
    increase("relockTime")
}

/**
 * Decrement the auto-relock delay time by one second.
 *
 * @return
 */
def decreaseRelockTime() {
    decrease("relockTime")
}

/**
 * Set the number of incorrect code entries before the lock stops taking input.
 *
 * @param value
 * @return
 */
def setWrongCodeEntryLimit(Integer value) {
    setValue("wrongCodeEntryLimit", value)
}

/**
 * Increment the number of incorrect code entries before the lock stops taking input by one.
 *
 * @return
 */
def increaseWrongCodeEntryLimit() {
    increase("wrongCodeEntryLimit")
}

/**
 * Decrement the number of incorrect code entries before the lock stops taking input by one.
 *
 * @return
 */
def decreaseWrongCodeEntryLimit() {
    decrease("wrongCodeEntryLimit")
}

/**
 * Set the number of seconds that the lock will suspend input after the wrong code entry limit is exceeded.
 *
 * @param value
 * @return
 */
def setShutdownTime(Integer value) {
    setValue("shutdownTime", value)
}

/**
 * Increment the number of seconds the lock will suspend input after the wrong code entry limit is exceeded by one.
 *
 * @return
 */
def increaseShutdownTime() {
    increase("shutdownTime")
}

/**
 * Decrement the number of seconds the lock will suspend input after the wrong code entry limit is exceeded by one.
 *
 * @return
 */
def decreaseShutdownTime() {
    decrease("shutdownTime")
}

/**
 * Change the the audio mode to the mode before it in the order.
 *
 * @return
 */
def previousAudioMode() {
    cycleMode("audioMode", true)
}

/**
 * Change the the language to the one before it in the order.
 * @return
 */
def previousLanguage() {
    cycleMode("language", true)
}

/**
 * Change the the operation mode to the mode before it in the order.
 * @return
 */
def previousOperatingMode() {
    cycleMode("operatingMode", true)
}

/**
 * Change the the audio mode to the mode after it in the order.
 * @return
 */
def nextAudioMode() {
    cycleMode("audioMode")
}

/**
 * Change the the language to the one after it in the order.
 *
 * @return
 */
def nextLanguage() {
    cycleMode("language")
}

/**
 * Change the the operating mode to the mode before it in the order.
 *
 * @return
 */
def nextOperatingMode() {
    cycleMode("operatingMode")
}

/**
 * Set the the audio mode.
 *
 * @param mode
 * @return
 */
def setAudioMode(mode) {
    setValue("audioMode", mode)
}

/**
 * Set the language.
 *
 * @param mode
 * @return
 */
def setLanguage(mode) {
    setValue("language", mode)
}

/**
 * Set the operating mode.
 *
 * @param mode
 * @return
 */
def setOperatingMode(mode) {
    setValue("operatingMode", mode)
}

/**
 * Get the supported values for settings variables.
 *
 * @param name
 * @return
 */
def getSupportedSettingsValues(name) {
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

/**
 * Cycle a setting up or down.
 *
 * @param name
 * @param reverse
 * @return
 */
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

/*
 *
 * Commands related to faults
 *
 */
 
/**
 * Get map of fault types that describes whether a type allows for multiple active
 * faults of the same type; and if not, how to determine if an existing fault should
 * be replaced.
 *
 * @return Map with key type strings mapped to [ multiple : Boolean, replace : Closure ]
 */
def getFaultTypeMap() {
	[ "tamper" : [ multiple : true ],
      "user" : [ multiple : true ],
      "jammed" : [ multiple : false ],
      "battery" : [ multiple : false, replace: { f, o -> higherLevel(f.level, o) } ]
}

/**
 * Create a fault and add it to the state if it satisfies the multiple/replace criteria.
 *
 * @return Map for creating/sending an event; or null, if the fault is redundant.
 */
def addFault(type = "", level = "info", descriptionText = "") {
	def timestamp = new Date().time
    
	def f = [ id : timestamp.toString(),
              type : type,
              level : level,
              descriptionText : descriptionText,
              timestamp : timestamp ]
              
    if (state.faults == null) state.faults = [:]
    
    if (state.faults."$f.id")
    	return null
        
    def faultType = getFaultTypeMap()."$type"
    if (!faultType.multiple) {
    	def otherFaults = state.faults.findAll { it.type == type }
        if (otherFaults) {
        	def o = otherFaults.get(0)
        	if (faultType.replace && faultType.replace(f, o)) {
            	clearFault(o.id)
            } else {
            	return null
            }           	
        }
    }
    
    state.faults[f.id] = f
    
    // Return a map to be used by createEvent or sendEvent
    
    [ name: "fault", 
      value: true, 
      descriptionText: "$device.displayName $f.descriptionText", 
      isPhysical: true, 
      isStateChange: true,
      displayed: true,
      data : [ fault : f ] ]
}

/**
 * Get a list of fault types this device might raise.
 *
 * @return list of fault type strings.
 */
def getSupportedFaultTypes() {
	getFaultTypeMap().collect { it.key }
}

/**
 * Clear an active fault
 *
 * @param stateId
 */
def clearFault(stateId) {
	if (state.faults) state.faults.remove(stateId.toString())
}

/**
 * Clear all active faults
 *
 */
def clearAllFaults() {
	if (state.faults) state.faults.clear()
}

/*
 *
 * Utility methods
 *
 */


private secure(physicalgraph.zwave.Command cmd) {
    zwave.securityV1.securityMessageEncapsulation().encapsulate(cmd).format()
}

private secureSequence(commands, delay=state.const.DELAY, delayBefore=false, delayAfter=false) {
    def cmds = []
    commands.each {
        if (it instanceof String && it.getAt(0..5) == "delay ") {
            cmds << it
            delayBefore = false
        } else {
            if (delayBefore) cmds << "delay $delay"
            cmds << secure(it)
            delayBefore = true
        }
    }
    if (delayAfter) cmds << "delay $delay"
    cmds
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

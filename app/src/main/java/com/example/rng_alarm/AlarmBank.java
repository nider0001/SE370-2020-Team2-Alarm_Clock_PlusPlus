/*********************************************************************
 *  File            : AlarmBank
 *  Created         : 22-March-2020
 *  Last Changed/By : 02-May-2020 / Eric Hernandez
 *  Author          : Eric Hernandez
 *
 *  Purpose: Builds the list of all alarms, whether we decide to 
 *           use LL, vectors, or arrays
 *********************************************************************/
package com.example.rng_alarm;
import java.util.*; // Needed for linked list

public class AlarmBank {
    /****** Private members ******/
    private static LinkedList<Alarm> Bank = new LinkedList<Alarm>();
    private static LinkedList<Alarm> Active = new LinkedList<Alarm>();
    private static LinkedList<Alarm> Inactive = new LinkedList<Alarm>();

    /**
     * DEFINITION:  Adds a new alarm to the List
     * PARAMETERS:  Takes in alarm element 'name'
     **/
    public void addNewAlarmToBank(Alarm name) {
        // Add alarm to bank
        Bank.add(name);
        isAlarmActive(name);
    }

    /**
     * DEFINITION:  Determines if alarm is active or not and stores accordingly
     * PARAMETERS:  Takes in alarm element
     **/
    public void isAlarmActive(Alarm name){
        // Determine the status of alarm
        if (name.getAlarmActiveStatus() == true) {
            Active.add(name);
        }
        else {
            Inactive.add(name);
        }
    }

    /**
     * DEFINITION:  Returns the size of the alarm bank
     * PARAMETERS:  None
     **/
    public int getAlarmBankCount() { return Bank.size(); }

    /**
     * DEFINITION:  Returns the first alarm in the bank
     * PARAMETERS:  None
     **/
    public Alarm getAlarm() { return Bank.getFirst(); }

    /**
     * DEFINITION:  Returns the full list of alarms
     * PARAMETERS:  None
     **/
    public LinkedList<Alarm> getAlarmBank() { return Bank; }

    /**
     * DEFINITION:  Returns the full list of active alarms
     * PARAMETERS:  None
     **/
    public LinkedList<Alarm> getActiveAlarmBank() { return Active; }
}
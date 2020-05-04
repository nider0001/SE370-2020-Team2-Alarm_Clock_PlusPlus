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
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import java.util.*; // Needed for linked list

public class AlarmBank {
    /****** Private members ******/
    public static LinkedList<Alarm> Bank = new LinkedList<Alarm>();
    private static LinkedList<Alarm> Active = new LinkedList<Alarm>();
    private static LinkedList<Alarm> Inactive = new LinkedList<Alarm>();
    private AlarmManager alarmManager;


    /**
     * DEFINITION:  Adds a new alarm to the List
     * PARAMETERS:  Takes in alarm element 'name'
     **/
    public void addNewAlarmToBank(Alarm newAlarm) {
        // Add alarm to bank
        Bank.add(newAlarm);
        isAlarmActive(newAlarm);
    }

    /**
     * DEFINITION:  Determines if alarm is active or not and stores accordingly
     * PARAMETERS:  Takes in alarm element
     **/
    public void isAlarmActive(Alarm alarm){
        // Determine the status of alarm
        if (alarm.getAlarmActiveStatus() == true) {
            Active.add(alarm);
        }
        else {
            Inactive.add(alarm);
        }
    }

    /**
     * DEFINITION:  Determines if alarm is active or not and stores accordingly
     * PARAMETERS:  Takes in alarm element
     **/
    public void moveAlarm(Alarm alarm, int id){
        boolean stat = alarm.getAlarmActiveStatus();
        // Determine the status of alarm
        if (stat == false) {
            rearrange(id, stat);
        }
        else {
            rearrange(id, stat);
        }
    }

    /**
     * DEFINITION:  Returns the size of the alarm bank
     * PARAMETERS:  None
     **/
    public int getAlarmBankCount() { return Bank.size(); }

    /**
     * DEFINITION:  Returns the first alarm in the bank
     * PARAMETERS:
     **/
    public Alarm getAlarm() {
        return Bank.getLast();
    }

    /**
     * DEFINITION:  Returns the full list of alarms
     * PARAMETERS:  None
     **/
    public LinkedList<Alarm> getAlarmBank() { return Bank; }

    /**
     * DEFINITION:  Returns the full list of active alarms
     * PARAMETERS:  None
     *
     **/
    public LinkedList<Alarm> getActiveAlarmBank() { return Active; }


    /**
     * DEFINITION:
     * PARAMETERS:  None
     *
     **/
    public Alarm getActiveAlarmByRequestCode(int requestCode) {
        for(int i = 0; i < Active.size(); i++)
        {
            if(Active.get(i).getId() == requestCode)
            {
                return Active.get(i);
            }
            else {
                continue;
            }
        }
        return Active.getFirst();
    }

    /**
     * DEFINITION:
     * PARAMETERS:  None
     *
     **/
    public Alarm getInactiveAlarmByRequestCode(int requestCode) {
        for(int i = 0; i < Inactive.size(); i++)
        {
            if(Inactive.get(i).getId() == requestCode)
            {
                return Inactive.get(i);
            }
            else {
                continue;
            }
        }
        return Inactive.getFirst();
    }

    /**
     * DEFINITION:  Rearranges Invalid and Valid linkedList
     * PARAMETERS:  
     **/
    public void rearrange(int id, boolean status) {

        if(status == false) {
            for (int i = 0; i < Active.size(); i++) {
                if (id == Active.get(i).getId()) {
                    Inactive.add(Active.get(i));
                    Active.remove(i);
                }
            }
        }
        else {
            for (int i = 0; i < Inactive.size(); i++) {
                if (id == Inactive.get(i).getId()) {
                    Active.add(Inactive.get(i));
                    Inactive.remove(i);
                }
            }
        }
    }
}


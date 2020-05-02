package com.example.rng_alarm;

/*********************************************************************
 *  File            : AlarmBank
 *  Created         : 22-March-2020
 *  Last Changed/By : 22-March-2020 / Eric Hernandez
 *  Author          : Eric Hernandez
 *
 *  Purpose: Builds the list of all alarms, whether we decide to 
 *           use LL, vectors, or arrays
 *********************************************************************/
import java.util.ArrayList; // import the ArrayList class

public class AlarmBank {
    /****** Private members ******/
    private ArrayList<Alarm> bank = new ArrayList<Alarm>();

    /**
     * DEFINITION:  Adds a new alarm to the Array List
     * PARAMETERS:  
     **/
    public void addNewAlarmToBank(Alarm name) {
        // Add alarm to bank
        bank.add(name);
    }

    /**
     * DEFINITION:  Returns the size of the Arraylist
     * PARAMETERS:  None
     **/
    public int getAlarmBankCount() {
        return bank.size();
    }

    /**
     * DEFINITION:  Returns the size of the Arraylist
     * PARAMETERS:  None
     **/
    public Alarm getAlarm(int index) { return bank.get(index); }
}
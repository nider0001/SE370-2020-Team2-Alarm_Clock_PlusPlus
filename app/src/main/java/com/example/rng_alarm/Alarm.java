/*********************************************************************
 *  File            : Alarm
 *  Created         : 27-Feb-2020
 *  Last Changed/By : 23-Mar-2020 / Eric Hernandez
 *  Author          : Eric Hernandez
 *
 *  Purpose: Alarm class is a super class where I plan on storing all
 *           the alarm settings and status.
 *********************************************************************/

package com.example.rng_alarm;
// import java.util.Calendar; // For date
// import android.app.AlarmManager; // For all things alarm in android


public class Alarm {
    /****** Private members ******/
    private int alarm_hour; // hour in 24hr time format
    private int alarm_minutes;
    private String alarm_name; // Name of the alarm
    // private String alarm_sound;
    // private int alarm_index = -1; // Alarm #
    // private int alarm_on_index = 0; // Future tripping bro..
    // private int alarm_off_index = 0; // Future tripping bro..


    /******** Constructor *******/
    public Alarm() {
        alarm_hour = 0;
        alarm_minutes = 0;
        alarm_name = "";
        // alarm_index++;
    }

    /********** Mutator **********/

    /**
     * DEFINITION:  Sets alarm name.
     * PARAMETERS:  Name defined by the user
     **/
    void setAlarmName(String name) {
        alarm_name = name;
    }

    /**
     * DEFINITION:  Sets alarm hour and minutes after input validation.
     * PARAMETERS:  hours and minutes
     **/
    void setAlarmTime(int hour, int minutes) {
        if (0 <= hour && 24 >= hour) { alarm_hour = hour; }
        else {
            // Some sort of error message here
        }
        if (0 <= minutes && 60 > minutes) { alarm_minutes = minutes; }
        else {
            // Also need some sort of error message here
        }
    }

    /**
     * DEFINITION:  Sets alarm sound.
     * PARAMETERS:  String path of the sound
     **/
    // void setAlarmSound(String sound) {
    //     this.alarm_sound = sound;
    // }

    /********* Accessors *********/
    /**
     * DEFINITION:  Gets alarm name.
     * PARAMETERS:  None
     **/
    String getAlarmName() {
        return alarm_name;
    }


    /**
     * DEFINITION:  Returns the alarm hours
     * PARAMETERS:  None
     **/
    int getAlarmHour() {
        return alarm_hour;
    }

    /**
     * DEFINITION:  Returns the alarm minutes
     * PARAMETERS:  None
     **/
    int getAlarmMinutes() {
        return alarm_minutes;
    }

    /**
     * DEFINITION:  Returns the alarm alart sound
     * PARAMETERS:  None
     **/
    // String getAlarmSound() {
    //     return alarm_sound;
    // }

}


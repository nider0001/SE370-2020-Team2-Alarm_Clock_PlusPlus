/*********************************************************************
 *  File            : Alarm
 *  Created         : 27-Feb-2020
 *  Last Changed/By : 01-May-2020 / Eric Hernandez
 *  Author          : Eric Hernandez
 *
 *  Purpose: Alarm class is a super class where I plan on storing all
 *           the alarm settings and status.
 *********************************************************************/

package rng.AlarmManager;

public class Alarm {
    /****** Private members ******/
    private int alarm_hour; // hour in 24hr time format
    private int alarm_minutes;
    private String alarm_name; // Name of the alarm
    private boolean active; // Set to true if alarm is active
    private static int count = 0;
    private int id = 0;

    /******** Default Constructor *******/
    public Alarm() {
        // Create blank Alarm
        alarm_hour = 0;
        alarm_minutes = 0;
        alarm_name = "";
        active = true;
    }

    /******** Constructor *******/
    public Alarm(int hour, int minutes, String name) {
        // Initializing constructor to create Alarm object
        alarm_hour = hour;
        alarm_minutes = minutes;
        alarm_name = name;
        active = true;
        setId(count++);
    }

    /********** Mutator **********/

    /**
     * DEFINITION:  Sets alarm name.
     * PARAMETERS:  Name defined by the user
     **/
    public void setAlarmName(String name) {
        alarm_name = name;
    }

    /**
     * DEFINITION:  Sets alarm hour and minutes after input validation.
     * PARAMETERS:  hours and minutes
     **/
    public void setAlarmTime(int hour, int minutes) {
        if (0 <= hour && 24 >= hour) { alarm_hour = hour; }
        else {
            // Some sort of error message here
        }
        if (0 <= minutes && 60 > minutes) { alarm_minutes = minutes; }
        else {
            // Also need some sort of error message here
        }
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setStatus(boolean status) { active = status; }

    /**
     * DEFINITION:  Sets alarm status on or off
     * PARAMETERS:
     **/
     public void toggleAlarm() {
         active = !active;
     }

    /********* Accessors *********/
    /**
     * DEFINITION:  Gets alarm name.
     * PARAMETERS:  None
     **/
    public String getAlarmName() {
        return alarm_name;
    }

    /********* Accessors *********/
    /**
     * DEFINITION:  Gets alarm name by id
     * PARAMETERS:  None
     **/

    /**
     * DEFINITION:  Returns the alarm hours
     * PARAMETERS:  None
     **/
    public int getAlarmHour() {
        return alarm_hour;
    }

    /**
     * DEFINITION:  Returns the alarm minutes
     * PARAMETERS:  None
     **/
    public int getAlarmMinutes() {
        return alarm_minutes;
    }

    /**
     * DEFINITION:  Returns the the status of Alarm
     * PARAMETERS:  None
     **/
     public boolean getAlarmActiveStatus() { return active; }
}


package com.example.myapplication;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class alarmPicker extends DialogFragment {


    //override is for polymorphism
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        /**
         * creates an instance that finds the system time to display when setting a time
         */
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        /**
         * creates a new instance that prompts user to select a time of day
         * parameters:
         *      getActivity() - sets what context(fragment) that is being used
         *      (TPD.OTSL)getActivity() - creates a listener that will set a time when
         *                                  the user selects one
         *      hour/minute - what the default time that shows
         *                      in this case we use the time from the system gathered above
         *      a.t.f.DF.is24HourFormat() - returns if the phone system is 24Hrs or not,
         *                                  you can manually change it to true/false
         */
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(),
                                                    hour, minute,
                                                    android.text.format.DateFormat.is24HourFormat(getActivity()));
    }
}

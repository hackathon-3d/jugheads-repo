package com.hackathon.jugs.TweetHunt;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TweetScheduler extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_tweet);
    }

    private final static String datePickerId = "datePicker";
    private final static String timePickerId = "timePicker";

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), datePickerId);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), timePickerId);
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        private final static String separatorTime = ":";


        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TextView time = (TextView) getActivity().findViewById(R.id.create_time);
            time.setText(String.format("%02d:%02d", hourOfDay, minute));
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        private final static String separatorDate = "/";

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView date = (TextView) getActivity().findViewById(R.id.create_date);
            date.setText(month + separatorDate + day + separatorDate + year);
        }


    }

    public void sendClick() {
        //validateDate();
        //validateTime();
        validateHashtag();
    }

    private void validateHashtag() {
        EditText hashTag = (EditText) findViewById(R.id.hashtag);
        String ht = hashTag.getText().toString();
        if (ht.isEmpty()) {
            hashTag.setError("Hashtag cannot be empty!");
        } else if(doesContainBadCharacters(ht)) {
            hashTag.setError("Hashtag cannot contain spaces or special characters !$%&^*+.");
        } else if (ht.substring(0, 1).matches("[0-9]") || ht.matches("[0-9]+")) {
            hashTag.setError("Hashtag cannot start with a number or be all numbers!");
        }
    }

    private List<String> badCharacters = Arrays.asList("!", "$", "%", "^", "&", "*", "+", ".", " ");

    private boolean doesContainBadCharacters(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (badCharacters.contains(input.charAt(i))) {
                return true;
            }
        }
        return false;
    }



}

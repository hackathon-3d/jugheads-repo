package com.hackathon.jugs.TweetHunt;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hackathon.jugs.TweetHunt.authenticate.TwitterInfo;

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

    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;

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
            TweetScheduler scheduler = (TweetScheduler) getActivity();
            scheduler.hour = hourOfDay;
            scheduler.minute = minute;
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
            TweetScheduler scheduler = (TweetScheduler) getActivity();
            scheduler.year = year;
            scheduler.month = month;
            scheduler.day = day;
        }
    }

    public void sendClick(View v) {
        if (hasValidDate() && hasValidTime() && hasValidHashtag() && hasValidMessage()) {
            TweetData data = getTweetData();
            Intent intent = new Intent(this, TwitterInfo.class);
            intent.putExtra("data", data);
            startActivity(intent);
        }
    }

    private TweetData getTweetData() {
        TweetData data = new TweetData();
        data.sendTimeUTC = Date.UTC(this.year, this.month, this.day, this.hour, this.minute, 0);
        EditText hashTag = (EditText) findViewById(R.id.hashtag);
        EditText message = (EditText) findViewById(R.id.message);
        data.message = message.getText().toString() + " " + hashTag.getText().toString();
        return data;
    }


    private boolean hasValidDate() {
        Button date = (Button) findViewById(R.id.create_date);
        if (date.getText().toString().isEmpty()) {
            date.setError("Please enter a date!");
            return false;
        } else {
            return true;
        }
    }

    private boolean hasValidTime() {
        Button time = (Button) findViewById(R.id.create_time);
        if (time.getText().toString().isEmpty()) {
            time.setError("Please enter a time!");
            return false;
        } else {
            return true;
        }
    }

    private boolean hasValidHashtag() {
        EditText hashTag = (EditText) findViewById(R.id.hashtag);
        String ht = hashTag.getText().toString();
        if (ht.isEmpty()) {
            hashTag.setError("Hashtag cannot be empty!");
        } else if(ht.substring(0, 1).equals("#") == false) {
            hashTag.setError("Hashtag must begin with #");
        } else if(doesContainBadCharacters(ht)) {
            hashTag.setError("Hashtag cannot contain spaces or special characters !$%&^*+.");
        } else if (ht.substring(1, 1).matches("[0-9]") || ht.substring(1, ht.length() - 1).matches("[0-9]+")) {
            hashTag.setError("Hashtag cannot start with a number or be all numbers!");
        } else {
            return true;
        }
        return false;
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

    private boolean hasValidMessage() {
        EditText message = (EditText) findViewById(R.id.message);
        EditText hashTag = (EditText) findViewById(R.id.hashtag);
        if (message.getText().toString().isEmpty()) {
            message.setError("Please enter a message!");
        } else if (message.getText().toString().length() + hashTag.getText().toString().length() + 1 > 140) {
            message.setError("Message is too long!");
        } else {
            return true;
        }
        return false;
    }



}

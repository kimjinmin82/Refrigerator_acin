package com.example.kimseolki.refrigerator_acin;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

/**
 * Created by kimseolki on 2017-05-02.
 */

public class SettingsFragment extends Fragment {

    Button bttime;
    int year, month, day, hour, minute;
    public SettingsFragment(){

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.activity_setting,container,false);
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        bttime = (Button) rootview.findViewById(R.id.bttime);
        rootview.findViewById(R.id.bttime)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new TimePickerDialog(getActivity(), timeSetListener, hour, minute, false).show();
                    }
                });

        return rootview;
    }
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String aMpM = "AM";
            if(hourOfDay>11){
                aMpM = "PM";
            }
            int currentHour;
            if(hourOfDay >11) {
                currentHour = hourOfDay - 12;
            }
            else{
                currentHour = hourOfDay;
            }
            bttime.setText("");
            bttime.setText(bttime.getText() + String.valueOf(hourOfDay) + "시"
                    + String.valueOf(minute)+ "분\n");



            // TODO Auto-generated method stub

//            String msg = String.format("%d / %d / %d", year, hourOfDay, minute);
//            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    };
}

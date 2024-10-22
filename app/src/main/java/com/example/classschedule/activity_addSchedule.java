package com.example.classschedule;

import android.app.Activity;
import android.app.TimePickerDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


import  java.util.Calendar;
import java.util.Date;

public class activity_addSchedule extends AppCompatActivity {



    public int start_mHour;
    public int start_mMinute;
    public int end_mHour;
    public int end_mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        //Custom Layout for the ActionBar
//        getSupportActionBar().setTitle("");
//        View actionBar=getLayoutInflater().inflate(R.layout.actionbar_layout, null);
//        TextView title=(TextView)actionBar.findViewById(R.id.title_action);
//        title.setText("Create Schedule");

//        actionBar.findViewById(R.id.back_but).setOnClickListener(new back_clickListener(this));

//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(actionBar);

//

        ((ImageView)findViewById(R.id.sch_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        Fills the spinner for subjects in schedule
        db_helper handler=new db_helper(this, null, null, 1);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.spinner_layout, handler.list_all_subject_name());
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner spn=(Spinner)findViewById(R.id.sch_sub_spinner);
        spn.setAdapter(adapter);

//

//        Fills the spinner for days.
       ArrayList<String> days=new ArrayList<String>();
        days.add("Sunday");
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
       ArrayAdapter<String> day_adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout, days);
        day_adapter.setDropDownViewResource(R.layout.spinner_layout);
        ((Spinner)findViewById(R.id.sch_day_spinner)).setAdapter(day_adapter);
        String position;
        try{
       position=(String)getIntent().getSerializableExtra("Day");
        }catch (Exception e) {
            position = "";
        }
        if(position!=null)
            ((Spinner) findViewById(R.id.sch_day_spinner)).setSelection(Integer.valueOf(position));







//

//        ######################

        //Get Start time and End time
        final SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        final SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");

        Calendar c = Calendar.getInstance();
        start_mHour = c.get(Calendar.HOUR_OF_DAY);
        start_mMinute = 0;

        end_mHour = c.get(Calendar.HOUR_OF_DAY);
        end_mMinute = 0;

        TextView _start_time=(TextView)findViewById(R.id.sch_start_time);
        _start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog time_pick=new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String _24HourTime = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                        Date _24HourDt;
                        try {
                            _24HourDt = _24HourSDF.parse(_24HourTime);
                        }catch (Exception e)
                        {
                            return;
                        }

                            ((EditText) findViewById(R.id.sch_start_time)).setText(_12HourSDF.format(_24HourDt));
                            start_mHour=hourOfDay;
                            start_mMinute=minute;




                    }
                },start_mHour, start_mMinute, false);
                time_pick.show();

            }
        });


        TextView _end_time=(TextView)findViewById(R.id.sch_end_time);
        _end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog time_pick=new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String _24HourTime = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                        Date _24HourDt;
                        try {
                            _24HourDt = _24HourSDF.parse(_24HourTime);
                        }catch (Exception e)
                        {
                            return;
                        }

                        ((EditText) findViewById(R.id.sch_end_time)).setText(_12HourSDF.format(_24HourDt));
                        end_mHour=hourOfDay;
                        end_mMinute=minute;




                    }
                },end_mHour, end_mMinute, false);
                time_pick.show();

            }
        });


//        #####################

//      For schedule edits Object passing between activities

        final object_schedule edit_sch_obj=(object_schedule)getIntent().getSerializableExtra("sch_obj");
//        #####################

//      For the Update Activity

        if(edit_sch_obj!=null)
        {
            ((TextView)findViewById(R.id.sch_add_info)).setText("UPDATE SCHEDULE");
            ((Button)findViewById(R.id.sch_addBt)).setText("Update");
//            getActionBar().setTitle("Update Schedule");
//            title.setText("Update Schedule");
            String _start="";
            String _end="";
            try
            {
                _start=_12HourSDF.format(_24HourSDF.parse(edit_sch_obj.get_start_time()));
                _end=_12HourSDF.format(_24HourSDF.parse(edit_sch_obj.get_end_time()));
            }catch (Exception e){}
            ((EditText)findViewById(R.id.sch_start_time)).setText(_start);
            ((EditText)findViewById(R.id.sch_end_time)).setText(_end);
            ((EditText)findViewById(R.id.sch_remarks)).setText(edit_sch_obj.get_remarks());


            ((Spinner)findViewById(R.id.sch_day_spinner)).setSelection(day_adapter.getPosition(edit_sch_obj.get_day()));

            String _subj=edit_sch_obj.get_sub_name();
           ((Spinner)findViewById(R.id.sch_sub_spinner)).setSelection(adapter.getPosition(edit_sch_obj.get_sub_name()+"-"+edit_sch_obj.get_th_or_p()));
            ((EditText)findViewById(R.id.sch_remarks)).setText(edit_sch_obj.get_remarks());





            }



//####################################################






//        Onclick listener for add button
        Button add_but=((Button)findViewById(R.id.sch_addBt));
        add_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get _sub_id from the selected item
                Spinner _sub_spn=(Spinner)findViewById(R.id.sch_sub_spinner);


                if(_sub_spn.getSelectedItem()==null){
                    Toast.makeText(getApplicationContext(), "Please add subjects to create schedules.", Toast.LENGTH_LONG).show();
                    return;

                }

                String _selected=_sub_spn.getSelectedItem().toString();;
                String[] splitted=_selected.split("\\-");
                String _sub =splitted[0];
                String _th_or_pra=splitted[1];

                db_helper sub_handler=new db_helper(getApplicationContext(), null, null, 1);
                int _sub_id=(sub_handler.findSubject(_sub, _th_or_pra)).getID();

                //Get a day
                String _day=((Spinner)findViewById(R.id.sch_day_spinner)).getSelectedItem().toString();


                String start_time=((EditText)findViewById(R.id.sch_start_time)).getText().toString();
                String end_time=((EditText)findViewById(R.id.sch_end_time)).getText().toString();
                String _start_time="";
                String _end_time="";
                try{
                    _start_time=_24HourSDF.format(_12HourSDF.parse(start_time));
                    _end_time=_24HourSDF.format(_12HourSDF.parse(end_time));
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }



                //Get Remarks
                String _remarks=((EditText)findViewById(R.id.sch_remarks)).getText().toString();

                if(_sub.isEmpty())
                {

                }
//                Check for null value
                if(_start_time.isEmpty() || _end_time.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Start Time and End Time is empty.", Toast.LENGTH_LONG).show();
                    return;
                }

//                Adding a schedule to database.
                db_helper sch_handler=new db_helper(getApplicationContext(), null, null, 1);
                object_schedule sch=new object_schedule(_sub_id, _day, _start_time,_end_time,_remarks);
                boolean result;


//                Updating the schedule
                if(edit_sch_obj!=null)
                {
                    sch.set_id(edit_sch_obj.get_id());
                    result=sch_handler.updateSchedule(sch);


                    if(result)
                    {
                        Toast.makeText(getApplicationContext(), "Schedule updated for "+_day+".", Toast.LENGTH_LONG).show();
                        setResult(Activity.RESULT_OK);
                        finish();
                        return;
                    }

                    Toast.makeText(getApplicationContext(), "Error. Schedule cannot be updated.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;



                }

                result=sch_handler.addSchedule(sch);
//
                if(result)
                {
                    Toast.makeText(getApplicationContext(), "Schedule added for "+_day+".", Toast.LENGTH_LONG).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Error. Schedule cannot be added.", Toast.LENGTH_SHORT).show();
                }


//





            }
        });
    }




}

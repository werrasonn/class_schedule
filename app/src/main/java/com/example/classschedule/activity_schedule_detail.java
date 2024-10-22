package com.example.classschedule;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class activity_schedule_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);


        ((ImageView)findViewById(R.id.sch_det_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



//        Adding Data to layout
        final object_schedule sch=(object_schedule)getIntent().getSerializableExtra("sch_obj");
        ((TextView)findViewById(R.id.sch_detail_name)).setText(sch.get_sub_name());
        ((TextView)findViewById(R.id.sch_detail_ins)).setText(sch.get_ins_name());
        ((TextView)findViewById(R.id.sch_detail_th_or_pra)).setText(sch.get_th_or_p());


        String _24HourTime_start = sch.get_start_time();
        String  _24HourTime_end=sch.get_end_time();
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm aaa");
        Date _24HourDt_start=null;
        Date _24HourDt_end=null;
        try {
            _24HourDt_start = _24HourSDF.parse(_24HourTime_start);
            _24HourDt_end = _24HourSDF.parse(_24HourTime_end);
        }catch (Exception e)
        {

        }
        String _start=_12HourSDF.format(_24HourDt_start);
        String _end=_12HourSDF.format(_24HourDt_end);

        ((TextView)findViewById(R.id.sch_detail_period)).setText(sch.get_day()+", "+_start+" to "+_end);
        String string=sch.get_remarks();
        if(string.isEmpty())
            string="Remarks";

        ((TextView)findViewById(R.id.sch_detail_remarks)).setText(string);



        ((ImageView)findViewById(R.id.sch_detail_edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sch_edit=new Intent(getApplicationContext(), activity_addSchedule.class);
                sch_edit.putExtra("sch_obj",sch);
                startActivity(sch_edit);
                finish();
            }
        });

        ((ImageView)findViewById(R.id.sch_detail_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_helper delete=new db_helper(getApplicationContext(), null, null, 1);
                int res=delete.delete_schedule(sch.get_id());
                if(res==1)
                    Toast.makeText(getApplicationContext(),"Deleted.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Error. Please try Again.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



    }
}

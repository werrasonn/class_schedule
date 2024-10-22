package com.example.classschedule;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by pi on 10/28/17.
 */

public class adapter_schedule extends ArrayAdapter<object_schedule> {



    SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm");

    public adapter_schedule(@NonNull Context context, @NonNull List<object_schedule> objects)
    {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View ConvertView, @NonNull ViewGroup parent)
    {
        object_schedule sch_obj=getItem(position);

        if(ConvertView==null)
//            Adding Data to list
            ConvertView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_layout_schedule, parent, false);

        String _24HourTime_start = sch_obj.get_start_time();
        String  _24HourTime_end=sch_obj.get_end_time();
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
        Date _24HourDt_start;
        Date _24HourDt_end;
        try {
            _24HourDt_start = _24HourSDF.parse(_24HourTime_start);
            _24HourDt_end = _24HourSDF.parse(_24HourTime_end);
        }catch (Exception e)
        {
            return ConvertView;
        }

        TextView sub_name=((TextView)ConvertView.findViewById(R.id.sch_list_sub));
        sub_name.setText(sch_obj.get_sub_name());
        TextView start_time=((TextView)ConvertView.findViewById(R.id.sch_list_start_time));
        start_time.setText(_12HourSDF.format(_24HourDt_start));
        TextView end_time=((TextView)ConvertView.findViewById(R.id.sch_list_end_time));
        end_time.setText(_12HourSDF.format(_24HourDt_end));
        TextView ins_name=((TextView)ConvertView.findViewById(R.id.sch_list_ins));
        ins_name.setText(sch_obj.get_ins_name());
        TextView th_or_pra=(TextView)ConvertView.findViewById(R.id.sch_th_or_pra);
        th_or_pra.setText(sch_obj.get_th_or_p());




//        Coloring the time_status
        SimpleDateFormat _24Hour = new SimpleDateFormat("HH:mm");

        Date date=null;
        Date dateCompareOne;
        Date dateCompareTwo;

        String compareStringOne=sch_obj.get_start_time();
        String compareStringTwo=sch_obj.get_end_time() ;




        Calendar now = Calendar.getInstance();


        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);


        date = parseDate(hour + ":" + minute);

        dateCompareOne = parseDate(compareStringOne);
        dateCompareTwo = parseDate(compareStringTwo);

        long _start=dateCompareOne.getTime();
        long _end=dateCompareTwo.getTime();
        long _now=date.getTime();
        Log.i("Number", "4");






        if ( _start<=_now && _end>=_now) {
            ((View)ConvertView.findViewById(R.id.schedule_item)).setBackgroundColor(getContext().getResources().getColor(R.color.NowColor));
        }
        else
        {
            ((View)ConvertView.findViewById(R.id.schedule_item)).setBackgroundColor(Color.WHITE);
        }


        return ConvertView;
    }


    private Date parseDate(String date) {

        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }
}

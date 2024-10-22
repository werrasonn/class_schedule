package com.example.classschedule;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;



/**
 * Created by pi on 11/1/17.
 */

public class adapter_assignment extends ArrayAdapter<object_assignment> {



    public adapter_assignment(@NonNull Context context, @NonNull ArrayList<object_assignment> objects)
    {
        super(context, 0, objects);
        Log.i("Number of Objects", String.valueOf(objects.size()));
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View ConvertView, @NonNull ViewGroup parent)
    {
        object_assignment assignment_obj=getItem(position);


        if(ConvertView==null)
//            Adding Data to list
            ConvertView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_layout_assg_pending, parent, false);

//        Assignment Name on adapter.
        TextView _assg_name=((TextView)ConvertView.findViewById(R.id.assg_list_name));
        _assg_name.setText(assignment_obj.get_assignment_name());
//        Assignment Submission Date on Adapter.
        TextView _assg_submission_date=((TextView)ConvertView.findViewById(R.id.assg_list_sub_date));
        _assg_submission_date.setText(assignment_obj.get_submission_date());
//        Assignment Subject Name.
        TextView __assg_sub_name=((TextView)ConvertView.findViewById(R.id.assg_list_sub));
        __assg_sub_name.setText(assignment_obj.get_sub_name());


        if(assignment_obj.get_status().compareTo("Pending")==0) {
    //        Calculation of Remaining Days
                Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                int day = cal.get(Calendar.DAY_OF_MONTH);
                String today_date = year + "-" + month + "-" + day;
    //        Remaining Days
                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                DateTime d1 = formatter.parseDateTime(today_date);
                DateTime d2 = formatter.parseDateTime(assignment_obj.get_submission_date());
    //
                Duration duration = new Duration(d1, d2);
            ((TextView) ConvertView.findViewById(R.id.assg_rem_text)).setText("Remaining Days :");
                TextView _assg_remaining_days = (TextView) ConvertView.findViewById(R.id.assg_list_rem);
                _assg_remaining_days.setText(String.valueOf(duration.getStandardDays()));

            Log.i("Remaining", String.valueOf(duration.getStandardDays()));
                    if (duration.getStandardDays() < 0) {
                        ((TextView) ConvertView.findViewById(R.id.assg_rem_text)).setText("Assignment due date has reached. Change the status.");
                        _assg_remaining_days.setText("");

                    }
        }else
        {
            TextView _assg_remaining_days = (TextView) ConvertView.findViewById(R.id.assg_list_rem);
            ((TextView) ConvertView.findViewById(R.id.assg_rem_text)).setTextColor(Color.parseColor("#43A047"));
            ((TextView) ConvertView.findViewById(R.id.submitted_message)).setText("Submitted.");
            ((TextView) ConvertView.findViewById(R.id.assg_rem_text)).setVisibility(View.GONE);
            ((ImageView) ConvertView.findViewById(R.id.notice_image)).setVisibility(View.GONE);
            _assg_remaining_days.setText("");

        }




//        Instructor Name

        db_helper sub_handler = new db_helper(getContext(), null, null, 1);
        String Instructor=assignment_obj.get_ins_name();
        ((TextView)ConvertView.findViewById(R.id.assg_list_ins)).setText(Instructor);








        return ConvertView;
    }



}

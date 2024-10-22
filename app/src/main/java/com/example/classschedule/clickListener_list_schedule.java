package com.example.classschedule;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;



/**
 * Created by pi on 10/29/17.
 */

public class clickListener_list_schedule implements AdapterView.OnItemClickListener {
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        object_schedule sch=(object_schedule)parent.getItemAtPosition(position);

        Intent sch_det=new Intent(view.getContext(), activity_schedule_detail.class);
        sch_det.putExtra("sch_obj",sch);
        view.getContext().startActivity(sch_det);



    }
}

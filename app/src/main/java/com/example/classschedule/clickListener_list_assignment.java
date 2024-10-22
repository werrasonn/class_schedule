package com.example.classschedule;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by pi on 11/13/17.
 */

public class clickListener_list_assignment implements AdapterView.OnItemClickListener {
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        object_assignment assg=(object_assignment) parent.getItemAtPosition(position);

        Intent add_assg=new Intent(view.getContext(), activity_addAssignment.class);
        add_assg.putExtra("assg_obj",assg);
        view.getContext().startActivity(add_assg);



    }
}

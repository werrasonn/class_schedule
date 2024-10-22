package com.example.classschedule;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by pi on 11/13/17.
 */

public class clickListener_list_subject implements AdapterView.OnItemClickListener {
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        object_subject sub=(object_subject)parent.getItemAtPosition(position);

        Intent sub_add=new Intent(view.getContext(), activity_addSubject.class);
        sub_add.putExtra("sub_obj",sub);
        view.getContext().startActivity(sub_add);



    }
}

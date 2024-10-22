package com.example.classschedule;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import  androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_assg_submitted extends Fragment {



    private View _view;
    private db_helper helper;
    private adapter_assignment assg_objs;
    private ListView ls_view;


    public fragment_assg_submitted() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_list_assg_submitted, container, false);
        this._view=view;
        ((ListView)_view.findViewById(R.id.list_submitted)).setOnItemClickListener(new clickListener_list_assignment());

        return view;
    }


    public void onResume()
    {

        //For dynamic views
        super.onResume();

        if(this.helper==null)
            this.helper=new db_helper(getActivity(), null, null, 1);


        ArrayList<object_assignment> assignments=this.helper.list_assg_status("Submitted");

        if(assignments.size()>0) {
            ((FrameLayout)_view.findViewById(R.id.assg_submitted_frame)).setBackgroundColor(Color.parseColor("#eeeeee"));
            ((LinearLayout)_view.findViewById(R.id.assg_submitted_notice)).setVisibility(View.GONE);
            ((ListView)_view.findViewById(R.id.list_submitted)).setVisibility(View.VISIBLE);
            this.assg_objs = new adapter_assignment(getContext(), assignments);
        }
        else {
            ((FrameLayout)_view.findViewById(R.id.assg_submitted_frame)).setBackgroundColor(Color.parseColor("#ffffff"));
            ((LinearLayout)_view.findViewById(R.id.assg_submitted_notice)).setVisibility(View.VISIBLE);
            ((ListView)_view.findViewById(R.id.list_submitted)).setVisibility(View.GONE);
            this.assg_objs = null;
        }


        this.ls_view=((ListView)_view.findViewById(R.id.list_submitted));

        this.ls_view.setAdapter(this.assg_objs);






    }


}

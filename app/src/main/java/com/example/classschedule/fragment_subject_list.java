package com.example.classschedule;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import  androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_subject_list extends Fragment {



    private View _view;
    private db_helper helper;
    private adapter_subject subj_objs;
    private ListView ls_view;
    public fragment_subject_list() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this._view= inflater.inflate(R.layout.fragment_list_subject, container, false);
        ((ListView)_view.findViewById(R.id.list_subject)).setOnItemClickListener(new clickListener_list_subject());

        return this._view;
    }


    @Override
    public void onResume() {

        super.onResume();

        if(this.helper==null)
            this.helper=new db_helper(getActivity(), null, null, 1);

        ArrayList<object_subject> subjects=this.helper.list_all_subjects();

        if (subjects.size()>0)
        {
            ((FrameLayout)_view.findViewById(R.id.subject_list_frame)).setBackgroundColor(Color.parseColor("#eeeeee"));
            ((LinearLayout)_view.findViewById(R.id.subject_notice)).setVisibility(View.GONE);
            ((ListView)_view.findViewById(R.id.list_subject)).setVisibility(View.VISIBLE);
            this.subj_objs=new adapter_subject(getContext(), subjects);
        }
        else
        {
            ((FrameLayout)_view.findViewById(R.id.subject_list_frame)).setBackgroundColor(Color.parseColor("#ffffff"));
            ((LinearLayout)_view.findViewById(R.id.subject_notice)).setVisibility(View.VISIBLE);
            ((ListView)_view.findViewById(R.id.list_subject)).setVisibility(View.GONE);
            this.subj_objs = null;
        }

        this.ls_view=((ListView)_view.findViewById(R.id.list_subject));
        this.ls_view.setAdapter(this.subj_objs);
//        ((TextView)_view.findViewById(R.id.sch_list)).setText(String.valueOf(this.subj_objs.getCount()));

//


    }







}

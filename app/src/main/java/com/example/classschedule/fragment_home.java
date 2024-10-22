package com.example.classschedule;


import android.animation.LayoutTransition;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.drawerlayout.widget.DrawerLayout;
import  androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_home extends Fragment {



    private View _view;
    public fragment_home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       _view= inflater.inflate(R.layout.fragment_home_view, container, false);
        TextSwitcher today_th=(TextSwitcher)_view.findViewById(R.id.home_number_classes);
        TextSwitcher today_assg=(TextSwitcher)_view.findViewById(R.id.home_number_assg);
        Animation animationIn = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
        Animation animationOut = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
//
//        today_assg.setInAnimation(animationIn);
//        today_assg.setOutAnimation(animationOut);
//        today_th.setInAnimation(animationIn);
//        today_th.setOutAnimation(animationOut);

        ViewSwitcher.ViewFactory fact=new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView num=new TextView(getContext());
                num.setTextColor(getResources().getColor(R.color.colorPrimary));
                num.setTextSize(25);
                num.setGravity(Gravity.CENTER_HORIZONTAL);
                return num;

            }
        };

        today_th.setFactory(fact);
        today_assg.setFactory(fact);


        return _view;
    }


    @Override
    public void  onResume()
    {

        super.onResume();
//         Hide FAB

        final MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.fab_but.setVisibility(View.GONE);
        final NavigationView navigationView = (NavigationView) mainActivity.findViewById(R.id.nav_view);

        ImageView _th=(ImageView)_view.findViewById(R.id.sch_th);
        _th.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onNavigationItemSelected(navigationView.getMenu().getItem(2));
                mainActivity.listener.onDrawerClosed((DrawerLayout)mainActivity.findViewById(R.id.drawer_layout));
            }
        });

//        Displaying day and Date
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("hh:mm:aa EEEE, MMMM dd yyyy  ");
        Date myDate = new Date();
        String date_today = timeStampFormat.format(myDate);
        ((TextView)_view.findViewById(R.id.home_date)).setText(date_today);
        Log.i("Day of week:", String.valueOf(myDate.getDay()));
//        Adding the Today's Schedule in Home View
        FrameLayout list_view=(FrameLayout)_view.findViewById(R.id.home_sch);
        Fragment frag=new fragment_sch_sunday();
//        Selecting the proper schedule at Home
        int day=myDate.getDay();
        if(day==0)
            frag=new fragment_sch_sunday();
        else if(day==1)
            frag=new fragment_sch_monday();
        else if(day==2)
            frag=new fragment_sch_tuesday();
        else if(day==3)
            frag=new fragment_sch_wednesday();
        else if(day==4)
            frag=new fragment_sch_thursday();
        else if(day==5)
            frag=new fragment_sch_friday();
        else if(day==6)
            frag=new fragment_sch_saturday();

        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.home_sch, frag);
        ft.commit();

//        Use of Async Task to perform operation and Update UI Thread
        home_thread thread=new home_thread();
        thread.execute();


    }


    private class home_thread extends AsyncTask<String, Void, ArrayList<Integer>>{

        @Override
        protected ArrayList<Integer> doInBackground(String... params)
        {

//            Result to be returned.
            ArrayList<Integer> result=new ArrayList<Integer>();
//

//            Today's Summary calculation.
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("EEEE");
            Date myDate = new Date();
            String day = timeStampFormat.format(myDate);

            db_helper db=new db_helper(getActivity(), null, null, 1);
            ArrayList<object_schedule> schedules=db.getSchedule_day(day);
            int numb;
            int prac_numb=0;

            if(schedules!=null) {
                numb = schedules.size();
                for (int i=0; i<numb; i++)
                {
                    if(schedules.get(i).get_th_or_p().compareTo("Practical")==0)
                        prac_numb++;
                }
            }

            else {
                numb = 0;
                prac_numb=0;
            }

            SimpleDateFormat yearformat = new SimpleDateFormat("yyyy-MM-dd");
            int assg_today=db.list_today_assg(yearformat.format(myDate));

            result.add(numb-prac_numb);
            result.add(prac_numb);
            result.add(assg_today);
//            ####################################


//            For tomorrow's calculation
            SimpleDateFormat day_format = new SimpleDateFormat("EEEE");
            Calendar cal=Calendar.getInstance();
            cal.setTime(myDate);
            cal.add(Calendar.DATE, 1);


//            Get tomorrow day and date
            String tomorrow_date=yearformat.format(new Date(cal.getTimeInMillis()));
            String tomorrow_day=day_format.format(new Date(cal.getTimeInMillis()));


//            Get Schedules for tomorrow's summary.
            ArrayList<object_schedule> tomorrow_schedules=db.getSchedule_day(tomorrow_day);
            int tomorrow_numb;
            int tomorrow_prac_numb=0;
            if(tomorrow_schedules!=null) {
                tomorrow_numb = tomorrow_schedules.size();
                for (int i=0; i<tomorrow_numb; i++)
                {
                    if(tomorrow_schedules.get(i).get_th_or_p().compareTo("Practical")==0)
                        tomorrow_prac_numb++;
                }
            }

            else {
                tomorrow_numb = 0;
                tomorrow_prac_numb=0;
            }
            int assg_tomorrow=db.list_today_assg(tomorrow_date);


//            ################################


//            Adding tomorrow Data to Result
            result.add(tomorrow_numb-tomorrow_prac_numb);
            result.add(tomorrow_prac_numb);
            result.add(assg_tomorrow);

            return result;

//            ###################################


        }


        protected void onPostExecute(ArrayList<Integer> result){



//            LayoutTransition lt=new LayoutTransition();
//            LayoutAnimationController lt=new LayoutAnimationController();


//            ((LinearLayout)_view.findViewById(R.id.today_summ)).setLayoutAnimation();
//              Adding Today Data
//            ((TextView)_view.findViewById(R.id.home_number_classes)).setText(String.valueOf(result.get(0)));
            ((TextSwitcher)_view.findViewById(R.id.home_number_classes)).setText(String.valueOf(result.get(0)));
            ((TextView)_view.findViewById(R.id.home_number_practical)).setText(String.valueOf(result.get(1)));
            ((TextSwitcher)_view.findViewById(R.id.home_number_assg)).setText(String.valueOf(result.get(2)));

//            Adding Tomorrow's Data

            ((TextView)_view.findViewById(R.id.tomorrow_theory_class)).setText("Number of Theory Classes: "+String.valueOf(result.get(3)));
            ((TextView)_view.findViewById(R.id.tomorrow_practicals)).setText("Number of Practicals: "+String.valueOf(result.get(4)));
            ((TextView)_view.findViewById(R.id.tomorrow_assignments)).setText("Number of Assignments to be submitted: "+String.valueOf(result.get(5)));

//            #################################################



        }

    }

}



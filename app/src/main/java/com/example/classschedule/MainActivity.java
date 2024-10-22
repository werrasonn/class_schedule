package com.example.classschedule;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import java.util.Calendar;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.FrameLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.classschedule.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public FloatingActionButton fab_but;
    public ViewPager vp;
    public int _item_id;
    public int prev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // creating the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //create function for floating button and its id

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        this.fab_but=fab;
// creating the menu drawer function
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerListener(listener);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        prev=R.id.nav_home;

        onHome();
    }


    DrawerLayout.DrawerListener listener=new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(View drawerView) {

        }

        @SuppressLint("RestrictedApi")
        @Override
        public void onDrawerClosed(View drawerView) {

            if(prev==_item_id) {
                prev=_item_id;
                return;
            }

            if(_item_id==R.id.nav_home)
            {
                onHome();
            }
            else if (_item_id == R.id.nav_sub_add) {
                //            Visibility
                ((TabLayout)findViewById(R.id.tabs)).setVisibility(View.GONE);
                ((ViewPager) findViewById(R.id.pager)).setVisibility(View.GONE);
                fab_but.setVisibility(View.VISIBLE);
                fab_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent add_activity=new Intent(getApplicationContext(), activity_addSubject.class);
                        startActivityForResult(add_activity, 111);
                    }
                });
                //            ####################
                FragmentTransaction ft_pending=getSupportFragmentManager().beginTransaction();
                ft_pending.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft_pending.replace(R.id.fragment_container, new fragment_subject_list(),"Subject List");
                ft_pending.commit();
                ((FrameLayout)findViewById(R.id.fragment_container)).setVisibility(View.VISIBLE);

            } else if (_item_id == R.id.nav_view_sch) {
//                Set Visibility


                ((TabLayout)findViewById(R.id.tabs)).setVisibility(View.VISIBLE);
                ((ViewPager) findViewById(R.id.pager)).setVisibility(View.VISIBLE);
                ((ViewPager)findViewById(R.id.pager)).setLayoutTransition(new LayoutTransition());

                fab_but.setVisibility(View.VISIBLE);
//                fab_but.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent add_activity=new Intent(getApplicationContext(), activity_addSchedule.class);
//                        startActivityForResult(add_activity, 111);
//                    }
//                });

                FragmentTransaction ft_pending=getSupportFragmentManager().beginTransaction();
//                ft_pending.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft_pending.replace(R.id.fragment_container, new fragment_blank(),"Blank Fragment");
//                Creating View Pager
                if(vp!=null)
                {
                    ft_pending.commit();
                    prev=_item_id;
//                    ((FrameLayout)findViewById(R.id.fragment_container)).setVisibility(View.INVISIBLE);
                    return;
                }
                vp= (ViewPager) findViewById(R.id.pager);
                addPages();
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);

                TabLayout tabLayout= (TabLayout) findViewById(R.id.tabs);
                tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                tabLayout.setupWithViewPager(vp);


                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(final TabLayout.Tab tab) {
                        Log.i("Tab position: ", String.valueOf(tab.getPosition()));
                        FloatingActionButton _fab=(FloatingActionButton)findViewById(R.id.fab);
                        _fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent add_activity=new Intent(getApplicationContext(), activity_addSchedule.class);
                                add_activity.putExtra("Day", String.valueOf(tab.getPosition()));
                                startActivityForResult(add_activity, 111);
                            }
                        });
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                TabLayout.Tab tabs=tabLayout.getTabAt(day-1);
                tabs.select();



                ft_pending.commit();
//                ((FrameLayout)findViewById(R.id.fragment_container)).setVisibility(View.INVISIBLE);



            }   else if (_item_id == R.id.nav_ass_pending) {
//            Visibility

                ((FrameLayout)findViewById(R.id.fragment_container)).setVisibility(View.VISIBLE);
                ((TabLayout)findViewById(R.id.tabs)).setVisibility(View.GONE);
                ((ViewPager) findViewById(R.id.pager)).setVisibility(View.GONE);
                fab_but.setVisibility(View.VISIBLE);
                fab_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent add_activity=new Intent(getApplicationContext(), activity_addAssignment.class);
                        startActivityForResult(add_activity, 111);
                    }
                });

//            ######
                FragmentTransaction ft_pending=getSupportFragmentManager().beginTransaction();
                ft_pending.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft_pending.replace(R.id.fragment_container, new fragment_assg_pending(),"View Frag");
                ft_pending.commit();
                ((FrameLayout)findViewById(R.id.fragment_container)).setVisibility(View.VISIBLE);

            }
            else if (_item_id == R.id.nav_ass_submitted) {
//            Visibility
                ((FrameLayout)findViewById(R.id.fragment_container)).setVisibility(View.VISIBLE);
                ((TabLayout)findViewById(R.id.tabs)).setVisibility(View.GONE);
                ((ViewPager) findViewById(R.id.pager)).setVisibility(View.GONE);
                fab_but.setVisibility(View.VISIBLE);
                fab_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent add_activity=new Intent(getApplicationContext(), activity_addAssignment.class);
                        startActivityForResult(add_activity, 111);
                    }
                });
//            ######
                FrameLayout frame=(FrameLayout)findViewById(R.id.fragment_container);
                Fragment frag_pending=new fragment_assg_submitted();
                FragmentTransaction ft_pending=getSupportFragmentManager().beginTransaction();
                ft_pending.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
                ft_pending.replace(R.id.fragment_container, frag_pending,"View Frag");
                ft_pending.commit();


            }
            prev=_item_id;

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, activity_about.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        _item_id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onActivityResult(int req_code, int res_code, Intent data)
    {
        super.onActivityResult(req_code, res_code, data);
        if(req_code==1001 && res_code== Activity.RESULT_OK);
        Fragment currentFragment=this.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.detach(currentFragment);
        ft.attach(currentFragment);
    }


    public void addPages()
    {
        schedulePager pageAdapter=new schedulePager(this.getSupportFragmentManager());
        pageAdapter.addFragment(new fragment_sch_sunday());
        pageAdapter.addFragment(new fragment_sch_monday());
        pageAdapter.addFragment(new fragment_sch_tuesday());
        pageAdapter.addFragment(new fragment_sch_wednesday());
        pageAdapter.addFragment(new fragment_sch_thursday());
        pageAdapter.addFragment(new fragment_sch_friday());
        pageAdapter.addFragment(new fragment_sch_saturday());
        vp.setAdapter(pageAdapter);



    }


    public void onHome()
    {
//        Set Visibility
        ((TabLayout)findViewById(R.id.tabs)).setVisibility(View.GONE);
        ((ViewPager) findViewById(R.id.pager)).setVisibility(View.GONE);
        ((FloatingActionButton)findViewById(R.id.fab)).setVisibility(View.GONE);

        Fragment frag=new fragment_home();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
        ft.replace(R.id.fragment_container, frag);
        ft.commit();
        ((FrameLayout)findViewById(R.id.fragment_container)).setVisibility(View.VISIBLE);

    }

    public void onTabSelected(TabLayout.Tab tab) {
        vp.setCurrentItem(tab.getPosition());
    }




}

package com.example.classschedule;

import android.app.Activity;
import android.view.View;
import android.view.animation.PathInterpolator;



public class back_clickListener implements View.OnClickListener {
    public Activity _act;

    public back_clickListener(Activity act)
    {
        this._act=act;
    }
    @Override
    public void onClick(View v)
    {
     this._act.finish();
    }
}

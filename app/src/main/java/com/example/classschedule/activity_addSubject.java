package com.example.classschedule;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_addSubject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);


        //Custom Layout for the ActionBar
//        getSupportActionBar().setTitle("");
//        View actionBar=getLayoutInflater().inflate(R.layout.actionbar_layout, null);
//        TextView title=(TextView)actionBar.findViewById(R.id.title_action);
//        title.setText("Add Subject");
//
//        actionBar.findViewById(R.id.back_but).setOnClickListener(new back_clickListener(this));
//
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(actionBar);

        ((ImageView)findViewById(R.id.subj_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//        Adding Data to Spinner
        Spinner th_p=(Spinner)findViewById(R.id.spinner);

        ArrayList<String> data=new ArrayList<String>();
        data.add("Theory");
        data.add("Practical");

        ArrayAdapter<String> datas=new ArrayAdapter<String>(this, R.layout.spinner_layout, data);
        datas.setDropDownViewResource(R.layout.spinner_layout);
        th_p.setAdapter(datas);


        final EditText sub_name=(EditText)findViewById(R.id.sub_name);
        final EditText inst_name=(EditText)findViewById(R.id.inst_name);
        final Spinner t_or_p=(Spinner)findViewById(R.id.spinner);

//        For Subject Updates
        final object_subject edit_sub_obj=(object_subject)getIntent().getSerializableExtra("sub_obj");

        if(edit_sub_obj!=null)
        {

            sub_name.setText(edit_sub_obj.get_sub_name());
            inst_name.setText(edit_sub_obj.get_inst_name());
            t_or_p.setSelection(datas.getPosition(edit_sub_obj.get_t_p()));
            ((TextView)findViewById(R.id.sub_title)).setText("UPDATE SUBJECT");
            ((Button)findViewById(R.id.addBut_sub)).setText("UPDATE");
            ((Button)findViewById(R.id.delBut_sub)).setVisibility(View.VISIBLE);


        }


//        ##################################################

        Button add_but=(Button)findViewById(R.id.addBut_sub);
        add_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(sub_name.getText().toString().isEmpty() || inst_name.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "One or More Fields are Empty. ", Toast.LENGTH_SHORT).show();
                    return;
                }




                object_subject sub=new object_subject(sub_name.getText().toString(), inst_name.getText().toString(), t_or_p.getSelectedItem().toString());
                db_helper helper=new db_helper(v.getContext(), null,null, 1);

//                Updating the schedule
                if(edit_sub_obj!=null)
                {
                    sub.setID(edit_sub_obj.getID());
                    boolean result=helper.updateSubject(sub);


                    if(result)
                    {
                        Toast.makeText(getApplicationContext(), "Subject Updated.", Toast.LENGTH_LONG).show();
                        setResult(Activity.RESULT_OK);
                        finish();
                        return;
                    }

                    Toast.makeText(getApplicationContext(), "Error. Subject cannot be updated.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;



                }


                if(helper.addSubject(sub))
                {

                   Toast.makeText(getApplicationContext(), sub_name.getText().toString()+" has been added.", Toast.LENGTH_SHORT).show();
//                    setResult(Activity.RESULT_OK);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), sub_name.getText().toString()+" cannot be added.", Toast.LENGTH_SHORT).show();


            }
        });


        ((Button)findViewById(R.id.delBut_sub)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_helper delete=new db_helper(getApplicationContext(), null, null, 1);
                int res=delete.delete_subject(edit_sub_obj.getID());
                if(res==1)
                    Toast.makeText(getApplicationContext(),"Deleted.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Error. Please try Again.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });









    }
}

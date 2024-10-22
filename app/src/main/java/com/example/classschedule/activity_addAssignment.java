package com.example.classschedule;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class activity_addAssignment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        //Custom Layout for the Action Bar

//        getSupportActionBar().setTitle("");
//        View actionBar = getLayoutInflater().inflate(R.layout.actionbar_layout, null);
//        TextView title = (TextView) actionBar.findViewById(R.id.title_action);
//        title.setText("Add Assignment");
//
//        actionBar.findViewById(R.id.back_but).setOnClickListener(new back_clickListener(this));
//
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(actionBar);

        ((ImageView)findViewById(R.id.assg_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


//      Database helper for transactions
        db_helper handler = new db_helper(this, null, null, 1);

//        Adapter for Subjects List
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, handler.list_all_subject_name());
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        Spinner spn = (Spinner) findViewById(R.id.ass_sub_spinner);
        spn.setAdapter(adapter);


//      Date Picker Listeners

        ((EditText) findViewById(R.id.ass_submission_datetime)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(100);
            }
        });

        ((EditText) findViewById(R.id.ass_reminder_datetime)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(101);
            }
        });

//        ##################################


//        Update Assignment
        final object_assignment edit_assg_obj=(object_assignment)getIntent().getSerializableExtra("assg_obj");

        if(edit_assg_obj!=null)
        {

            ((TextView)findViewById(R.id.assg_title)).setText("UPDATE ASSIGNMENT");
            ((EditText)findViewById(R.id.ass_name)).setText(edit_assg_obj.get_assignment_name());
            ((EditText)findViewById(R.id.ass_submission_datetime)).setText(edit_assg_obj.get_submission_date());
            ((EditText)findViewById(R.id.ass_reminder_datetime)).setText(edit_assg_obj.get_reminder_date());
            if(edit_assg_obj.get_status().compareTo("Pending")==0)
            {
                ((RadioButton) findViewById(R.id.ass_pending)).setChecked(true);
            }else
                ((RadioButton) findViewById(R.id.ass_submitted)).setChecked(true);

            ((Button)findViewById(R.id.addBut_assg)).setText("UPDATE");
            ((Button)findViewById(R.id.delBut_assg)).setVisibility(View.VISIBLE);


        }




//        Onclick Listener
                Button add_assg = (Button) findViewById(R.id.addBut_assg);

        add_assg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              If no subjects are added
                if (((Spinner) findViewById(R.id.ass_sub_spinner)).getSelectedItem() == null) {
                    Toast.makeText(getApplicationContext(), "Please Add Subjects.", Toast.LENGTH_LONG).show();
                    return;
                }

//                Check for blank fields
                boolean validator = ((EditText) findViewById(R.id.ass_name)).getText().toString().isEmpty() || ((EditText) findViewById(R.id.ass_submission_datetime)).getText().toString().isEmpty() || ((EditText) findViewById(R.id.ass_reminder_datetime)).getText().toString().isEmpty();

                if ((((RadioGroup) findViewById(R.id.ass_radiogroup)).getCheckedRadioButtonId()) == -1 || validator) {

                    Toast.makeText(getApplicationContext(), "One or More Fields are Empty.", Toast.LENGTH_LONG).show();
                    return;
                }



//              Preparind data to store to database

                String assg_name = ((EditText) findViewById(R.id.ass_name)).getText().toString();
                String sub_date = ((EditText) findViewById(R.id.ass_submission_datetime)).getText().toString();
                String rem_date = ((EditText) findViewById(R.id.ass_reminder_datetime)).getText().toString();
                String status = "";




//              Get a subject id from Subjects table
                String _sub_selected = ((Spinner) findViewById(R.id.ass_sub_spinner)).getSelectedItem().toString();
                String[] splitted=_sub_selected.split("\\-");
                String _sub =splitted[0];
                String _th_or_pra=splitted[1];
                db_helper sub_handler = new db_helper(getApplicationContext(), null, null, 1);
                int _sub_id = (sub_handler.findSubject(_sub, _th_or_pra)).getID();


//              Get status
                if (((RadioButton) findViewById(R.id.ass_pending)).isChecked())
                    status = "Pending";
                else if (((RadioButton) findViewById(R.id.ass_submitted)).isChecked())
                    status = "Submitted";
//####################################################################



//                Adding to assignment
                object_assignment assg = new object_assignment(assg_name, sub_date, rem_date, status, _sub_id);
                db_helper assg_handler = new db_helper(getApplicationContext(), null, null, 1);


//                Updating the schedule
                if(edit_assg_obj!=null)
                {
                    assg.set_id(edit_assg_obj.getID());
                    boolean result=assg_handler.updateAssignment(assg);


                    if(result)
                    {
                        Toast.makeText(getApplicationContext(), "Assignment updated for "+edit_assg_obj.get_sub_name()+".", Toast.LENGTH_LONG).show();
                        setResult(Activity.RESULT_OK);
                        finish();
                        return;
                    }

                    Toast.makeText(getApplicationContext(), "Error. Assignment cannot be updated.", Toast.LENGTH_SHORT).show();
                    finish();
                    return;



                }

                if (assg_handler.addAssgn(assg)) {
                    Toast.makeText(getApplicationContext(), "Assignment for " + _sub + " has been Stored.", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else
                    Toast.makeText(getApplicationContext(), "Error. Schedule cannot be added.", Toast.LENGTH_SHORT).show();


            }
        });

//        Deletion
        ((Button)findViewById(R.id.delBut_assg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db_helper delete=new db_helper(getApplicationContext(), null, null, 1);
                int res=delete.delete_assignment(edit_assg_obj.getID());
                if(res==1)
                    Toast.makeText(getApplicationContext(),"Deleted.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"Error. Please try Again.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    @Override
    protected Dialog onCreateDialog(int id)
    {

        Calendar cal=Calendar.getInstance();

        if(id==100)
            return new DatePickerDialog(this,myDateListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH ));
        if(id==101)
            return new DatePickerDialog(this,myDateListener2, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH ));
        return null;
    }



    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day
            Calendar cal=Calendar.getInstance();
            if(arg1<cal.get(Calendar.YEAR) && arg2<cal.get(Calendar.MONTH) && arg3<cal.get(Calendar.DAY_OF_MONTH) ){
                Toast.makeText(getApplicationContext(), "Please enter the correct Submission Date.", Toast.LENGTH_SHORT).show();
            }

            ((EditText)findViewById(R.id.ass_submission_datetime)).setText(String.valueOf(arg1)+"-"+String.valueOf(arg2+1)+"-"+String.valueOf(arg3));
        }
    };



    private DatePickerDialog.OnDateSetListener myDateListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // arg1 = year
            // arg2 = month
            // arg3 = day

            Calendar cal=Calendar.getInstance();
            if(arg1<cal.get(Calendar.YEAR) && arg2<cal.get(Calendar.MONTH) && arg3<cal.get(Calendar.DAY_OF_MONTH) ){
                Toast.makeText(getApplicationContext(), "Please enter the correct Reminder Date.", Toast.LENGTH_SHORT).show();
                return;
            }

            ((EditText)findViewById(R.id.ass_reminder_datetime)).setText(String.valueOf(arg1)+"-"+String.valueOf(arg2+1)+"-"+String.valueOf(arg3));
        }
    };
}

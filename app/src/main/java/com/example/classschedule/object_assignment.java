package com.example.classschedule;

import java.io.Serializable;

/**
 * Created by pi on 10/26/17.
 */

public class object_assignment implements Serializable{



    public int _id;
    public String _assignment_name;
    public String _submission_date;
    public String _reminder_date;
    public String _status;
    public int _sub_id;

    //    Get name from foreign key references
    private String _sub_name;
    private String _ins_name;

    public object_assignment(){}
    public object_assignment(String assignment_name, String submission_date, String reminder_date, String status, int sub_id)
    {
        this._assignment_name=assignment_name;
        this._submission_date=submission_date;
        this._reminder_date=reminder_date;
        this._status=status;
        this._sub_id=sub_id;
    }

    public void set_id(int id){this._id=id;}
    public int getID()
    {
        return  this._id;
    }

    public void set_assignment_name(String assignment_name)
    {
        this._assignment_name=assignment_name;
    }

    public String get_assignment_name()
    {
        return this._assignment_name;
    }

    public void set_submission_date(String submission_date){this._submission_date=submission_date;   }
    public String get_submission_date(){return this._submission_date;}

    public void set_reminder_date_date(String reminder_date)
    {
        this._reminder_date=reminder_date;
    }
    public String get_reminder_date(){return this._reminder_date;}


    public void set_status(String status)
    {
        this._status=status;
    }
    public String get_status(){return this._status;}

    public void set_sub_id(int id)
    {
        this._sub_id=id;
    }
    public int get_sub_id()
    {
        return this._sub_id;
    }


    public String get_sub_name(){return  this._sub_name;}
    public void set_sub_name(String name){this._sub_name=name;}

    public String get_ins_name(){return  this._ins_name;}
    public void set_ins_name(String name){this._ins_name=name;}


}

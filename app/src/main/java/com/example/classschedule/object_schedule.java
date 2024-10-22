package com.example.classschedule;

import java.io.Serializable;

/**
 * Created by pi on 10/27/17.
 */

public class object_schedule implements Serializable{
//    DB fields
    private int _id;
    private int _sub_id;
    private String _day;
    private String _start_time;
    private String _end_time;
    private String _remarks;
//    Get name from foreign key references
    private String _sub_name;
    private String _ins_name;
    private String _theory_or_practical;


    public object_schedule()
    {

    }
    public object_schedule(int sub_id, String day, String start_time, String end_time, String _remarks)
    {
        this._sub_id=sub_id;
        this._day=day;
        this._start_time=start_time;
        this._end_time=end_time;
        this._remarks=_remarks;
    }

    public int get_id(){return this._id;}
    public void set_id(int id){this._id=id;}

    public int get_sub_id(){return this._sub_id;}
    public void set_sub_id(int sub_id){this._sub_id=sub_id;}

    public String get_day(){return this._day;}
    public void set_day(String day){this._day=day;}

    public String get_start_time(){return this._start_time;}
    public void set_start_time(String start_time){this._start_time=start_time;}

    public String get_end_time(){return this._end_time;}
    public void set_end_time(String end_time){this._end_time=end_time;}

    public String get_remarks(){return  this._remarks;}
    public void set_remarks(String remarks){this._remarks=remarks;}

    public String get_sub_name(){return  this._sub_name;}
    public void set_sub_name(String name){this._sub_name=name;}

    public String get_ins_name(){return  this._ins_name;}
    public void set_ins_name(String name){this._ins_name=name;}

    public String get_th_or_p(){return  this._theory_or_practical;}
    public void set_th_or_p(String name){this._theory_or_practical=name;}



}

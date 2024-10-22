package com.example.classschedule;

import java.io.Serializable;

/**
 * Created by pi on 10/26/17.
 */

public class object_subject implements Serializable{
    private int _id;
    private String _sub_name;
    private String _inst_name;
    private String _t_p;


    public object_subject()
    {
        this._id=0;
    }
    public object_subject(int id, String sub_name, String inst_name, String t_p)
    {
        this._id=id;
        this._sub_name=sub_name;
        this._inst_name=inst_name;
        this._t_p=t_p;
    }

    public object_subject(String sub_name, String inst_name, String t_p)
    {
        this._id=0;
        this._sub_name=sub_name;
        this._inst_name=inst_name;
        this._t_p=t_p;
    }

    public void setID(int id)
    {
        this._id=id;
    }
    public int getID()
    {
        return  this._id;
    }

    public void set_sub_name(String sub_name)
    {
        this._sub_name=sub_name;
    }

    public String get_sub_name()
    {
        return  this._sub_name;
    }

    public  void set_inst_name(String inst_name)
    {
        this._inst_name=inst_name;
    }

    public String get_inst_name()
    {
        return this._inst_name;
    }

    public void set_t_p(String t_p)
    {
        this._t_p=t_p;
    }

    public String get_t_p()
    {
        return  this._t_p;
    }

}

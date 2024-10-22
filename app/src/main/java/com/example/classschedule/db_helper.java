package com.example.classschedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class db_helper extends SQLiteOpenHelper {
//  Database Ints.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "schedule.db";
//    Table for subjects
    public static final String TABLE_SUBJECTS = "subjects";
    public static final String COLUMN_SUBJECT_ID = "_id";
    public static final String COLUMN_SUBJECT_SUBJECTNAME = "_sub_name";
    public static final String COLUMN_SUBJECT_INSTRUCTORNAME = "_inst_name";
    public static final String COLUMN_SUBJECT_TH_OR_PRA = "_t_p";
//    Table for assignments.
    public static final String TABLE_ASSIGNMENTS = "assignments";
    public static final String COLUMN_ASSG_ID = "_id";
    public static final String COLUMN_ASSG_ASSIGNMENTNAME = "_assignment_name";
    public static final String COLUMN_ASSG_SUBMISSIONDATE = "_submission_date";
    public static final String COLUMN_ASSG_REMINDERDATE = "_reminder_date";
    public static final String COLUMN_ASSG_STATUS = "_status";
    public static final String COLUMN_ASSG_SUBID = "_sub_id";
//    Table for Schedule
    public static final String TABLE_SCH = "schedules";
    public static final String COLUMN_SCH_ID = "_id";
    public static final String COLUMN_SCH_SUBID = "_sub_id";
    public static final String COLUMN_SCH_DAY = "_day";
    public static final String COLUMN_SCH_START = "_start_time";
    public static final String COLUMN_SCH_END = "_end_time";
    public static final String COLUMN_SCH_REMARKS = "_remarks";




    public db_helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + "("+COLUMN_SUBJECT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "  + COLUMN_SUBJECT_SUBJECTNAME + " TEXT ," + COLUMN_SUBJECT_INSTRUCTORNAME + " TEXT," + COLUMN_SUBJECT_TH_OR_PRA + " TEXT, UNIQUE("+COLUMN_SUBJECT_SUBJECTNAME+","+COLUMN_SUBJECT_TH_OR_PRA+") )";
        String CREATE_ASSGN_TABLE = "CREATE TABLE " + TABLE_ASSIGNMENTS + "("+COLUMN_ASSG_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "  + COLUMN_ASSG_ASSIGNMENTNAME + " TEXT," + COLUMN_ASSG_SUBMISSIONDATE + " DATETIME," + COLUMN_ASSG_REMINDERDATE + " DATETIME,"+ COLUMN_ASSG_STATUS + " TEXT,"+ COLUMN_ASSG_SUBID + " INTEGER, FOREIGN KEY("+COLUMN_ASSG_SUBID+") REFERENCES subjects(_id)  )";
        String CREATE_TABLE_SCH = "CREATE TABLE " + TABLE_SCH + "("+COLUMN_SCH_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "  + COLUMN_SCH_SUBID + " INTEGER," + COLUMN_SCH_DAY + " TEXT," + COLUMN_SCH_START + " TEXT," + COLUMN_SCH_END + " TEXT," + COLUMN_SCH_REMARKS + " TEXT, FOREIGN KEY("+COLUMN_SCH_SUBID+") REFERENCES subjects(_id)  )";

        db.execSQL(CREATE_SUBJECTS_TABLE);
        db.execSQL(CREATE_ASSGN_TABLE);
        db.execSQL(CREATE_TABLE_SCH);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCH);
        onCreate(db);


    }


    public boolean addSubject(object_subject subj)
    {
        ContentValues values=new ContentValues();
        //values.put(COLUMN_SUBJECT_ID,subj.getID());
        values.put(COLUMN_SUBJECT_SUBJECTNAME, subj.get_sub_name());
        values.put(COLUMN_SUBJECT_INSTRUCTORNAME, subj.get_inst_name());
        values.put(COLUMN_SUBJECT_TH_OR_PRA, subj.get_t_p());

        SQLiteDatabase db=this.getWritableDatabase();

        try {
            db.insert(TABLE_SUBJECTS, null, values);
        }catch(Exception e)
        {
            return false;
        }

        db.close();
        return true;

    }

    public object_subject findSubject(String subj_name, String th_or_pra)
    {
        //Creating a raw query to find the data.
        String query="Select * from "+TABLE_SUBJECTS+" where "+COLUMN_SUBJECT_SUBJECTNAME+"= \""+subj_name+"\" and "+COLUMN_SUBJECT_TH_OR_PRA+"= \""+th_or_pra+"\"";
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);


        object_subject subj=new object_subject();
        if (cursor.moveToFirst())
        {
            cursor.moveToFirst();
            subj.setID(Integer.parseInt(cursor.getString(0)));
            subj.set_sub_name(cursor.getString(1));
            subj.set_inst_name(cursor.getString(2));
            subj.set_t_p(cursor.getString(3));
            cursor.close();

        }
        else
            subj=null;

        db.close();
        return subj;



    }

    public ArrayList<String> list_all_inst()
    {
        ArrayList<String> inst=new ArrayList<String>();


        String query="Select * from "+TABLE_SUBJECTS;
        SQLiteDatabase db=getWritableDatabase();
        if(db==null)
        {
            return null;
        }
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isLast())
            {
                inst.add(cursor.getString(2));
                cursor.moveToNext();
            }
            inst.add(cursor.getString(2));

        }
        db.close();
        return inst;
    }


    public ArrayList<String> list_all_subject_name()
    {
        ArrayList<String> subjects=new ArrayList<>();
        String query="Select * from "+TABLE_SUBJECTS;
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isLast())
            {
                subjects.add(cursor.getString(1)+"-"+cursor.getString(3));
                cursor.moveToNext();
            }
            subjects.add(cursor.getString(1)+"-"+cursor.getString(3));
            cursor.moveToNext();

        }
        db.close();
        return subjects;




    }
    public ArrayList<object_subject> list_all_subjects()
    {
        ArrayList<object_subject> subs=new ArrayList<>();
        String query="Select * from "+TABLE_SUBJECTS;
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isLast())
            {
                object_subject sub=new object_subject();
                sub.setID(cursor.getInt(0));
                sub.set_sub_name(cursor.getString(1));
                sub.set_inst_name(cursor.getString(2));
                sub.set_t_p(cursor.getString(3));
                subs.add(sub);
                cursor.moveToNext();
            }
            object_subject sub=new object_subject();
            sub.setID(cursor.getInt(0));
            sub.set_sub_name(cursor.getString(1));
            sub.set_inst_name(cursor.getString(2));
            sub.set_t_p(cursor.getString(3));
            subs.add(sub);
            cursor.moveToNext();

        }
        db.close();
        return subs;
    }



    public ArrayList<object_assignment> list_assg_status(String _stats)
    {
        ArrayList<object_assignment> assignments=new ArrayList<object_assignment>();


        String query="Select "+TABLE_ASSIGNMENTS+"."+COLUMN_ASSG_ID+","+COLUMN_ASSG_ASSIGNMENTNAME+","+COLUMN_ASSG_SUBMISSIONDATE+","+COLUMN_ASSG_REMINDERDATE+","+COLUMN_ASSG_STATUS+","+COLUMN_ASSG_SUBID+","+COLUMN_SUBJECT_SUBJECTNAME+","+COLUMN_SUBJECT_INSTRUCTORNAME+" from "+TABLE_ASSIGNMENTS+ "  INNER JOIN "+TABLE_SUBJECTS+" ON "+TABLE_SUBJECTS+"."+COLUMN_SUBJECT_ID+"="+TABLE_ASSIGNMENTS+"."+COLUMN_ASSG_SUBID+" where "+COLUMN_ASSG_STATUS+"=\""+_stats+"\" ORDER BY "+COLUMN_ASSG_SUBMISSIONDATE+" ASC" ;
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isLast())
            {
                object_assignment assg=new object_assignment();
                assg.set_id(cursor.getInt(0));
                assg.set_assignment_name(cursor.getString(1));
                assg.set_submission_date(cursor.getString(2));
                assg.set_reminder_date_date(cursor.getString(3));
                assg.set_status(cursor.getString(4));
                assg.set_sub_id(cursor.getInt(5));
                assg.set_sub_name(cursor.getString(6));
                assg.set_ins_name(cursor.getString(7));
                assignments.add(assg);
                cursor.moveToNext();
            }

            object_assignment assg=new object_assignment();
            assg.set_id(cursor.getInt(0));
            assg.set_assignment_name(cursor.getString(1));
            assg.set_submission_date(cursor.getString(2));
            assg.set_reminder_date_date(cursor.getString(3));
            assg.set_status(cursor.getString(4));
            assg.set_sub_id(cursor.getInt(5));
            assg.set_sub_name(cursor.getString(6));
            assg.set_ins_name(cursor.getString(7));
            assignments.add(assg);
            cursor.moveToNext();


        }
        db.close();
        return assignments;
    }


    public int list_today_assg(String date)
    {
        String query="Select "+TABLE_ASSIGNMENTS+"."+COLUMN_ASSG_ID+","+COLUMN_ASSG_ASSIGNMENTNAME+","+COLUMN_ASSG_SUBMISSIONDATE+","+COLUMN_ASSG_REMINDERDATE+","+COLUMN_ASSG_STATUS+","+COLUMN_ASSG_SUBID+","+COLUMN_SUBJECT_SUBJECTNAME+","+COLUMN_SUBJECT_INSTRUCTORNAME+" from "+TABLE_ASSIGNMENTS+ "  INNER JOIN "+TABLE_SUBJECTS+" ON "+TABLE_SUBJECTS+"."+COLUMN_SUBJECT_ID+"="+TABLE_ASSIGNMENTS+"."+COLUMN_ASSG_SUBID+" where "+COLUMN_ASSG_SUBMISSIONDATE+"=\""+date+"\" and "+COLUMN_ASSG_STATUS+"=\"Pending\"";
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        int number=cursor.getCount();
        db.close();
        return number;
    }

    public boolean addAssgn(object_assignment assg)
    {
        ContentValues values=new ContentValues();
        //values.put(COLUMN_ASSG_ID,subj.getID());
        values.put(COLUMN_ASSG_ASSIGNMENTNAME, assg.get_assignment_name());
        values.put(COLUMN_ASSG_SUBMISSIONDATE, assg.get_submission_date());
        values.put(COLUMN_ASSG_REMINDERDATE, assg.get_reminder_date());
        values.put(COLUMN_ASSG_STATUS, assg.get_status());
        values.put(COLUMN_ASSG_SUBID, assg.get_sub_id());

        SQLiteDatabase db=this.getWritableDatabase();
        try {
            db.insert(TABLE_ASSIGNMENTS, null, values);
        }catch(Exception e)
        {
            return false;
        }

        db.close();
        return true;

    }

    public boolean addSchedule(object_schedule sch)
    {
        ContentValues values=new ContentValues();
        //values.put(COLUMN_SCH_ID,subj.getID());
        values.put(COLUMN_SCH_SUBID, sch.get_sub_id());
        values.put(COLUMN_SCH_DAY, sch.get_day());
        values.put(COLUMN_SCH_START, sch.get_start_time());
        values.put(COLUMN_SCH_END, sch.get_end_time());
        values.put(COLUMN_SCH_REMARKS, sch.get_remarks());

        SQLiteDatabase db=this.getWritableDatabase();
        try {
            db.insert(TABLE_SCH, null, values);
        }catch(Exception e)
        {
            return false;
        }

        db.close();
        return true;

    }

    public boolean updateSchedule(object_schedule sch)
    {
        ContentValues values=new ContentValues();
//        values.put(COLUMN_SCH_ID,sch.get_id());
        values.put(COLUMN_SCH_SUBID, sch.get_sub_id());
        values.put(COLUMN_SCH_DAY, sch.get_day());
        values.put(COLUMN_SCH_START, sch.get_start_time());
        values.put(COLUMN_SCH_END, sch.get_end_time());
        values.put(COLUMN_SCH_REMARKS, sch.get_remarks());

        SQLiteDatabase db=this.getWritableDatabase();

        try {
            db.update(TABLE_SCH,  values, " _id= "+String.valueOf(sch.get_id()), null);
        }catch(Exception e)
        {
            return false;
        }

        db.close();
        return true;

    }


    public ArrayList<object_schedule> getSchedule_day(String day)
    {
        //Creating a raw query to find the data.
        String query="Select "+TABLE_SUBJECTS+"."+COLUMN_SUBJECT_SUBJECTNAME+", "+TABLE_SCH+"."+COLUMN_SCH_ID+" ,"+TABLE_SCH+"."+COLUMN_SCH_DAY+", "+TABLE_SCH+"."+COLUMN_SCH_START+", "+TABLE_SCH+"."+COLUMN_SCH_END+", "+TABLE_SCH+"."+COLUMN_SCH_REMARKS+", "+TABLE_SUBJECTS+"."+COLUMN_SUBJECT_INSTRUCTORNAME+", "+TABLE_SCH+"."+COLUMN_SCH_ID+","+COLUMN_SUBJECT_TH_OR_PRA+" from "+TABLE_SCH+" INNER JOIN "+TABLE_SUBJECTS+" ON "+TABLE_SCH+"."+COLUMN_SCH_SUBID+"="+TABLE_SUBJECTS+"."+COLUMN_SUBJECT_ID+" where "+TABLE_SCH+"."+COLUMN_SCH_DAY+"=\""+day+"\" order by "+COLUMN_SCH_START+" ASC";
//        String query="Select * from "+TABLE_SCH; //+" where "+COLUMN_SCH_DAY+"= \""+day+"\"";
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery(query,null);


        ArrayList<object_schedule> schedules=new ArrayList<object_schedule>();
        if (cursor.moveToFirst())
        {

            while(!cursor.isLast()) {
                object_schedule sch=new object_schedule();
                sch.set_sub_name(cursor.getString(0));
                sch.set_sub_id(Integer.parseInt(cursor.getString(1)));
                sch.set_day(cursor.getString(2));
                sch.set_start_time(cursor.getString(3));
                sch.set_end_time(cursor.getString(4));
                sch.set_remarks(cursor.getString(5));
                sch.set_ins_name(cursor.getString(6));
                sch.set_id(cursor.getInt(7));
                sch.set_th_or_p(cursor.getString(8));
                schedules.add(sch);
                cursor.moveToNext();
            }
            object_schedule sch=new object_schedule();
            sch.set_sub_name(cursor.getString(0));
            sch.set_sub_id(Integer.parseInt(cursor.getString(1)));
            sch.set_day(cursor.getString(2));
            sch.set_start_time(cursor.getString(3));
            sch.set_end_time(cursor.getString(4));
            sch.set_remarks(cursor.getString(5));
            sch.set_ins_name(cursor.getString(6));
            sch.set_id(cursor.getInt(7));
            sch.set_th_or_p(cursor.getString(8));
            schedules.add(sch);

        }
        else
            schedules=null;

        db.close();
        return schedules;



    }

    public int delete_schedule(int id)
    {
        String query="DELETE FROM "+TABLE_SCH+" WHERE "+COLUMN_SCH_ID+"="+id;
        SQLiteDatabase db=getWritableDatabase();
        return db.delete(TABLE_SCH, COLUMN_SCH_ID+"="+id, null);




    }

    public int delete_subject(int id)
    {

        SQLiteDatabase db=getWritableDatabase();
        return db.delete(TABLE_SUBJECTS, COLUMN_SUBJECT_ID+"="+id, null);




    }


    public boolean updateSubject(object_subject sub)
    {
        ContentValues values=new ContentValues();
//        values.put(COLUMN_SCH_ID,sch.get_id());
        values.put(COLUMN_SUBJECT_ID, sub.getID());
        values.put(COLUMN_SUBJECT_SUBJECTNAME,sub.get_sub_name());
        values.put(COLUMN_SUBJECT_INSTRUCTORNAME, sub.get_inst_name());
        values.put(COLUMN_SUBJECT_TH_OR_PRA, sub.get_t_p());

        SQLiteDatabase db=this.getWritableDatabase();

        try {
            db.update(TABLE_SUBJECTS,  values, " _id= "+String.valueOf(sub.getID()), null);
        }catch(Exception e)
        {
            return false;
        }

        db.close();
        return true;

    }


    public boolean updateAssignment(object_assignment assg)
    {
        ContentValues values=new ContentValues();

        values.put(COLUMN_ASSG_ID, assg.getID());
        values.put(COLUMN_ASSG_ASSIGNMENTNAME,assg.get_assignment_name());
        values.put(COLUMN_ASSG_SUBMISSIONDATE, assg.get_submission_date());
        values.put(COLUMN_ASSG_REMINDERDATE, assg.get_reminder_date());
        values.put(COLUMN_ASSG_STATUS, assg.get_status());
        values.put(COLUMN_ASSG_SUBID, assg.get_sub_id());

        SQLiteDatabase db=this.getWritableDatabase();

        try {
            db.update(TABLE_ASSIGNMENTS,  values, " _id= "+String.valueOf(assg.getID()), null);
        }catch(Exception e)
        {
            return false;
        }

        db.close();
        return true;

    }


    public int delete_assignment(int id)
    {

        SQLiteDatabase db=getWritableDatabase();
        return db.delete(TABLE_ASSIGNMENTS, COLUMN_ASSG_ID+"="+id, null);




    }





}



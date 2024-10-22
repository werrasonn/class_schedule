package com.example.classschedule;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pi on 11/13/17.
 */

public class adapter_subject extends ArrayAdapter<object_subject> {

    public adapter_subject(@NonNull Context context, @NonNull List<object_subject> objects)
    {
        super(context, 0, objects);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View ConvertView, @NonNull ViewGroup parent)
    {
        object_subject sub_obj=getItem(position);

        if(ConvertView==null)
//            Adding Data to list
            ConvertView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_layout_subject, parent, false);

//        Adding data to adapter item
        ((TextView)ConvertView.findViewById(R.id.adapter_subject_name)).setText(sub_obj.get_sub_name());
        ((TextView)ConvertView.findViewById(R.id.adapter_subject_ins)).setText(sub_obj.get_inst_name());
        ((TextView)ConvertView.findViewById(R.id.adapter_subject_th_or_pra)).setText(sub_obj.get_t_p());

        return ConvertView;


    }
}

package com.example.contextmenu_lv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contextmenu_lv.MainActivity;
import com.example.contextmenu_lv.R;
import com.example.contextmenu_lv.model.Student;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Student> {
    private Context context;
    private int resource;
    private List<Student> arrStudent;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Student> arrStudent ) {
        super(context, resource, arrStudent);
        this.context = context;
        this.resource = resource;
        this.arrStudent = arrStudent;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            viewHolder.tvClass = convertView.findViewById(R.id.tv_class);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        checkBox.setTag(position);
        final Student student = arrStudent.get(position);
        viewHolder.tvName.setText(student.getmName());
        viewHolder.tvClass.setText(student.getmClass());
        if(MainActivity.isActionMode){
            checkBox.setVisibility(View.VISIBLE);
        }else{
            checkBox.setVisibility(View.GONE);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int postion  = (int) buttonView.getTag();
                if(MainActivity.StudentSeletion.contains(arrStudent.get(position))){
                    MainActivity.StudentSeletion.remove(arrStudent.get(position));
                }else{
                    MainActivity.StudentSeletion.add(arrStudent.get(position));
                }
                MainActivity.actionMode.setTitle(MainActivity.StudentSeletion.size() + "item selected ..");
            }
        });

        return convertView;
    }
    public class ViewHolder{
        TextView tvName,tvClass;
    }
    public void removeItem(ArrayList<Student> students){
        for (Student student: students){
            arrStudent.remove(student);
        }
        notifyDataSetChanged();
    }
}

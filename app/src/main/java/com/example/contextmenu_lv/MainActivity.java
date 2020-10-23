package com.example.contextmenu_lv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.contextmenu_lv.adapter.CustomAdapter;
import com.example.contextmenu_lv.model.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvStudent;
    private CustomAdapter customAdapter;
    ArrayList<Student> arrStudent;
    Button btn_add;
    public static boolean isActionMode = false;
    public static ArrayList<Student> StudentSeletion = new ArrayList<>();
    public static ActionMode actionMode = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add = findViewById(R.id.btn_add);
        lvStudent = findViewById(R.id.list_view);
        arrStudent = new ArrayList<>();

        arrStudent.add(new Student("Nguyễn Hữu Tuấn","18T3"));
        arrStudent.add(new Student("Nguyễn Văn Trí","18T4"));
        arrStudent.add(new Student("Nguyễn Kim An","18T1"));
        arrStudent.add(new Student("Trịnh Quang Phúc","18T2"));

        customAdapter = new CustomAdapter(this,R.layout.row_item,arrStudent);

        lvStudent.setAdapter(customAdapter);

//        lvStudent.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
//        lvStudent.setMultiChoiceModeListener(modeListener);

        registerForContextMenu(lvStudent);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("Add", null);
            }
        });
    }
    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_action_mode,menu);
            customAdapter.notifyDataSetChanged();
            isActionMode = true;
            actionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_delete:
                    customAdapter.removeItem(StudentSeletion);
                    mode.finish();
                    Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionMode = false;
            customAdapter.notifyDataSetChanged();
            actionMode = null;
            StudentSeletion.clear();
            btn_add.setEnabled(true);
        }
    };
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_edit:
                openDialog("Update", arrStudent.get(info.position));
                return true;
            case R.id.menu_delete:
                arrStudent.remove(info.position);
                customAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_deleteItems:
                if(actionMode != null){
                    return false;
                }else{
                    btn_add.setEnabled(false);
                    startActionMode(modeListener);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    };
    public void openDialog(String action, final Student student){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(false);

        Button btn_confirm = dialog.findViewById(R.id.btn_confirm);
        Button btn_cancle = dialog.findViewById(R.id.btn_cancle);
        final EditText ed_name = dialog.findViewById(R.id.ed_name);
        final EditText ed_class = dialog.findViewById(R.id.ed_class);

        if(action.equals("Add")){
            btn_confirm.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String sName = ed_name.getText().toString().trim();
                    String sClass = ed_class.getText().toString().trim();
                    if(sName.length() > 0 && sClass.length() > 0){
                        arrStudent.add(new Student(sName,sClass));
                        customAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            btn_confirm.setText("Lưu");
            ed_name.setText(student.getmName());
            ed_class.setText(student.getmClass());
            btn_confirm.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    String sName = ed_name.getText().toString().trim();
                    String sClass = ed_class.getText().toString().trim();
                    if(sName.length() > 0 && sClass.length() > 0){
                        student.setmName(ed_name.getText().toString());
                        student.setmClass(ed_class.getText().toString());
                        customAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
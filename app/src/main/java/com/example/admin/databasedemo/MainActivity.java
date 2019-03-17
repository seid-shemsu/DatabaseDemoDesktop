package com.example.admin.databasedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText name;
    Button add,reset,clear;
    ListView lv;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.editText);
        reset = (Button) findViewById(R.id.reset) ;
        clear = (Button) findViewById(R.id.clear);
        add = (Button) findViewById(R.id.button);
        lv = (ListView) findViewById(R.id.lv);

        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
        lv.setAdapter(arrayAdapter);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewTo();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTo();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student dbHelper = new Student(MainActivity.this);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                dbHelper.onUpgrade(db,1,2);
                arrayList.clear();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
    /*public void addTo(){
        String res = name.getText().toString();
        arrayList.add(res);
        arrayAdapter.notifyDataSetChanged();
    }

    public void clear(){
        arrayList.clear();
        arrayAdapter.notifyDataSetChanged();
    }*/
    public void viewTo(){
        arrayAdapter.clear();
        arrayAdapter.notifyDataSetChanged();
        Student dbHelper = new Student(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String name[] = {"name"};
        Cursor c = db.query("student",name,null,null,null,null,null);

        if(c.moveToFirst()){
            do{
                String s = c.getString(0);
                arrayList.add(s);
                arrayAdapter.notifyDataSetChanged();
            }while (c.moveToNext());
        }

    }

    public void addTo(){
        Student dbHelper = new Student(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues names = new ContentValues();
        String st = name.getText().toString();
        names.put("name",st);
        long row = db.insert("student",null,names);
        name.setText("");

        Toast.makeText(this, st +" has been added",Toast.LENGTH_SHORT).show();
    }
}

package com.example.crudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeViewActivity extends AppCompatActivity {
    private ListView listView;
    private String[] mFileStrings;
    private File[] listFile;
    LazyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listview);

        DatabaseHelperClass databaseHelperClass = new DatabaseHelperClass(this);
        ArrayList<HashMap<String,String>> employeeModelClasses = databaseHelperClass.getEmployeeList();
        adapter=new LazyAdapter(this, employeeModelClasses);
        //ListAdapter adapter = new SimpleAdapter(EmployeeViewActivity.this,employeeModelClasses,R.layout.list_row,new String[]{"name","email","image"},new int[]{R.id.name,R.id.email,R.id.showimage});
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
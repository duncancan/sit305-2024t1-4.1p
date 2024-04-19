package com.example.a41ptaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // References to main screen controls
    Button btn_addTask;
    Switch sw_showCompleted;
    ListView lv_taskList;
    ArrayAdapter taskArrayAdapter;
    boolean showCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btn_addTask = findViewById(R.id.btn_addTask);
        sw_showCompleted = findViewById(R.id.sw_showCompleted);
        lv_taskList = findViewById(R.id.lv_taskList);
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);

        // Show list items
        showCompleted = sw_showCompleted.isChecked();
        showTasks(dbHelper, showCompleted);

        // Handle clicks on list items
        lv_taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskModel taskModel = (TaskModel) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, EditTask.class);
                intent.putExtra("ID", taskModel.getId());
                startActivity(intent);
            }
        });

        // Handle clicks of new task button
        btn_addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTask.class);
                startActivity(intent);
            }
        });

        sw_showCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCompleted = sw_showCompleted.isChecked();
                showTasks(dbHelper, showCompleted);
            }
        });
    }

    private void showTasks(DatabaseHelper dbHelper, boolean showCompleted) {
        taskArrayAdapter = new ArrayAdapter<TaskModel>(MainActivity.this, android.R.layout.simple_list_item_1, dbHelper.getAll(showCompleted));
        lv_taskList.setAdapter(taskArrayAdapter);
    }
}
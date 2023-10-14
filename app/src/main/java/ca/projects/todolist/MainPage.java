package ca.projects.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ca.projects.todolist.Models.TaskManager;

public class MainPage extends AppCompatActivity {
    //Create task manager object to manage tasks
    private TaskManager taskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get instance of task manager
        taskManager = TaskManager.getInstance();
    }
}
package ca.projects.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ca.projects.todolist.Models.TaskManager;

public class MainPage extends AppCompatActivity {
    //Create task manager object to manage tasks
    private TaskManager taskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get instance of task manager
        taskManager = TaskManager.getInstance();
        //Display main page
        if (taskManager.isTaskManagerEmpty() == true){
            setContentView(R.layout.empty_to_do_list);
        }
        else{
            setContentView(R.layout.activity_main);
        }
        addNewTaskBtnClicked();
    }

    private void addNewTaskBtnClicked(){
        Button btn = findViewById(R.id.addTaskBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NewTask.makeIntent(MainPage.this);
                startActivity(intent);
            }
        });
    }
}
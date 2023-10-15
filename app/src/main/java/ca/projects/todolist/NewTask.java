package ca.projects.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ca.projects.todolist.Models.TaskManager;
import ca.projects.todolist.Models.TaskToDo;

public class NewTask extends AppCompatActivity {
    private TaskToDo task;
    private TaskManager taskManager;
    private EditText taskTitleEditTxt;
    private EditText taskNotesEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_details);

        //Create task and task manager object
        task = new TaskToDo("", "");
        taskManager = TaskManager.getInstance();

        //Display action bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Add Task");
        ab.setDisplayHomeAsUpEnabled(true);

        //Add priorities to spinner
        Spinner s = findViewById(R.id.taskPrioritySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, task.getPriorities());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        //Get the text from user input
        taskTitleEditTxt = findViewById(R.id.taskTitleLabel);
        taskNotesEditTxt = findViewById(R.id.taskNotesLabel);

        //Call save task button
        saveTaskBtnClicked();
    }

    //When save button is clicked, checks to see if title is provided or not
    //Will save the task if title is not empty
    private void saveTaskBtnClicked(){
        Button btn = findViewById(R.id.saveTaskBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SaveTaskButton", "Button clicked");
                taskTitleEditTxt = findViewById(R.id.taskTitleLabel);
                taskNotesEditTxt = findViewById(R.id.taskNotesLabel);
                if (taskTitleEditTxt.getText().toString().isEmpty() == true){
                    Toast.makeText(NewTask.this,"Title can't be empty", Toast.LENGTH_LONG).show();
                }
                else{
                    task.setTitle(taskTitleEditTxt.getText().toString());
                    task.setNotes(taskNotesEditTxt.getText().toString());
                    taskManager.addTask(task);
                    finish();
                }
            }
        });
    }

    //Makes an intent and returns it
    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, NewTask.class);
        return intent;
    }

}

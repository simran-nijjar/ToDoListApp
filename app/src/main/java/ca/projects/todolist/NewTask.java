package ca.projects.todolist;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
        task = new TaskToDo("", "");
        taskManager = TaskManager.getInstance();
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Add Task");
        ab.setDisplayHomeAsUpEnabled(true);
        Spinner s = findViewById(R.id.taskPrioritySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, task.priority);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        saveTaskBtnClicked();
    }

    private void saveTaskBtnClicked(){
        Button btn = findViewById(R.id.saveTaskBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskTitleEditTxt = findViewById(R.id.taskTitleLabel);
                taskNotesEditTxt = findViewById(R.id.taskNotesLabel);
                if (taskTitleEditTxt.getText().toString().isEmpty() == true){
                    Toast.makeText(NewTask.this,"Title can't be empty", Toast.LENGTH_LONG).show();
                }
                else{
                    task.SetTitle(taskTitleEditTxt.getText().toString());
                    task.SetNotes(taskNotesEditTxt.getText().toString());
                    taskManager.addTask(task);
                    finish();
                    return;
                }
            }
        });
    }

}

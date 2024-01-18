package ca.projects.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ca.projects.todolist.Models.SaveUsingGson;
import ca.projects.todolist.Models.TaskManager;
import ca.projects.todolist.Models.TaskToDo;

//This class displays the details of the selected completed task
public class CompletedTaskDetails extends AppCompatActivity {
    private TaskToDo task;
    private SaveUsingGson toSaveUsingGsonAndSP = new SaveUsingGson();

    private TextView taskTitleTxt;
    private TextView taskNotesTxt;
    private TextView taskPriorityTxt;
    private TextView taskDateCreatedTxt;
    private TextView taskDateCompletedTxt;

    private String taskTitle;
    private String taskNotes;
    private String taskPriority;
    private String taskDateCreated;
    private String taskDateCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_task_details);
        //Create task manager object
        getCompletedTaskDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCompletedTaskDetails();
        //To save config manager
        toSaveUsingGsonAndSP.saveToSharedRefs(CompletedTaskDetails.this);
    }

    //Makes an intent and returns it
    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, CompletedTaskDetails.class);
        return intent;
    }

    //Gets completed task details
    private void getCompletedTaskDetails() {
        TaskManager taskManager = TaskManager.getInstance();

        //Get the text from user input
        taskTitleTxt = findViewById(R.id.completedTaskTitleDetails);
        taskNotesTxt = findViewById(R.id.completedTaskNotesDetails);
        taskPriorityTxt = findViewById(R.id.completedTaskPriorityDetails);
        taskDateCreatedTxt = findViewById(R.id.completedTaskDateCreatedDetails);
        taskDateCompletedTxt = findViewById(R.id.completedTaskDateCompletedDetails);

        //Display action bar
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Completed Task Details");
        //Get the position of the task selected
        int currentPosition = taskManager.getIndexOfCurrentCompletedTask();
        task = taskManager.getCompletedTaskAtIndex(currentPosition);

        //Get the string values of the task selected
        taskTitle = String.valueOf(task.getTitle());
        taskNotes = String.valueOf(task.getNotes());
        taskPriority = String.valueOf(task.getPriority());
        taskDateCreated = String.valueOf(task.getDateCreated());
        taskDateCompleted = String.valueOf(task.getDateCompleted());

        //Add the string value of task selected to the text fields
        taskTitleTxt.setText(taskTitle);
        taskNotesTxt.setText(taskNotes);
        taskPriorityTxt.setText(taskPriority);
        taskDateCreatedTxt.setText(taskDateCreated);
        taskDateCompletedTxt.setText(taskDateCompleted);

        //To save config manager
        toSaveUsingGsonAndSP.saveToSharedRefs(CompletedTaskDetails.this);
    }
}

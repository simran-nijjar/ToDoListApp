package ca.projects.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ca.projects.todolist.Models.SaveUsingGson;
import ca.projects.todolist.Models.TaskManager;
import ca.projects.todolist.Models.TaskToDo;

//This class displays the individual task details when the user is adding or editing a task
public class TaskDetails extends AppCompatActivity {
    private TaskToDo task;
    private SaveUsingGson toSaveUsingGsonAndSP = new SaveUsingGson();

    private EditText taskTitleEditTxt;
    private EditText taskNotesEditTxt;
    private Spinner prioritySpinner;
    private TextView taskDateCreatedEditTxt;
    private TextView dateCreatedTitleTxt;

    private String taskTitle;
    private String taskNotes;
    private String taskPriority;
    private String taskDateCreated;

    private final int MAX_TITLE_LENGTH = 1000;
    private final int MAX_NOTES_LENGTH = 4000;

    public static final String EXTRA_TITLE = "ca.projects.todolist.NewTask - Title";
    public static final String EXTRA_NOTES = "ca.projects.todolist.NewTask - Notes";
    public static final String EXTRA_PRIORITY = "ca.projects.todolist.NewTask - Priority";
    public static final String EXTRA_DATE_CREATED = "ca.projects.todolist.NewTask - Date Created";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_details);
        //Create task manager object
        getTaskDetails();
        updateUI();
        //Call save task button
        saveTaskBtnClicked();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTaskDetails();
        //To save config manager
        toSaveUsingGsonAndSP.saveToSharedRefs(TaskDetails.this);
    }

    //Gets task details
    private void getTaskDetails() {
        TaskManager taskManager = TaskManager.getInstance();

        //Get the text from user input
        taskTitleEditTxt = findViewById(R.id.taskTitleInput);
        taskNotesEditTxt = findViewById(R.id.taskNotesInput);
        taskDateCreatedEditTxt = findViewById(R.id.dateCreatedTxt);
        dateCreatedTitleTxt = findViewById(R.id.dateCreatedTitleTxt);

        //Add priorities to spinner
        prioritySpinner = findViewById(R.id.taskPrioritySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, taskManager.getPriorities());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);

        //Display action bar
        ActionBar ab = getSupportActionBar();
        Bundle b = getIntent().getExtras();
        //If task is being edited
        if (b != null){
            ab.setTitle("Edit Task Details");
            //Get the position of the task selected
            int selectedTaskPosition = b.getInt(getString(R.string.selected_task_position));
            int currentPosition = taskManager.getIndexofCurrentTask();
            task = taskManager.getTaskAtIndex(currentPosition);

            //Get the string values of the task selected
            taskTitle = String.valueOf(task.getTitle());
            taskNotes = String.valueOf(task.getNotes());
            taskPriority = String.valueOf(task.getPriority());
            taskDateCreated = String.valueOf(task.getDateCreated());
            dateCreatedTitleTxt.setVisibility(View.VISIBLE);
            taskDateCreatedEditTxt.setVisibility(View.VISIBLE);
            taskDateCreatedEditTxt.setText(taskDateCreated);

            //Add the string value of task selected to the input fields
            taskTitleEditTxt.setText(taskTitle);
            taskNotesEditTxt.setText(taskNotes);
            prioritySpinner.setSelection(task.getPriorityPosition(taskPriority));
            //Display delete and complete task buttons
            displayDeleteTaskBtn(currentPosition);
            displayCompleteTaskBtn(currentPosition);
        }
        //Else if new task is being added
        else {
            ab.setTitle("Add Task Details");
            task = new TaskToDo("", "", "");
            //Hide delete and complete task button and date task is created
            Button deleteBtn = findViewById(R.id.deleteTaskBtn);
            deleteBtn.setVisibility(View.INVISIBLE);
            Button completeBtn = findViewById(R.id.completeTaskBtn);
            completeBtn.setVisibility(View.INVISIBLE);
            dateCreatedTitleTxt.setVisibility(View.INVISIBLE);
            taskDateCreatedEditTxt.setVisibility(View.INVISIBLE);
        }
        //to save config manager
        toSaveUsingGsonAndSP.saveToSharedRefs(TaskDetails.this);
    }

    //Add text watcher for title and notes
    private void updateUI() {
        taskTitleEditTxt.addTextChangedListener(textWatcher);
        taskNotesEditTxt.addTextChangedListener(textWatcher);}

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //Display alert dialog if title or notes exceed max allowed characters
            if (taskTitleEditTxt.getText().toString().length() > MAX_TITLE_LENGTH){
                displayMaxCharactersMessage("title");
            }
            if (taskNotesEditTxt.getText().toString().length() > MAX_NOTES_LENGTH){
                displayMaxCharactersMessage("notes");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //Displays alerts when title or notes exceeds max allowed characters
    private void displayMaxCharactersMessage(String input) {
            AlertDialog alertDialog = new AlertDialog.Builder(TaskDetails.this).create();
            if (input.equals("title")) {
                alertDialog.setTitle(getString(R.string.title_too_long));
                alertDialog.setMessage(getString(R.string.sorry_title_too_long));
                taskTitleEditTxt.setText(taskTitleEditTxt.getText().toString().substring(0, taskTitleEditTxt.getText().toString().length() - 1));
            }
            else if (input.equals("notes")){
                alertDialog.setTitle(getString(R.string.notes_too_long));
                alertDialog.setMessage(getString(R.string.sorry_notes_too_long));
                taskNotesEditTxt.setText(taskNotesEditTxt.getText().toString().substring(0, taskNotesEditTxt.getText().toString().length() - 1));
            }
            alertDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //Stay on ViewAchievement activity
                }
            });
            alertDialog.show();
    }

    //When save button is clicked, checks to see if title is provided or not
    //Will save the task if title is not empty
    private void saveTaskBtnClicked(){
        Button btn = findViewById(R.id.saveTaskBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskTitleEditTxt = findViewById(R.id.taskTitleInput);
                taskNotesEditTxt = findViewById(R.id.taskNotesInput);
                prioritySpinner = findViewById(R.id.taskPrioritySpinner);

                taskTitle = taskTitleEditTxt.getText().toString();
                taskNotes = taskNotesEditTxt.getText().toString();
                taskPriority = prioritySpinner.getSelectedItem().toString();

                if (taskTitle.isEmpty()){
                    //Display error message
                    Toast.makeText(TaskDetails.this,"Title can't be empty", Toast.LENGTH_LONG).show();
                }
                else{
                    saveTaskDetails();
                }
            }
        });
    }

    //Saves task details to same position if task is being edited, else adds new task
    private void saveTaskDetails(){
        Bundle b = getIntent().getExtras();
        //If task is being edited
        if (b != null){
            //Get the position of the task in the list view
            int selectedTaskPosition = b.getInt(getString(R.string.selected_task_position));
            TaskManager taskManager = TaskManager.getInstance();
            task = taskManager.getTaskAtIndex(selectedTaskPosition);
            Toast.makeText(TaskDetails.this, "Task saved", Toast.LENGTH_SHORT).show();
            //Update the task details
            task.setTitle(taskTitle);
            task.setNotes(taskNotes);
            task.setPriority(prioritySpinner.getSelectedItem().toString());
            //Replace the old task with the edited task
            taskManager.addTaskToIndex(task, selectedTaskPosition);
        }
        //Else if task is a new task
        else {
            //Set the entered values for the task
            task = new TaskToDo(taskTitle,taskNotes, taskPriority);
            TaskManager taskManager = TaskManager.getInstance();
            Toast.makeText(TaskDetails.this, "Task saved", Toast.LENGTH_SHORT).show();
            //Add new task
            taskManager.addTask(task);
            Intent intent = new Intent(TaskDetails.this, MainPage.class);
            intent.putExtra(EXTRA_TITLE, taskTitle);
            intent.putExtra(EXTRA_NOTES, taskNotes);
            intent.putExtra(EXTRA_PRIORITY, taskPriority);
            intent.putExtra(EXTRA_DATE_CREATED, task.getDateCreated());
            startActivity(intent);
        }
        finish();
        toSaveUsingGsonAndSP.saveToSharedRefs(TaskDetails.this);
    }

    //Display delete button for when task is being edited
    private void displayDeleteTaskBtn(int position){
        Button btn = findViewById(R.id.deleteTaskBtn);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Alert dialog to confirm with user if task should be deleted
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskDetails.this);
                builder.setMessage(getString(R.string.delete_task_msg))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.delete), (dialog, id) -> {
                            TaskManager taskManager = TaskManager.getInstance();
                            taskManager.deleteTask(position);
                            toSaveUsingGsonAndSP.saveToSharedRefs(TaskDetails.this);
                            Intent intent = new Intent(TaskDetails.this, MainPage.class);
                            startActivity(intent);

                        })
                        .setNegativeButton(getString(R.string.cancel), (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    //Display complete task button for when task is being edited
    private void displayCompleteTaskBtn(int position){
        Button btn = findViewById(R.id.completeTaskBtn);
        btn.setVisibility(View.VISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Alert dialog to confirm with user if task should be marked as completed
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskDetails.this);
                builder.setMessage(getString(R.string.complete_task_msg))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.complete), (dialog, id) -> {
                            TaskManager taskManager = TaskManager.getInstance();
                            taskManager.setAnyTasksCompleted(true);
                            //Add task to completed task list
                            TaskToDo task = taskManager.getTaskAtIndex(position);
                            task.setDateCompleted();
                            taskManager.addCompletedTask(task);
                            //Remove task from to do list
                            taskManager.deleteTask(position);
                            toSaveUsingGsonAndSP.saveToSharedRefs(TaskDetails.this);
                            Intent intent = new Intent(TaskDetails.this, MainPage.class);
                            startActivity(intent);

                        })
                        .setNegativeButton(getString(R.string.cancel), (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    //Makes an intent and returns it
    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, TaskDetails.class);
        return intent;
    }
}

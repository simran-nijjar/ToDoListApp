package ca.projects.todolist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class TaskDetails extends AppCompatActivity {
    private TaskToDo task;
    private TaskManager taskManager;

    private EditText taskTitleEditTxt;
    private EditText taskNotesEditTxt;
    private Spinner prioritySpinner;

    private String taskTitle;
    private String taskNotes;
    private String taskPriority;

    private final int MAX_TITLE_LENGTH = 1000;
    private final int MAX_NOTES_LENGTH = 4000;

    public static final String EXTRA_TITLE = "ca.projects.todolist.NewTask - Title";
    public static final String EXTRA_NOTES = "ca.projects.todolist.NewTask - Notes";
    public static final String EXTRA_PRIORITY = "ca.projects.todolist.NewTask - Priority";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_details);

        //Create task manager object
        taskManager = TaskManager.getInstance();

        //Get the text from user input
        taskTitleEditTxt = findViewById(R.id.taskTitleInput);
        taskNotesEditTxt = findViewById(R.id.taskNotesInput);

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
            task = taskManager.getTaskAtIndex(selectedTaskPosition);

            //Get the string values of the task selected
            taskTitle = String.valueOf(task.getTitle());
            taskNotes = String.valueOf(task.getNotes());
            taskPriority = String.valueOf(task.getPriority());

            //Add the string value of task selected to the input fields
            taskTitleEditTxt.setText(taskTitle);
            taskNotesEditTxt.setText(taskNotes);
            prioritySpinner.setSelection(task.getPriorityPosition(taskPriority));
        }
        //Else if new task is being added
        else {
            ab.setTitle("Add Task Details");
            task = new TaskToDo("", "", "");
        }

        updateUI();
        //Call save task button
        saveTaskBtnClicked();
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
            if (input == "title") {
                alertDialog.setTitle(getString(R.string.title_too_long));
                alertDialog.setMessage(getString(R.string.sorry_title_too_long));
                taskTitleEditTxt.setText(taskTitleEditTxt.getText().toString().substring(0, taskTitleEditTxt.getText().toString().length() - 1));
            }
            else if (input == "notes"){
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

                if (taskTitle.isEmpty() == true){
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
            task = taskManager.getTaskAtIndex(selectedTaskPosition);
            Toast.makeText(TaskDetails.this, "Task saved", Toast.LENGTH_SHORT).show();
            //Update the task details
            task.setTitle(taskTitle);
            task.setPriority(taskPriority);
            task.setTitle(taskTitle);
            //Replace the old task with the edited task
            taskManager.addTaskToIndex(task, selectedTaskPosition);
        }
        //Else if task is a new task
        else {
            //Set the entered values for the task
            task = new TaskToDo(taskTitle,taskNotes, taskPriority);
            Toast.makeText(TaskDetails.this, "Task saved", Toast.LENGTH_SHORT).show();
            //Add new task
            taskManager.addTask(task);
            Intent intent = new Intent(TaskDetails.this, MainPage.class);
            intent.putExtra(EXTRA_TITLE, taskTitle);
            intent.putExtra(EXTRA_NOTES, taskNotes);
            intent.putExtra(EXTRA_PRIORITY, taskPriority);
            startActivity(intent);
        }
        finish();
    }

    //Makes an intent and returns it
    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, TaskDetails.class);
        return intent;
    }
}

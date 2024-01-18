package ca.projects.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ca.projects.todolist.Models.SaveUsingGson;
import ca.projects.todolist.Models.TaskManager;
import ca.projects.todolist.Models.TaskToDo;

//This class displays all of the tasks the user has completed
public class CompletedTasks extends AppCompatActivity {
    //Create task manager object to manage tasks
    private TaskManager taskManager = TaskManager.getInstance();
    private SaveUsingGson toSaveUsingGsonAndSP = new SaveUsingGson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCompletedTasksDetails();
        //Retrieve config manager
        toSaveUsingGsonAndSP.retrieveFromSharedPrefs(this);
        //To save config manager
        toSaveUsingGsonAndSP.saveToSharedRefs(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCompletedTasksDetails();
        //To save config manager
        toSaveUsingGsonAndSP.saveToSharedRefs(this);
    }

    //Gets details for completed tasks page
    private void getCompletedTasksDetails() {
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Completed Tasks");
        //Display empty page if no tasks have been completed
        if (taskManager.getCompletedTasksSize() == 0){
            setContentView(R.layout.empty_completed_tasks);
        }
        //Else display the list of completed tasks
        else {
            setContentView(R.layout.completed_tasks);
            populateCompletedTasksListView();
            registerClickCallBack();
            addSortBySpinnerDetails();
        }
    }

    //Makes an intent and returns it
    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, CompletedTasks.class);
        return intent;
    }

    //Populates list view with tasks if there are any
    private void populateCompletedTasksListView(){
        ArrayAdapter<TaskToDo> adapter = new MyListAdapter();
        ListView list = findViewById(R.id.completedTasksListView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<TaskToDo> {
        public MyListAdapter() {
            super(CompletedTasks.this, R.layout.completed_tasks, taskManager.getListOfCompletedTasks());
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //Make a view
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.task_list, parent, false);
            }
            TaskToDo currentTask = taskManager.getCompletedTaskAtIndex(position);
            //Add task details to item in list view
            TextView makeText = itemView.findViewById(R.id.task_name_txt);
            String taskName = currentTask.getTitle() + "\nCompleted: " + currentTask.getDateCompleted() + "\n";
            makeText.setText(taskName);

            return itemView;
        }
    }

    //Listens for when user clicks an item on the list view
    private void registerClickCallBack(){
        ListView list = findViewById(R.id.completedTasksListView);
        list.setOnItemClickListener((parent, viewClicked, position, id) -> {
            taskManager.setIndexOfCurrentCompletedTask(position);
            //Make an intent for viewing completed tasks
            Intent intent = CompletedTaskDetails.makeIntent(CompletedTasks.this);
            intent.putExtra(getString(R.string.selected_completed_task_position), position);
            startActivity(intent);
        });
    }

    //Add details to spinner to sort tasks alphabetically, by priority, or date completed
    private void addSortBySpinnerDetails() {
        //Add spinner options
        Spinner sortBySpinner = findViewById(R.id.sortByCompletedTasksSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.sort_completed_options,  // Define string array in resources/values/arrays.xml
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(spinnerAdapter);
        sortBySpinner.setSelection(taskManager.getIndexOfSortOption());

        sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Handle sorting based on selected option
                String selectedOption = (String) parentView.getItemAtPosition(position);
                if ("Alphabetical".equals(selectedOption)) {
                    taskManager.sortTasksAlphabetically(taskManager.getListOfCompletedTasks());
                } else if ("Priority".equals(selectedOption)) {
                    taskManager.sortTasksByPriority(taskManager.getListOfCompletedTasks());
                } else if ("Date Completed".equals(selectedOption)){
                    taskManager.sortTasksByDateCompleted(taskManager.getListOfCompletedTasks());
                }
                //Update the ListView
                ListView list = findViewById(R.id.completedTasksListView);
                ArrayAdapter<TaskToDo> adapter = (ArrayAdapter<TaskToDo>) list.getAdapter();
                adapter.notifyDataSetChanged();
                taskManager.setIndexOfSortOption(sortBySpinner.getSelectedItemPosition());
                toSaveUsingGsonAndSP.saveToSharedRefs(CompletedTasks.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Do nothing
            }
        });
    }
}

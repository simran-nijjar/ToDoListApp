package ca.projects.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import ca.projects.todolist.Models.SaveUsingGson;
import ca.projects.todolist.Models.TaskManager;
import ca.projects.todolist.Models.TaskToDo;

public class MainPage extends AppCompatActivity {
    //Create task manager object to manage tasks
    private TaskManager taskManager = TaskManager.getInstance();
    private SaveUsingGson toSaveUsingGsonAndSP = new SaveUsingGson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retrieve config manager
        toSaveUsingGsonAndSP.retrieveFromSharedPrefs(this);
        //Get details of main page
        getMainPageDetails();
        registerClickCallBack();
        //To save config manager
        toSaveUsingGsonAndSP.saveToSharedRefs(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Get details of main page
        getMainPageDetails();
        registerClickCallBack();
        //To save config manager
        toSaveUsingGsonAndSP.saveToSharedRefs(this);
    }

    //Gets details for main page
    private void getMainPageDetails() {
        //Display empty page if task manager is empty
        if (taskManager.getTaskManagerSize() == 0){
            setContentView(R.layout.empty_to_do_list);
            Log.d("MainPage", "No tasks to display");
        }
        //Else display the list of tasks
        else {
            setContentView(R.layout.activity_main);
            populateTasksListView();
            Log.d("MainPage", "Tasks are present. Number of tasks: " + taskManager.getTaskManagerSize());
        }
        addNewTaskBtnClicked();
    }

    //Button to add new tasks
    private void addNewTaskBtnClicked(){
        Button btn = findViewById(R.id.addTaskBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = TaskDetails.makeIntent(MainPage.this);
                startActivity(intent);
            }
        });
    }

    //Populates list view with tasks if there are any
    private void populateTasksListView(){
        ArrayAdapter<TaskToDo> adapter1 = new MyListAdapter();
        ListView list = findViewById(R.id.tasksListView);
        list.setAdapter(adapter1);
    }

    private class MyListAdapter extends ArrayAdapter<TaskToDo> {
        public MyListAdapter() {
            super(MainPage.this, R.layout.task_list, taskManager.getListOfTasks());
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            //makes a view to work with
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.task_list, parent, false);
            }
            TaskToDo currentTask = taskManager.getTaskAtIndex(position);
            //file the textView
            TextView makeText = itemView.findViewById(R.id.task_name_txt);
            String taskName = currentTask.getPriorityExclamation() + " " + currentTask.getTitle() + "\n";
            makeText.setText(taskName);

            return itemView;
        }
    }

    //Listens for when user clicks an item on the list view
    private void registerClickCallBack(){
        ListView list = findViewById(R.id.tasksListView);
        list.setOnItemClickListener((parent, viewClicked, position, id) -> {
            taskManager.setIndexofCurrentTask(position);
            //make an intent for view configuration activity
            Intent intent = TaskDetails.makeIntent(MainPage.this);
            intent.putExtra(getString(R.string.selected_task_position), position);
            startActivity(intent);
        });
    }
}


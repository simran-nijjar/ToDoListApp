package ca.projects.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import ca.projects.todolist.Models.SaveUsingGson;
import ca.projects.todolist.Models.TaskManager;
import ca.projects.todolist.Models.TaskToDo;

public class CompletedTasks extends AppCompatActivity {
    //Create task manager object to manage tasks
    private TaskManager taskManager = TaskManager.getInstance();
    private SaveUsingGson toSaveUsingGsonAndSP = new SaveUsingGson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_tasks);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Completed Tasks");
        populateCompletedTasksListView();
        registerClickCallBack();
        //Retrieve config manager
        toSaveUsingGsonAndSP.retrieveFromSharedPrefs(this);
        //To save config manager
        toSaveUsingGsonAndSP.saveToSharedRefs(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateCompletedTasksListView();
        registerClickCallBack();
        //To save config manager
        toSaveUsingGsonAndSP.saveToSharedRefs(this);
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
            super(CompletedTasks.this, R.layout.completed_tasks, taskManager.getListofCompletedTasks());
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
            String taskName = currentTask.getPriorityExclamation() + " " + currentTask.getTitle() + "\n";
            makeText.setText(taskName);

            return itemView;
        }
    }

    //Listens for when user clicks an item on the list view
    private void registerClickCallBack(){
        ListView list = findViewById(R.id.completedTasksListView);
        list.setOnItemClickListener((parent, viewClicked, position, id) -> {
            taskManager.setIndexofCurrentCompletedTask(position);
            //make an intent for view configuration activity
            Intent intent = CompletedTaskDetails.makeIntent(CompletedTasks.this);
            intent.putExtra(getString(R.string.selected_completed_task_position), position);
            startActivity(intent);
        });
    }
}

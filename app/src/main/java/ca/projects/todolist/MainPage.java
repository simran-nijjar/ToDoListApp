package ca.projects.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import ca.projects.todolist.Models.TaskManager;
import ca.projects.todolist.Models.TaskToDo;

public class MainPage extends AppCompatActivity {
    //Create task manager object to manage tasks
    private TaskManager taskManager;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get instance of task manager
        taskManager = TaskManager.getInstance();
        //Display empty page if task manager is empty
        if (taskManager.getTaskManagerSize() == 0){
            setContentView(R.layout.empty_to_do_list);
        }
        //Else display the list of tasks
        else {
            setContentView(R.layout.activity_main);
            populateTasksListView();
        }
        addNewTaskBtnClicked();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Display empty page if task manager is empty
        if (taskManager.getTaskManagerSize() == 0){
            setContentView(R.layout.empty_to_do_list);
        }
        //Else display the list of tasks
        else {
            setContentView(R.layout.activity_main);
            populateTasksListView();
            registerClickCallBack();
        }
        addNewTaskBtnClicked();
    }

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

    private void populateTasksListView(){
        String strTasks[] = new String[taskManager.getTaskManagerSize()];
        int count = 0;
        //Get tasks into a string array
        for (TaskToDo task: taskManager){
            strTasks[count] = task.getTitle();
            count++;
        }

        //If there is at least one task, display the task(s) in the list view
        if (count > 0){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this,
                    R.layout.list_of_tasks,
                    strTasks);
            //Display the games in the list
            list = findViewById(R.id.tasksListView);
            list.setAdapter(adapter);
        }
    }

    //Listens for when user clicks an item on the list view
    private void registerClickCallBack(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                taskManager.setIndexofCurrentTask(position);
                Intent intent = new Intent(MainPage.this, TaskDetails.class);
                intent.putExtra(getString(R.string.selected_task_position), position);
                startActivity(intent);
            }
        });
    }
}


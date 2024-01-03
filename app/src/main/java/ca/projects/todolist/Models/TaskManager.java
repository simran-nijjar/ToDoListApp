package ca.projects.todolist.Models;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

//This class stores the collection of tasks
public class TaskManager implements Iterable<TaskToDo> {
    private List<TaskToDo> taskCollection = new ArrayList<>();
    private static TaskManager instance;
    private int indexofCurrentTask;
    public String priorities[] = {"Low", "Medium", "High"};

    //Constructor
    private TaskManager(){
    }

    //Gets singleton
    public static TaskManager getInstance(){
        if (instance == null){
            instance = new TaskManager();
        }
        return instance;
    }

    //Adds a task to the to do list
    public synchronized void addTask(TaskToDo task){
        taskCollection.add(task);
    }

    //Removes a task from the to do list
    public synchronized void deleteTask(int index){
            taskCollection.remove(index);
    }

    //Returns size of task manager
    public Integer getTaskManagerSize(){
        return taskCollection.size();
    }

    //Returns task at specific index
    public TaskToDo getTaskAtIndex(Integer index){
        return taskCollection.get(index);
    }

    //Gets index of the current task
    public int getIndexofCurrentTask(){
        return indexofCurrentTask;
    }

    //Sets the index of the selected task from the list view
    public void setIndexofCurrentTask(int indexofCurrentTask){
        this.indexofCurrentTask = indexofCurrentTask;
    }

    //Adds task to index given
    public void addTaskToIndex(TaskToDo task, int index){
        taskCollection.set(index, task);
    }

    //Returns the priorities array
    public String[] getPriorities(){return this.priorities;}

    //Returns the list of tasks
    public List<TaskToDo> getListOfTasks() {return taskCollection;}

    //Sets the list of tasks
    public void setListOfTasks(List<TaskToDo> newList) {this.taskCollection = newList;}

    //Iterator to iterate through list
    @NonNull
    @Override
    public Iterator<TaskToDo> iterator() {
        return taskCollection.listIterator();
    }
}

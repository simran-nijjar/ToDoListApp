package ca.projects.todolist.Models;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

//This class stores the collection of tasks
public class TaskManager implements Iterable<TaskToDo> {
    private int index;
    private List<TaskToDo> taskCollection = new ArrayList<>();
    private static TaskManager instance;

    //Contructor
    public TaskManager(){
        this.index = 0;
    }

    //Gets singleton
    public static TaskManager getInstance(){
        if (instance == null){
            instance = new TaskManager();
        }
        return instance;
    }

    //Adds a task to the to do list
    public void addTask(TaskToDo task){
        taskCollection.add(task);
        index++;
    }

    //Removes a task from the to do list
    public void deleteTask(int index){
        taskCollection.remove(index);
        index--;
    }

    //Returns true if there are no tasks, false otherwise
    public boolean isTaskManagerEmpty(){
        if (taskCollection.isEmpty()){
            return true;
        }
        return false;
    }

    //Iterator to iterate through list
    @NonNull
    @Override
    public Iterator<TaskToDo> iterator() {
        return null;
    }
}

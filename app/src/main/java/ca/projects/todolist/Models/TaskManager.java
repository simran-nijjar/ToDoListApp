package ca.projects.todolist.Models;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

//This class stores the collection of tasks
public class TaskManager implements Iterable<TaskToDo> {
    private int size;
    private List<TaskToDo> taskCollection = new ArrayList<>();
    private static TaskManager instance;

    //Constructor
    private TaskManager(){
        this.size = 0;
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
        size++;
    }

    //Removes a task from the to do list
    public void deleteTask(int index){
        taskCollection.remove(index);
        size--;
    }

    //Returns size of task manager
    public Integer getTaskManagerSize(){
        return this.size;
    }

    //Iterator to iterate through list
    @NonNull
    @Override
    public Iterator<TaskToDo> iterator() {
        return taskCollection.listIterator();
    }
}

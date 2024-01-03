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
    private int indexofCurrentTask;
    public String priorities[] = {"Low", "Medium", "High"};

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

    //Returns task at specific index
    public TaskToDo getTaskAtIndex(Integer index){
        return taskCollection.get(index);
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

package ca.projects.todolist.Models;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

//This class stores the collection of tasks
public class TaskManager implements Iterable<TaskToDo> {
    private List<TaskToDo> taskCollection = new ArrayList<>();
    private List<TaskToDo> completedTaskCollection = new ArrayList<>();
    private static TaskManager instance;
    private int indexOfCurrentTask;
    private int indexOfSortOption;
    public String priorities[] = {"Low", "Medium", "High"};
    public boolean anyTasksCompleted = false;
    private int indexOfCurrentCompletedTask;

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

    //Adds a task to the completed task list
    public synchronized void addCompletedTask(TaskToDo task) {completedTaskCollection.add(task);}

    //Returns size of task manager
    public Integer getTaskManagerSize(){
        return taskCollection.size();
    }

    public Integer getCompletedTasksSize(){ return completedTaskCollection.size();}

    //Returns task at specific index
    public TaskToDo getTaskAtIndex(Integer index){
        return taskCollection.get(index);
    }

    //Returns completed task at specific index
    public TaskToDo getCompletedTaskAtIndex(Integer index){
        return completedTaskCollection.get(index);
    }

    //Gets index of the current task
    public int getIndexOfCurrentTask(){
        return indexOfCurrentTask;
    }

    //Gets index of the selected sort option
    public int getIndexOfSortOption(){ return indexOfSortOption; }

    //Sets the index of the selected task from the list view
    public void setIndexOfCurrentTask(int indexOfCurrentTask){
        this.indexOfCurrentTask = indexOfCurrentTask;
    }

    //Sets the index of the selected sort option
    public void setIndexOfSortOption(int indexOfSortOption){
        this.indexOfSortOption = indexOfSortOption;
    }

    //Returns boolean of any tasks completed
    public boolean getAreAnyTasksCompleted() { return anyTasksCompleted;}

    //Sets boolean to true if any tasks are completed
    public void setAnyTasksCompleted(boolean anyTasksCompleted) {
        this.anyTasksCompleted = anyTasksCompleted;
    }

    //Adds task to index given
    public void addTaskToIndex(TaskToDo task, int index){
        taskCollection.set(index, task);
    }

    //Sets the index of the selected completed task from the list view
    public void setIndexOfCurrentCompletedTask(int indexOfCurrentCompletedTask){
        this.indexOfCurrentCompletedTask = indexOfCurrentCompletedTask;
    }

    //Gets index of the current completed task
    public int getIndexOfCurrentCompletedTask(){ return indexOfCurrentCompletedTask;}

    //Returns the priorities array
    public String[] getPriorities(){return this.priorities;}

    //Returns the list of tasks
    public List<TaskToDo> getListOfTasks() {return taskCollection;}

    //Sets the list of tasks
    public void setListOfTasks(List<TaskToDo> newList) {this.taskCollection = newList;}

    //Sets the list of completed tasks
    public void setListOfCompletedTasks(List<TaskToDo> newList) {this.completedTaskCollection = newList;}

    //Returns the list of completed tasks
    public List<TaskToDo> getListOfCompletedTasks() {return completedTaskCollection;}

    //Sorts items in to do list alphabetically
    public void sortTasksAlphabetically(List<TaskToDo> collection) {
        Collections.sort(collection, new Comparator<TaskToDo>() {
            @Override
            public int compare(TaskToDo task1, TaskToDo task2) {
                return task1.getTitle().compareToIgnoreCase(task2.getTitle());
            }
        });
    }

    //Sorts items in to do list by priority
    public void sortTasksByPriority(List<TaskToDo> collection){
        Collections.sort(collection, new Comparator<TaskToDo>() {
            @Override
            public int compare(TaskToDo task1, TaskToDo task2) {
                return task2.getPriorityPosition(task2.getPriority()).compareTo(task1.getPriorityPosition(task1.getPriority()));
            }
        });
    }

    //Sorts items in to do list by date created
    public void sortTasksByDateCreated(List<TaskToDo> collection){
        Collections.sort(collection, new Comparator<TaskToDo>() {
            @Override
            public int compare(TaskToDo task1, TaskToDo task2) {
                return task1.getDateCreated().compareTo(task2.getDateCreated());
            }
        });
    }

    //Sorts items in to do list by date created
    public void sortTasksByDateCompleted(List<TaskToDo> collection){
        Collections.sort(collection, new Comparator<TaskToDo>() {
            @Override
            public int compare(TaskToDo task1, TaskToDo task2) {
                return task1.getDateCompleted().compareTo(task2.getDateCompleted());
            }
        });
    }

    //Clears completed tasks
    public void clearCompletedTasks(){completedTaskCollection.clear();}

    //Iterator to iterate through list
    @NonNull
    @Override
    public Iterator<TaskToDo> iterator() {
        return taskCollection.listIterator();
    }
}

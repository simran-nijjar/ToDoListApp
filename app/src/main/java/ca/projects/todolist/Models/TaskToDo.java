package ca.projects.todolist.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//This class stores the details of each task
public class TaskToDo {
    private String title;
    private String notes;
    private String priority;
    private String dateCreated;
    public String priorities[] = {"Low", "Medium", "High"};
    private boolean isTaskCompleted;
    private String dateCompleted;

    //Constructor
    public TaskToDo(String title, String notes, String priority){
        this.title = title;
        this.notes = notes;
        this.priority = priority;
        this.dateCreated = getCurrentDate();
        this.isTaskCompleted = false;
    }

    //Getter function for the title of the task
    public String getTitle(){
        return this.title;
    }

    //Getter function for the notes of the task
    public String getNotes(){
        return this.notes;
    }

    //Gets the selected priority
    public String getPriority(){ return this.priority;}

    //Gets the date the task was created
    public String getDateCreated() { return this.dateCreated;}

    //Gets the date the task was completed
    public String getDateCompleted(){ return this.dateCompleted;}

    //Gets the current date and time
    public String getCurrentDate(){
        //Create the correct format
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MMM d, y @ h:mma");
        //Get the current date
        LocalDateTime currentDate = LocalDateTime.now();
        String date = currentDate.format(formatDate);
        return date;
    }

    //Gets the position of the priority in the priorities array
    public Integer getPriorityPosition(String priority){
        for (int i = 0; i< priorities.length; i++){
            if (priority.equals(priorities[i])){
                return i;
            }
        }
        return -1;
    }

    //Returns the exclamation value for priority
    //! = low, !! = medium, !!! = high
    public String getPriorityExclamation(){
        String priority = "";
        for (int i = 0; i< priorities.length; i++){
            priority += "!";
            if (getPriority().equals(priorities[i])){
                return priority;
            }
        }
        return priority;
    }

    //Gets task completion status
    public boolean getIsTaskCompleted(){return this.isTaskCompleted;}

    //Sets title of the task
    public void setTitle(String title){
        this.title = title;
    }

    //Sets notes of the task
    public void setNotes(String notes){
        this.notes = notes;
    }

    //Sets priority of the task
    public void setPriority(String priority){this.priority = priority;}

    //Sets date task is completed
    public void setDateCompleted(){
        this.dateCompleted = getCurrentDate();
        this.isTaskCompleted = true;
    }
}

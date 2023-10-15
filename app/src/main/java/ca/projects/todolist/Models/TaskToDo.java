package ca.projects.todolist.Models;

//This class stores the details of each task
public class TaskToDo {
    private String title;
    private String notes;
    private String priority;
    public String priorities[] = {"Low", "Medium", "High"};

    //Constructor
    public TaskToDo(String title, String notes){
        this.title = title;
        this.notes = notes;
    }

    //Getter function for the title of the task
    public String getTitle(){
        return this.title;
    }

    //Getter function for the notes of the task
    public String getNotes(){
        return this.notes;
    }

    //Returns priorities array
    public String[] getPriorities(){
        return priorities;
    }

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
}

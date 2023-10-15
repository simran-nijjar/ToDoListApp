package ca.projects.todolist.Models;

//This class stores the details of each task
public class TaskToDo {
    private String title;
    private String notes;
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

    public String[] getPriorities(){
        return priorities;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }
}

package ca.projects.todolist.Models;

//This class stores the details of each task
public class TaskToDo {
    private String title;
    private String notes;
    public String priority[] = {"low", "medium", "high"};

    //Constructor
    public TaskToDo(String title, String notes){
        this.title = title;
        this.notes = notes;
    }

    //Getter function for the title of the task
    public String GetTitle(){
        return this.title;
    }

    //Getter function for the notes of the task
    public String GetNotes(){
        return this.notes;
    }

    public void SetTitle(String title){
        this.title = title;
    }

    public void SetNotes(String notes){
        this.notes = notes;
    }
}

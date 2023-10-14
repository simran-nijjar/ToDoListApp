package ca.projects.todolist.Models;

//This class stores the details of each task
public class TaskToDo {
    private String title;
    private String description;
    private String priority[] = {"low", "medium", "high"};

    //Constructor
    public TaskToDo(String title, String description){
        this.title = title;
        this.description = description;
    }

    //Getter function for the title of the task
    public String GetTitle(){
        return this.title;
    }

    //Getter function for the description of the task
    public String GetDescription(){
        return this.description;
    }
}

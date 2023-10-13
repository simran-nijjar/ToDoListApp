package ca.projects.todolist.Models;

public class ItemToDo {
    private String title;
    private String description;
    private String priority[] = {"low", "medium", "high"};

    public ItemToDo(String title, String description){
        this.title = title;
        this.description = description;
    }

    public String GetTitle(){
        return this.title;
    }

    public String GetDescription(){
        return this.description;
    }
}

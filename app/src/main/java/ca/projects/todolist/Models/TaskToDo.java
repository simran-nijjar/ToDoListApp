package ca.projects.todolist.Models;

//This class stores the details of each task
public class TaskToDo {
    private String title;
    private String notes;
    private String priority;
    public String priorities[] = {"Low", "Medium", "High"};

    //Constructor
    public TaskToDo(String title, String notes, String priority){
        this.title = title;
        this.notes = notes;
        this.priority = priority;
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

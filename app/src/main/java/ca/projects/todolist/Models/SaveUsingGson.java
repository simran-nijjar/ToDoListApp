package ca.projects.todolist.Models;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import ca.projects.todolist.R;

public class SaveUsingGson {
    private Context context;
    private Gson gson = new Gson();
    private SharedPreferences newPrefs;
    private String json;
    private SharedPreferences.Editor editor;
    private TaskManager manager = TaskManager.getInstance();
    private Type listType = new TypeToken<List<TaskToDo>>() {}.getType();

    //To save config manager
    public void saveToSharedRefs(Context newContext){
        this.context = newContext;
        newPrefs = context.getSharedPreferences(context.getString(R.string.save_task_manager),MODE_PRIVATE);
        editor = newPrefs.edit();
        json = gson.toJson(manager.getListOfTasks());
        editor.putString(context.getString(R.string.my_object), json);
        editor.apply();
    }

    //Retrieve config manager
    public void retrieveFromSharedPrefs(Context newContext){
        this.context = newContext;
        newPrefs = context.getSharedPreferences(context.getString(R.string.save_task_manager),MODE_PRIVATE);
        editor = newPrefs.edit();
        json = newPrefs.getString(context.getString(R.string.my_object), context.getString(R.string.no_manager_saved));
        if (!Objects.equals(json, context.getString(R.string.no_manager_saved))) {
            manager.setListOfTasks(gson.fromJson(json, listType));
        }
    }
}

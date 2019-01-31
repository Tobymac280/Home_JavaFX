package com.projects.codepractitioner.POJO.TodoItem;

public class TodoItem {
    private String title;
    private String description;
    private boolean isCompleted;

    public TodoItem(String title, String description, boolean isCompleted){
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    // GETTERS & SETTERS
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof TodoItem){
            TodoItem other = (TodoItem)obj;
            if(title.equals(other.getTitle()) && description.equals(other.getDescription()) && (isCompleted == other.isCompleted())){
                return true; // they're the same object
            }
        }
        return false;
    }
}

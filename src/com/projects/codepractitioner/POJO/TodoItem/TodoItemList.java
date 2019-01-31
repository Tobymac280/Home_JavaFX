package com.projects.codepractitioner.POJO.TodoItem;

import java.util.ArrayList;

public class TodoItemList {
    private ArrayList<TodoItem> todoItems;

    public TodoItemList(){
        todoItems = new ArrayList<>();
    }

    public ArrayList<TodoItem> getTodoItems(){
        return clone(); // clone this instance and return it
    }

    /** Add an item. */
    public boolean addTodoItem(TodoItem newItem){
        for(TodoItem currentItem : todoItems){
            if(currentItem.equals(newItem)){
                return false; // cannot add the item
            }
        }
        todoItems.add(newItem);
        return true;
    }

    /** Remove an item. */
    public boolean removeTodoItem(TodoItem item){
        for(int i = 0; i < todoItems.size(); i++){
            if(todoItems.get(i).equals(item)){
                todoItems.remove(i); // remove the item
                return true;
            }
        }
        return false;
    }

    /** Set the whole thing equal to one list. */
    public void setTodoItems(ArrayList<TodoItem> list){
        todoItems = list;
    }

    public ArrayList<TodoItem> clone(){
        ArrayList<TodoItem> items = new ArrayList<>();
        items.addAll(todoItems);
        return items;
    }
}

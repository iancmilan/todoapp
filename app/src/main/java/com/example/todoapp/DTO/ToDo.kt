package com.example.todoapp.DTO

class ToDo {
    var id : Long = -1
    var name = ""
    var createdAt = ""
    var items : MutableList<ToDoItem> = ArrayList()
}
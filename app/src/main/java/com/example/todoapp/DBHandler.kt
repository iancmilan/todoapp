package com.example.todoapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.todoapp.DTO.ToDo
import com.example.todoapp.DTO.ToDoItem
import com.example.todoapp.DTO.Usuario
import java.lang.Exception

class DBHandler(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){
    override fun onCreate(db: SQLiteDatabase) {

        val createUsuarioTable = "CREATE TABLE $TABLE_USUARIO(" +
                "$COL_ID_USUARIO integer PRIMARY KEY AUTOINCREMENT, " +
                "$COL_EMAIL varchar(100)," +
                "$COL_SENHA varchar(100));"

        val createToDoTable = "  CREATE TABLE $TABLE_TODO (" +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_ID_TABLE_USUARIO integet "
                "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                "$COL_NAME varchar," +
                "FOREIGN KEY($COL_ID_TABLE_USUARIO) REFERENCES $TABLE_USUARIO($COL_ID_USUARIO)"

        val createToDoItemTable =
            "CREATE TABLE $TABLE_TODO_ITEM (" +
                    "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                    "$COL_CREATED_AT datetime DEFAULT CURRENT_TIMESTAMP," +
                    "$COL_TODO_ID integer," +
                    "$COL_ITEM_NAME varchar," +
                    "$COL_IS_COLPLETED integer);"

        db.execSQL(createToDoTable)
        db.execSQL(createToDoItemTable)
        db.execSQL(createUsuarioTable)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun addToDo(toDo : ToDo) : Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, toDo.name)
        val result = db.insert(TABLE_TODO, null, cv)
        return result!=(-1).toLong()
    }

    fun updateToDo(toDo : ToDo) {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_NAME, toDo.name)
        db.update(TABLE_TODO, cv, "$COL_ID=?", arrayOf(toDo.id.toString()))
    }

    fun deleteToDo(todoId: Long){
        val db = writableDatabase
        db.delete(TABLE_TODO_ITEM, "$COL_TODO_ID=?", arrayOf(todoId.toString()))
        db.delete(TABLE_TODO, "$COL_ID=?", arrayOf(todoId.toString()))
    }

    fun updateToDoItemCompletedStatus(todoId: Long, isCompleted: Boolean){
        val db = writableDatabase
        val queryResult = db.rawQuery("SELECT * FROM $TABLE_TODO_ITEM WHERE $COL_TODO_ID=$todoId", null)

        if(queryResult.moveToFirst()){
            do {
                val item = ToDoItem()
                item.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                item.toDoId = queryResult.getLong(queryResult.getColumnIndex(COL_TODO_ID))
                item.itemName = queryResult.getString(queryResult.getColumnIndex(COL_ITEM_NAME))
                item.isCompleted = isCompleted
                updateToDoItem(item)
            }while (queryResult.moveToNext())
        }

        queryResult.close()
    }

    fun getToDos() : MutableList<ToDo>{
        val result : MutableList<ToDo> = ArrayList()
        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * from $TABLE_TODO", null)
        if(queryResult.moveToFirst()){
            do{
                val todo = ToDo()
                todo.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                todo.name = queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                result. add(todo)
            } while (queryResult.moveToNext())
        }
        queryResult.close()

        return result
    }


    fun addToDoItem(item : ToDoItem) : Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_ITEM_NAME, item.itemName)
        cv.put(COL_TODO_ID, item.toDoId)
        if(item.isCompleted)
            cv.put(COL_IS_COLPLETED, 1)
        else
            cv.put(COL_IS_COLPLETED, 0)

        val result = db.insert(TABLE_TODO_ITEM, null, cv)
        return result != (-1).toLong()
    }

    fun updateToDoItem(item : ToDoItem){
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_ITEM_NAME, item.itemName)
        cv.put(COL_TODO_ID, item.toDoId)
        cv.put(COL_IS_COLPLETED, item.isCompleted)


        db.update(TABLE_TODO_ITEM, cv, "$COL_ID=?", arrayOf(item.id.toString()))
    }

    fun deleteToDoItem(itemId : Long){
        val db = writableDatabase
        db.delete(TABLE_TODO_ITEM, "$COL_ID=?", arrayOf(itemId.toString()))
    }

    fun getToDoItems(todoId : Long) : MutableList<ToDoItem> {
        val result : MutableList<ToDoItem> = ArrayList()

        val db = readableDatabase
        val queryResult = db.rawQuery("SELECT * FROM $TABLE_TODO_ITEM WHERE $COL_TODO_ID=$todoId", null)

        if(queryResult.moveToFirst()){
            do {
                val item = ToDoItem()
                item.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                item.toDoId = queryResult.getLong(queryResult.getColumnIndex(COL_TODO_ID))
                item.itemName = queryResult.getString(queryResult.getColumnIndex(COL_ITEM_NAME))
                item.isCompleted = queryResult.getInt(queryResult.getColumnIndex(COL_IS_COLPLETED)) == 1
                result.add(item)
            }while (queryResult.moveToNext())
        }

        queryResult.close()
        return result
    }

    fun createUser(usuario: Usuario): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        cv.put(COL_EMAIL, usuario.email)
        cv.put(COL_SENHA, usuario.senha)
        val result = db.insert(TABLE_USUARIO, null, cv)

        return result != (-1).toLong()
    }

    fun makeLogin(usuario: Usuario): Boolean{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USUARIO WHERE $COL_EMAIL = '${usuario.email}' COL_$COL_SENHA =' ${usuario.senha}'"
        val queryResult = db.rawQuery(query, null)
        return queryResult.count > 0
    }
}
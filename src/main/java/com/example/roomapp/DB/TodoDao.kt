package com.example.roomapp.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

// DML 명령어 작성
@Dao
interface TodoDao {
    @Query("select * from TodoEntity")
    fun getAllTodo() : List<TodoEntity>

    @Insert
    fun insertTodo(todo : TodoEntity)

    @Delete
    fun deleteTop(todo : TodoEntity)
}
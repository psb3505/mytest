package com.example.roomapp.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// 테이블
@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "importance")
    var importance : Int
)

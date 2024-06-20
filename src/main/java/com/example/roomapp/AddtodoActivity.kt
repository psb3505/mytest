package com.example.roomapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.roomapp.DB.AppDatabase
import com.example.roomapp.DB.TodoDao
import com.example.roomapp.DB.TodoEntity
import com.example.roomapp.databinding.ActivityAddTodoBinding

class AddtodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTodoBinding
    lateinit var db : AppDatabase
    lateinit var todoDao : TodoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.btnComplete.setOnClickListener {
            insertTodo()
        }
    }

    // 삽입
    private fun insertTodo() {
        val todoTitle = binding.edtTitle.text.toString()
        var rcheck = binding.radioGroup.checkedRadioButtonId
        var tmpData = 0
        when(rcheck) {
            R.id.btn_high -> tmpData = 1
            R.id.btn_middle -> tmpData = 2
            R.id.btn_low -> tmpData = 3
        }
        if (tmpData == 0 || todoTitle.isBlank()) {
            Toast.makeText(this, "모든 항목을 채워주세요", Toast.LENGTH_SHORT).show()
        } else {
            Thread {
                todoDao.insertTodo(TodoEntity(null, todoTitle, tmpData))
                runOnUiThread {
                    Toast.makeText(this, "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    finish()                // 메인 액티비티로 전환(onRestart() 호출)
                }
            }.start()
        }
    }

}
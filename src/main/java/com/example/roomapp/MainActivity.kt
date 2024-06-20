package com.example.roomapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.DB.AppDatabase
import com.example.roomapp.DB.TodoDao
import com.example.roomapp.DB.TodoEntity
import com.example.roomapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnItemLongClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db : AppDatabase
    private lateinit var todoDao: TodoDao
    private lateinit var todoList : ArrayList<TodoEntity>
    private lateinit var adapter : TodoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        getAllTodoList()

        binding.btnTodoAdd.setOnClickListener {
            val intent = Intent(this, AddtodoActivity::class.java)
            startActivity(intent)
        }

    }

    // 백 그라운드에서 DB 작업(스레드 사용)
    // 메인작업(runOnUiThread 사용)
    private fun getAllTodoList() {
        Thread {
            todoList = ArrayList(todoDao.getAllTodo())
            setRecyclerView()
        }.start()
    }
    // 리사이클러뷰에 데이터 추가 (메인스레드에서 작업)
    private fun setRecyclerView() {
        runOnUiThread {
            adapter = TodoRecyclerViewAdapter(todoList, this)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }
    // 서브 액티비티에서 매안 약타비티로 넘어올때 실행
    override fun onRestart() {
        super.onRestart()
        getAllTodoList()
    }
    // 롱클릭시 대화상자
    override fun onLongClick(position: Int) {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("할 일 삭제")
        dialog.setMessage("정말로 삭제하시겠습니까?")
        dialog.setNegativeButton("취소", null)
        dialog.setPositiveButton("확인", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                deleteMethod(position)
            }
        }).show()
    }

    private fun deleteMethod(position : Int) {
        Thread {
            todoDao.deleteTop(todoList[position])
            todoList.removeAt(position)
            runOnUiThread {
                adapter.notifyDataSetChanged()          // 어댑터 재갱신
                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}
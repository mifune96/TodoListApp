package com.codeathome.todo.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.codeathome.todo.R
import com.codeathome.todo.model.Item
import com.codeathome.todo.model.Todo
import com.codeathome.todo.network.ApiConfig
import com.codeathome.todo.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterItemCard (var activity: Activity, var item: List<Item>):
    RecyclerView.Adapter<AdapterItemCard.Holder>(){
    class Holder (view: View): RecyclerView.ViewHolder(view){
        val tvTittleItem = view.findViewById<TextView>(R.id.name_itemtodo)
        val btnEditItem = view.findViewById<ImageView>(R.id.btn_edt_itemtodo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.carditemtodo_item,
                parent, false)
        return AdapterItemCard.Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
       holder.tvTittleItem.text = item[position].name
        val name = item[position].name
        val id = item[position].id

        holder.btnEditItem.setOnClickListener {
            val mDialog =
                LayoutInflater.from(activity).inflate(R.layout.dialog_edit, null)

            val edtDialog = mDialog.findViewById<EditText>(R.id.edt_dialog_edit_todo)
            val btnUpdate = mDialog.findViewById<Button>(R.id.btn_update_dialog_edit_todos)
            val btnBatal = mDialog.findViewById<Button>(R.id.btn_batal_dialog_edit_todos)
            val btnDelet = mDialog.findViewById<Button>(R.id.btn_hapus_dialog_edit_todos)

            val mBuild = AlertDialog.Builder(activity)
                .setView(mDialog)
                .setTitle("Create New Todo")

            val mAlertDialog = mBuild.show()

            edtDialog.setText(name)

            btnBatal.setOnClickListener {
                mAlertDialog.dismiss()
            }

            btnUpdate.setOnClickListener {
                val edtname = edtDialog.text.toString()
                ApiConfig.instanceRetrofit.updateTodoItem(id,edtname).enqueue(object : Callback<Item> {
                    override fun onResponse(call: Call<Item>, response: Response<Item>) {
                        if (response.isSuccessful){
                            Toast.makeText(activity, "Item Berhasil di Update", Toast.LENGTH_SHORT).show()
                            (activity as MainActivity).getAllTodos()
                        } else{
                            Toast.makeText(activity, "Item Gagal di Update", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Item>, t: Throwable) {
                        (activity as MainActivity).getAllTodos()
                    }

                })
                mAlertDialog.dismiss()
            }

            btnDelet.setOnClickListener {
                ApiConfig.instanceRetrofit.deleteTodoItem(id).enqueue(object : Callback<Item> {
                    override fun onResponse(call: Call<Item>, response: Response<Item>) {
                        if (response.isSuccessful){
                            Toast.makeText(activity, "Item Berhasil di Hapus", Toast.LENGTH_SHORT).show()
                            (activity as MainActivity).getAllTodos()
                        } else{
                            Toast.makeText(activity, "Item Gagal di Hapus", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Item>, t: Throwable) {
                        (activity as MainActivity).getAllTodos()
                    }

                })
                mAlertDialog.dismiss()
            }
        }
    }

    override fun getItemCount(): Int = item.size
}
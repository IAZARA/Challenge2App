package com.example.autenticacion.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.autenticacion.R

class TaskAdapter(
    private val onToggle: (Task) -> Unit,
    private val onEdit: (Task) -> Unit,
    private val onDelete: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.VH>() {

    private val items = mutableListOf<Task>()

    fun submitList(list: List<Task>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val task = items[position]
        holder.title.text = task.title
        if (task.description.isNullOrBlank()) {
            holder.description.visibility = View.GONE
        } else {
            holder.description.visibility = View.VISIBLE
            holder.description.text = task.description
        }
        holder.check.setOnCheckedChangeListener(null)
        holder.check.isChecked = task.completed
        holder.check.setOnCheckedChangeListener { _, _ -> onToggle(task) }
        holder.btnEdit.setOnClickListener { onEdit(task) }
        holder.btnDelete.setOnClickListener { onDelete(task) }
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val check: CheckBox = v.findViewById(R.id.check_completa)
        val title: TextView = v.findViewById(R.id.texto_titulo)
        val description: TextView = v.findViewById(R.id.texto_descripcion)
        val btnEdit: ImageButton = v.findViewById(R.id.boton_editar)
        val btnDelete: ImageButton = v.findViewById(R.id.boton_eliminar)
    }
}

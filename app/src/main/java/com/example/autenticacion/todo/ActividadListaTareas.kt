package com.example.autenticacion.todo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.autenticacion.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ActividadListaTareas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        val vm = ViewModelProvider(this)[TaskViewModel::class.java]

        val listaPendientes: RecyclerView = findViewById(R.id.lista_pendientes)
        val listaCompletadas: RecyclerView = findViewById(R.id.lista_completadas)
        val textoVacio: TextView = findViewById(R.id.texto_vacio)
        val fab: FloatingActionButton = findViewById(R.id.fab_agregar)

        val adapterPendientes = TaskAdapter(
            onToggle = { vm.toggleCompletion(it) },
            onEdit = { abrirEdicion(it.id) },
            onDelete = { vm.deleteTask(it) }
        )
        val adapterCompletadas = TaskAdapter(
            onToggle = { vm.toggleCompletion(it) },
            onEdit = { abrirEdicion(it.id) },
            onDelete = { vm.deleteTask(it) }
        )

        listaPendientes.layoutManager = LinearLayoutManager(this)
        listaPendientes.adapter = adapterPendientes

        listaCompletadas.layoutManager = LinearLayoutManager(this)
        listaCompletadas.adapter = adapterCompletadas

        vm.pending.observe(this) { pendientes ->
            adapterPendientes.submitList(pendientes)
            actualizarVacio(textoVacio, pendientes, vm.completed.value)
        }
        vm.completed.observe(this) { completadas ->
            adapterCompletadas.submitList(completadas)
            actualizarVacio(textoVacio, vm.pending.value, completadas)
        }

        fab.setOnClickListener { abrirEdicion(null) }
    }

    private fun actualizarVacio(texto: TextView, pendientes: List<Task>?, completadas: List<Task>?) {
        val vacio = (pendientes?.isEmpty() != false) && (completadas?.isEmpty() != false)
        texto.visibility = if (vacio) View.VISIBLE else View.GONE
    }

    private fun abrirEdicion(id: Int?) {
        val intent = Intent(this, ActividadEditarTarea::class.java)
        if (id != null) intent.putExtra(ActividadEditarTarea.EXTRA_ID, id)
        startActivity(intent)
    }
}


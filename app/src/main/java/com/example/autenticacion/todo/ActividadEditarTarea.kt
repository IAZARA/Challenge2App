package com.example.autenticacion.todo

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.autenticacion.R
import com.google.android.material.textfield.TextInputEditText

class ActividadEditarTarea : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "task_id"
    }

    private var taskId: Int? = null
    private var currentTask: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val vm = ViewModelProvider(this)[TaskViewModel::class.java]

        val campoTitulo: TextInputEditText = findViewById(R.id.campo_titulo)
        val campoDescripcion: TextInputEditText = findViewById(R.id.campo_descripcion)
        val botonGuardar: Button = findViewById(R.id.boton_guardar)
        val botonCancelar: Button = findViewById(R.id.boton_cancelar)

        taskId = intent.getIntExtra(EXTRA_ID, -1).takeIf { it != -1 }

        val tituloPantalla: android.widget.TextView = findViewById(R.id.titulo_pantalla)
        if (taskId != null) {
            tituloPantalla.setText(R.string.titulo_editar_tarea)
            vm.getById(taskId!!).observe(this) { task ->
                currentTask = task
                if (task != null) {
                    campoTitulo.setText(task.title)
                    campoDescripcion.setText(task.description ?: "")
                }
            }
        } else {
            tituloPantalla.setText(R.string.titulo_nueva_tarea)
        }

        botonGuardar.setOnClickListener {
            val titulo = campoTitulo.text?.toString()?.trim().orEmpty()
            val descripcion = campoDescripcion.text?.toString()?.trim().orEmpty().ifBlank { null }

            if (titulo.isBlank()) {
                Toast.makeText(this, R.string.hint_titulo_tarea, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = taskId
            if (id == null) {
                vm.addTask(titulo, descripcion)
            } else {
                currentTask?.let { existente ->
                    vm.updateTask(existente.copy(title = titulo, description = descripcion))
                } ?: vm.addTask(titulo, descripcion)
            }
            finish()
        }

        botonCancelar.setOnClickListener { finish() }
    }
}

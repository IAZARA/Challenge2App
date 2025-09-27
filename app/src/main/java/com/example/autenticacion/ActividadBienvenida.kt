package com.example.autenticacion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/*
 * Ciclo de vida de la actividad:
 * - onCreate: se ejecuta cuando la actividad se crea por primera vez.
 * - onStart: se invoca cuando la actividad está a punto de hacerse visible.
 * - onResume: se llama cuando la actividad ya es visible e interactiva.
 * - onPause: se ejecuta al perder foco pero antes de dejar de ser visible.
 * - onStop: se invoca cuando la actividad deja de ser visible para el usuario.
 * - onDestroy: se llama justo antes de que la actividad sea destruida y liberada.
 */
class ActividadBienvenida : AppCompatActivity() {

    // onCreate se ejecuta cuando la actividad se crea por primera vez dentro del ciclo de vida.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_bienvenida)

        val botonIrInicio: Button = findViewById(R.id.boton_ir_a_inicio)
        val botonIrRegistro: Button = findViewById(R.id.boton_ir_a_registro)

        botonIrInicio.setOnClickListener {
            startActivity(Intent(this, ActividadInicioSesion::class.java))
        }

        botonIrRegistro.setOnClickListener {
            startActivity(Intent(this, ActividadRegistro::class.java))
        }
    }
}

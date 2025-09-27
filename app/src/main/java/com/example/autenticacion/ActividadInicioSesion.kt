package com.example.autenticacion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

/*
 * Ciclo de vida de la actividad:
 * - onCreate: inicializa la interfaz y dependencias al crear la actividad.
 * - onStart: prepara la actividad para ser visible al usuario.
 * - onResume: la actividad ya es visible y recibe interacción del usuario.
 * - onPause: se pausa cuando otra actividad toma el foco temporalmente.
 * - onStop: nadie puede verla, pero el estado aún se conserva en memoria.
 * - onDestroy: se libera la actividad y sus recursos antes de salir por completo.
 */
class ActividadInicioSesion : AppCompatActivity() {

    // onCreate configura la vista y listeners la primera vez que la actividad se instancia.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_inicio_sesion)

        val gestor = GestorAutenticacion(this)

        val campoCorreo: TextInputEditText = findViewById(R.id.campo_correo)
        val campoContrasena: TextInputEditText = findViewById(R.id.campo_contrasena)
        val botonIniciarSesion: Button = findViewById(R.id.boton_iniciar_sesion)
        val textoIrRegistro: TextView = findViewById(R.id.texto_ir_a_registro)

        botonIniciarSesion.setOnClickListener {
            val correo = campoCorreo.text?.toString().orEmpty()
            val contrasena = campoContrasena.text?.toString().orEmpty()

            var entradaValida = true

            if (!gestor.esCorreoValido(correo)) {
                campoCorreo.error = getString(R.string.error_correo_invalido)
                entradaValida = false
            } else {
                campoCorreo.error = null
            }

            if (!gestor.esContrasenaValida(contrasena)) {
                campoContrasena.error = getString(R.string.error_contrasena_invalida)
                entradaValida = false
            } else {
                campoContrasena.error = null
            }

            if (entradaValida) {
                val inicioExitoso = gestor.iniciarSesion(correo, contrasena)
                if (inicioExitoso) {
                    Toast.makeText(this, R.string.mensaje_sesion_iniciada, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, R.string.mensaje_credenciales_invalidas, Toast.LENGTH_SHORT).show()
                }
            }
        }

        textoIrRegistro.setOnClickListener {
            startActivity(Intent(this, ActividadRegistro::class.java))
        }
    }
}

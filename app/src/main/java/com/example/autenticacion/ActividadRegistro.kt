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
 * - onCreate: inicializa vistas y lógica desde que nace la actividad.
 * - onStart: deja la actividad lista para ser visible en pantalla.
 * - onResume: habilita la interacción directa del usuario con la actividad.
 * - onPause: detiene interacciones al perder el foco temporalmente.
 * - onStop: la actividad ya no es visible pero aún conserva su estado.
 * - onDestroy: libera recursos finales cuando se elimina la actividad.
 */
class ActividadRegistro : AppCompatActivity() {

    // onCreate instala la interfaz y listeners apenas la actividad es creada.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_registro)

        val gestor = GestorAutenticacion(this)

        val campoNombre: TextInputEditText = findViewById(R.id.campo_nombre)
        val campoCorreo: TextInputEditText = findViewById(R.id.campo_correo)
        val campoContrasena: TextInputEditText = findViewById(R.id.campo_contrasena)
        val botonRegistrarse: Button = findViewById(R.id.boton_registrarse)
        val textoIrInicio: TextView = findViewById(R.id.texto_ir_a_inicio)

        botonRegistrarse.setOnClickListener {
            val nombre = campoNombre.text?.toString().orEmpty()
            val correo = campoCorreo.text?.toString().orEmpty()
            val contrasena = campoContrasena.text?.toString().orEmpty()

            var entradaValida = true

            if (nombre.isBlank()) {
                campoNombre.error = getString(R.string.error_nombre_vacio)
                entradaValida = false
            } else {
                campoNombre.error = null
            }

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
                val registroExitoso = gestor.registrar(nombre, correo, contrasena)
                if (registroExitoso) {
                    Toast.makeText(this, R.string.mensaje_registro_exitoso, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ActividadInicioSesion::class.java))
                } else {
                    Toast.makeText(this, R.string.mensaje_registro_error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        textoIrInicio.setOnClickListener {
            startActivity(Intent(this, ActividadInicioSesion::class.java))
        }
    }
}

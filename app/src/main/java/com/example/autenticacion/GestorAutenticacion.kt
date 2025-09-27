package com.example.autenticacion

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns

/**
 * Clase auxiliar que maneja la lógica de autenticación con SharedPreferences.
 */
class GestorAutenticacion(context: Context) {

    private val preferencias: SharedPreferences =
        context.getSharedPreferences("autenticacion_pref", Context.MODE_PRIVATE)

    fun registrar(nombre: String, correo: String, contrasena: String): Boolean {
        if (!esCorreoValido(correo) || !esContrasenaValida(contrasena)) {
            return false
        }
        preferencias.edit()
            .putString("usuario_nombre", nombre)
            .putString("usuario_correo", correo)
            .putString("usuario_contrasena", contrasena)
            .apply()
        return true
    }

    fun iniciarSesion(correo: String, contrasena: String): Boolean {
        val correoGuardado = preferencias.getString("usuario_correo", null)
        val contrasenaGuardada = preferencias.getString("usuario_contrasena", null)
        return correo == correoGuardado && contrasena == contrasenaGuardada
    }

    fun esCorreoValido(correo: String): Boolean {
        return correo.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }

    fun esContrasenaValida(contrasena: String): Boolean {
        return contrasena.length >= 6
    }
}

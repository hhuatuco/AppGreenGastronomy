package com.example.trabajofinal

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import modelo.UsuarioDAO
import utilidades.SessionManager


class MainActivity : AppCompatActivity() {
    lateinit var et_user: TextView
    lateinit var et_contrasena: TextView
    lateinit var btn_iniciarsesion: Button
    lateinit var btn_registrar: Button
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)
        et_user=findViewById(R.id.et_user)
        et_contrasena=findViewById(R.id.et_contrasena)
        btn_iniciarsesion=findViewById(R.id.btn_iniciarsesion)
        btn_registrar=findViewById(R.id.btn_registrar)
        btn_iniciarsesion.setOnClickListener{
            Logear()
        }
        btn_registrar.setOnClickListener{
            val i = Intent(this@MainActivity, Usuario::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
        }

    }
    fun Logear(){
        val usu=et_user.text.toString()
        val contra=et_contrasena.text.toString()
        val usuarioDAO=UsuarioDAO(this)

        val cursor: Cursor = usuarioDAO.login(usu, contra)

        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        val dialog = AlertDialog.Builder(this)
            .setView(progressBar)
            .setMessage("Cargando...")
            .create()
        dialog.show()

        try {
            if (cursor.moveToFirst()) {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val dni = cursor.getString(2)
                val rol = cursor.getString(3)
                val telefono = cursor.getString(4)
                val usuario = cursor.getString(5)
                val contraseña = cursor.getString(6)

                sessionManager.createSession(nombre,dni,rol, telefono,usuario, contraseña, id)

                if (rol == "Administrador") {
                    //Toast.makeText(this, "tipo admin "+nombre, Toast.LENGTH_SHORT).show()
                    val i = Intent(this@MainActivity, MenuAdmin::class.java)
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(i)
                    //progressDialog.dismiss()
                    dialog.dismiss()
                    finish()
                }
                else if (rol == "Cliente") {
                    //Toast.makeText(this, "tipo Cliente usuario "+nombre, Toast.LENGTH_SHORT).show()
                    val i = Intent(this@MainActivity, Carta::class.java)
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(i)
                    //progressDialog.dismiss()
                    dialog.dismiss()
                    finish()
                }
            }else {
                    Toast.makeText(this@MainActivity, "Usuario o Contraseña incorrecta", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
            }
        } catch (ex: Exception) {
            Toast.makeText(this@MainActivity, "Error de base de datos, intente de nuevo", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("¿Desea salir de la App?")
                .setPositiveButton("Si", DialogInterface.OnClickListener { dialog, id ->
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
            builder.show()
        }
        return super.onKeyDown(keyCode, event)
    }
}
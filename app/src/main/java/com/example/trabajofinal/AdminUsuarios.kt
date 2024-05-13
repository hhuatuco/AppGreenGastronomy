package com.example.trabajofinal

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import modelo.UsuarioDAO
import utilidades.ListaUsuarios


class AdminUsuarios : AppCompatActivity() {

    private lateinit var rvUsuarios: RecyclerView
    private lateinit var usuarioDAO: UsuarioDAO
    private var list:ListaUsuarios = ListaUsuarios()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_usuarios)
        usuarioDAO=UsuarioDAO(this)
        
        rvUsuarios=findViewById(R.id.rvUsuarios)
        rvUsuarios.layoutManager = LinearLayoutManager(this)
        rvUsuarios.adapter = list

        list.onClickDeleteItem {
            eliminar(it.id)
        }

        listar()

    }

    fun listar(){
        val listaUsuarios = usuarioDAO.cargar()
        list.agregarDatos(listaUsuarios)
        list.contexto(this)
    }

    fun eliminar(id:Int){
        val ventana= AlertDialog.Builder(this)
        ventana.setMessage("Desea eliminar el usuario?")
        ventana.setCancelable(true)
        ventana.setPositiveButton("SI") { dialog, which ->
            val mensaje = usuarioDAO.eliminar(id)
            listar()
            dialog.dismiss()
            mostrarMensaje(mensaje)
        }
        ventana.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        ventana.create().show()
    }

    fun mostrarMensaje(mensaje:String){
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Green Gastronomy")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, i: Int ->
            val intent = Intent(this, AdminUsuarios::class.java)
            startActivity(intent)
        }
        ventana.create().show()
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val refresh = Intent(this, MenuAdmin::class.java)
            refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(refresh)
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }
}
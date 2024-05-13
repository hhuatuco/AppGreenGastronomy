package com.example.trabajofinal

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import modelo.ProductoDAO
import modelo.UsuarioDAO
import utilidades.ListaProducto

class AdminProductos : AppCompatActivity() {
    private lateinit var rvProductos: RecyclerView
    private lateinit var btnNuevoProducto: FloatingActionButton
    private lateinit var productoDAO: ProductoDAO
    private var list: ListaProducto = ListaProducto()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_productos)

        productoDAO= ProductoDAO(this)
        btnNuevoProducto=findViewById(R.id.btnNuevoProducto)
        btnNuevoProducto.setOnClickListener {
            val intent = Intent(this, RegistroProducto::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        rvProductos=findViewById(R.id.rvProductos)
        rvProductos.layoutManager = LinearLayoutManager(this)
        rvProductos.adapter = list

        list.onClickDeleteItem {
            eliminar(it.id)
        }

        listar()
    }

    fun listar(){
        val listaProducto = productoDAO.cargar()
        list.agregarDatos(listaProducto)
        list.contexto(this)
    }

    fun eliminar(id:Int){
        val ventana= AlertDialog.Builder(this)
        ventana.setMessage("Desea eliminar el producto?")
        ventana.setCancelable(true)
        ventana.setPositiveButton("SI") { dialog, which ->
            val mensaje = productoDAO.eliminar(id)
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
            val intent = Intent(this, AdminProductos::class.java)
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
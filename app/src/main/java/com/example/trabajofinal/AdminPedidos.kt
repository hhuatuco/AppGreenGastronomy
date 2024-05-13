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
import modelo.DPedidoDAO
import utilidades.ListaPedido

class AdminPedidos : AppCompatActivity() {

    private lateinit var rvPedidos: RecyclerView
    private lateinit var pedidoDAO: DPedidoDAO
    private var list: ListaPedido = ListaPedido()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_pedidos)
        pedidoDAO = DPedidoDAO(this)

        rvPedidos = findViewById(R.id.rvPedidos)
        rvPedidos.layoutManager = LinearLayoutManager(this)
        rvPedidos.adapter = list

        list.onClickDeleteItem {
            eliminar(it.id)
        }

        listar()

    }

    fun listar() {
        val listaPedido = pedidoDAO.cargar()
        list.agregarDatos(listaPedido)
        list.contexto(this)
    }

    fun eliminar(id: Int) {
        val ventana = AlertDialog.Builder(this)
        ventana.setMessage("Desea despachar el pedido?")
        ventana.setCancelable(true)
        ventana.setPositiveButton("SI") { dialog, which ->
            val mensaje = pedidoDAO.despacho(id)
            listar()
            dialog.dismiss()
            mostrarMensaje(mensaje)
        }
        ventana.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        ventana.create().show()
    }

    fun mostrarMensaje(mensaje: String) {
        val ventana = AlertDialog.Builder(this)
        ventana.setTitle("Green Gastronomy")
        ventana.setMessage(mensaje)
        ventana.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, i: Int ->
            val intent = Intent(this, AdminPedidos::class.java)
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
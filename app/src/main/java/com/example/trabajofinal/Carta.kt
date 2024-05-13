package com.example.trabajofinal

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import entidades.Producto
import modelo.DPedidoDAO
import modelo.ProductoDAO
import utilidades.ListaCarta
import utilidades.ListaRecomendado
import utilidades.SessionManager


class Carta : AppCompatActivity(){
    private lateinit var rvProductosRecomendados: RecyclerView
    private lateinit var rvProductosCarta: RecyclerView
    private lateinit var productoDAO: ProductoDAO
    private lateinit var pedidoDAO: DPedidoDAO
    private lateinit var ivLogout: ImageView
    private lateinit var ivPedido: ImageView
    lateinit var sessionManager: SessionManager

    private var listR: ListaRecomendado = ListaRecomendado()
    private var listCarta: ListaCarta = ListaCarta()

    var mensaje : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carta)

        sessionManager = SessionManager(this)
        productoDAO= ProductoDAO(this)
        pedidoDAO= DPedidoDAO(this)

        ivLogout=findViewById(R.id.ivLogout)
        ivLogout.setOnClickListener{
            sessionManager.logout()
        }
        ivPedido=findViewById(R.id.ivPedido)
        ivPedido.setOnClickListener{
            val selectedItems = listCarta.getSelectedItems()
            val selectedText = StringBuilder()
            var total = 0.0
            for (item in selectedItems) {
                selectedText.append(item.nombre_producto).append(" "+item.precio).append("\n")
                total +=item.precio
            }
            //val ventana = androidx.appcompat.app.AlertDialog.Builder(this)
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_pedido, null)
            builder.setView(dialogView)
            builder.setTitle("Green Gastornomy")
            builder.setIcon(R.drawable.ic_logo_bg)

            val rbParaLlevar = dialogView.findViewById<RadioButton>(R.id.rbParaLlevar)
            val rbParaMesa = dialogView.findViewById<RadioButton>(R.id.rbParaMesa)
            val etNumMesa = dialogView.findViewById<EditText>(R.id.etNumMesa)

            rbParaLlevar.isChecked = true

            if (selectedItems.isEmpty()){
                builder.setMessage("Seleccione productos, mantenga pulsado el que quiere pedir")
                builder.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, i: Int ->
                }
            }else{
                builder.setMessage(selectedText.toString()+" \nTotal: "+total.toString()+" \nDesea realizar su pedido?")
                builder.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, i: Int ->
                    for (item in selectedItems) {
                        if (rbParaMesa.isChecked) {
                            val numMesa = "Mesa: "+etNumMesa.text.toString()
                            mensaje = pedidoDAO.registrar(item.id,item.precio,numMesa)
                        } else {
                            mensaje = pedidoDAO.registrar(item.id,item.precio,"Para llevar")
                        }
                    }
                    Toast.makeText(this, "Se realizo su pedido con exito! \nTotal: "+total.toString(), Toast.LENGTH_SHORT).show()
                }.setNegativeButton("Cancelar") { dialog, id ->
                    dialog.dismiss()
                }
            }
            val dialog = builder.create()
            dialog.show()

        }

        rvProductosRecomendados=findViewById(R.id.rvProductosRecomendados)
        rvProductosRecomendados.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        rvProductosRecomendados.adapter = listR

        rvProductosCarta=findViewById(R.id.rvProductosCarta)
        rvProductosCarta.layoutManager = LinearLayoutManager(this)
        rvProductosCarta.adapter = listCarta

        val listaProducto = productoDAO.cargar()
        listR.agregarDatos(listaProducto)
        listR.contexto(this)
        listCarta.agregarDatos(listaProducto)
        listCarta.contexto(this)

    }

    fun registroPedido(){

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
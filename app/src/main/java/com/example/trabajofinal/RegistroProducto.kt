package com.example.trabajofinal

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import entidades.Producto
import modelo.ProductoDAO

class RegistroProducto : AppCompatActivity() {

    lateinit var etNombre: TextView
    lateinit var etDescripcion: TextView
    lateinit var etPrecio: TextView
    lateinit var tvTituloProducto: TextView
    lateinit var btnRegistrar: Button
    private var modificar = false
    private var id:Int=0
    var mensaje : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_producto)

        etNombre = findViewById(R.id.etNombre)
        etDescripcion = findViewById(R.id.etDescripcion)
        etPrecio = findViewById(R.id.etPrecio)
        tvTituloProducto = findViewById(R.id.tvTituloProducto)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener{
            Registrarse()
        }
        recuperarDatos()
    }
    fun Registrarse(){

        val prDAO= ProductoDAO(this)

        val nombre = etNombre.text.toString()
        val des = etDescripcion.text.toString()
        val precio = etPrecio.text.toString()
        var valida = true


        if(nombre.isEmpty()){
            valida = false
            etNombre.setError("Nombre es obligatorio")
        }

        if(precio.isEmpty()){
            valida = false
            etPrecio.setError("Precio es obligatorio")
        }
        if(des.isEmpty()){
            valida = false
            etDescripcion.setError("Descripcion es obligatorio")
        }

        if(valida){
            val pr = Producto()
            pr.nombre_producto = nombre
            pr.descripcion = des
            pr.precio = precio.toDouble()
            if (modificar){
                pr.id  =id
                mensaje = prDAO.editar(pr)

            }else{
                mensaje = prDAO.registrar(pr)
            }
            val ventana = AlertDialog.Builder(this)
            ventana.setTitle("Green Gastornomy")
            ventana.setMessage(mensaje)
            ventana.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, i: Int ->
                val intent = Intent(this, AdminProductos::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
            ventana.create().show()
        }
    }

    fun recuperarDatos(){
        if(intent.hasExtra("var_id")){
            modificar=true
            tvTituloProducto.text="Editar Producto"
            btnRegistrar.text="Guardar Cambios"
            id=intent.getIntExtra("var_id",0)
            etNombre.setText(intent.getStringExtra("var_nombre_producto"))
            etDescripcion.setText(intent.getStringExtra("var_descripcion"))
            etPrecio.setText(intent.getStringExtra("var_precio"))

        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val refresh = Intent(this, AdminProductos::class.java)
            refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(refresh)
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }
}
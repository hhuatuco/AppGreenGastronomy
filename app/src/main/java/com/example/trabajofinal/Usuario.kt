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
import entidades.UsuarioE
import modelo.UsuarioDAO

class Usuario : AppCompatActivity() {
    lateinit var etNombre: TextView
    lateinit var etDni: TextView
    lateinit var etTelefono: TextView
    lateinit var etUsuario: TextView
    lateinit var etContrasena: TextView
    lateinit var tvTituloUsuario: TextView
    lateinit var btnRegistrar: Button
    private var modificar = false
    private var id:Int=0
    var mensaje : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_usuario)

        etNombre = findViewById(R.id.etNombre)
        etDni = findViewById(R.id.etDni)
        etTelefono = findViewById(R.id.etTelefono)
        etUsuario = findViewById(R.id.etUsuario)
        etContrasena = findViewById(R.id.etContrasena)
        tvTituloUsuario = findViewById(R.id.tvTituloUsuario)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            Registrarse()
        }
        recuperarDatos()

    }

    fun Registrarse(){

        val usuarioDAO= UsuarioDAO(this)

        val nombre = etNombre.text.toString()
        val dni = etDni.text.toString()
        val telefono = etTelefono.text.toString()
        val user = etUsuario.text.toString()
        val contra = etContrasena.text.toString()
        var valida = true


        if(nombre.isEmpty()){
            valida = false
            etNombre.setError("Nombre es obligatorio")
        }

        if(dni.isEmpty() || dni.length>8){
            valida = false
            etDni.setError("DNI es obligatorio y debe tener 8 caracteres")
        }
        if(telefono.isEmpty()){
            valida = false
            etTelefono.setError("Telefono es obligatorio")
        }
        if(user.isEmpty()){
            valida = false
            etUsuario.setError("Usuario es obligatorio")
        }
        if(contra.isEmpty()){
            valida = false
            etContrasena.setError("Contraseña es obligatorio")
        }

        if(valida){
            val usu = UsuarioE()
            usu.dni = dni
            usu.nombre = nombre
            usu.telefono = telefono
            usu.usuario = user
            usu.contraseña = contra
            usu.rol = "Cliente"
            if (modificar){
                usu.id  =id
                mensaje = usuarioDAO.editar(usu)

            }else{
                mensaje = usuarioDAO.registrar(usu)
            }
            val ventana = AlertDialog.Builder(this)
            ventana.setTitle("Green Gastornomy")
            ventana.setMessage(mensaje)
            ventana.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, i: Int ->
                if (modificar){
                    val intent = Intent(this, AdminUsuarios::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)

                }else{
                    val intent = Intent(this, MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
            }
            ventana.create().show()
        }
    }

    fun recuperarDatos(){
        if(intent.hasExtra("var_id")){
            modificar=true
            tvTituloUsuario.text="Editar Usuario"
            btnRegistrar.text="Guardar Cambios"
            id=intent.getIntExtra("var_id",0)
            etNombre.setText(intent.getStringExtra("var_nombre"))
            etDni.setText(intent.getStringExtra("var_dni"))
            etTelefono.setText(intent.getStringExtra("var_telefono"))
            etUsuario.setText(intent.getStringExtra("var_usuario"))
            etContrasena.setText(intent.getStringExtra("var_contraseña"))
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(intent.hasExtra("var_id")){
                val refresh = Intent(this, AdminUsuarios::class.java)
                refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(refresh)
                finish()
            }else{
                val refresh = Intent(this, MainActivity::class.java)
                refresh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(refresh)
                finish()
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
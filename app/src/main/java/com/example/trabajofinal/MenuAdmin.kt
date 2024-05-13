package com.example.trabajofinal

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import utilidades.SessionManager


class MenuAdmin : AppCompatActivity() {
    lateinit var tvBienvenido: TextView
    lateinit var sessionManager: SessionManager
    lateinit var cardMenu: CardView
    lateinit var cardUsuario:CardView
    lateinit var cardPedidos:CardView
    lateinit var cardCerrarSesion:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_admin)
        sessionManager = SessionManager(this)
        tvBienvenido=findViewById(R.id.tvBienvenido)
        cardMenu=findViewById(R.id.cardMenu)
        cardUsuario=findViewById(R.id.cardUsuario)
        cardPedidos=findViewById(R.id.cardPedidos)
        cardCerrarSesion=findViewById(R.id.cardCerrarSesion)

        cardMenu.setOnClickListener{
            val i = Intent(this, AdminProductos::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
        }

        cardUsuario.setOnClickListener{
            val i = Intent(this, AdminUsuarios::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
        }

        cardPedidos.setOnClickListener{
            val i = Intent(this, AdminPedidos::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
        }

        cardCerrarSesion.setOnClickListener {
            sessionManager.logout()
        }

        val user = sessionManager.getUserDetail()
        val Nombre = user[sessionManager.NOMBRE]
        tvBienvenido.setText("Bienvenido $Nombre")

    }

    //pulsacion boton atras
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
package utilidades

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import com.example.trabajofinal.MainActivity
import com.example.trabajofinal.MenuAdmin


class SessionManager (val context: Context) {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    //lateinit var context: Context

    var PRIVATE_MODE = 0

    val PREF_NAME = "LOGIN"
    val LOGIN = "IS_LOGIN"
    val ID = "id"
    val NOMBRE = "nombre"
    val DNI = "dni"
    val ROL= "rol"
    val TELEFONO = "telefono"
    val USUARIO = "usuario"
    val CONTRASEÑA = "contraseña"


    //val storage = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)


    init {
        //this.context = context
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = sharedPreferences.edit()
    }

    fun createSession(
        Nombre: String,
        Dni: String,
        Rol: String,
        Telefono: String,
        Usuario: String,
        Contraseña: String,
        //Tipo: Int,
        Id: Int
    ) {
        editor.putBoolean(LOGIN, true)
        editor.putInt(ID, Id)
        editor.putString(NOMBRE, Nombre)
        editor.putString(DNI, Dni)
        editor.putString(ROL, Rol)
        editor.putString(TELEFONO, Telefono)
        editor.putString(USUARIO, Usuario)
        editor.putString(CONTRASEÑA, Contraseña)
        editor.apply()
        //storage.edit().putInt(ID, Id).apply()
        //storage.edit().putString(NOMBRE, Nombre).apply()
    }

    fun isLoggin(): Boolean {
        return sharedPreferences.getBoolean(LOGIN, false)
        //return storage.getBoolean(LOGIN, false)
    }

    fun isAdmin(): Boolean {
        if (sharedPreferences.getString(ROL, null)=="Administrador"){
            return true
        }else{
            return false
        }
    }

    fun checkLogin() {
        if (!isLoggin()) {
            val i = Intent(context, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(i)
            (context as MenuAdmin).finish()
        }
    }

    fun getUserDetail(): HashMap<String, Any?> {
        val user = HashMap<String, Any?>()
        user[DNI] = sharedPreferences.getString(DNI, null)
        user[NOMBRE] = sharedPreferences.getString(NOMBRE, null)
        user[ROL] = sharedPreferences.getString(ROL, null)
        user[TELEFONO] = sharedPreferences.getString(TELEFONO, null)
        user[USUARIO] = sharedPreferences.getString(USUARIO, null)
        user[CONTRASEÑA] = sharedPreferences.getString(CONTRASEÑA, null)
        user[ID] = sharedPreferences.getInt(ID, 0)
        //user[ID] = storage.getString(ID, null)
        //user[NOMBRE] = storage.getString(NOMBRE, null)
        return user
    }

    fun logout() {
        editor.clear()
        editor.commit()
        /*storage.edit().clear()
        storage.edit().commit()*/
        val i = Intent(context, MainActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(i)
        (context as MenuAdmin).finish()
    }
}
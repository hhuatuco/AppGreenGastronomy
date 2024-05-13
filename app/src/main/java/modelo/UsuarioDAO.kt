package modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import entidades.UsuarioE
import utilidades.ConexionSQLiteHelper
import utilidades.Utilidades


class UsuarioDAO (context: Context) {
    private var sqLiteHelper: ConexionSQLiteHelper= ConexionSQLiteHelper(context)

    fun registrar(usuario: UsuarioE):String{
        var respuesta ="";
        val db=sqLiteHelper.writableDatabase
        try {
            val valores= ContentValues()
            valores.put(Utilidades.CAMPO_DNI,usuario.dni)
            valores.put(Utilidades.CAMPO_NOMBRE,usuario.nombre)
            valores.put(Utilidades.CAMPO_ROL,usuario.rol)
            valores.put(Utilidades.CAMPO_TELEFONO,usuario.telefono)
            valores.put(Utilidades.CAMPO_USUARIO,usuario.usuario)
            valores.put(Utilidades.CAMPO_CONTRASEÑA,usuario.contraseña)
            val rpta=db.insert(Utilidades.TABLA_USUARIO,null,valores)

            if(rpta == -1L){
                respuesta="Error al insertar, intente nuevamente"
            }else{
                respuesta="se registró correctamente"
            }


        }catch (e:Exception){
            respuesta="Ocurrio un error, intente nuevamente"

        }finally {
            db.close()
        }
        return respuesta
    }

    fun login(Usuario: String, Contra: String): Cursor {
        val db=sqLiteHelper.writableDatabase
        val colums = arrayOf<String>(
            Utilidades.CAMPO_ID,
            Utilidades.CAMPO_DNI,
            Utilidades.CAMPO_NOMBRE,
            Utilidades.CAMPO_ROL,
            Utilidades.CAMPO_TELEFONO,
            Utilidades.CAMPO_USUARIO,
            Utilidades.CAMPO_CONTRASEÑA
        )
        val SQL = "SELECT * FROM "+Utilidades.TABLA_USUARIO+" WHERE "+Utilidades.CAMPO_USUARIO+" ='"+ Usuario+"' AND "+Utilidades.CAMPO_CONTRASEÑA+" ='"+Contra+"'"
        return db.rawQuery(SQL,null)
    }

    fun cargar():ArrayList<UsuarioE>{
        val listaUsuario:ArrayList<UsuarioE> = ArrayList()
        val query = "SELECT * FROM "+Utilidades.TABLA_USUARIO
        val db = sqLiteHelper.readableDatabase
        val cursor:Cursor

        try{
            cursor = db.rawQuery(query,null)
            cursor.moveToFirst()
            do{
                val id:Int = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val dni:String = cursor.getString(cursor.getColumnIndexOrThrow("dni"))
                val nombre:String = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val rol:String = cursor.getString(cursor.getColumnIndexOrThrow("rol"))
                val telefono:String = cursor.getString(cursor.getColumnIndexOrThrow("telefono"))
                val usu:String = cursor.getString(cursor.getColumnIndexOrThrow("usuario"))
                val contra:String = cursor.getString(cursor.getColumnIndexOrThrow("contraseña"))

                val u = UsuarioE()
                u.id = id
                u.dni = dni
                u.nombre = nombre
                u.rol = rol
                u.telefono = telefono
                u.usuario = usu
                u.contraseña = contra

                listaUsuario.add(u)
            }while (cursor.moveToNext())
        }catch (e:Exception){
            Log.d("Exception Cargar Usuarios", e.message.toString())
        }finally {
            db.close()
        }
        return listaUsuario;
    }

    fun editar(usuario: UsuarioE):String{
        var respuesta = "";
        val db = sqLiteHelper.writableDatabase
        try {
            val valores = ContentValues()
            valores.put(Utilidades.CAMPO_DNI,usuario.dni)
            valores.put(Utilidades.CAMPO_NOMBRE,usuario.nombre)
            valores.put(Utilidades.CAMPO_ROL,usuario.rol)
            valores.put(Utilidades.CAMPO_TELEFONO,usuario.telefono)
            valores.put(Utilidades.CAMPO_USUARIO,usuario.usuario)
            valores.put(Utilidades.CAMPO_CONTRASEÑA,usuario.contraseña)

            val rpta = db.update(Utilidades.TABLA_USUARIO,valores,"id="+usuario.id,null)

            if(rpta == -1){
                respuesta = "Error al actualizar el usuario, intente nuevamente"
            }else{
                respuesta = "Se modifico correctamente"
            }
        }catch (e:Exception){
            respuesta = "Ocurrio un error, intente nuevamente"
        }finally {
            db.close()
        }
        return respuesta
    }
    fun eliminar(id:Int):String{
        var respuesta=""
        val db=sqLiteHelper.writableDatabase
        try{
            val rpta=db.delete(Utilidades.TABLA_USUARIO,"id="+id,null)
            if(rpta==-1){
                respuesta="Error al elimnar, intente nuevamente"
            }else{
                respuesta="Se elimino correctamente"
            }

        }catch (e:Exception){
            respuesta="ocurrioun error, intente nuevamente"
        }finally {
            db.close()
        }
        return  respuesta
    }
}

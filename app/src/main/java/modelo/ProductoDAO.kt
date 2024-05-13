package modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import entidades.Producto
import utilidades.ConexionSQLiteHelper
import utilidades.Utilidades

class ProductoDAO (context: Context) {
    private var sqLiteHelper: ConexionSQLiteHelper = ConexionSQLiteHelper(context)

    fun cargar():ArrayList<Producto>{
        val listaProducto:ArrayList<Producto> = ArrayList()
        val query = "SELECT * FROM "+ Utilidades.TABLA_PRODUCTO
        val db = sqLiteHelper.readableDatabase
        val cursor: Cursor

        try{
            cursor = db.rawQuery(query,null)
            cursor.moveToFirst()
            do{
                val id:Int = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre:String = cursor.getString(cursor.getColumnIndexOrThrow("nombre_producto"))
                val descripcion:String = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                val precio:Double = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"))

                val p = Producto()
                p.id = id
                p.nombre_producto = nombre
                p.descripcion = descripcion
                p.precio = precio

                listaProducto.add(p)
            }while (cursor.moveToNext())
        }catch (e:Exception){
            Log.d("Exception Cargar Producto", e.message.toString())
        }finally {
            db.close()
        }
        return listaProducto;
    }

    fun registrar(pr: Producto):String{
        var respuesta ="";
        val db=sqLiteHelper.writableDatabase
        try {
            val valores= ContentValues()
            valores.put(Utilidades.CAMPO_NOMBRE_PRODUCTO,pr.nombre_producto)
            valores.put(Utilidades.CAMPO_DESCRIPCION,pr.descripcion)
            valores.put(Utilidades.CAMPO_PRECIO,pr.precio)
            val rpta=db.insert(Utilidades.TABLA_PRODUCTO,null,valores)

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

    fun editar(pr: Producto):String{
        var respuesta = "";
        val db = sqLiteHelper.writableDatabase
        try {
            val valores = ContentValues()
            valores.put(Utilidades.CAMPO_NOMBRE_PRODUCTO,pr.nombre_producto)
            valores.put(Utilidades.CAMPO_DESCRIPCION,pr.descripcion)
            valores.put(Utilidades.CAMPO_PRECIO,pr.precio)

            val rpta = db.update(Utilidades.TABLA_PRODUCTO,valores,"id="+pr.id,null)

            if(rpta == -1){
                respuesta = "Error al actualizar el producto, intente nuevamente"
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
            val rpta=db.delete(Utilidades.TABLA_PRODUCTO,"id="+id,null)
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
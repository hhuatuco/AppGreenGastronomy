package modelo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import entidades.Pedido
import utilidades.ConexionSQLiteHelper
import utilidades.SessionManager
import utilidades.Utilidades
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DPedidoDAO (context: Context){
    private var sqLiteHelper: ConexionSQLiteHelper = ConexionSQLiteHelper(context)
    lateinit var sessionManager: SessionManager

    init {
        // Inicializar sessionManager aquí
        sessionManager = SessionManager(context)
    }

    //fun registrar(pe: Pedido):String{
    fun registrar(IdProducto: Int, Total:Double, Mesa:String):String{
        val user = sessionManager.getUserDetail()
        val usuarioid = user[sessionManager.ID]
        val idUsuarioString = usuarioid.toString()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val fechaActual = dateFormat.format(Date())
        var idDetalle: Long = -1
        var respuesta ="";
        val db=sqLiteHelper.writableDatabase
        try {
            val valores= ContentValues()
            valores.put(Utilidades.CAMPO_DETALLE_PRODUCTO,IdProducto)
            valores.put(Utilidades.CAMPO_TOTAL,Total)
            idDetalle=db.insert(Utilidades.TABLA_DETALLE_PEDIDO,null,valores)
            val valoresPedido = ContentValues()
            valoresPedido.put(Utilidades.CAMPO_ID_DET_PEDIDO,idDetalle)
            valoresPedido.put(Utilidades.CAMPO_ID_USUARIO,idUsuarioString)
            valoresPedido.put(Utilidades.CAMPO_TIPO_MESA,Mesa)
            valoresPedido.put(Utilidades.CAMPO_FECHA_PEDIDO,fechaActual)
            valoresPedido.put(Utilidades.CAMPO_ESTADO_PEDIDO,1)
            val rpta = db.insert(Utilidades.TABLA_PEDIDO,null,valoresPedido)
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

    fun cargar():ArrayList<Pedido>{
        val listaPedido:ArrayList<Pedido> = ArrayList()
        val query = "SELECT p.id AS id_pedido, p.fecha AS fecha_pedido, p.tipo AS tipo_mesa, u.nombre AS nombre_usuario, " +
                "GROUP_CONCAT(pr.nombre_producto, ', ') AS productos, dp.total AS total_pedido " +
                "FROM pedido p " +
                "JOIN detalle_pedido dp ON p.id_detalle = dp.id " +
                "JOIN producto pr ON dp.id_producto = pr.id  JOIN usuario u ON p.id_usuario = u.id WHERE p.estado=1 " +
                "GROUP BY p.id "
        val db = sqLiteHelper.readableDatabase
        val cursor: Cursor

        try{
            cursor = db.rawQuery(query,null)
            cursor.moveToFirst()
            do{
                val id:Int = cursor.getInt(cursor.getColumnIndexOrThrow("id_pedido"))
                val cliente:String = cursor.getString(cursor.getColumnIndexOrThrow("nombre_usuario"))
                val fecha_pedido:String = cursor.getString(cursor.getColumnIndexOrThrow("fecha_pedido"))
                val tipo_mesa:String = cursor.getString(cursor.getColumnIndexOrThrow("tipo_mesa"))
                val productos:String = cursor.getString(cursor.getColumnIndexOrThrow("productos"))
                val total_pedido:Double = cursor.getDouble(cursor.getColumnIndexOrThrow("total_pedido"))

                val p = Pedido()
                p.id = id
                p.cliente = cliente
                p.tipo_mesa = tipo_mesa
                p.fecha_pedido = fecha_pedido
                p.producto = productos
                p.total = total_pedido

                listaPedido.add(p)
            }while (cursor.moveToNext())
        }catch (e:Exception){
            Log.d("Exception Cargar", e.message.toString())
        }finally {
            db.close()
        }
        return listaPedido;
    }
    fun despacho(id: Int): String {
        var respuesta = ""
        val db = sqLiteHelper.writableDatabase
        try {
            val valores = ContentValues()
            valores.put(Utilidades.CAMPO_ESTADO_PEDIDO, 0)
            val whereClause = "${Utilidades.CAMPO_ID_PEDIDO} = ?"
            val whereArgs = arrayOf(id.toString())
            val rpta = db.update(Utilidades.TABLA_PEDIDO, valores, whereClause, whereArgs)
            if (rpta == 1) {
                respuesta = "Se despachó correctamente"
            } else {
                respuesta = "No se encontró el pedido o ocurrió un error al despachar"
            }
        } catch (e: Exception) {
            respuesta = "Ocurrió un error, intente nuevamente"
        } finally {
            db.close()
        }
        return respuesta
    }

}
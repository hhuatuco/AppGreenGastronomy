package utilidades

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ConexionSQLiteHelper (context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "final.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Utilidades.CREAR_TABLA_USUARIO)
        db.execSQL("insert into "+Utilidades.TABLA_USUARIO+"("+Utilidades.CAMPO_NOMBRE +", " + Utilidades.CAMPO_DNI + "," + Utilidades.CAMPO_ROL + " ," + Utilidades.CAMPO_TELEFONO + " ," + Utilidades.CAMPO_USUARIO + ", "+Utilidades.CAMPO_CONTRASEÑA+ ") " +
                "values('Hugo Huatuco','12345678','Administrador','912345678','admin','admin'),('Pancho Angulo','87654321','Cliente','987654321','pancho1','pancho1')");
        //tipo 0 admin
        db.execSQL(Utilidades.CREAR_TABLA_PRODUCTO)
        db.execSQL("insert into "+Utilidades.TABLA_PRODUCTO+"("+Utilidades.CAMPO_NOMBRE_PRODUCTO +", " + Utilidades.CAMPO_DESCRIPCION + "," + Utilidades.CAMPO_PRECIO + ") " +
                "values('Menú Ensalada del dia','150 GR.','25.00'),('Galleta Integral','1 paquete Integral con Miel','3.00')");
        db.execSQL(Utilidades.CREAR_TABLA_DETALLE_PEDIDO)
        db.execSQL(Utilidades.CREAR_TABLA_PEDIDO)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS "+ Utilidades.TABLA_USUARIO)
        db.execSQL("DROP TABLE IF EXISTS "+ Utilidades.TABLA_PRODUCTO)
        db.execSQL("DROP TABLE IF EXISTS "+ Utilidades.TABLA_DETALLE_PEDIDO)
        db.execSQL("DROP TABLE IF EXISTS "+ Utilidades.TABLA_PEDIDO)
        onCreate(db)
    }

}
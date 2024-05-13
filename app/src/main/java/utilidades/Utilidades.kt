package utilidades

class Utilidades {

    companion object {

    //Constantes campos tabla usuario
    val TABLA_USUARIO = "usuario"
    val CAMPO_ID = "id"
    val CAMPO_NOMBRE = "nombre"
    val CAMPO_DNI = "dni"
    val CAMPO_ROL= "rol"
    val CAMPO_TELEFONO = "telefono"
    val CAMPO_USUARIO = "usuario"
    val CAMPO_CONTRASEÑA = "contraseña"
    val CREAR_TABLA_USUARIO = "CREATE TABLE " +
            "" + TABLA_USUARIO + " (" + CAMPO_ID + " " +
            "INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_NOMBRE + " TEXT," + CAMPO_DNI + " TEXT," + CAMPO_ROL + " TEXT," + CAMPO_TELEFONO + " TEXT," +
            CAMPO_USUARIO + " TEXT ," +CAMPO_CONTRASEÑA + " TEXT)"

    //Constantes campos tabla pedido
    val TABLA_PEDIDO = "pedido"
    val CAMPO_ID_PEDIDO = "id"
    val CAMPO_ID_DET_PEDIDO = "id_detalle"
    val CAMPO_ID_USUARIO = "id_usuario"
    val CAMPO_TIPO_MESA = "tipo"
    val CAMPO_FECHA_PEDIDO = "fecha"
    val CAMPO_ESTADO_PEDIDO = "estado"
    val CREAR_TABLA_PEDIDO =
        "CREATE TABLE $TABLA_PEDIDO ($CAMPO_ID_PEDIDO INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_ID_DET_PEDIDO INTEGER, $CAMPO_ID_USUARIO INTEGER, $CAMPO_TIPO_MESA TEXT, $CAMPO_FECHA_PEDIDO TEXT, $CAMPO_ESTADO_PEDIDO INTEGER)"

    //Constantes campos tabla producto
    val TABLA_PRODUCTO = "producto"
    val CAMPO_ID_PRODUCTO = "id"
    val CAMPO_NOMBRE_PRODUCTO = "nombre_producto"
    val CAMPO_DESCRIPCION = "descripcion"
    val CAMPO_PRECIO = "precio"
    //val CAMPO_CANTIDAD = "cantidad"
    val CREAR_TABLA_PRODUCTO =
        //"CREATE TABLE $TABLA_PRODUCTO ($CAMPO_ID_PRODUCTO INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOMBRE_PRODUCTO TEXT, $CAMPO_DESCRIPCION TEXT, $CAMPO_PRECIO REAL, $CAMPO_CANTIDAD TEXT)"
        "CREATE TABLE $TABLA_PRODUCTO ($CAMPO_ID_PRODUCTO INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOMBRE_PRODUCTO TEXT, $CAMPO_DESCRIPCION TEXT, $CAMPO_PRECIO REAL)"

    //Constantes campos tabla detalle pedido
    val TABLA_DETALLE_PEDIDO= "detalle_pedido"
    val CAMPO_ID_DETALLE_PEDIDO = "id"
    val CAMPO_DETALLE_PRODUCTO = "id_producto"
    val CAMPO_TOTAL = "total"
    val CREAR_TABLA_DETALLE_PEDIDO = "CREATE TABLE " +
            "" + TABLA_DETALLE_PEDIDO + " (" + CAMPO_ID_DETALLE_PEDIDO + " " +
            "INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_DETALLE_PRODUCTO + " INTEGER," + CAMPO_TOTAL + " REAL)"


    }

}
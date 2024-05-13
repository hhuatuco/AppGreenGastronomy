package entidades

class Pedido {
    var id:Int=0
    var id_producto:Int=0
    lateinit var cliente:String
    lateinit var tipo_mesa:String
    lateinit var producto:String
    lateinit var fecha_pedido:String
    var total:Double=0.0
}
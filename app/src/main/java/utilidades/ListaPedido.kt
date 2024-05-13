package utilidades

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajofinal.R
import entidades.Pedido

class ListaPedido: RecyclerView.Adapter<ListaPedido.MiViewHolder>() {
    private var listaPedido:ArrayList<Pedido> = ArrayList()
    private lateinit var context: Context

    private var onClickDeleteItem:((Pedido) -> Unit)?= null

    fun onClickDeleteItem (callback: (Pedido)-> Unit){
        this.onClickDeleteItem=callback
    }

    fun agregarDatos(items: ArrayList<Pedido>){
        this.listaPedido = items
    }

    fun contexto(context: Context){
        this.context = context
    }


    class MiViewHolder (var view: View): RecyclerView.ViewHolder(view) {
        private var mesa = view.findViewById<TextView>(R.id.tvMesa)
        private var productos = view.findViewById<TextView>(R.id.tvProductos)
        private var cliente = view.findViewById<TextView>(R.id.tvCLiente)
        var despacho = view.findViewById<ImageView>(R.id.ivDespacho)

        fun bindView(p: Pedido){
            mesa.text = p.tipo_mesa
            productos.text = p.producto
            cliente.text = p.cliente
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MiViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_pedidos,parent,false)
    )

    override fun onBindViewHolder(holder: ListaPedido.MiViewHolder, position: Int) {
        val pItem = listaPedido[position]
        holder.bindView(pItem)

        holder.despacho.setOnClickListener{
            onClickDeleteItem?.invoke(pItem)
        }
    }

    override fun getItemCount(): Int {
        return listaPedido.size
    }
}
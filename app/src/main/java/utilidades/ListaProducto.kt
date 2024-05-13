package utilidades

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajofinal.R
import com.example.trabajofinal.RegistroProducto
import entidades.Producto

class ListaProducto: RecyclerView.Adapter<ListaProducto.MiViewHolder>() {
    private var listaProducto:ArrayList<Producto> = ArrayList()
    private lateinit var context: Context

    private var onClickDeleteItem:((Producto) -> Unit)?= null

    fun onClickDeleteItem (callback: (Producto)-> Unit){
        this.onClickDeleteItem=callback
    }

    fun agregarDatos(items: ArrayList<Producto>){
        this.listaProducto = items
    }

    fun contexto(context: Context){
        this.context = context
    }


    class MiViewHolder (var view: View):RecyclerView.ViewHolder(view) {
        private var nombre = view.findViewById<TextView>(R.id.tvNombreProducto)
        private var descripcion = view.findViewById<TextView>(R.id.tvDescripcion)
        private var precio = view.findViewById<TextView>(R.id.tvPrecio)
        var editar = view.findViewById<ImageView>(R.id.ivEditarMenu)
        var eliminar = view.findViewById<ImageView>(R.id.ivEliminarMenu)

        fun bindView(p: Producto){
            nombre.text = p.nombre_producto
            descripcion.text = p.descripcion
            precio.text = p.precio.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MiViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_menu,parent,false)
    )

    override fun onBindViewHolder(holder: ListaProducto.MiViewHolder, position: Int) {
        val pItem = listaProducto[position]
        holder.bindView(pItem)

        holder.editar.setOnClickListener {
            val intent = Intent(context, RegistroProducto::class.java)
            intent.putExtra("var_id", listaProducto[position].id)
            intent.putExtra("var_nombre_producto", listaProducto[position].nombre_producto)
            intent.putExtra("var_descripcion", listaProducto[position].descripcion)
            intent.putExtra("var_precio", listaProducto[position].precio.toString())
            context.startActivity(intent)
        }
        holder.eliminar.setOnClickListener{
            onClickDeleteItem?.invoke(pItem)
        }
    }


    override fun getItemCount(): Int {
        return listaProducto.size
    }
}
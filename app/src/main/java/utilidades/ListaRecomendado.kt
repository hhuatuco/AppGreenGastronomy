package utilidades

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajofinal.R
import entidades.Producto

class ListaRecomendado: RecyclerView.Adapter<ListaRecomendado.MiViewHolder>() {
    
    private var listaRecomendado:ArrayList<Producto> = ArrayList()
    private lateinit var context: Context

    fun agregarDatos(items: ArrayList<Producto>){
        this.listaRecomendado = items
    }

    fun contexto(context: Context){
        this.context = context
    }

    class MiViewHolder (var view: View): RecyclerView.ViewHolder(view) {
        private var nombre = view.findViewById<TextView>(R.id.tvNombreProducto)
        //private var descripcion = view.findViewById<TextView>(R.id.tvDescripcion)
        private var precio = view.findViewById<TextView>(R.id.tvPrecioProducto)

        //var filaEditar = view.findViewById<ImageButton>(R.id.filaEditar)

        fun bindView(p: Producto){
            nombre.text = p.nombre_producto
            //descripcion.text = p.descripcion
            precio.text = "S/. "+p.precio.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MiViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_recomendados,parent,false)
    )

    override fun onBindViewHolder(holder: ListaRecomendado.MiViewHolder, position: Int) {
        val pItem = listaRecomendado[position]
        holder.bindView(pItem)

        /*holder.filaEditar.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }*/
    }

    override fun getItemCount(): Int {
        return listaRecomendado.size
    }
}
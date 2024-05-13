package utilidades

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajofinal.R
import entidades.Producto

class ListaCarta: RecyclerView.Adapter<ListaCarta.MiViewHolder>() {

    private var listaCarta:ArrayList<Producto> = ArrayList()
    private var selectedItems: ArrayList<Producto> = ArrayList()
    private lateinit var context: Context

    fun agregarDatos(items: ArrayList<Producto>){
        this.listaCarta = items
    }

    fun contexto(context: Context){
        this.context = context
    }

    inner class MiViewHolder (var view: View): RecyclerView.ViewHolder(view) {
        private var nombre = view.findViewById<TextView>(R.id.tvNombreCarta)
        private var descripcion = view.findViewById<TextView>(R.id.tvDescripcionCarta)
        private var precio = view.findViewById<TextView>(R.id.tvPrecioCarta)
        private var checkLista = view.findViewById<CheckBox>(R.id.checkLista)
        private var linearLayout = view.findViewById<LinearLayout>(R.id.linearLayoutMenu)

        fun bindView(p: Producto){
            nombre.text = p.nombre_producto
            descripcion.text = p.descripcion
            precio.text = "S/. "+p.precio.toString()

            checkLista.isChecked = selectedItems.contains(p)

            // Manejar eventos de clic en CheckBox
            checkLista.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    toggleSelection(p)
                } else {
                    toggleSelection(p)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MiViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_todo_menu, parent, false)
        return MiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListaCarta.MiViewHolder, position: Int) {
        val pItem = listaCarta[position]
        holder.bindView(pItem)
    }

    override fun getItemCount(): Int {
        return listaCarta.size
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun toggleSelection(item: Producto) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
        } else {
            selectedItems.add(item)
        }
        notifyDataSetChanged()
    }

    fun isItemSelected(item: Producto): Boolean {
        return selectedItems.contains(item)
    }

    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItems(): ArrayList<Producto> {
        return selectedItems
    }

}
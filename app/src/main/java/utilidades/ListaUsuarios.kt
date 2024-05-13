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
import com.example.trabajofinal.Usuario
import entidades.UsuarioE

class ListaUsuarios: RecyclerView.Adapter<ListaUsuarios.MiViewHolder>() {

    private var listaUsuarios:ArrayList<UsuarioE> = ArrayList()
    private lateinit var context: Context
    private var onClickDeleteItem:((UsuarioE) -> Unit)?= null

    fun onClickDeleteItem (callback: (UsuarioE)-> Unit){
        this.onClickDeleteItem=callback
    }


    fun agregarDatos(items: ArrayList<UsuarioE>){
        this.listaUsuarios = items
    }

    fun contexto(context: Context){
        this.context = context
    }


    class MiViewHolder (var view: View):RecyclerView.ViewHolder(view) {
        private var dni = view.findViewById<TextView>(R.id.tvDni)
        private var nombre = view.findViewById<TextView>(R.id.tvNombre)
        private var rol = view.findViewById<TextView>(R.id.tvRol)
        var editar = view.findViewById<ImageView>(R.id.ivEditarUsuario)
        var eliminar = view.findViewById<ImageView>(R.id.ivEliminarUsuario)


        fun bindView(u: UsuarioE){
            dni.text = u.dni
            nombre.text = u.nombre
            rol.text = u.rol
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = MiViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_usuario,parent,false)
    )

    override fun onBindViewHolder(holder: ListaUsuarios.MiViewHolder, position: Int) {
        val uItem = listaUsuarios[position]
        holder.bindView(uItem)

        holder.editar.setOnClickListener {
            val intent = Intent(context, Usuario::class.java)
            intent.putExtra("var_id", listaUsuarios[position].id)
            intent.putExtra("var_nombre", listaUsuarios[position].nombre)
            intent.putExtra("var_dni", listaUsuarios[position].dni)
            intent.putExtra("var_rol", listaUsuarios[position].rol)
            intent.putExtra("var_telefono", listaUsuarios[position].telefono)
            intent.putExtra("var_usuario", listaUsuarios[position].usuario)
            intent.putExtra("var_contraseña", listaUsuarios[position].contraseña)
            context.startActivity(intent)
        }
        holder.eliminar.setOnClickListener{
            onClickDeleteItem?.invoke(uItem)
        }
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }

}
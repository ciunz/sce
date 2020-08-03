package sen.com.sce.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_menu.view.*
import sen.com.sce.R
import sen.com.sce.model.Menu
import sen.com.sce.model.Type

/**
 * Created by korneliussendy on 01/08/20,
 * at 23.07.
 * SCE
 */
class AdaMenu(private val items: ArrayList<Menu>) : RecyclerView.Adapter<AdaMenu.VH>() {
    var onItemClick: ((item: Menu, pos: Int) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.cell_menu, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.v.setOnClickListener { onItemClick?.invoke(item, position) }
        item.iconRes?.let {
            holder.v.ivIcon?.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.v.ivIcon.context,
                    it
                )
            )
        }
        holder.v.tvTitle?.text = item.title
    }

    class VH(val v: View) : RecyclerView.ViewHolder(v)
}
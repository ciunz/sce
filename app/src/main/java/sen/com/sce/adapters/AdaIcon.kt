package sen.com.sce.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_extra_part.view.*
import sen.com.sce.R

/**
 * Created by korneliussendy on 02/08/20,
 * at 16.30.
 * SCE
 */

class AdaIcon(val items: List<Int>) : RecyclerView.Adapter<AdaIcon.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.cell_extra_part, parent, false))

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.v.ivIcon?.setImageDrawable(ContextCompat.getDrawable(holder.v.context, item))
    }

    class VH(val v: View) : RecyclerView.ViewHolder(v)
}
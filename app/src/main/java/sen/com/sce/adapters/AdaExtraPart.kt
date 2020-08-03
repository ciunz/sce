package sen.com.sce.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_extra_part.view.*
import sen.com.sce.R
import sen.com.sce.model.ExtraPart
import sen.com.sce.model.getPartDrawable
import sen.com.sce.utils.ViewUtil.gone
import sen.com.sce.utils.ViewUtil.visible
import sen.com.sce.utils.toRupiah

/**
 * Created by korneliussendy on 02/08/20,
 * at 16.06.
 * SCE
 */

open class AdaExtraPart(protected val items: ArrayList<ExtraPart?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onDeleteClick: ((item: ExtraPart?, pos: Int) -> Unit)? = null
    var onEditClick: ((item: ExtraPart?, pos: Int) -> Unit)? = null
    var onItemSelected: ((item: ExtraPart?, pos: Int) -> Unit)? = null
    var enableUpdate = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.cell_extra_part, parent, false))

    fun addItems(items: ArrayList<ExtraPart?>) {
        this.items.addAll(items)
    }

    fun clear() {
        items.clear()
    }

    override fun getItemCount() = items.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        val h = holder as VH

        h.v.ivIcon?.setImageDrawable(item?.getPartDrawable(h.v.context))
        h.v.tvTitle?.text = item?.name
        h.v.tvPrice?.text = item?.price.toRupiah()
        h.v.ivDelete?.setOnClickListener { onDeleteClick?.invoke(item, position) }
        h.v.ivEdit?.setOnClickListener { onEditClick?.invoke(item, position) }

        if (enableUpdate) {
            visible(h.v.ivEdit, h.v.ivDelete)
            h.v.vParent?.setOnClickListener(null)
        } else {
            h.v.vParent?.setOnClickListener { onItemSelected?.invoke(item, position) }
            gone(h.v.ivEdit, h.v.ivDelete)
        }
    }

    class VH(val v: View) : RecyclerView.ViewHolder(v)
}
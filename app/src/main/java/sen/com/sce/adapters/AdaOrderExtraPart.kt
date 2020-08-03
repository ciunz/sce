package sen.com.sce.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_extra_part_order.view.*
import sen.com.sce.R
import sen.com.sce.model.OrderExtraPart
import sen.com.sce.model.getPartDrawable
import sen.com.sce.utils.format
import sen.com.sce.utils.toRupiah

/**
 * Created by korneliussendy on 03/08/20,
 * at 00.12.
 * SCE
 */
class AdaOrderExtraPart(private val items: ArrayList<OrderExtraPart?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_ADD = 11
    private val TYPE_DATA = 22

    var onAddClicked: (() -> Unit)? = null
    var onItemUpdated: (() -> Unit)? = null

    fun addItem(item: OrderExtraPart?) {
        val clearItem = items.filterNotNull()
        items.clear()
        if (item == null) items.addAll(clearItem + listOf(item))
        else items.addAll(clearItem + listOf(item, null))
        notifyDataSetChanged()
    }

    fun getTotal() = items.filterNotNull().sumByDouble {
        it.price * it.quantity
    }


    override fun getItemViewType(position: Int): Int {
        items.getOrNull(position)?.let { return TYPE_DATA }
        return TYPE_ADD
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ADD -> AddVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_extra_part_add, parent, false)
            )
            else -> DataVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.cell_extra_part_order, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AddVH -> bindAdd(holder, items[position], position)
            is DataVH -> bindData(holder, items[position], position)
        }
    }

    private fun bindAdd(holder: AddVH, extraPart: OrderExtraPart?, position: Int) {
        holder.v.setOnClickListener { onAddClicked?.invoke() }
    }

    private fun bindData(h: DataVH, item: OrderExtraPart?, position: Int) {
        h.v.ivIcon?.setImageDrawable(item?.getPartDrawable(h.v.context))
        h.v.tvTitle?.text = item?.name
        h.v.tvPrice?.text = item?.price.toRupiah()
        h.v.ivDelete?.setOnClickListener {
            items.removeAt(position)
            notifyDataSetChanged()
            onItemUpdated?.invoke()
        }
        h.v.tvQuantity.text = (item?.quantity ?: 0.0).format()
        h.v.ivMinus.setOnClickListener {
            item?.quantity?.let {
                if (it > 0) {
                    items[position]?.quantity = it - 1
                    notifyItemChanged(position)
                    onItemUpdated?.invoke()
                }
            }
        }
        h.v.ivPlus.setOnClickListener {
            item?.quantity?.let {
                items[position]?.quantity = it + 1
                notifyItemChanged(position)
                onItemUpdated?.invoke()
            }
        }
    }

    class AddVH(val v: View) : RecyclerView.ViewHolder(v)
    class DataVH(val v: View) : RecyclerView.ViewHolder(v)

    override fun getItemCount() = items.size
}
package sen.com.sce.pages

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.a_extra_parts.*
import sen.com.sce.R
import sen.com.sce.adapters.AdaExtraPart
import sen.com.sce.base.BaseActivity
import sen.com.sce.fragments.FPartUpdateBos
import sen.com.sce.manager.DataManager
import sen.com.sce.model.ExtraPart
import sen.com.sce.utils.DividerItemDecorator

/**
 * Created by korneliussendy on 02/08/20,
 * at 17.00.
 * SCE
 */

class AExtraParts : BaseActivity() {
    private val manager by lazy { DataManager(this) }
    private val adapter by lazy { AdaExtraPart(ArrayList()) }

    private var mode: Mode = Mode.NORMAL

    override fun contentView() = R.layout.a_extra_parts
    override fun showToolbar() = true
    override fun showLeftIcon() = true
    override fun initView() {
        title = "Tambahan"
        (intent.getSerializableExtra("MODE") as Mode?)?.let { this.mode = it }

        fabAdd.setOnClickListener { showUpdateDialog(null) }
        val manager = LinearLayoutManager(this)
        rvList.layoutManager = manager
        rvList.adapter = adapter
        rvList.addItemDecoration(
            DividerItemDecorator(ContextCompat.getDrawable(this, R.drawable.divider))
        )
        adapter.enableUpdate = mode == Mode.NORMAL
        adapter.onItemSelected = { item, _ ->
            setResult(Activity.RESULT_OK, Intent().putExtra("EXTRA_PART", item))
            finish()
        }
        loadData()
    }

    private fun showUpdateDialog(part: ExtraPart?) {
        FPartUpdateBos()
            .apply {
                this.extraPart = part
                onPartUpdated = { loadData() }
            }
            .show(supportFragmentManager, "UPDATE")
    }

    private fun loadData() {
        adapter.clear()
        adapter.notifyDataSetChanged()
        adapter.addItems(ArrayList(manager.getExtraPart()))
        adapter.onDeleteClick = { item, _ ->
            item?.let { showDialogDeletePart(item) }
        }
        adapter.onEditClick = { item, _ -> showUpdateDialog(item) }
    }

    private fun showDialogDeletePart(part: ExtraPart) {
        AlertDialog.Builder(this)
            .setTitle("Hapus")
            .setMessage("Anda yakin untuk menghapus ${part.name}?")
            .setPositiveButton("Ya") { dialog, _ ->
                manager.removeExtraPart(part)
                loadData()
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    enum class Mode {
        NORMAL, PICKER
    }
}
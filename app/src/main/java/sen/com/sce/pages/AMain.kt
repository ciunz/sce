package sen.com.sce.pages

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.a_main.*
import sen.com.sce.BuildConfig
import sen.com.sce.R
import sen.com.sce.adapters.AdaMenu
import sen.com.sce.base.BaseActivity
import sen.com.sce.model.Menu
import sen.com.sce.utils.ItemOffsetDecorator
import sen.com.sce.utils.dp

class AMain : BaseActivity() {
    private val adapter by lazy { AdaMenu(ArrayList(Menu.values().asList())) }
    override fun contentView() = R.layout.a_main
    override fun setHeaderView(inflater: LayoutInflater, root: ViewGroup): View {
        val v = inflater.inflate(R.layout.header_a_main, root)
        v.findViewById<TextView?>(R.id.tvVersion)?.text = BuildConfig.VERSION_NAME
        return v
    }


    override fun initView() {
        title = "Surya Citra"
        val manager = GridLayoutManager(this, 3)
        rvList.layoutManager = manager
        rvList.adapter = adapter
        rvList.addItemDecoration(ItemOffsetDecorator(4.dp))
        adapter.onItemClick = { menu, _ ->
            when (menu) {
                Menu.MENU_SETTING -> startActivity(Intent(this, ASettings::class.java))
                Menu.MENU_BOX_PANEL -> alert("Coming Soon!")
                Menu.MENU_EXIT -> onBackPressed()
                else -> startActivity(Intent(this, ACalculator::class.java)
                    .apply { putExtra("TYPE", menu.type) })
            }
        }

    }


    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Keluar")
            .setMessage("Anda yakin?")
            .setPositiveButton("Ya") { dialog, _ ->
                dialog.dismiss()
                super.onBackPressed()
            }
            .setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
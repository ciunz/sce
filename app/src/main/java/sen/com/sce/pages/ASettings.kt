package sen.com.sce.pages

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.a_main.*
import sen.com.sce.R
import sen.com.sce.adapters.AdaMenuSettings
import sen.com.sce.base.BaseActivity
import sen.com.sce.model.MenuSetting
import sen.com.sce.utils.ItemOffsetDecorator
import sen.com.sce.utils.dp

/**
 * Created by korneliussendy on 02/08/20,
 * at 15.42.
 * SCE
 */

class ASettings : BaseActivity() {
    private val adapter by lazy { AdaMenuSettings(ArrayList(MenuSetting.values().toList())) }
    override fun contentView() = R.layout.a_settings
    override fun showLeftIcon() = true
    override fun initView() {
        title = "Pengaturan"
        val manager = GridLayoutManager(this, 3)
        rvList.layoutManager = manager
        rvList.adapter = adapter
        rvList.addItemDecoration(ItemOffsetDecorator(4.dp))
        adapter.onItemClick = { menu, _ ->
            when (menu) {
                MenuSetting.EXTRA_PARTS -> startActivity(Intent(this, AExtraParts::class.java))
            }
        }
    }
}
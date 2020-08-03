package sen.com.sce.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.base_activity.*
import sen.com.sce.R
import kotlin.math.abs

/**
 * Created by korneliussendy on 01/08/20,
 * at 20.04.
 * SCE
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var vsContent: ViewStub

    @LayoutRes
    abstract fun contentView(): Int

    open var onAppbarOffsetChanged: ((offset: Float) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        setSupportActionBar(findViewById(R.id.toolbar))

        flHeader?.let {
            if (it.childCount > 0) it.removeAllViews()
            setHeaderView(LayoutInflater.from(this), it)
        }

        appBar?.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
                val offset = abs(i / appBarLayout.totalScrollRange.toFloat())
                updateViews(offset)
            })

        toolbar?.visibility = if (showToolbar()) View.VISIBLE else View.GONE
        ivBaseLeft?.visibility = if (showLeftIcon()) View.VISIBLE else View.INVISIBLE
        ivBaseLeft?.setOnClickListener { onBackPressed() }

        vsContent = findViewById(R.id.vsContent)
        vsContent.layoutResource = contentView()
        vsContent.inflate()
        initView()
    }

    private fun updateViews(offset: Float) {
        onAppbarOffsetChanged?.invoke(offset)
        tvBaseTitle?.alpha = if (offset < 0.5) 0f else (2 * (offset - 0.5f))
    }

    abstract fun initView()

    open fun showToolbar() = true

    open fun showLeftIcon() = false

    override fun setTitle(title: CharSequence?) {
        tvBaseTitle?.text = title
    }

    override fun setTitle(titleId: Int) {
        tvBaseTitle?.setText(titleId)
    }

    fun setSubtitle(subtitle: CharSequence?) {
        tvBaseSubTitle?.visibility = if (subtitle.isNullOrEmpty()) View.GONE else View.VISIBLE
        tvBaseSubTitle?.text = subtitle
    }

    open fun setHeaderView(inflater: LayoutInflater, root: ViewGroup): View? = null

    fun toast(message: Any?) {
        message?.let { Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show() }
    }

    fun alert(message: Any?) {
        message?.let {
            AlertDialog.Builder(this)
                .setMessage(it.toString())
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }
}
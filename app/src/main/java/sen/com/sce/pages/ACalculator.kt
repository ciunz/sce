package sen.com.sce.pages

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.a_calculator.*
import sen.com.sce.R
import sen.com.sce.adapters.AdaOrderExtraPart
import sen.com.sce.base.BaseActivity
import sen.com.sce.manager.DataManager
import sen.com.sce.model.ExtraPart
import sen.com.sce.model.OrderExtraPart
import sen.com.sce.model.Type
import sen.com.sce.utils.*
import sen.com.sce.utils.ViewUtil.gone
import sen.com.sce.utils.ViewUtil.invisible
import sen.com.sce.utils.ViewUtil.visible

/**
 * Created by korneliussendy on 02/08/20,
 * at 11.09.
 * SCE
 */
class ACalculator : BaseActivity() {
    private var type: Type? = null
    private var height: Double = 0.0
    private var width: Double = 0.0
    private var diameter: Double = 0.0
    private var quantity: Int = 1
    private var total: Double = 0.0
    private var priceEach: Double = 0.0
    private var dimension: Float = 0f

    private val manager by lazy { DataManager(this) }
    private val adapter by lazy { AdaOrderExtraPart(ArrayList()) }

    override fun contentView() = R.layout.a_calculator
    override fun showToolbar() = true
    override fun showLeftIcon() = true
    override fun initView() {
        type = intent.getSerializableExtra("TYPE") as Type?
        if (type == null) {
            Toast.makeText(this, "Type Required", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        title = "Kalkulator"

        tvTotal.text = 0.0.toRupiah()
        etHeight.watchNumber {
            this.height = it.toDouble()
            calculate()
        }
        etWidth.watchNumber {
            this.width = it.toDouble()
            calculate()
        }
        etDiameter.watchNumber {
            this.diameter = it.toDouble()
            calculate()
        }
        etQuantity.watchNumber {
            this.quantity = it
            calculate()
        }

        vHeight.setOnClickListener { etHeight.requestFocus() }
        vWidth.setOnClickListener { etWidth.requestFocus() }
        vDiameter.setOnClickListener { etDiameter.requestFocus() }
        vQuantity.setOnClickListener { etQuantity.requestFocus() }

        etQuantity.setText("1")
        btnCalculate.setOnClickListener { calculate() }
        setSubtitle(type?.title)
        setupView(type)
        setupExtraParts()
    }

    private fun setupView(type: Type?) {
        gone(vHeight, vDiameter, vWidth, vQuantity)
        when (type) {
            Type.BAND, Type.NOZZLE -> {
                showHeight()
                showDiameter()
                showQuantity()
                showExtra()
            }
            Type.STRIP -> {
                showHeight()
                showWidth()
                showQuantity()
                showExtra()
            }
            else -> showUnderConstruction()
        }
    }

    private fun setupExtraParts() {
        val manager = GridLayoutManager(this, 3)
        rvList.layoutManager = manager
        rvList.adapter = adapter
        rvList.addItemDecoration(ItemOffsetDecorator(4.dp))
        adapter.addItem(null)
        adapter.onAddClicked = { toPickExtraPartPage() }
        adapter.onItemUpdated = { calculate() }
    }

    private fun showHeight() {
        visible(vHeight)
    }

    private fun showWidth() {
        visible(vWidth)
    }

    private fun showDiameter() {
        visible(vDiameter)
    }

    private fun showQuantity() {
        visible(vQuantity)
    }

    private fun showExtra() {

    }

    private fun showUnderConstruction() {

    }

    @SuppressLint("SetTextI18n")
    private fun calculate() {
        priceEach = when (type) {
            Type.STRIP -> {
                dimension = type?.calculateDimension(width, height) ?: 0f
                type.calculatePrice(width, height)
            }
            Type.NOZZLE, Type.BAND -> {
                dimension = type?.calculateDimension(diameter, height) ?: 0f
                type.calculatePrice(diameter, height)
            }
            else -> {
                dimension = 0f
                0.0
            }
        }
        tvDimension.text = "Dimensi ${dimension.toDouble().format()}"
        if (dimension > 0) visible(tvDimension)
        else gone(tvDimension)

        tvEach.text = "@ ${priceEach.toRupiah()}"
        if (priceEach > 0) visible(tvEach)
        else invisible(tvEach)

        val price = priceEach * quantity + adapter.getTotal()
        if (price != total) {
            ValueAnimator.ofFloat(total.toFloat(), price.toFloat()).apply {
                duration = 400
                interpolator = FastOutSlowInInterpolator()
                addUpdateListener { animation ->
                    tvTotal.text = (animation.animatedValue as Float).toDouble().toRupiah()
                }
            }.start()

        } else {
            tvTotal.text = price.toRupiah()
        }
        total = price
    }

    private fun toPickExtraPartPage() {
        startActivityForResult(
            Intent(this, AExtraParts::class.java).apply {
                putExtra("MODE", AExtraParts.Mode.PICKER)
            }, 88
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 88 && resultCode == Activity.RESULT_OK) {
            data?.getSerializableExtra("EXTRA_PART")?.let {
                adapter.addItem(OrderExtraPart().consume(it as ExtraPart, 1))
                calculate()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
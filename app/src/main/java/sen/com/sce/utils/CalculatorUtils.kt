package sen.com.sce.utils

import android.util.Log
import sen.com.sce.model.PriceList
import sen.com.sce.model.Type
import kotlin.math.abs

/**
 * Created by korneliussendy on 02/08/20,
 * at 14.02.
 * SCE
 */
fun Type?.calculatePrice(widthOrDia: Double, height: Double): Double {
    this.calculateDimension(widthOrDia, height)?.let {
        Log.d("Type", "x = ${this?.name}")
        Log.d("DIAMETER", "x = $widthOrDia")
        Log.d("DIAMETER", "y = $height")
        Log.d("DIAMETER", "d = $it")
        val p = getPrice(it)
        Log.d("Price", "p = $p")
        return p
    }
    return 0.0
}

fun Type?.calculateDimension(x: Double, height: Double): Float? =
    when (this) {
        Type.STRIP -> x.toFloat() * height.toFloat()
        Type.BAND, Type.NOZZLE -> 3.14f * x.toFloat() * height.toFloat()
        else -> null
    }

fun Type?.getPrice(dimension: Float): Double {
    val priceHeater = PriceList.priceList.firstOrNull { dimension in it.min..it.max }
    return when {
        priceHeater == null -> 0.0
        priceHeater.price <= 0 -> dimension * abs(priceHeater.price)
        else -> priceHeater.price
    }

}


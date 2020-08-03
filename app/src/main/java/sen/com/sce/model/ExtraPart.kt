package sen.com.sce.model

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import sen.com.sce.R
import java.io.Serializable

/**
 * Created by korneliussendy on 02/08/20,
 * at 15.39.
 * SCE
 */
open class ExtraPart : Serializable {
    var id: Int? = null
    var name: String = ""
    var price: Double = 0.0
    var icon: Int? = null
}

fun ExtraPart.getPartDrawable(context: Context) =
    ContextCompat.getDrawable(
        context,
        when (icon) {
            else -> R.drawable.ic_default_part
        }
    )
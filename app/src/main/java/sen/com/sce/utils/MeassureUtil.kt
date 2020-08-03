package sen.com.sce.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by korneliussendy on 01/08/20,
 * at 23.32.
 * SCE
 */

val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
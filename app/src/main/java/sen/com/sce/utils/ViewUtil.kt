package sen.com.sce.utils

import android.view.View

/**
 * Created by korneliussendy on 02/08/20,
 * at 20.01.
 * SCE
 */
object ViewUtil {
    fun invisible(vararg views: View?) =
        views.forEach { if (it != null) it.visibility = View.INVISIBLE }

    fun visible(vararg views: View?) =
        views.forEach { if (it != null) it.visibility = View.VISIBLE }

    fun gone(vararg views: View?) = views.forEach { if (it != null) it.visibility = View.GONE }

    fun enable(vararg views: View?) = views.forEach { if (it != null) it.isEnabled = true }

    fun disable(vararg views: View?) = views.forEach { if (it != null) it.isEnabled = false }

}
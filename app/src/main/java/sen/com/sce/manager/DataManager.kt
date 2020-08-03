package sen.com.sce.manager

import android.content.Context
import com.google.gson.Gson
import sen.com.sce.model.ExtraPart
import sen.com.sce.utils.Constants.PREF_KEY
import sen.com.sce.utils.fromJson

/**
 * Created by korneliussendy on 02/08/20,
 * at 15.49.
 * SCE
 */
class DataManager(context: Context) {
    private val pref = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
    private val KEY_EXTRA_PART = "EXTRA_PARTS"

    fun getExtraPart() =
        Gson().fromJson<ArrayList<ExtraPart>>(pref.getString(KEY_EXTRA_PART, "")).orEmpty()

    fun saveExtraParts(list: List<ExtraPart>) {
        pref.edit().putString(KEY_EXTRA_PART, Gson().toJson(list)).apply()
    }

    fun saveExtraPart(part: ExtraPart) {
        val parts = getExtraPart().filter { it.id != part.id }
        if (part.id == null) {
            part.id = (parts.lastOrNull()?.id ?: 0) + 1
        }
        saveExtraParts((parts + listOf(part)).sortedBy { it.id })
    }

    fun removeExtraPart(part: ExtraPart) {
        saveExtraParts(getExtraPart().filter { it.id != part.id })
    }
}
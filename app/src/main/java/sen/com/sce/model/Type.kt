package sen.com.sce.model

import sen.com.sce.R

/**
 * Created by korneliussendy on 01/08/20,
 * at 19.35.
 * SCE
 */

enum class Type(val title: String, val iconRes: Int? = null) {
    BAND("Band Heater", R.drawable.ic_band_heater),
    STRIP("Strip Heater", R.drawable.ic_strip_heater),
    NOZZLE("Nozzle", R.drawable.ic_nozzle),
    BOX_PANEL("Panel", R.drawable.ic_box_panel)
}
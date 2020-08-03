package sen.com.sce.model

import sen.com.sce.R

/**
 * Created by korneliussendy on 02/08/20,
 * at 10.53.
 * SCE
 */

enum class Menu(
    val type: Type?,
    val title: String? = type?.title,
    val iconRes: Int? = type?.iconRes
) {
    MENU_BAND(Type.BAND),
    MENU_STRIP(Type.STRIP),
    MENU_NOZZLE(Type.NOZZLE),
    MENU_BOX_PANEL(Type.BOX_PANEL),
    MENU_SETTING(null, "Pengaturan", R.drawable.ic_setting),
    MENU_EXIT(null, "Keluar", R.drawable.ic_logout)
}

enum class MenuSetting(val title: String?, val iconRes: Int?) {
    EXTRA_PARTS("Tambahan", R.drawable.ic_default_part)
}
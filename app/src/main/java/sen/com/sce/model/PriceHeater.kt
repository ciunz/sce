package sen.com.sce.model

/**
 * Created by korneliussendy on 02/08/20,
 * at 15.17.
 * SCE
 */
data class PriceHeater(val min: Float, val max: Float, val price: Double)

object PriceList {
    val priceList: List<PriceHeater> = listOf(
        PriceHeater(Float.MIN_VALUE, 15f, 62000.0),
        PriceHeater(15f, 20f, 65000.0),
        PriceHeater(20f, 40f, 67000.0),
        PriceHeater(40f, 50f, 69000.0),
        PriceHeater(50f, 60f, 72000.0),
        PriceHeater(60f, 70f, 77000.0),
        PriceHeater(70f, 80f, 80000.0),
        PriceHeater(80f, 90f, 88000.0),
        PriceHeater(90f, 110f, 95000.0),
        PriceHeater(110f, 140f, 102000.0),
        PriceHeater(140f, 180f, 109000.0),
        PriceHeater(180f, 220f, 120000.0),
        PriceHeater(220f, 260f, 130000.0),
        PriceHeater(260f, 300f, 140000.0),
        PriceHeater(300f, 340f, 148000.0),
        PriceHeater(340f, 380f, 153000.0),
        PriceHeater(380f, 420f, 164000.0),
        PriceHeater(420f, 460f, 174000.0),
        PriceHeater(460f, 500f, 184000.0),
        PriceHeater(500f, 540f, 192000.0),
        PriceHeater(540f, 580f, 203000.0),
        PriceHeater(580f, 620f, 215000.0),
        PriceHeater(620f, 660f, 226000.0),
        PriceHeater(660f, 700f, 236000.0),
        PriceHeater(700f, 740f, 248000.0),
        PriceHeater(740f, 780f, 255000.0),
        PriceHeater(780f, 820f, 269000.0),
        PriceHeater(820f, 860f, 280000.0),
        PriceHeater(860f, 900f, 291000.0),
        PriceHeater(900f, 940f, 299000.0),
        PriceHeater(940f, 1000f, 312000.0),
        PriceHeater(940f, Float.MAX_VALUE, -319.0)
    )
}
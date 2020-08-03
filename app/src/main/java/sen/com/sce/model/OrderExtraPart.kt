package sen.com.sce.model

/**
 * Created by korneliussendy on 03/08/20,
 * at 01.32.
 * SCE
 */
class OrderExtraPart : ExtraPart() {
    var quantity: Double = 0.0
    fun consume(part: ExtraPart, quantity: Int = 0): OrderExtraPart {
        id = part.id
        name = part.name
        price = part.price
        icon = part.icon
        this.quantity = quantity.toDouble()
        return this
    }
}
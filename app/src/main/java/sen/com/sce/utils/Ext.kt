package sen.com.sce.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

/**
 * Created by korneliussendy on 02/08/20,
 * at 15.53.
 * SCE
 */

inline fun <reified T> Gson.fromJson(json: String?): T? =
    try {
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        null
    }

fun EditText.watchText(onTextChange: (text: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChange(s.toString())
        }
    })
}


fun EditText.watchMoney(onTextChange: ((value: Double) -> Unit)?) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val str = s.toString()
            if (str.isEmpty()) return
            this@watchMoney.removeTextChangedListener(this)
            val cleanString = str.replace("[Rp \$,.]".toRegex(), "")
            val parsed = cleanString.toDoubleOrNull() ?: 0.0
            val formatted = parsed.toRupiah()
            this@watchMoney.setText(formatted)
            this@watchMoney.setSelection(formatted.length)
            this@watchMoney.addTextChangedListener(this)
            onTextChange?.invoke(cleanString.toDoubleOrNull() ?: 0.0)
        }
    })
}

fun EditText.watchNumber(onTextChange: ((value: Int) -> Unit)?) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val str = s.toString()
//            if (str.isEmpty()) return
            this@watchNumber.removeTextChangedListener(this)
            val cleanString = str.replace("[Rp \$,.]".toRegex(), "")
            val parsed = cleanString.toDoubleOrNull() ?: 0.0
            val formatted = parsed.format(0)
            this@watchNumber.setText(formatted)
            this@watchNumber.setSelection(formatted.length)
            this@watchNumber.addTextChangedListener(this)
            onTextChange?.invoke(cleanString.toIntOrNull() ?: 0)
        }
    })
}


fun Double?.toRupiah(usePrefix: Boolean = false, digit: Int = 0): String =
    toMoney("Rp ", usePrefix, digit)

fun Double?.toMoney(): String = toMoney("")

fun Double?.toMoney(symbol: String?, usePrefix: Boolean = false, digit: Int = 0): String {
    val kurs = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.currencySymbol = symbol ?: ""
    format.monetaryDecimalSeparator = ','
    format.groupingSeparator = '.'
    kurs.decimalFormatSymbols = format
    kurs.minimumFractionDigits = digit
    kurs.maximumFractionDigits = digit
    if (usePrefix) {
        kurs.positivePrefix = if (this.orZero() == 0.0) "$symbol" else "+ $symbol"
        kurs.negativePrefix = "- $symbol"
    }
    return kurs.format(this ?: 0.0)
}

fun Double?.format(digit: Int = 0, usePrefix: Boolean = false) = format(digit, null, usePrefix)
fun Double?.format(maxDigit: Int = 0, minDigit: Int? = null, usePrefix: Boolean = false): String {
    val number = DecimalFormat.getInstance() as DecimalFormat
    val format = DecimalFormatSymbols()
    format.decimalSeparator = ','
    format.groupingSeparator = '.'
    number.decimalFormatSymbols = format
    number.maximumFractionDigits = maxDigit
    minDigit?.let { number.minimumFractionDigits = it }
    if (usePrefix) {
        number.positivePrefix = if (this.orZero() == 0.0) "" else "+"
        number.negativePrefix = "-"
    }
    return number.format(this ?: 0.0)
}

fun Float?.orZero(): Float {
    return this ?: 0f
}

fun Double?.orZero(): Double {
    return this ?: 0.0
}

fun Int?.orZero(): Int {
    return this ?: 0
}

fun Long?.orZero(): Long {
    return this ?: 0
}
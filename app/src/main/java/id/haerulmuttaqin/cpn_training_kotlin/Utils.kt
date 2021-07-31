package id.haerulmuttaqin.cpn_training_kotlin

import org.ocpsoft.prettytime.PrettyTime
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun toPrettyTime(oldStringDate: String?): String? {
    val p = PrettyTime(Locale("en-US"))
    var isTime: String? = null
    try {
        val sdf = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss",
            Locale.ENGLISH
        )
        val date = sdf.parse(oldStringDate)
        isTime = p.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return isTime
}
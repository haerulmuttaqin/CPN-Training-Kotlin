package id.haerulmuttaqin.entity.typeconverter

import androidx.room.TypeConverter
import id.haerulmuttaqin.entity.PostOwner
import org.json.JSONObject

class TagTypeConverter {
    @TypeConverter
    fun fromTag(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toTag(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }
}
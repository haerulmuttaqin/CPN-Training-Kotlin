package id.haerulmuttaqin.entity.typeconverter

import androidx.room.TypeConverter
import id.haerulmuttaqin.entity.PostOwner
import org.json.JSONObject

class PostOwnerTypeConverter {
    @TypeConverter
    fun fromPostOwner(owner: PostOwner): String {
        return JSONObject().apply {
            put("id", owner.id)
            put("firstName", owner.firstName)
            put("lastName", owner.lastName)
            put("title", owner.title)
            put("email", owner.email)
            put("picture", owner.picture)
        }.toString()
    }

    @TypeConverter
    fun toPostOwner(source: String): PostOwner {
        val json = JSONObject(source)
        return PostOwner(
            json.getString("id"),
            json.getString("firstName"),
            json.getString("lastName"),
            json.getString("title"),
            json.getString("email"),
            json.getString("picture")
        )
    }
}
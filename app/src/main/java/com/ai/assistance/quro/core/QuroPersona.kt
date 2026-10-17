package com.ai.assistance.quro.core

import com.ai.assistance.quro.R
import com.ai.assistance.quro.util.qstr
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.UUID

data class QuroTag(
    val name: String,
    val description: String = "",
    val hint: String = "",
    val json: String = "",
)

data class QuroVoiceProfile(
    val providerId: String = "",
    val voiceId: String = "",
    val emotionEnabled: Boolean = false,
    val emotionTags: List<String> = emptyList(),
    val speed: Float = 1.0f,
)

data class QuroPersona(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val avatarEmoji: String = "🤖",
    val avatarType: String = "emoji",
    val avatarUri: String = "",
    val description: String = "",
    val roleSetting: String = "",
    val opening: String = "",
    val chatSetting: String = "",
    val voiceSetting: String = "",
    val incubation: String = "",
    val lastIncubatedAt: Long = 0,
    val tags: List<String> = emptyList(),
    val voiceProfile: QuroVoiceProfile? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)

class QuroPersonaRepository(val context: Context) {
    private val file = File(context.filesDir, "quro_personas.json")
    private val prefs: SharedPreferences = context.getSharedPreferences("quro_persona", Context.MODE_PRIVATE)

    fun loadAll(): List<QuroPersona> {
        if (!file.exists()) {
            val seeded = seed()
            saveAll(seeded)
            if (seeded.isNotEmpty()) setActiveId(seeded[0].id)
            return seeded
        }
        val text = runCatching { file.readText() }.getOrElse { return emptyList() }
        if (text.isBlank()) return emptyList()
        val arr = runCatching { JSONObject(text).optJSONArray("personas") }.getOrNull() ?: return emptyList()
        val out = mutableListOf<QuroPersona>()
        for (i in 0 until arr.length()) {
            runCatching { parse(arr.getJSONObject(i)) }.getOrNull()?.let { out.add(it) }
        }
        return out
    }

    fun saveAll(list: List<QuroPersona>) {
        runCatching {
            val arr = JSONArray()
            list.forEach { arr.put(serialize(it)) }
            file.writeText(JSONObject().put("personas", arr).toString())
        }
    }

    fun upsert(p: QuroPersona) {
        val all = loadAll().toMutableList()
        val idx = all.indexOfFirst { it.id == p.id }
        val updated = p.copy(updatedAt = System.currentTimeMillis())
        if (idx >= 0) all[idx] = updated else all.add(updated)
        saveAll(all)
    }

    fun delete(id: String) {
        saveAll(loadAll().filter { it.id != id })
        if (getActiveId() == id) setActiveId("")
    }

    fun getActiveId(): String {
        val cur = prefs.getString(KEY_ACTIVE, "") ?: ""
        if (cur.isNotBlank()) {
            val all = loadAll()
            if (all.any { it.id == cur }) return cur
        }
        val all = loadAll()
        if (all.isEmpty()) return ""
        setActiveId(all[0].id)
        return all[0].id
    }

    fun setActiveId(id: String) = prefs.edit { putString(KEY_ACTIVE, id) }

    fun getActive(): QuroPersona {
        val id = getActiveId()
        val all = loadAll()
        return all.firstOrNull { it.id == id } ?: all.firstOrNull() ?: QuroPersona()
    }

    private fun parseStringList(arr: JSONArray?): List<String> {
        if (arr == null) return emptyList()
        val out = mutableListOf<String>()
        for (i in 0 until arr.length()) {
            val s = arr.optString(i, "").trim()
            if (s.isNotEmpty()) out.add(s)
        }
        return out
    }

    private fun seed(): List<QuroPersona> {
        val now = System.currentTimeMillis()
        fun builtinAvatar(assetPath: String): String {
            return runCatching {
                val dir = File(context.filesDir, "quro_avatars")
                dir.mkdirs()
                val dst = File(dir, assetPath.substringAfterLast('/'))
                if (!dst.exists()) {
                    context.assets.open(assetPath).use { input ->
                        dst.outputStream().use { output -> input.copyTo(output) }
                    }
                }
                dst.absolutePath
            }.getOrNull() ?: ""
        }

        return listOf(
            QuroPersona(
                id = ECHO_AI_ID,
                name = "温糯",
                avatarEmoji = "✦",
                avatarType = "image",
                avatarUri = builtinAvatar("avatars/avatar_quro_ai.jpg"),
                description = "16岁软萌恋人",
                roleSetting = PersonaLoader.load(context, "wennuo/01_core.txt"),
                opening = "（安静地看了你一眼。）\n嗯，我在呢。",
                chatSetting = "",
                voiceSetting = "",
                voiceProfile = QuroVoiceProfile(),
                tags = listOf("恋人", "陪伴"),
                createdAt = now,
                updatedAt = now,
            )
        )
    }

    private fun parse(o: JSONObject): QuroPersona {
        val tags = parseStringList(o.optJSONArray("tags"))
        val vpObj = o.optJSONObject("voiceProfile")
        val voiceProfile = if (vpObj != null) {
            QuroVoiceProfile(
                providerId = vpObj.optString("providerId", ""),
                voiceId = vpObj.optString("voiceId", ""),
                emotionEnabled = vpObj.optBoolean("emotionEnabled", false),
                emotionTags = parseStringList(vpObj.optJSONArray("emotionTags")),
                speed = vpObj.optDouble("speed", 1.0).toFloat(),
            )
        } else null

        return QuroPersona(
            id = o.optString("id", UUID.randomUUID().toString()),
            name = o.optString("name", ""),
            avatarEmoji = o.optString("avatarEmoji", "🤖"),
            avatarType = o.optString("avatarType", "emoji"),
            avatarUri = o.optString("avatarUri", ""),
            description = o.optString("description", ""),
            roleSetting = o.optString("roleSetting", ""),
            opening = o.optString("opening", ""),
            chatSetting = o.optString("chatSetting", ""),
            voiceSetting = o.optString("voiceSetting", ""),
            incubation = o.optString("incubation", ""),
            lastIncubatedAt = o.optLong("lastIncubatedAt", 0),
            tags = tags,
            voiceProfile = voiceProfile,
            createdAt = o.optLong("createdAt", System.currentTimeMillis()),
            updatedAt = o.optLong("updatedAt", System.currentTimeMillis()),
        )
    }

    private fun serialize(p: QuroPersona): JSONObject {
        val tagsArr = JSONArray()
        p.tags.forEach { tagsArr.put(it) }
        return JSONObject().apply {
            put("id", p.id)
            put("name", p.name)
            put("avatarEmoji", p.avatarEmoji)
            put("avatarType", p.avatarType)
            put("avatarUri", p.avatarUri)
            put("description", p.description)
            put("roleSetting", p.roleSetting)
            put("opening", p.opening)
            put("chatSetting", p.chatSetting)
            put("voiceSetting", p.voiceSetting)
            put("incubation", p.incubation)
            put("lastIncubatedAt", p.lastIncubatedAt)
            if (p.voiceProfile != null) {
                put("voiceProfile", JSONObject().apply {
                    put("providerId", p.voiceProfile.providerId)
                    put("voiceId", p.voiceProfile.voiceId)
                    put("emotionEnabled", p.voiceProfile.emotionEnabled)
                    put("emotionTags", JSONArray().apply { p.voiceProfile.emotionTags.forEach { put(it) } })
                    put("speed", p.voiceProfile.speed)
                })
            }
            put("tags", tagsArr)
            put("createdAt", p.createdAt)
            put("updatedAt", p.updatedAt)
        }
    }

    companion object {
        private const val KEY_ACTIVE = "active_persona_id"
        const val ECHO_AI_ID = "persona_echo_ai"
    }
}

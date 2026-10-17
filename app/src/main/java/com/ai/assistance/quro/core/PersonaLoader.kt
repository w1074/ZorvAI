package com.ai.assistance.quro.core

import android.content.Context

object PersonaLoader {
    fun load(context: Context, path: String): String {
        return runCatching {
            context.assets.open("personas/$path").bufferedReader().use { it.readText() }
        }.getOrElse { "你叫温糯。" }
    }
}

package com.ai.assistance.quro.core

import android.content.Context

object PersonaLoader {
    fun load(context: Context, fileName: String): String {
        return runCatching {
            context.assets.open("personas/$fileName")
                .bufferedReader()
                .use { it.readText() }
        }.getOrElse { "" }
    }
}

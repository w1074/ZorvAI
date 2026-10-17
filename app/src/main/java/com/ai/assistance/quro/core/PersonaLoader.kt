package com.ai.assistance.quro.core

import android.content.Context

object PersonaLoader {
    // 读取单个文件
    private fun readAsset(context: Context, path: String): String {
        return runCatching {
            context.assets.open(path).bufferedReader().use { it.readText() }
        }.getOrElse { "" }
    }

    // 按需加载并拼接：传入角色文件夹名，自动读取内部的 01, 02, 03
    fun load(context: Context, folderName: String): String {
        val sb = StringBuilder()
        
        // 1. 核心设定（必须加载）
        sb.append(readAsset(context, "personas/$folderName/01_core.txt"))
        sb.append("\n\n")
        
        // 2. 功能规则（必须加载）
        sb.append(readAsset(context, "personas/$folderName/02_function.txt"))
        sb.append("\n\n")
        
        // 3. 深度细节（可以在这里做时间判断，但为了稳定，我们先全量拼接）
        // 如果以后想晚上才加载深度陪伴，可以在这里加 if (isNightTime)
        sb.append(readAsset(context, "personas/$folderName/03_detail.txt"))
        
        return sb.toString()
    }
}

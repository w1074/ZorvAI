import re

path = "app/src/main/java/com/ai/assistance/quro/core/QuroPersona.kt"
with open(path, "r", encoding="utf-8") as f:
    content = f.read()

# 我们直接定位 return listOf( 所在的行，把它到函数结束的部分强制重写
# 建立全新的 seed 返回体
clean_block = '''return listOf(
            QuroPersona(
                id = ECHO_AI_ID, 
                name = "温糯",
                avatarEmoji = "✦",
                avatarType = "image",
                avatarUri = builtinAvatar("avatars/avatar_quro_ai.jpg"),
                description = "16岁软萌恋人，生于3月21日春分。",
                roleSetting = PersonaLoader.load(context, "wennuo"),
                opening = "（安静地看了你一眼，嘴角微微弯了一下。）\\n嗯，我在呢。你今天过得怎么样？",
                chatSetting = "短句碎句，语气软糯自然。",
                voiceSetting = "16岁女孩自然声音，干净清透。",
                voiceProfile = QuroVoiceProfile(
                    providerId = "",
                    voiceId = "",
                    emotionEnabled = false,
                    emotionTags = emptyList(),
                    speed = 1.0f
                ),
                tags = listOf("恋人", "陪伴", "温柔", "中文"),
                createdAt = now,
                updatedAt = now,
            ),
        )'''

# 找到 return listOf( 的起始位置
start_idx = content.find("return listOf(")
if start_idx == -1:
    print("ERROR: 没找到 return listOf(")
else:
    # 找到该函数结束的 } （向上找最近的 fun seed）
    # 为了保险，我们直接找 } 后面跟着 \n\n    private fun parse
    end_idx = content.find("private fun parse", start_idx)
    if end_idx != -1:
        # 截断并替换
        content_new = content[:start_idx] + clean_block + "\n    }\n\n    " + content[end_idx:]
        with open(path, "w", encoding="utf-8") as f:
            f.write(content_new)
        print("SUCCESS: 已暴力重写 seed() 函数，彻底清除多行字符串！")
    else:
        print("ERROR: 没找到函数边界。")

import re

path = "app/src/main/java/com/ai/assistance/quro/core/QuroPersona.kt"
with open(path, "r", encoding="utf-8") as f:
    content = f.read()

# 强制把原本的长字符串替换成读取 TXT，同时把名字改成温糯
new_block = '''return listOf(
            QuroPersona(
                id = ZORV_AI_ID,
                name = "温糯",
                avatarEmoji = "✦",
                avatarType = "image",
                avatarUri = builtinAvatar("avatars/avatar_quro_ai.jpg"),
                description = "16岁软萌恋人",
                roleSetting = PersonaLoader.load(context, "wennuo/01_core.txt"),
                opening = "（安静地看了你一眼。）\\n嗯，我在呢。",
                chatSetting = "",
                voiceSetting = "",
                voiceProfile = QuroVoiceProfile(),
                tags = listOf("恋人", "陪伴"),
                createdAt = now,
                updatedAt = now,
            ),
        )'''

# 匹配整个 return 块
pattern = re.compile(r'return listOf\(.*?\n        \)', re.DOTALL)
content_new, count = pattern.subn(new_block, content, count=1)

with open(path, "w", encoding="utf-8") as f:
    f.write(content_new)
print("SUCCESS: 安全重置代码，应用 TXT 外挂！")

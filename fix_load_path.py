import re
path = "app/src/main/java/com/ai/assistance/quro/core/QuroPersona.kt"
with open(path, "r", encoding="utf-8") as f:
    content = f.read()

# 把读取路径改为目录名 "wennuo"
content = content.replace('PersonaLoader.load(context, "wennuo_lover.txt")', 'PersonaLoader.load(context, "wennuo")')

with open(path, "w", encoding="utf-8") as f:
    f.write(content)
print("SUCCESS: 外挂路径已修正为 wennuo 文件夹！")

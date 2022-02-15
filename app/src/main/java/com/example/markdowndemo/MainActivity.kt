package com.example.markdowndemo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var vditor: Vditor
    lateinit var tabIconView: TabIconView
    lateinit var expandableLinearLayout: ExpandableLinearLayout
    lateinit var moreToolbarItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        expandableLinearLayout = findViewById(R.id.action_other_operate)
        vditor = findViewById<WebView>(R.id.vditor) as Vditor

        initTab()
        vditor.startLoad(true, true, object : Vditor.IVditorCallback {
            override fun onSelectPicture() {
                // ignore
            }

            /**
             * 初始化完成
             */
            override fun onLoadFinished() {
                if (vditor.isEditMode) {
                    // 如果是editMode的话，只需要调用initEditor，setCache是给编辑器赋初始值，可选
                    // vditor会根据key自动保存草稿，不需要再在安卓端保存，使用localStorage实现的
                    vditor.setCache("edit", "Vditor 是一款**所见即所得**编辑器，支持 *Markdown*。\n" +
                            "\n" +
                            "* 不熟悉 Markdown 可使用工具栏或快捷键进行排版\n" +
                            "* 熟悉 Markdown 可直接排版，也可切换为分屏预览\n" +
                            "\n" +
                            "更多细节和用法请参考 [Vditor - 浏览器端的 Markdown 编辑器](https://ld246.com/article/1549638745630)，同时也欢迎向我们提出建议或报告问题，谢谢 ❤️\n" +
                            "\n" +
                            "## 教程\n" +
                            "\n" +
                            "这是一篇讲解如何正确使用 **Markdown** 的排版示例，学会这个很有必要，能让你的文章有更佳清晰的排版。\n" +
                            "\n" +
                            "> 引用文本：Markdown is a text formatting syntax inspired\n" +
                            "\n" +
                            "## 语法指导\n" +
                            "\n" +
                            "### 普通内容\n" +
                            "\n" +
                            "这段内容展示了在内容里面一些排版格式，比如：\n" +
                            "\n" +
                            "- **加粗** - `**加粗**`\n" +
                            "- *倾斜* - `*倾斜*`\n" +
                            "- ~~删除线~~ - `~~删除线~~`\n" +
                            "- `Code 标记` - `` `Code 标记` ``\n" +
                            "- [超级链接](https://ld246.com) - `[超级链接](https://ld246.com)`\n" +
                            "- [username@gmail.com](mailto:username@gmail.com) - `[username@gmail.com](mailto:username@gmail.com)`\n" +
                            "\n" +
                            "### 提及用户\n" +
                            "\n" +
                            "@Vanessa 通过 `@User` 可以在内容中提及用户，被提及的用户将会收到系统通知。\n" +
                            "\n" +
                            "> NOTE:\n" +
                            ">\n" +
                            "> 1. @用户名之后需要有一个空格\n" +
                            "> 2. 新手没有艾特的功能权限\n" +
                            "\n" +
                            "### 表情符号 Emoji\n" +
                            "\n" +
                            "支持大部分标准的表情符号，可使用输入法直接输入，也可手动输入字符格式。通过输入 `:` 触发自动完成，可在个人设置中[设置常用表情](https://ld246.com/settings/function)。\n" +
                            "\n" +
                            "#### 一些表情例子\n" +
                            "\n" +
                            "\uD83D\uDE04 \uD83D\uDE06 \uD83D\uDE35 \uD83D\uDE2D \uD83D\uDE30 \uD83D\uDE05  \uD83D\uDE22 \uD83D\uDE24 \uD83D\uDE0D \uD83D\uDE0C\n" +
                            "\uD83D\uDC4D \uD83D\uDC4E \uD83D\uDCAF \uD83D\uDC4F \uD83D\uDD14 \uD83C\uDF81 ❓ \uD83D\uDCA3 ❤️ ☕️ \uD83C\uDF00 \uD83D\uDE47 \uD83D\uDC8B \uD83D\uDE4F \uD83D\uDCA2\n" +
                            "\n" +
                            "### 大标题 - Heading 3\n" +
                            "\n" +
                            "你可以选择使用 H1 至 H6，使用 ##(N) 打头。建议帖子或回帖中的顶级标题使用 Heading 3，不要使用 1 或 2，因为 1 是系统站点级，2 是帖子标题级。\n" +
                            "\n" +
                            "> NOTE: 别忘了 # 后面需要有空格！\n" +
                            "\n" +
                            "#### Heading 4\n" +
                            "\n" +
                            "##### Heading 5\n" +
                            "\n" +
                            "###### Heading 6\n" +
                            "\n" +
                            "### 图片\n" +
                            "\n" +
                            "```\n" +
                            "![alt 文本](http://image-path.png)\n" +
                            "![alt 文本](http://image-path.png \"图片 Title 值\")\n" +
                            "```\n" +
                            "\n" +
                            "支持复制粘贴直接上传。\n" +
                            "\n" +
                            "### 代码块\n" +
                            "\n" +
                            "#### 普通\n" +
                            "\n" +
                            "```\n" +
                            "*emphasize*    **strong**\n" +
                            "_emphasize_    __strong__\n" +
                            "var a = 1\n" +
                            "```\n" +
                            "\n" +
                            "#### 语法高亮支持\n" +
                            "\n" +
                            "如果在 ``` 后面跟随语言名称，可以有语法高亮的效果哦，比如:\n" +
                            "\n" +
                            "##### 演示 Go 代码高亮\n" +
                            "\n" +
                            "```go\n" +
                            "package main\n" +
                            "\n" +
                            "import \"fmt\"\n" +
                            "\n" +
                            "func main() {\n" +
                            "\tfmt.Println(\"Hello, 世界\")\n" +
                            "}\n" +
                            "```\n" +
                            "\n" +
                            "##### 演示 Java 高亮\n" +
                            "\n" +
                            "```java\n" +
                            "public class HelloWorld {\n" +
                            "\n" +
                            "    public static void main(String[] args) {\n" +
                            "        System.out.println(\"Hello World!\");\n" +
                            "    }\n" +
                            "\n" +
                            "}\n" +
                            "```\n" +
                            "\n" +
                            "> Tip: 语言名称支持下面这些: `ruby`, `python`, `js`, `html`, `erb`, `css`, `coffee`, `bash`, `json`, `yml`, `xml` ...\n" +
                            "\n" +
                            "### 有序、无序、任务列表\n" +
                            "\n" +
                            "#### 无序列表\n" +
                            "\n" +
                            "- Java\n" +
                            "  - Spring\n" +
                            "    - IoC\n" +
                            "    - AOP\n" +
                            "- Go\n" +
                            "  - gofmt\n" +
                            "  - Wide\n" +
                            "- Node.js\n" +
                            "  - Koa\n" +
                            "  - Express\n" +
                            "\n" +
                            "#### 有序列表\n" +
                            "\n" +
                            "1. Node.js\n" +
                            "   1. Express\n" +
                            "   2. Koa\n" +
                            "   3. Sails\n" +
                            "2. Go\n" +
                            "   1. gofmt\n" +
                            "   2. Wide\n" +
                            "3. Java\n" +
                            "   1. Latke\n" +
                            "   2. IDEA\n" +
                            "\n" +
                            "#### 任务列表\n" +
                            "\n" +
                            "- [X] 发布 Sym\n" +
                            "- [X] 发布 Solo\n" +
                            "- [ ] 预约牙医\n" +
                            "\n" +
                            "### 表格\n" +
                            "\n" +
                            "如果需要展示数据什么的，可以选择使用表格。\n" +
                            "\n" +
                            "| header 1 | header 2 |\n" +
                            "| -------- | -------- |\n" +
                            "| cell 1   | cell 2   |\n" +
                            "| cell 3   | cell 4   |\n" +
                            "| cell 5   | cell 6   |\n" +
                            "\n" +
                            "### 隐藏细节\n" +
                            "\n" +
                            "<details>\n" +
                            "<summary>这里是摘要部分。</summary>\n" +
                            "这里是细节部分。\n" +
                            "</details>\n" +
                            "\n" +
                            "### 段落\n" +
                            "\n" +
                            "空行可以将内容进行分段，便于阅读。（这是第一段）\n" +
                            "\n" +
                            "使用空行在 Markdown 排版中相当重要。（这是第二段）\n" +
                            "\n" +
                            "### 链接引用\n" +
                            "\n" +
                            "[链接文本][链接标识]\n" +
                            "\n" +
                            "[链接标识]: https://b3log.org\n" +
                            "```\n" +
                            "[链接文本][链接标识]\n" +
                            "\n" +
                            "[链接标识]: https://b3log.org\n" +
                            "```\n" +
                            "\n" +
                            "### 数学公式\n" +
                            "\n" +
                            "多行公式块：\n" +
                            "\n" +
                            "\$\$\n" +
                            "\\frac{1}{\n" +
                            "  \\Bigl(\\sqrt{\\phi \\sqrt{5}}-\\phi\\Bigr) e^{\n" +
                            "  \\frac25 \\pi}} = 1+\\frac{e^{-2\\pi}} {1+\\frac{e^{-4\\pi}} {\n" +
                            "    1+\\frac{e^{-6\\pi}}\n" +
                            "    {1+\\frac{e^{-8\\pi}}{1+\\cdots}}\n" +
                            "  }\n" +
                            "}\n" +
                            "\$\$\n" +
                            "\n" +
                            "行内公式：\n" +
                            "\n" +
                            "公式 \$a^2 + b^2 = \\color{red}c^2\$ 是行内。\n" +
                            "\n" +
                            "### 脑图\n" +
                            "\n" +
                            "```mindmap\n" +
                            "- 教程\n" +
                            "- 语法指导\n" +
                            "  - 普通内容\n" +
                            "  - 提及用户\n" +
                            "  - 表情符号 Emoji\n" +
                            "    - 一些表情例子\n" +
                            "  - 大标题 - Heading 3\n" +
                            "    - Heading 4\n" +
                            "      - Heading 5\n" +
                            "        - Heading 6\n" +
                            "  - 图片\n" +
                            "  - 代码块\n" +
                            "    - 普通\n" +
                            "    - 语法高亮支持\n" +
                            "      - 演示 Go 代码高亮\n" +
                            "      - 演示 Java 高亮\n" +
                            "  - 有序、无序、任务列表\n" +
                            "    - 无序列表\n" +
                            "    - 有序列表\n" +
                            "    - 任务列表\n" +
                            "  - 表格\n" +
                            "  - 隐藏细节\n" +
                            "  - 段落\n" +
                            "  - 链接引用\n" +
                            "  - 数学公式\n" +
                            "  - 脑图\n" +
                            "  - 流程图\n" +
                            "  - 时序图\n" +
                            "  - 甘特图\n" +
                            "  - 图表\n" +
                            "  - 五线谱\n" +
                            "  - Graphviz\n" +
                            "  - 多媒体\n" +
                            "  - 脚注\n" +
                            "- 快捷键\n" +
                            "```\n" +
                            "\n" +
                            "### 流程图\n" +
                            "\n" +
                            "```mermaid\n" +
                            "graph TB\n" +
                            "    c1-->a2\n" +
                            "    subgraph one\n" +
                            "    a1-->a2\n" +
                            "    end\n" +
                            "    subgraph two\n" +
                            "    b1-->b2\n" +
                            "    end\n" +
                            "    subgraph three\n" +
                            "    c1-->c2\n" +
                            "    end\n" +
                            "```\n" +
                            "\n" +
                            "### 时序图\n" +
                            "\n" +
                            "```mermaid\n" +
                            "sequenceDiagram\n" +
                            "    Alice->>John: Hello John, how are you?\n" +
                            "    loop Every minute\n" +
                            "        John-->>Alice: Great!\n" +
                            "    end\n" +
                            "```\n" +
                            "\n" +
                            "### 甘特图\n" +
                            "\n" +
                            "```mermaid\n" +
                            "gantt\n" +
                            "    title A Gantt Diagram\n" +
                            "    dateFormat  YYYY-MM-DD\n" +
                            "    section Section\n" +
                            "    A task           :a1, 2019-01-01, 30d\n" +
                            "    Another task     :after a1  , 20d\n" +
                            "    section Another\n" +
                            "    Task in sec      :2019-01-12  , 12d\n" +
                            "    another task      : 24d\n" +
                            "```\n" +
                            "\n" +
                            "### 图表\n" +
                            "\n" +
                            "```echarts\n" +
                            "{\n" +
                            "  \"title\": { \"text\": \"最近 30 天\" },\n" +
                            "  \"tooltip\": { \"trigger\": \"axis\", \"axisPointer\": { \"lineStyle\": { \"width\": 0 } } },\n" +
                            "  \"legend\": { \"data\": [\"帖子\", \"用户\", \"回帖\"] },\n" +
                            "  \"xAxis\": [{\n" +
                            "      \"type\": \"category\",\n" +
                            "      \"boundaryGap\": false,\n" +
                            "      \"data\": [\"2019-05-08\",\"2019-05-09\",\"2019-05-10\",\"2019-05-11\",\"2019-05-12\",\"2019-05-13\",\"2019-05-14\",\"2019-05-15\",\"2019-05-16\",\"2019-05-17\",\"2019-05-18\",\"2019-05-19\",\"2019-05-20\",\"2019-05-21\",\"2019-05-22\",\"2019-05-23\",\"2019-05-24\",\"2019-05-25\",\"2019-05-26\",\"2019-05-27\",\"2019-05-28\",\"2019-05-29\",\"2019-05-30\",\"2019-05-31\",\"2019-06-01\",\"2019-06-02\",\"2019-06-03\",\"2019-06-04\",\"2019-06-05\",\"2019-06-06\",\"2019-06-07\"],\n" +
                            "      \"axisTick\": { \"show\": false },\n" +
                            "      \"axisLine\": { \"show\": false }\n" +
                            "  }],\n" +
                            "  \"yAxis\": [{ \"type\": \"value\", \"axisTick\": { \"show\": false }, \"axisLine\": { \"show\": false }, \"splitLine\": { \"lineStyle\": { \"color\": \"rgba(0, 0, 0, .38)\", \"type\": \"dashed\" } } }],\n" +
                            "  \"series\": [\n" +
                            "    {\n" +
                            "      \"name\": \"帖子\", \"type\": \"line\", \"smooth\": true, \"itemStyle\": { \"color\": \"#d23f31\" }, \"areaStyle\": { \"normal\": {} }, \"z\": 3,\n" +
                            "      \"data\": [\"18\",\"14\",\"22\",\"9\",\"7\",\"18\",\"10\",\"12\",\"13\",\"16\",\"6\",\"9\",\"15\",\"15\",\"12\",\"15\",\"8\",\"14\",\"9\",\"10\",\"29\",\"22\",\"14\",\"22\",\"9\",\"10\",\"15\",\"9\",\"9\",\"15\",\"0\"]\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"name\": \"用户\", \"type\": \"line\", \"smooth\": true, \"itemStyle\": { \"color\": \"#f1e05a\" }, \"areaStyle\": { \"normal\": {} }, \"z\": 2,\n" +
                            "      \"data\": [\"31\",\"33\",\"30\",\"23\",\"16\",\"29\",\"23\",\"37\",\"41\",\"29\",\"16\",\"13\",\"39\",\"23\",\"38\",\"136\",\"89\",\"35\",\"22\",\"50\",\"57\",\"47\",\"36\",\"59\",\"14\",\"23\",\"46\",\"44\",\"51\",\"43\",\"0\"]\n" +
                            "    },\n" +
                            "    {\n" +
                            "      \"name\": \"回帖\", \"type\": \"line\", \"smooth\": true, \"itemStyle\": { \"color\": \"#4285f4\" }, \"areaStyle\": { \"normal\": {} }, \"z\": 1,\n" +
                            "      \"data\": [\"35\",\"42\",\"73\",\"15\",\"43\",\"58\",\"55\",\"35\",\"46\",\"87\",\"36\",\"15\",\"44\",\"76\",\"130\",\"73\",\"50\",\"20\",\"21\",\"54\",\"48\",\"73\",\"60\",\"89\",\"26\",\"27\",\"70\",\"63\",\"55\",\"37\",\"0\"]\n" +
                            "    }\n" +
                            "  ]\n" +
                            "}\n" +
                            "```\n" +
                            "\n" +
                            "### 五线谱\n" +
                            "\n" +
                            "```abc\n" +
                            "X: 24\n" +
                            "T: Clouds Thicken\n" +
                            "C: Paul Rosen\n" +
                            "S: Copyright 2005, Paul Rosen\n" +
                            "M: 6/8\n" +
                            "L: 1/8\n" +
                            "Q: 3/8=116\n" +
                            "R: Creepy Jig\n" +
                            "K: Em\n" +
                            "|:\"Em\"EEE E2G|\"C7\"_B2A G2F|\"Em\"EEE E2G|\\\n" +
                            "\"C7\"_B2A \"B7\"=B3|\"Em\"EEE E2G|\n" +
                            "\"C7\"_B2A G2F|\"Em\"GFE \"D (Bm7)\"F2D|\\\n" +
                            "1\"Em\"E3-E3:|2\"Em\"E3-E2B|:\"Em\"e2e gfe|\n" +
                            "\"G\"g2ab3|\"Em\"gfeg2e|\"D\"fedB2A|\"Em\"e2e gfe|\\\n" +
                            "\"G\"g2ab3|\"Em\"gfe\"D\"f2d|\"Em\"e3-e3:|\n" +
                            "```\n" +
                            "\n" +
                            "### Graphviz\n" +
                            "\n" +
                            "```graphviz\n" +
                            "digraph finite_state_machine {\n" +
                            "    rankdir=LR;\n" +
                            "    size=\"8,5\"\n" +
                            "    node [shape = doublecircle]; S;\n" +
                            "    node [shape = point ]; qi\n" +
                            "\n" +
                            "    node [shape = circle];\n" +
                            "    qi -> S;\n" +
                            "    S  -> q1 [ label = \"a\" ];\n" +
                            "    S  -> S  [ label = \"a\" ];\n" +
                            "    q1 -> S  [ label = \"a\" ];\n" +
                            "    q1 -> q2 [ label = \"ddb\" ];\n" +
                            "    q2 -> q1 [ label = \"b\" ];\n" +
                            "    q2 -> q2 [ label = \"b\" ];\n" +
                            "}\n" +
                            "```\n" +
                            "\n" +
                            "### Flowchart\n" +
                            "\n" +
                            "```flowchart\n" +
                            "st=>start: Start\n" +
                            "op=>operation: Your Operation\n" +
                            "cond=>condition: Yes or No?\n" +
                            "e=>end\n" +
                            "\n" +
                            "st->op->cond\n" +
                            "cond(yes)->e\n" +
                            "cond(no)->op\n" +
                            "```\n" +
                            "\n" +
                            "### 多媒体\n" +
                            "\n" +
                            "支持 v.qq.com，youtube.com，youku.com，coub.com，facebook.com/video，dailymotion.com，.mp4，.m4v，.ogg，.ogv，.webm，.mp3，.wav 链接解析\n" +
                            "\n" +
                            "https://v.qq.com/x/cover/zf2z0xpqcculhcz/y0016tj0qvh.html\n" +
                            "\n" +
                            "### 脚注\n" +
                            "\n" +
                            "这里是一个脚注引用[^1]，这里是另一个脚注引用[^bignote]。\n" +
                            "\n" +
                            "[^1]: 第一个脚注定义。\n" +
                            "    \n" +
                            "[^bignote]: 脚注定义可使用多段内容。\n" +
                            "    \n" +
                            "       缩进对齐的段落包含在这个脚注定义内。\n" +
                            "    \n" +
                            "       ```\n" +
                            "       可以使用代码块。\n" +
                            "       ```\n" +
                            "       还有其他行级排版语法，比如**加粗**和[链接](https://b3log.org)。\n" +
                            "    \n" +
                            "```\n" +
                            "这里是一个脚注引用[^1]，这里是另一个脚注引用[^bignote]。\n" +
                            "[^1]: 第一个脚注定义。\n" +
                            "[^bignote]: 脚注定义可使用多段内容。\n" +
                            "\n" +
                            "    缩进对齐的段落包含在这个脚注定义内。\n" +
                            "\n" +
                            "    ```\n" +
                            "    可以使用代码块。\n" +
                            "    ```\n" +
                            "\n" +
                            "    还有其他行级排版语法，比如**加粗**和[链接](https://b3log.org)。\n" +
                            "```\n" +
                            "\n" +
                            "## 快捷键\n" +
                            "\n" +
                            "我们的编辑器支持很多快捷键，具体请参考 [键盘快捷键](https://ld246.com/article/1474030007391)（或者按 \"`?` \"\uD83D\uDE3C）")
                    vditor.initEditor("edit")
                } else {
                    // 如果不是editMode，只是预览，只需要调用initPreviewer，传入md格式的字符串即可
                    vditor.initPreviewer("# 测试 \n## 222\n ### 333\n![ICON](https://b3log.org/images/brand/vditor-128.png)![ICON](https://b3log.org/images/brand/vditor-128.png)![ICON](https://b3log.org/images/brand/vditor-128.png)")
                }
            }

            override fun onChange(value: String) {
                //TODO 编辑器内容改变后的回调
            }

            override fun onPictureClick(imgUrls: String, position: Int) {
                //TODO 查看大图
            }
        })
    }

    private fun initTab() {
        tabIconView = findViewById(R.id.tabIconView)
        tabIconView.addTab(
            R.drawable.ic_shortcut_format_header_1,
            R.id.id_shortcut_format_header_1,
            R.string.heading_1,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_format_header_2,
            R.id.id_shortcut_format_header_2,
            R.string.heading_2,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_format_header_3,
            R.id.id_shortcut_format_header_3,
            R.string.heading_3,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_format_bold,
            R.id.id_shortcut_format_bold,
            R.string.bold,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_format_italic,
            R.id.id_shortcut_format_italic,
            R.string.italic,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_format_strikethrough,
            R.id.id_shortcut_format_strike_through,
            R.string.strike_through,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_insert_link,
            R.id.id_shortcut_insert_link,
            R.string.link,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_format_list_bulleted,
            R.id.id_shortcut_format_list,
            R.string.list,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_format_ordered_list,
            R.id.id_shortcut_format_ordered_list,
            R.string.ordered_list,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_check_box,
            R.id.id_shortcut_format_check_box,
            R.string.list_check,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_indent_increase,
            R.id.id_shortcut_format_indent_increase,
            R.string.indent_increase,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_indent_decrease,
            R.id.id_shortcut_format_indent_decrease,
            R.string.indent_decrease,
            this
        )

        tabIconView.addTab(
            R.drawable.ic_shortcut_format_quote,
            R.id.id_shortcut_format_quote,
            R.string.quote,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_minus,
            R.id.id_shortcut_insert_line,
            R.string.line,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_console,
            R.id.id_shortcut_insert_code,
            R.string.code,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_xml,
            R.id.id_shortcut_insert_inline_code,
            R.string.inline_code,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_insert_before,
            R.id.id_shortcut_insert_before,
            R.string.insert_before,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_insert_after,
            R.id.id_shortcut_insert_after,
            R.string.insert_after,
            this
        )

        tabIconView.addTab(
            R.drawable.ic_shortcut_insert_photo,
            R.id.id_shortcut_insert_photo,
            R.string.picture,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_grid,
            R.id.id_shortcut_insert_grid,
            R.string.table,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_format_header_4,
            R.id.id_shortcut_format_header_4,
            R.string.heading_4,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_format_header_5,
            R.id.id_shortcut_format_header_5,
            R.string.heading_5,
            this
        )
        tabIconView.addTab(
            R.drawable.ic_shortcut_format_header_6,
            R.id.id_shortcut_format_header_6,
            R.string.heading_6,
            this
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.undo -> {
                vditor.undo()
            }
            R.id.redo -> {
                vditor.redo()
            }
            R.id.toggle_toolbar -> {
                //没有展开，但是接下来就是展开，设置向上箭头
                moreToolbarItem.setIcon(
                    if (!expandableLinearLayout.isExpanded)
                        R.drawable.ic_arrow_up else R.drawable.ic_add_white_24dp
                )
                expandableLinearLayout.toggle()
            }
            R.id.help -> {
                vditor.help()
            }
            R.id.preview -> {
                vditor.preview()
            }
            R.id.switch_theme -> {
                if (vditor.isDarkTheme) {
                    vditor.setLightTheme()
                } else {
                    vditor.setDarkTheme()
                }
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        moreToolbarItem = menu!!.findItem(R.id.toggle_toolbar)
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        const val TAG = "MainActivity："
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_shortcut_format_header_1 -> {
                vditor.headingLevel1()
            }
            R.id.id_shortcut_format_header_2 -> {
                vditor.headingLevel2()
            }
            R.id.id_shortcut_format_header_3 -> {
                vditor.headingLevel3()
            }
            R.id.id_shortcut_format_header_4 -> {
                vditor.headingLevel4()
            }
            R.id.id_shortcut_format_header_5 -> {
                vditor.headingLevel5()
            }
            R.id.id_shortcut_format_header_6 -> {
                vditor.headingLevel6()
            }
            R.id.id_shortcut_format_bold -> {
                vditor.bold()
            }
            R.id.id_shortcut_format_italic -> {
                vditor.italic()
            }
            R.id.id_shortcut_format_strike_through -> {
                vditor.strike()
            }
            R.id.id_shortcut_format_list -> {
                vditor.list()
            }
            R.id.id_shortcut_format_ordered_list -> {
                vditor.orderedList()
            }
            R.id.id_shortcut_format_check_box -> {
                vditor.checkList()
            }
            R.id.id_shortcut_format_quote -> {
                vditor.quote()
            }
            R.id.id_shortcut_insert_link -> {
                vditor.link()
            }
            R.id.id_shortcut_insert_photo -> {
                //TODO id_shortcut_insert_photo
                vditor.insertPicture(
                    "https://www.baidu.com/img/flexible/logo/pc/result.png",
                    "百度",
                    "百度"
                )
            }
            R.id.id_shortcut_insert_code -> {
                vditor.code()
            }
            R.id.id_shortcut_insert_inline_code -> {
                vditor.inlineCode()
            }
            R.id.id_shortcut_insert_grid -> {
                vditor.table()
            }
            R.id.id_shortcut_insert_line -> {
                vditor.line()
            }
            R.id.id_shortcut_insert_before -> {
                vditor.insertBefore()
            }
            R.id.id_shortcut_insert_after -> {
                vditor.insertAfter()
            }
            R.id.id_shortcut_format_indent_increase -> {
                vditor.indent()
            }
            R.id.id_shortcut_format_indent_decrease -> {
                vditor.outdent()
            }
            else -> {
                // ignore
            }
        }
    }

}
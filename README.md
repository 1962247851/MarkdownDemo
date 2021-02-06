# 一个基于Vditor的自定义WebView安卓markdown编辑器
- 本地化cdn
- 自定义工具栏
- 封装为WebView并暴露常用方法
  - 日夜间主题切换
  - Markdown常用操作
  - ......

其他介绍详见[Vditor](https://b3log.org/vditor/ "Vditor - 一款浏览器端的 Markdown 编辑器，支持所见即所得（富文本）、即时渲染（类似 Typora）和分屏预览模式")

# 使用方法

**具体使用方法都在MainActivity里了，下面是关键代码和说明**

## vditor初始化

使用`vditor.startLoad()`方法初始化vditor，参数为`isEditMode`，`isDarkTheme`，`Vditor.IVditorCallback`

```kotlin
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
            // vditor.setCache("edit", "# 一级标题")
            vditor.initEditor("edit")
        } else {
            // 如果不是editMode，只是预览，只需要调用initPreviewer，传入md格式的字符串即可
            vditor.initPreviewer("# 一级标题")
        }
    }

    override fun onChange(value: String) {
        //TODO 编辑器内容改变后的回调
    }
<<<<<<< HEAD

    override fun onPictureClick(imgUrls: String, position: Int) {
        //TODO 查看大图
    }
=======
>>>>>>> 6e7c02ee7714c1653227ac5f52315886767cbcb7
})
```

## 给vditor赋初值

使用`startLoad()`方法开始初始化后，页面加载完成后会触发`onLoadFinished()`回调，然后才能正式开始初始化vditor，根据是否为编辑器模式来调用不同的方法。因为vditor中编辑器和仅预览是两个不同的初始化方法，详见[Vditor官网的API介绍](https://ld246.com/article/1549638745630)。

## 编辑器模式

例如，如果是编辑器模式`vditor.isEditMode == true`，则调用`vditor.initEditor()`方法，参数为`key`，字符串类型，用来保存草稿，草稿根据传入的`key`自动保存，原理是`localStorage`，用户可以自己决定是否要赋初值，如果需要，使用`vditor.setCache()`方法给编辑器设置初始值。

## 预览模式

<<<<<<< HEAD
调用`vditor.initPreviewer()`方法，参数为要预览的内容。
=======
调用` vditor.initPreviewer()`方法，参数为要预览的内容。
>>>>>>> 6e7c02ee7714c1653227ac5f52315886767cbcb7

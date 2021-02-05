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
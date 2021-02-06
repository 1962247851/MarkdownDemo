/*
 * Copyright 2016. SHENQINCI(沈钦赐)<946736079@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.markdowndemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.TooltipCompat;


/**
 * TAB
 * Created by 沈钦赐 on 16/6/21.
 */
public class TabIconView extends HorizontalScrollView {

    private static final String TAG = "TabIconView：";

    private LinearLayout mLayout;
    private LayoutInflater mInflater;

    public TabIconView(Context context) {
        super(context);
        init();
    }

    public TabIconView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOverScrollMode(OVER_SCROLL_NEVER);
        this.setHorizontalScrollBarEnabled(false);

        mInflater = LayoutInflater.from(getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLayout = new LinearLayout(getContext());
        mLayout.setPadding(1, 0, 1, 0);
        mLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(mLayout, params);
    }


    public void addTab(@DrawableRes int iconId, @IdRes int id, OnClickListener onClickListener) {
        ImageButton view = (ImageButton) mInflater.inflate(R.layout.item_tab_icon, mLayout, false);
        view.setImageResource(iconId);
        view.setId(id);
        view.setOnClickListener(onClickListener);
        mLayout.addView(view, mLayout.getChildCount());
        //滑到最右边
        this.postDelayed(() -> this.smoothScrollBy(1000, 0), 5);
    }

    public void addTab(@DrawableRes int iconId, @IdRes int id, @StringRes int contentDescriptionId, OnClickListener onClickListener) {
        ImageButton view = (ImageButton) mInflater.inflate(R.layout.item_tab_icon, mLayout, false);
        view.setImageResource(iconId);
        view.setId(id);
        view.setContentDescription(getContext().getString(contentDescriptionId));
        TooltipCompat.setTooltipText(view, getContext().getString(contentDescriptionId));
        view.setOnClickListener(onClickListener);
        mLayout.addView(view, mLayout.getChildCount());
        //滑到最右边
        this.postDelayed(() -> this.smoothScrollBy(1000, 0), 5);
    }


    public void removeTab() {
        int count = mLayout.getChildCount();
        //移除最后一个
        mLayout.removeViewAt(count - 1);
    }

}

package com.jmzd.ghazal.movies.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/* روی ویو این کارها را انجام بده*/
fun View.showInvisible(isShown: Boolean) {
    if (isShown) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

/*
روی ریسایکلر ویو این کارها را انجام بده *
*
* چون نمیدونیم چه layout manager ی قراره روش ست بشه مثلا horizontal | vertical
و چه آداپتری قراره بهش پاس داده بشه
پس یک چیز جنرال میزنیم که هر چیزی رو بشه بهش  پاس داد
بعد مثلا میخوایم setHasFixedSize بزنیم ؟ همینجا بزن
انیمیشن خاصی میخوای بزنی؟ همینجا بزن
یک بار بزن همه جای اپ استفاده کن
*  */
fun RecyclerView.initRecycler(layoutManager: RecyclerView.LayoutManager, adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = layoutManager
    this.adapter = adapter
}
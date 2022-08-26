package com.jmzd.ghazal.movies.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmzd.ghazal.movies.R

/*
* سناریو :
* قراره ۳ تا api کال شه توی این صفحه
* 1- top movies یا همون ترندها
* 2- generes
* 3- last movies
* قراره این ها را با حداقل خط کد کال کنیم
* همچنین اینو یاد میگیریم که در هر اسکرول ریسایکلر ویو فقط یک آیتم نشون بدیم
*
*
*
*
* */
class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}
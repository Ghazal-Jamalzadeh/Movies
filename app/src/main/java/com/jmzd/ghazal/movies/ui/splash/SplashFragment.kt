package com.jmzd.ghazal.movies.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmzd.ghazal.movies.R

/*
* سناریو :
* اول وارد صفحه اسپلش میشیم
* در اینجا اپلیکیشن تصمیم میگیره که وارد صفحه رجیستر بشه یا وارد صفحه هوم بشه
* چطور تصمیم میگیره؟
* میبینه آیا اطلاعات کاربر ذخیره شده یا نه
*
* برای شروع فلش های نو گراف را میکشیم
*
* بعد میریم سراغ MainActivity تا کدهای مربوط به نویگشین و باتم نویگشین را بزنیم
* و از طرفی باید بگیم که این باتم نویگیشن تو کدام فرگمنت ها باید نشون داده بشه و تو کدوم ها نه
* -> Main Activity
*
* */


import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.jmzd.ghazal.movies.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentSplashBinding

//    @Inject
//    lateinit var storeUserData: StoreUserData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set delay
        lifecycle.coroutineScope.launchWhenCreated {
            delay(2000)
            //Check user token
//            storeUserData.getUserToken().collect {
//                if (it.isEmpty()) {
//                    findNavController().navigate(R.id.actionSplashToRegister)
//                } else {
//                    findNavController().navigate(R.id.actionToHome)
//                }
//            }
        }
    }
}
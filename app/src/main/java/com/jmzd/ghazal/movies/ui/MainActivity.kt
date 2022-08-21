package com.jmzd.ghazal.movies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jmzd.ghazal.movies.R
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jmzd.ghazal.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/*
* قراره در اینجا نویگیشن و نویگیشن باتم را درست کنیم
*  و همچنین تعیین کنیم که باتم نویگشین در کدام فرگمنت ها دیده شود و در کدام ها نه
*
* -> activity_main.xml طراحی  لایه

* */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityMainBinding

    //navigation
    /* اول از همه نویگیشن را تعریف میکردیم */
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //InitViews
        binding.apply {

            //Navigation
            /*دوم navController را ست میکنیم روش که در واقع آیدی همون navHost ماست که بش میدیم*/
            navController = findNavController(R.id.navHost)

            /* بعد هم باتم نویگیشن را به navHost و navController وصل میکنیم */
            bottomNav.setupWithNavController(navController)

            //Show bottom navigation
            /*
            * خب navController یک آپشنی دارد که میتوانیم بگوییم
            * مثلا در فلان مقصدها هر چیزی که میخوای رو visible & gone کن
            * یا هر کار دیگه ای میخوای انجام بده
            * مثلا متد خاصی را کال کن
            *
            * خود کاتلین توصیه میکنه اگه از پارامتری استفاده نمیکنی بهتره به _ تغییرش بدی
            * */
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.splashFragment || destination.id == R.id.registerFragment
                    || destination.id == R.id.detailFragment) {
                    bottomNav.visibility = View.GONE
                } else {
                    bottomNav.visibility = View.VISIBLE
                }
            }
        }
    }

    /* هندل عملیات بک زدن حتما این متد را override کنید  */
    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

    /* -> SplashFragment */
}
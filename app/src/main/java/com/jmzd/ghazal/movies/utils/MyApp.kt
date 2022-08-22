package com.jmzd.ghazal.movies.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/*
* این کلاس را برای استفاده از هیلت میساختیم
* و از کلاس اپلیکیشن ارث بری میکردیم
* چرا این کارو میکنیم؟
* برای اینکه این عملیاتی که مربوط به هیلت هست رو
* یعنی در واقع گراف کلی هیلت رو وارد اپلیکیشن ما کنه
* در نهایت هم انوتیشن اون رو اضافه میکردیم
* بعد میرفتیم در مانیفست در تگ اپلیکیشن اسم این کلاس رو داخل name میدادیم بش
* -> manifest
* بعد از تعریف این موارد اپلیکیشن من کلا سازو کار هلیت رو تونست به دست بیاره
* برمیگردیم به
* -> StoreUserData
*
* */
@HiltAndroidApp
class MyApp : Application()
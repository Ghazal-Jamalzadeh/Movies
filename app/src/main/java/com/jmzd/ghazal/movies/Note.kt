package com.jmzd.ghazal.movies
/* توضیحات کلی پروژه */
/*
* در مورد پکیج بندی ها:
* repository:
* توس این ریپازیتوری ما میایم اطلاعاتی که قراره رد و بدل بشه را کنترل میکنیم
* چه برای سرور چه برای دیتابیس
* خوبیش اینه که هم زمان جفتش را با هم داریم
* مثلا در صفحه detail هم سرور را داریم هم دیتابیس را داریم جفتشون تو یه ریپازیتوری ترکیب میشن
* */

/*
* نکته: فرگمنت که میسازید همه چیو پاک کنید به جز onCreateView
* */

/*
* ساختار اکتیویتی قرار هست سینگل اکتیویتی باشه و کل اپ روی یک اکتیویتی سوار میشه
* مثلا بعضی فرگمنت ها مثل اسپلش اصلا باتم نویگیشن ندارن
* یاد میگیریم اینا را هندل کنیم
*
*
* */

/*
* شروع با
* -> Splash Fragment
* */

/*
* مراحل کال کردن api :
* ۱- مدل بادی و مدل ریپانس را تعریف میکنیم
* ۲- کال کردن api در اینتذفیس ApiServices
* ۳- تعریف کردن خود رتروفیت و تتظیماتش در ApiModule در di
* */

/*
* در مرحله بعد تعریف ویومدل و ریپازیتوری رو انجام میدیم
* حالا چرا ریپازیتوری و ویومدل؟
* گفتیم زمانیکه میخوایم از هیلت و اینجور چیزها استفاده کنیم
*  گفیتم که نیاز به یک کلاسی یا یک چیزی داریم که اطلاعات یا وظیفه تامین کردن اطلاعات رو بر عهده داشته باشه
* مثلا اطلاعات من از api میاد
* یا مثلا از دیتابیس میاد
* این ها رو میگیره در اختیار ما قرار میده و به واسطه اپنکه از api یا از دیتابیس میاد میتونیم ازش استفاده کنیم
* ما به ازای هر صفحه ای یک ریپازیتوری داریم
* چون اطلاعات صفحات مختلف از جاهای مختلفی میاد
* مثلا اطلاعات رجیستر از روت رجیستر میاد و اطلاعات صفحه اصلی از یک روت دیگه
* پس به ازای هر صفحه ای ما یک ریپازیتوری میایم تعریف میکنیم
* اگز الاعات دو تا صفحه یعنی api هاشون یا دیتابیسشون هیچ فرقی با هم نداشت و کاملا مشابه بود
* در این صورت میتونید از این ریپازیتوری توی چندین صفحه استفاده کنید
* -> RegisterRepository
* */

/*
* مراحل ساخت بخش top movies :
* 1- xml
* 2- model
* 3- apiServices -> api
* 4- repository -> میگیم اطلاعات از کجا قراره تامین شه
* 5- viewModel
*
*
* */

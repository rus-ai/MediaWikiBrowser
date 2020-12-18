package ru.wtw.mediawikibrowser.Common

import ru.wtw.mediawikibrowser.Retrofit.RetrofitClient
import ru.wtw.mediawikibrowser.Interface.RetrofitServices

object Common {
    private val BASE_URL = "https://www.simplifiedcoding.net/demos/"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}
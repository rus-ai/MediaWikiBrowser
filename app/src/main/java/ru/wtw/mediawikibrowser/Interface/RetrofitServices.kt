package ru.wtw.mediawikibrowser.Interface

import retrofit2.Call
import retrofit2.http.GET
import ru.wtw.mediawikibrowser.Model.AllPages_old

interface RetrofitServices {
    @GET("marvel")
    fun getAllPagesList(): Call<MutableList<AllPages_old>>
}


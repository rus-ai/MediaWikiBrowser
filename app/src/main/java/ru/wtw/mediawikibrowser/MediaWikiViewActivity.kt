package ru.wtw.mediawikibrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.wtw.mediawikibrowser.Common.Common
import ru.wtw.mediawikibrowser.Interface.RetrofitServices
import ru.wtw.mediawikibrowser.databinding.ActivityMediawikiBinding
import ru.wtw.mediawikibrowser.Model.AllPages_old
import ru.wtw.mediawikibrowser.Adapter.MediaWikiAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MediaWikiViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediawikiBinding
    lateinit var mService: RetrofitServices
    lateinit var adapter: MediaWikiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediawikiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mService = Common.retrofitService

        binding.mediaWikiRecycleView.setHasFixedSize(true)

        binding.buttonSearch.setOnClickListener {
            if (!binding.editTextSearch.text.isNullOrBlank()) {
                binding.editTextSearch.text.clear()
            }
        }

        getAllPagesList()
    }

    private fun getAllPagesList() {
            mService.getAllPagesList().enqueue(object : Callback<MutableList<AllPages_old>> {

                override fun onFailure(call: Call<MutableList<AllPages_old>>, t: Throwable) {

                }

                override fun onResponse(call: Call<MutableList<AllPages_old>>, response: Response<MutableList<AllPages_old>>) {
                    adapter = MediaWikiAdapter(baseContext, response.body() as MutableList<AllPages_old>)
                    adapter.notifyDataSetChanged()
                    binding.mediaWikiRecycleView.adapter = adapter
                }
            })
    }
}



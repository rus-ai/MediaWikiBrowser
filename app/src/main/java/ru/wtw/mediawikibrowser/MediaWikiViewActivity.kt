package ru.wtw.mediawikibrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import ru.wtw.mediawikibrowser.databinding.ActivityMediawikiBinding
import ru.wtw.mediawikibrowser.Adapter.MediaWikiAdapter
import ru.wtw.mediawikibrowser.Model.AllPagesClass
import ru.wtw.mediawikibrowser.Model.MediaWikiResponse
import java.io.IOException
import okhttp3.*

class MediaWikiViewActivity : AppCompatActivity() {

    private val pageCount = 20
    private val url = "https://en.wikipedia.org/w/api.php?action=query&format=json&list=allpages&aplimit=$pageCount&apfrom="
    
    private lateinit var binding: ActivityMediawikiBinding
    private lateinit var adapter: MediaWikiAdapter

    private var allPages: MutableList<AllPagesClass> = ArrayList()

    private var okHttpClient: OkHttpClient = OkHttpClient()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediawikiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MediaWikiAdapter(allPages)

        binding.mediaWikiRecycleView.adapter = adapter
        binding.mediaWikiRecycleView.setHasFixedSize(true)

        binding.buttonSearch.setOnClickListener {
            if (!binding.editTextSearch.text.isNullOrBlank()) {
                allPages.clear()
                getAllPagesList(binding.editTextSearch.text.toString())
                binding.editTextSearch.text.clear()
            }
        }

        getAllPagesList("A")
    }

    private fun getAllPagesList(query : String) {
        Log.i("MediaWikiBrowser", "Request")
        val request: Request = Request.Builder().url(url+query).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("MediaWikiBrowser", "Failure")
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body()?.string()
                Log.i("MediaWikiBrowser", "Response")
                Log.i("MediaWikiBrowser", json.toString())
                val mediaWikiResponse: MediaWikiResponse = Gson().fromJson(json, MediaWikiResponse::class.java)
                Log.i("MediaWikiBrowser", mediaWikiResponse.continueField?.apcontinue.toString())
                Log.i("MediaWikiBrowser", mediaWikiResponse.query?.allPages?.size.toString())
                runOnUiThread {
                    if (mediaWikiResponse.query?.allPages != null) {
                        Log.i("MediaWikiBrowser", allPages.size.toString())
                        allPages.plusAssign(mediaWikiResponse.query.allPages)
                        Log.i("MediaWikiBrowser", allPages.size.toString())
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}



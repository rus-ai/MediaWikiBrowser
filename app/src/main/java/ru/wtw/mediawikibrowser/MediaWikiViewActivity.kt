package ru.wtw.mediawikibrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import com.google.gson.Gson
import ru.wtw.mediawikibrowser.databinding.ActivityMediawikiBinding
import ru.wtw.mediawikibrowser.Adapter.MediaWikiAdapter
import ru.wtw.mediawikibrowser.Model.AllPagesClass
import ru.wtw.mediawikibrowser.Model.MediaWikiResponse
import java.io.IOException
import okhttp3.*
import ru.wtw.mediawikibrowser.Interface.OnLoadMoreListener

class MediaWikiViewActivity : AppCompatActivity() {

    private val pageCount = 30
    private val url = "https://en.wikipedia.org/w/api.php?action=query&format=json&list=allpages&aplimit=$pageCount&apfrom="

    var nextSearch = ""

    private lateinit var binding: ActivityMediawikiBinding
    private lateinit var adapter: MediaWikiAdapter
    private lateinit var scrollListener: RecyclerViewLoadMoreScroll

    private var allPages: MutableList<AllPagesClass> = ArrayList()

    private var okHttpClient: OkHttpClient = OkHttpClient()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediawikiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MediaWikiAdapter(allPages)

        binding.mediaWikiRecycleView.adapter = adapter
        binding.mediaWikiRecycleView.setHasFixedSize(true)

        if (binding.mediaWikiRecycleView.layoutManager != null) {
            scrollListener = RecyclerViewLoadMoreScroll(binding.mediaWikiRecycleView.layoutManager!!)
            scrollListener.setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMore() {
                    loadMoreData()
                }

                private fun loadMoreData() {
                    getAllPagesList(nextSearch)
                }
            })
            binding.mediaWikiRecycleView.addOnScrollListener(scrollListener)
        }

        binding.buttonSearch.setOnClickListener {
            if (!binding.editTextSearch.text.isNullOrBlank()) {
                allPages.clear()
                getAllPagesList(binding.editTextSearch.text.toString())
                binding.editTextSearch.text.clear()
            }
        }

        binding.editTextSearch.setOnKeyListener { _, keyCode, event ->
            when {
                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
                    binding.buttonSearch.performClick()
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        getAllPagesList("Zombie")
    }

    private fun getAllPagesList(query : String) {
        Log.i("MediaWikiBrowser", "Request")
        val request: Request = Request.Builder().url(url+query).build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("MediaWikiBrowser", "Failure")
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                Log.i("MediaWikiBrowser", "Response")
                Log.i("MediaWikiBrowser", json.toString())
                val mediaWikiResponse: MediaWikiResponse = Gson().fromJson(json, MediaWikiResponse::class.java)
                nextSearch = mediaWikiResponse.continueField?.apcontinue.toString()
                Log.i("MediaWikiBrowser", mediaWikiResponse.continueField?.apcontinue.toString())
                Log.i("MediaWikiBrowser", mediaWikiResponse.query?.allPages?.size.toString())
                runOnUiThread {
                    if (mediaWikiResponse.query?.allPages != null) {
                        Log.i("MediaWikiBrowser", allPages.size.toString())
                        allPages.plusAssign(mediaWikiResponse.query.allPages)
                        Log.i("MediaWikiBrowser", allPages.size.toString())
                        adapter.notifyDataSetChanged()
                        scrollListener.setLoaded()
                    }
                }
            }
        })
    }
}



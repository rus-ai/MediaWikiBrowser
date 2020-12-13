package ru.wtw.mediawikibrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.wtw.mediawikibrowser.databinding.ActivityMediawikiBinding

class MediaWikiViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediawikiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediawikiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = (0..100).map { "Value: $it" }
        binding.mediaWikiRecycleView.adapter = MediaWikiAdapter(data)

        binding.buttonSearch.setOnClickListener {
            if (!binding.editTextSearch.text.isNullOrBlank()) {
                binding.editTextSearch.text.clear()
            }
        }



    }
}
package ru.wtw.mediawikibrowser.Model

import com.google.gson.annotations.SerializedName

data class AllPagesClass (
	@SerializedName("pageid") var pageId : Int? = null,
	@SerializedName("ns") var ns : Int? = null,
	@SerializedName("title") var title : String? = null
)
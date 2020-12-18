package ru.wtw.mediawikibrowser.Model

import com.google.gson.annotations.SerializedName

data class MediaWikiResponse (
	@SerializedName("batchcomplete") val batchComplete : String? = null,
	@SerializedName("continue") val continueField : ContinueClass? = null,
	@SerializedName("query") val query : QueryClass? = null
)
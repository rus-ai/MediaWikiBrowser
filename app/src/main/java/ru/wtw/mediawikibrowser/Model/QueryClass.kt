package ru.wtw.mediawikibrowser.Model

import com.google.gson.annotations.SerializedName

data class QueryClass (
	@SerializedName("allpages") val allPages : List<AllPagesClass>? = null
)
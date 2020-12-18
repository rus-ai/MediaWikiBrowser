package ru.wtw.mediawikibrowser.Model

import com.google.gson.annotations.SerializedName

data class ContinueClass (
	@SerializedName("apcontinue") var apcontinue : String? = null,
	@SerializedName("continue") var continueType : String? = null
)
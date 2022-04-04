package com.ekotyoo.inspirago.data

import com.ekotyoo.inspirago.data.entity.Quote
import com.google.gson.annotations.SerializedName

data class QuoteResponse(

	@SerializedName("authorSlug")
	val authorSlug: String? = null,

	@SerializedName("author")
	val author: String? = null,

	@SerializedName("length")
	val length: Int? = null,

	@SerializedName("dateModified")
	val dateModified: String? = null,

	@SerializedName("_id")
	val id: String? = null,

	@SerializedName("content")
	val content: String? = null,

	@SerializedName("dateAdded")
	val dateAdded: String? = null,

	@SerializedName("tags")
	val tags: List<String?>? = null
) {
	fun toEntity() = Quote(author, content, "asd")
}

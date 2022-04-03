package com.ekotyoo.inspirago.data.datasource.remote.responses

import com.google.gson.annotations.SerializedName

data class ImageResponse(

	@field:SerializedName("topic_submissions")
	val topicSubmissions: TopicSubmissions,

	@field:SerializedName("current_user_collections")
	val currentUserCollections: List<Any>,

	@field:SerializedName("color")
	val color: String,

	@field:SerializedName("sponsorship")
	val sponsorship: Any,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("description")
	val description: Any,

	@field:SerializedName("liked_by_user")
	val likedByUser: Boolean,

	@field:SerializedName("urls")
	val urls: Urls,

	@field:SerializedName("alt_description")
	val altDescription: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("downloads")
	val downloads: Int,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("blur_hash")
	val blurHash: String,

	@field:SerializedName("links")
	val links: Links,

	@field:SerializedName("location")
	val location: Location,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("categories")
	val categories: List<Any>,

	@field:SerializedName("promoted_at")
	val promotedAt: String,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("views")
	val views: Int,

	@field:SerializedName("height")
	val height: Int,

	@field:SerializedName("likes")
	val likes: Int,

	@field:SerializedName("exif")
	val exif: Exif
)

data class Social(

	@field:SerializedName("twitter_username")
	val twitterUsername: Any,

	@field:SerializedName("paypal_email")
	val paypalEmail: Any,

	@field:SerializedName("instagram_username")
	val instagramUsername: String,

	@field:SerializedName("portfolio_url")
	val portfolioUrl: String
)

data class Exif(

	@field:SerializedName("exposure_time")
	val exposureTime: String,

	@field:SerializedName("aperture")
	val aperture: String,

	@field:SerializedName("focal_length")
	val focalLength: String,

	@field:SerializedName("iso")
	val iso: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("model")
	val model: String,

	@field:SerializedName("make")
	val make: String
)

data class Urls(

	@field:SerializedName("small")
	val small: String,

	@field:SerializedName("small_s3")
	val smallS3: String,

	@field:SerializedName("thumb")
	val thumb: String,

	@field:SerializedName("raw")
	val raw: String,

	@field:SerializedName("regular")
	val regular: String,

	@field:SerializedName("full")
	val full: String
)

data class Position(

	@field:SerializedName("latitude")
	val latitude: Any,

	@field:SerializedName("longitude")
	val longitude: Any
)

data class Links(

	@field:SerializedName("followers")
	val followers: String,

	@field:SerializedName("portfolio")
	val portfolio: String,

	@field:SerializedName("following")
	val following: String,

	@field:SerializedName("self")
	val self: String,

	@field:SerializedName("html")
	val html: String,

	@field:SerializedName("photos")
	val photos: String,

	@field:SerializedName("likes")
	val likes: String,

	@field:SerializedName("download")
	val download: String,

	@field:SerializedName("download_location")
	val downloadLocation: String
)

data class Location(

	@field:SerializedName("country")
	val country: Any,

	@field:SerializedName("city")
	val city: Any,

	@field:SerializedName("name")
	val name: Any,

	@field:SerializedName("position")
	val position: Position,

	@field:SerializedName("title")
	val title: Any
)

data class ProfileImage(

	@field:SerializedName("small")
	val small: String,

	@field:SerializedName("large")
	val large: String,

	@field:SerializedName("medium")
	val medium: String
)

data class TopicSubmissions(
	val any: Any? = null
)

data class User(

	@field:SerializedName("total_photos")
	val totalPhotos: Int,

	@field:SerializedName("accepted_tos")
	val acceptedTos: Boolean,

	@field:SerializedName("social")
	val social: Social,

	@field:SerializedName("twitter_username")
	val twitterUsername: Any,

	@field:SerializedName("last_name")
	val lastName: String,

	@field:SerializedName("bio")
	val bio: String,

	@field:SerializedName("total_likes")
	val totalLikes: Int,

	@field:SerializedName("portfolio_url")
	val portfolioUrl: String,

	@field:SerializedName("profile_image")
	val profileImage: ProfileImage,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("for_hire")
	val forHire: Boolean,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("links")
	val links: Links,

	@field:SerializedName("total_collections")
	val totalCollections: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("first_name")
	val firstName: String,

	@field:SerializedName("instagram_username")
	val instagramUsername: String,

	@field:SerializedName("username")
	val username: String
)

package com.patriciafiona.a_30_days.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipesResponse(
	val count: Int? = null,
	val results: List<ResultsItem?>? = null
) : Parcelable

@Parcelize
data class MeasurementsItem(
	val unit: Unit? = null,
	val quantity: String? = null,
	val id: Int? = null
) : Parcelable

@Parcelize
data class Brand(
	val image_url: String? = null,
	val name: String? = null,
	val id: Int? = null,
	val slug: String? = null
) : Parcelable

@Parcelize
data class ShowItem(
	val name: String? = null,
	val id: Int? = null
) : Parcelable

@Parcelize
data class InstructionsItem(
	val start_time: Int? = null,
	val appliance: String? = null,
	val end_time: Int? = null,
	val temperature: Int? = null,
	val id: Int? = null,
	val position: Int? = null,
	val display_text: String? = null
) : Parcelable

@Parcelize
data class TopicsItem(
	val name: String? = null,
	val slug: String? = null
) : Parcelable

@Parcelize
data class TotalTimeTier(
	val tier: String? = null,
	val display_tier: String? = null
) : Parcelable

@Parcelize
data class ComponentsItem(
	val extra_comment: String? = null,
	val raw_text: String? = null,
	val ingredient: Ingredient? = null,
	val id: Int? = null,
	val position: Int? = null,
	val measurements: List<MeasurementsItem?>? = null
) : Parcelable

@Parcelize
data class Unit(
	val system: String? = null,
	val name: String? = null,
	val display_plural: String? = null,
	val display_singular: String? = null,
	val abbreviation: String? = null
) : Parcelable

@Parcelize
data class CreditsItem(
	val name: String? = null,
	val type: String? = null,
	val image_url: String? = null,
	val id: Int? = null,
	val slug: String? = null
) : Parcelable

@Parcelize
data class Ingredient(
	val updated_at: Int? = null,
	val name: String? = null,
	val created_at: Int? = null,
	val display_plural: String? = null,
	val id: Int? = null,
	val display_singular: String? = null
) : Parcelable

@Parcelize
data class SectionsItem(
	val components: List<ComponentsItem?>? = null,
	val name: String? = null,
	val position: Int? = null
) : Parcelable

@Parcelize
data class Show(
	val name: String? = null,
	val id: Int? = null
) : Parcelable

@Parcelize
data class CompilationsItem(
	val country: String? = null,
	val aspect_ratio: String? = null,
	val is_shoppable: Boolean? = null,
	val keywords: String? = null,
	val show: List<ShowItem?>? = null,
	val description: String? = null,
	val create_at: Int? = null,
	val draft_status: String? = null,
	val language: String? = null,
	val thumbnail_url: String? = null,
	val thumbnail_alt_Text: String? = null,
	val video_url: String? = null,
	val approved_at: Int? = null,
	val name: String? = null,
	val canonical_id: String? = null,
	val id: Int? = null,
	val slug: String? = null,
	val promotion: String? = null,
	val video_id: Int? = null
) : Parcelable

@Parcelize
data class TagsItem(
	val name: String? = null,
	val id: Int? = null,
	val display_name: String? = null,
	val type: String? = null
) : Parcelable

@Parcelize
data class ResultsItem(
	val nutrition_visibility: String? = null,
	val instructions: List<InstructionsItem?>? = null,
	val country: String? = null,
	val keywords: String? = null,
	val language: String? = null,
	val user_ratings: UserRatings? = null,
	val id: Int? = null,
	val slug: String? = null,
	val show_id: Int? = null,
	val servings_noun_singular: String? = null,
	val prep_time_minutes: String? = null,
	val sections: List<SectionsItem?>? = null,
	val brand_id: String? = null,
	val tags: List<TagsItem?>? = null,
	val nutrition: Nutrition? = null,
	val name: String? = null,
	val num_servings: Int? = null,
	val buzz_id: String? = null,
	val tips_and_ratings_enabled: Boolean? = null,
	val aspect_ratio: String? = null,
	val show: Show? = null,
	val description: String? = null,
	val created_at: Int? = null,
	val draft_status: String? = null,
	val thumbnail_url: String? = null,
	val thumbnail_alt_text: String? = null,
	val video_url: String? = null,
	val updated_at: Int? = null,
	val credits: List<CreditsItem?>? = null,
	val is_one_top: Boolean? = null,
	val approved_at: Int? = null,
	val renditions: List<RenditionsItem?>? = null,
	val servings_noun_plural: String? = null,
	val is_shoppable: Boolean? = null,
	val topics: List<TopicsItem?>? = null,
	val seo_title: String? = null,
	val yields: String? = null,
	val canonical_id: String? = null,
	val cook_time_minutes: String? = null,
	val promotion: String? = null,
	val video_id: Int? = null,
	val recipes: List<RecipesItem?>? = null
) : Parcelable

@Parcelize
data class RecipesItem(
	val nutrition_visibility: String? = null,
	val country: String? = null,
	val instructions: List<InstructionsItem?>? = null,
	val keywords: String? = null,
	val language: String? = null,
	val user_ratings: UserRatings? = null,
	val id: Int? = null,
	val slug: String? = null,
	val show_id: Int? = null,
	val servings_noun_singular: String? = null,
	val prep_time_minutes: String? = null,
	val sections: List<SectionsItem?>? = null,
	val tags: List<TagsItem?>? = null,
	val nutrition: Nutrition? = null,
	val name: String? = null,
	val compilations: List<CompilationsItem?>? = null,
	val num_servings: Int? = null,
	val tips_and_ratings_enabled: Boolean? = null,
	val aspect_ratio: String? = null,
	val show: Show? = null,
	val created_at: Int? = null,
	val description: String? = null,
	val draft_status: String? = null,
	val thumbnail_url: String? = null,
	val thumbnail_alt_text: String? = null,
	val total_time_minutes: String? = null,
	val video_url: String? = null,
	val updated_at: Int? = null,
	val credits: List<CreditsItem?>? = null,
	val approved_at: Int? = null,
	val is_one_top: Boolean? = null,
	val renditions: List<RenditionsItem?>? = null,
	val servings_noun_plural: String? = null,
	val is_shoppable: Boolean? = null,
	val topics: List<TopicsItem?>? = null,
	val video_ad_content: String? = null,
	val yields: String? = null,
	val original_videoUrl: String? = null,
	val canonical_id: String? = null,
	val cook_time_minutes: String? = null,
	val promotion: String? = null,
	val video_id: Int? = null
) : Parcelable

@Parcelize
data class RenditionsItem(
	val container: String? = null,
	val poster_url: String? = null,
	val url: String? = null,
	val file_size: Int? = null,
	val duration: Int? = null,
	val bitRate: Int? = null,
	val content_type: String? = null,
	val aspect: String? = null,
	val minimum_bit_rate: String? = null,
	val width: Int? = null,
	val name: String? = null,
	val maximum_bit_rate: String? = null,
	val height: Int? = null
) : Parcelable

@Parcelize
data class UserRatings(
	val count_positive: Int? = null,
	val score: Double? = null,
	val count_negative: Int? = null
) : Parcelable

@Parcelize
data class Nutrition(
	val carbohydrates: Int? = null,
	val fiber: Int? = null,
	val updated_at: String? = null,
	val protein: Int? = null,
	val fat: Int? = null,
	val calories: Int? = null,
	val sugar: Int? = null
) : Parcelable

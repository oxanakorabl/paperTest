package com.paper.paperapp.api.objects

import com.google.gson.annotations.SerializedName

class  Product {

    @SerializedName("productId") private lateinit var _id: String
    @SerializedName("productName") private var _name: String? = null
    @SerializedName("sectionId") private lateinit var _sectionId: String
    @SerializedName("eDescription")  private var _description: String? = null
    @SerializedName("available")private var _available: Boolean = false
    @SerializedName("premium")  private var _premium: Boolean? = null
    @SerializedName("kitchen")  private var _kitchen: String? = null
    @SerializedName("price")  private var _price: Float = 0f
    @SerializedName("weight")  private var _weight: String = ""
    @SerializedName("kcal")  private var _kcal: Float = 0f
    @SerializedName("proteins")  private var _proteins: Float = 0f
    @SerializedName("fats")  private var _fats: Float = 0f
    @SerializedName("carbohydrates")  private var _carbohydrates: Float = 0f
    @SerializedName("productOrder")  private var _order: Int = 0
    @SerializedName("images") private var _images: List<String>? = null
    @SerializedName("components")  private var _components: List<Product>? = null
    var _componentsAdded: List<String>? = null

    val id get() = _id
    val name get() = _name ?: ""
    val sectionId get() = _sectionId
    val description get() = _description ?: ""
    val available get() = _available
    val premium get() = _premium ?: false
    val kitchen get() = _kitchen
    val price get() = _price
    val weight get() = _weight
    val kcal get() = _kcal
    val proteins get() = _proteins
    val fats get() = _fats
    val carbohydrates get() = _carbohydrates
    val order get() = _order
    val images get() = _images ?: listOf()
    val image get() = images.firstOrNull()
    val components get() = _components ?: listOf()
    val componentsAdded get() = _componentsAdded ?: listOf()
}
package com.paper.paperapp.api

import com.google.gson.*
import com.paper.paperapp.api.objects.Product
import java.lang.reflect.Type

class ProductDeserializer : JsonDeserializer<Product> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Product {
        val product = Gson().fromJson(json, Product::class.java)

        val componentIds = mutableListOf<String>()
        val componentsAdded = json.asJsonObject.get("componentsAdded") ?: return product

        for (item: JsonElement in componentsAdded.asJsonArray) {
            val componentValues = item.asJsonObject.get("values").asJsonArray.first().asJsonObject
            componentIds.add(componentValues.get("componentId").asString)
        }
        product._componentsAdded = componentIds
        return product
    }
}

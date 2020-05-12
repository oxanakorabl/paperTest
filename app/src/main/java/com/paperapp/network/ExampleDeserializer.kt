package com.paperapp.network

import com.google.gson.*
import com.paperapp.data.Example
import java.lang.reflect.Type

class ExampleDeserializer : JsonDeserializer<Example> {

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Example {
        val product = Gson().fromJson(json, Example::class.java)

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

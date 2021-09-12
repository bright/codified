@file:UseSerializers(Fruit.CodifiedSerializer::class, Vegetable.CodifiedSerializer::class)

package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import pl.brightinventions.codified.enums.CodifiedEnum

@Serializable
data class FoodBasket(
    val fruits: List<CodifiedEnum<Fruit, String>>,
    val vegetables: List<CodifiedEnum<Vegetable, String>>
)

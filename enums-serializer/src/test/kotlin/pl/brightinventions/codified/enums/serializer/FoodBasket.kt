package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.Serializable
import pl.brightinventions.codified.enums.CodifiedEnum

@Serializable
data class FoodBasket(
    val fruits: List<@Serializable(with = Fruit.CodifiedSerializer::class) CodifiedEnum<Fruit, String>>,
    val vegetables: List<@Serializable(with = Vegetable.CodifiedSerializer::class) CodifiedEnum<Vegetable, String>>
)

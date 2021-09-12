package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.KSerializer
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum

enum class Fruit(override val code: String) : Codified<String> {
    APPLE("elppa"), ORANGE("egnaro");

    object CodifiedSerializer : KSerializer<CodifiedEnum<Fruit, String>> by codifiedEnumSerializer()
}

enum class Vegetable(override val code: String) : Codified<String> {
    CUCUMBER("rebmucuc"), POTATO("otatop");

    object CodifiedSerializer : KSerializer<CodifiedEnum<Vegetable, String>> by codifiedEnumSerializer()
}
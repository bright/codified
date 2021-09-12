package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.Serializable
import pl.brightinventions.codified.enums.CodifiedEnum

@Serializable
data class StringEnumWrapper(
    @Serializable(with = StringEnum.CodifiedSerializer::class)
    val stringEnum: CodifiedEnum<StringEnum, String>
)
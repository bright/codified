package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.Serializable
import pl.brightinventions.codified.enums.CodifiedEnum


@Serializable
data class StringEnumListWrapper(
    val stringEnums: List<@Serializable(with = StringEnum.CodifiedSerializer::class) CodifiedEnum<StringEnum, String>>
)
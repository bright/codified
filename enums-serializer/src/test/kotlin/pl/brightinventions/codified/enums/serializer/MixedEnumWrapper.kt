package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.Serializable
import pl.brightinventions.codified.enums.CodifiedEnum

@Serializable
data class MixedEnumWrapper(
    @Serializable(with = StringEnum.CodifiedSerializer::class)
    val stringEnum: CodifiedEnum<StringEnum, String>,
    @Serializable(with = IntEnum.CodifiedSerializer::class)
    val intEnum: CodifiedEnum<IntEnum, Int>,
)
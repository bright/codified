package pl.brightinventions.codified.gson

import pl.brightinventions.codified.enums.CodifiedEnum

data class MixedEnumWrapper(
    val stringEnum: CodifiedEnum<StringEnum, String>,
    val intEnum: CodifiedEnum<IntEnum, Int>,
)
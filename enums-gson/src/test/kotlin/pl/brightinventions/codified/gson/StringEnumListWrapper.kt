package pl.brightinventions.codified.gson

import pl.brightinventions.codified.enums.CodifiedEnum

data class StringEnumListWrapper(
    val stringEnums: List<CodifiedEnum<StringEnum, String>>
)
package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.KSerializer
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum

const val FOO_CODE = "oof"
const val BAR_CODE = "rab"

enum class StringEnum(override val code: String) : Codified<String> {
    FOO(FOO_CODE), BAR(BAR_CODE);

    object CodifiedSerializer : KSerializer<CodifiedEnum<StringEnum, String>> by codifiedEnumSerializer()
}
package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum

const val BAZ_CODE = 100
const val QUX_CODE = 999

enum class IntEnum(override val code: Int) : Codified<Int> {
    BAZ(BAZ_CODE), QUX(QUX_CODE);

    object CodifiedSerializer : KSerializer<CodifiedEnum<IntEnum, Int>> by codifiedEnumSerializer(Int.serializer())
}
package pl.brightinventions.codified.gson

import pl.brightinventions.codified.Codified

const val BAZ_CODE = 100
const val QUX_CODE = 999

enum class IntEnum(override val code: Int) : Codified<Int> {
    BAZ(BAZ_CODE), QUX(QUX_CODE);
}
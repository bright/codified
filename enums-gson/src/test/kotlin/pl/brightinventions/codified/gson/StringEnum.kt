package pl.brightinventions.codified.gson

import pl.brightinventions.codified.Codified

const val FOO_CODE = "oof"
const val BAR_CODE = "rab"

enum class StringEnum(override val code: String) : Codified<String> {
    FOO(FOO_CODE), BAR(BAR_CODE);
}
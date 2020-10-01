package pl.brightinventions.codified.enums

import pl.brightinventions.codified.Codified
import java.io.Serializable

sealed class CodifiedEnum<T, C> : Serializable where T : Enum<T>, T : Codified<C> {
    data class Known<T, C>(val value: T) : CodifiedEnum<T, C>() where T : Enum<T>, T : Codified<C>
    data class Unknown<T, C>(val value: C) : CodifiedEnum<T, C>() where T : Enum<T>, T : Codified<C>

    fun knownOrNull() = (this as? Known<T, C>)?.value
    fun code(): C = when (this) {
        is Known -> value.code
        is Unknown -> value
    }

    companion object {
        inline fun <reified T> decode(value: String): CodifiedEnum<T, String> where T : Enum<T>, T : Codified<String> {
            return CodifiedEnumDecoder.decode(value, T::class.java)
        }
    }
}


fun <T, C> T.codifiedEnum(): CodifiedEnum<T, C> where T : Codified<C>, T : Enum<T> =
    CodifiedEnum.Known(this)

inline fun <reified T> String.codifiedEnum(): CodifiedEnum<T, String> where T : Enum<T>, T : Codified<String> =
    CodifiedEnum.decode(this)

inline fun <reified T, C> codes(): List<C> where T : Codified<C>, T : Enum<T> =
    enumValues<T>().map(Codified<C>::code)

package pl.brightinventions.codified.enums

import pl.brightinventions.codified.Codified
import java.util.concurrent.ConcurrentHashMap

object CodifiedEnumDecoder {
    private val enumsSerializedNamed = ConcurrentHashMap<Class<*>, Map<String, Enum<*>>>()

    fun <T> decode(value: String, clazz: Class<T>): CodifiedEnum<T, String>
            where T : Enum<T>, T : Codified<String> {

        val namesForEnum = enumsSerializedNamed.getOrPut(clazz) {
            clazz.enumConstants.associateBy { it.code }
        }
        val enumValue = namesForEnum[value]
        return if (enumValue != null) {
            @Suppress("UNCHECKED_CAST")
            (CodifiedEnum.Known(enumValue as T))
        } else {
            CodifiedEnum.Unknown(value)
        }
    }
}

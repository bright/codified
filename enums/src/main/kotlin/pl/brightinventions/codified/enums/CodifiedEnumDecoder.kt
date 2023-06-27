package pl.brightinventions.codified.enums

import pl.brightinventions.codified.Codified
import java.util.concurrent.ConcurrentHashMap

object CodifiedEnumDecoder {
    private val enumsSerializedNamed = ConcurrentHashMap<Class<*>, Map<Any, Enum<*>>>()

    fun <TEnum, TCode: Any> decode(value: TCode, clazz: Class<TEnum>): CodifiedEnum<TEnum, TCode>
            where TEnum : Enum<TEnum>, TEnum : Codified<TCode> {

        val namesForEnum = enumsSerializedNamed.getOrPut(clazz) {
            clazz.enumConstants.associateBy { it.code }
        }
        val enumValue = namesForEnum[value]
        return if (enumValue != null) {
            @Suppress("UNCHECKED_CAST")
            (CodifiedEnum.Known(enumValue as TEnum))
        } else {
            CodifiedEnum.Unknown(value)
        }
    }
}

package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum

class CodifiedEnumSerializer<T, C>(
    enumValues: Array<T>,
    private val codeSerializer: KSerializer<C>
) : KSerializer<CodifiedEnum<T, C>> where T : Enum<T>, T : Codified<C> {

    override val descriptor = codeSerializer.descriptor

    private val enumValuesByCode = enumValues.associateBy { it.code }

    override fun deserialize(decoder: Decoder): CodifiedEnum<T, C> {
        val code = decoder.decodeSerializableValue(codeSerializer)
        val valueForCode = enumValuesByCode[code]
        return if (valueForCode == null) {
            CodifiedEnum.Unknown(code)
        } else {
            CodifiedEnum.Known(valueForCode)
        }
    }

    override fun serialize(encoder: Encoder, value: CodifiedEnum<T, C>) {
        when (value) {
            is CodifiedEnum.Known -> encoder.encodeSerializableValue(codeSerializer, value.value.code)
            is CodifiedEnum.Unknown -> encoder.encodeSerializableValue(codeSerializer, value.value)
        }
    }
}

inline fun <reified T> codifiedEnumSerializer(): KSerializer<CodifiedEnum<T, String>>
        where T : Enum<T>, T : Codified<String> =
    codifiedEnumSerializer(String.serializer())

inline fun <reified T, C> codifiedEnumSerializer(codeSerializer: KSerializer<C>): KSerializer<CodifiedEnum<T, C>>
        where T : Enum<T>, T : Codified<C> =
    CodifiedEnumSerializer(enumValues(), codeSerializer)

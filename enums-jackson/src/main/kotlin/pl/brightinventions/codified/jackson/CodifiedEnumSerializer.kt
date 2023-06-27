package pl.brightinventions.codified.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import pl.brightinventions.codified.enums.CodifiedEnum

internal class CodifiedEnumSerializer : StdSerializer<CodifiedEnum<*, *>>(CodifiedEnum::class.java) {
    override fun serialize(value: CodifiedEnum<*, *>, gen: JsonGenerator, serializers: SerializerProvider) {
        serializers.defaultSerializeValue(value.code(), gen)
    }
}

package dev.codified.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import pl.brightinventions.codified.Codified

internal class CodifiedSerializer : StdSerializer<Codified<*>>(Codified::class.java) {
    override fun serialize(value: Codified<*>, gen: JsonGenerator, serializers: SerializerProvider) {
        serializers.defaultSerializeValue(value.code, gen)
    }
}

package dev.codified.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum
import pl.brightinventions.codified.enums.CodifiedEnumDecoder
import java.io.Serializable

internal class CodifiedEnumDeserializer<T>() : JsonDeserializer<CodifiedEnum<T, String>>(),
    ContextualDeserializer where T : Codified<String>, T : Enum<T> {
    private lateinit var enumCodeType: Class<Serializable>
    private lateinit var enumType: Class<T>

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): CodifiedEnum<T, String> {
        return CodifiedEnumDecoder.decode(p.valueAsString, enumType)
    }

    override fun createContextual(
        ctxt: DeserializationContext,
        property: BeanProperty?
    ): JsonDeserializer<CodifiedEnum<T, String>> {
        return CodifiedEnumDeserializer(ctxt.contextualType)
    }

    @Suppress("UNCHECKED_CAST")
    constructor(contextualType: JavaType) : this() {
        this.enumType = contextualType.bindings.getBoundType(0).rawClass as Class<T>
        this.enumCodeType = contextualType.bindings.getBoundType(1).rawClass as Class<Serializable>
    }
}

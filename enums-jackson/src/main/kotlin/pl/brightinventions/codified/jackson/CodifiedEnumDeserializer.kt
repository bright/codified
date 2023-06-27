package pl.brightinventions.codified.jackson

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

internal class CodifiedEnumDeserializer<TEnum, TCode : Any>() : JsonDeserializer<CodifiedEnum<TEnum, TCode>>(),
    ContextualDeserializer where TEnum : Codified<TCode>, TEnum : Enum<TEnum> {
    private lateinit var enumCodeType: Class<TCode>
    private lateinit var enumType: Class<TEnum>

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): CodifiedEnum<TEnum, TCode> {
        @Suppress("UNCHECKED_CAST")
        return CodifiedEnumDecoder.decode(p.readValueAs(enumCodeType), enumType)
    }

    override fun createContextual(
        ctxt: DeserializationContext,
        property: BeanProperty?
    ): JsonDeserializer<CodifiedEnum<TEnum, TCode>> {
        return CodifiedEnumDeserializer(ctxt.contextualType)
    }

    @Suppress("UNCHECKED_CAST")
    constructor(contextualType: JavaType) : this() {
        this.enumType = contextualType.bindings.getBoundType(0).rawClass as Class<TEnum>
        this.enumCodeType = contextualType.bindings.getBoundType(1).rawClass as Class<TCode>
    }
}

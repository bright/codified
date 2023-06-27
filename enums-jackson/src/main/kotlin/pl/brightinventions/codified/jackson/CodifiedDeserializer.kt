package pl.brightinventions.codified.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnumDecoder
import java.lang.reflect.ParameterizedType

class CodifiedDeserializer<TEnum, TCode : Any>() : JsonDeserializer<TEnum>(),
    ContextualDeserializer where TEnum : Codified<TCode>, TEnum : Enum<TEnum> {
    private lateinit var enumCodeType: Class<TCode>
    private lateinit var enumType: Class<TEnum>

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): TEnum? {
        @Suppress("UNCHECKED_CAST")
        return CodifiedEnumDecoder.decode(p.readValueAs(enumCodeType), enumType).knownOrNull()
    }

    override fun createContextual(
        ctxt: DeserializationContext,
        property: BeanProperty?
    ): JsonDeserializer<TEnum> {
        return CodifiedDeserializer(ctxt.contextualType)
    }

    override fun handledType(): Class<*> {
        return Codified::class.java
    }

    @Suppress("UNCHECKED_CAST")
    constructor(contextualType: JavaType) : this() {
        this.enumType = contextualType.rawClass as Class<TEnum>
        this.enumCodeType = (enumType.genericInterfaces[0] as ParameterizedType).actualTypeArguments[0] as Class<TCode>
    }
}

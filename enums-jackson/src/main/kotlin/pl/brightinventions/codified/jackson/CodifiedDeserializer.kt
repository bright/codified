package pl.brightinventions.codified.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.deser.ContextualDeserializer
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnumDecoder

class CodifiedDeserializer<T>() : JsonDeserializer<T>(),
    ContextualDeserializer where T : Codified<String>, T : Enum<T> {
    private lateinit var enumType: Class<T>

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext?): T? {
        return CodifiedEnumDecoder.decode(p.valueAsString, enumType).knownOrNull()
    }

    override fun createContextual(
        ctxt: DeserializationContext,
        property: BeanProperty?
    ): JsonDeserializer<T> {
        return CodifiedDeserializer(ctxt.contextualType)
    }

    override fun handledType(): Class<*> {
        return Codified::class.java
    }

    @Suppress("UNCHECKED_CAST")
    constructor(contextualType: JavaType) : this() {
        this.enumType = contextualType.rawClass as Class<T>
    }
}

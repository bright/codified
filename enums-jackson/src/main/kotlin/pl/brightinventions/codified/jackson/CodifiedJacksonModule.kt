package pl.brightinventions.codified.jackson

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleDeserializers
import com.fasterxml.jackson.databind.module.SimpleSerializers
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum

class CodifiedJacksonModule : Module() {
    override fun version(): Version {
        return Version.unknownVersion()
    }

    override fun getModuleName(): String {
        return CodifiedJacksonModule::class.qualifiedName!!
    }

    override fun setupModule(context: SetupContext) {
        context.addSerializers(
            SimpleSerializers(
                listOf(
                    CodifiedEnumSerializer(),
                    CodifiedSerializer()
                )
            )
        )

        val simpleDeserializers = SimpleDeserializers().apply {
            addDeserializer(CodifiedEnum::class.java, CodifiedEnumDeserializer::class.java.newInstance())
            addDeserializer(Codified::class.java, CodifiedDeserializer::class.java.newInstance())
        }
        context.addDeserializers(simpleDeserializers)
    }
}


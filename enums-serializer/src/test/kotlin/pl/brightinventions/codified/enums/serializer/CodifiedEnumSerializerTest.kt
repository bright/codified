package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum
import pl.brightinventions.codified.enums.codifiedEnum
import pl.miensol.shouldko.shouldEqual

class CodifiedEnumSerializerTest {

    companion object {
        const val FOO_CODE = "oof"
        const val BAR_CODE = "rab"
    }

    enum class StringEnum(override val code: String) : Codified<String> {
        FOO(FOO_CODE), BAR(BAR_CODE);

        object CodifiedSerializer : KSerializer<CodifiedEnum<StringEnum, String>> by codifiedEnumSerializer()
    }

    @Serializable
    data class StringEnumWrapper(
        @Serializable(with = StringEnum.CodifiedSerializer::class)
        val stringEnum: CodifiedEnum<StringEnum, String>
    )

    private val json = Json.Default

    @Test
    fun `codified enum should be represented by the its code in JSON`() {
        StringEnum.values().forEach {
            val wrapper = StringEnumWrapper(it.codifiedEnum())
            val string = json.encodeToString(StringEnumWrapper.serializer(), wrapper)
            string.shouldEqual("{\"stringEnum\":\"${it.code}\"}")
        }
    }

    @Test
    fun `unknown codified enum should be represented by the its code in JSON`() {
        val unknownCode = "unknownCode"
        val unknownCodified = unknownCode.codifiedEnum<StringEnum>()
        val wrapper = StringEnumWrapper(unknownCodified)
        val string = json.encodeToString(StringEnumWrapper.serializer(), wrapper)
        string.shouldEqual("{\"stringEnum\":\"$unknownCode\"}")
    }

    @Test
    fun `codified enum in JSON should be parsed`() {
        StringEnum.values().forEach {
            val stringified = "{\"stringEnum\":\"${it.code}\"}"
            val parsedWrapper = json.decodeFromString(StringEnumWrapper.serializer(), stringified)
            val expectedWrapper = StringEnumWrapper(it.codifiedEnum())
            parsedWrapper.shouldEqual(expectedWrapper)
        }
    }

    @Test
    fun `unknown code in JSON should be parsed`() {
        val unknownCode = "unknownCode"
        val unknownCodified = unknownCode.codifiedEnum<StringEnum>()
        val stringified = "{\"stringEnum\":\"$unknownCode\"}"
        val parsedWrapper = json.decodeFromString(StringEnumWrapper.serializer(), stringified)
        val expectedWrapper = StringEnumWrapper(unknownCodified)
        parsedWrapper.shouldEqual(expectedWrapper)
    }
}
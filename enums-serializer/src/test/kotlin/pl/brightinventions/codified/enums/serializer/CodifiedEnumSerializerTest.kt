package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import pl.brightinventions.codified.enums.codifiedEnum
import pl.miensol.shouldko.shouldEqual

class CodifiedEnumSerializerTest {


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
    fun `codified enums should be represented by the their codes in JSON array`() {
        val enums = StringEnum.values()
        val expectedEnumsArray = enums.joinToString(",") { "\"${it.code}\"" }
        val codifiedEnums = enums.map { it.codifiedEnum() }
        val wrapper = StringEnumListWrapper(codifiedEnums)
        val string = json.encodeToString(StringEnumListWrapper.serializer(), wrapper)
        string.shouldEqual("{\"stringEnums\":[$expectedEnumsArray]}")
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
    fun `unknown codified enums should be represented by the its code in JSON array`() {
        val unknownCodes = listOf("unknownCode1", "unknownCode2")
        val expectedCodesArray = unknownCodes.joinToString(",") { "\"$it\"" }
        val unknownCodified = unknownCodes.map { it.codifiedEnum<StringEnum>() }
        val wrapper = StringEnumListWrapper(unknownCodified)
        val string = json.encodeToString(StringEnumListWrapper.serializer(), wrapper)
        string.shouldEqual("{\"stringEnums\":[$expectedCodesArray]}")
    }

    @Test
    fun `mixed known and unknown codified enums should be represented by the its code in JSON array`() {
        val mixedCodifiedEnums = listOf(
            StringEnum.FOO.codifiedEnum(),
            "unknownCode1".codifiedEnum(),
            StringEnum.BAR.codifiedEnum(),
            "unknownCode2".codifiedEnum()
        )
        val expectedCodesArray = mixedCodifiedEnums.joinToString(",") { "\"${it.code()}\"" }
        val wrapper = StringEnumListWrapper(mixedCodifiedEnums)
        val string = json.encodeToString(StringEnumListWrapper.serializer(), wrapper)
        string.shouldEqual("{\"stringEnums\":[$expectedCodesArray]}")
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
    fun `codified enums in JSON array should be parsed`() {
        val enums = StringEnum.values()
        val stringifiedEnumsArray = enums.joinToString(",") { "\"${it.code}\"" }
        val stringifiedWrapper = "{\"stringEnums\":[$stringifiedEnumsArray]}"
        val codifiedEnums = enums.map { it.codifiedEnum() }
        val expectedWrapper = StringEnumListWrapper(codifiedEnums)
        val parsedWrapper = json.decodeFromString(StringEnumListWrapper.serializer(), stringifiedWrapper)
        parsedWrapper.shouldEqual(expectedWrapper)
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

    @Test
    fun `unknown codified enums in JSON array should be parsed`() {
        val unknownCodes = listOf("unknownCode1", "unknownCode2")
        val stringifiedCodesArray = unknownCodes.joinToString(",") { "\"$it\"" }
        val stringifiedWrapper = "{\"stringEnums\":[$stringifiedCodesArray]}"
        val codifiedEnums = unknownCodes.map { it.codifiedEnum<StringEnum>() }
        val expectedWrapper = StringEnumListWrapper(codifiedEnums)
        val parsedWrapper = json.decodeFromString(StringEnumListWrapper.serializer(), stringifiedWrapper)
        parsedWrapper.shouldEqual(expectedWrapper)
    }

    @Test
    fun `mixed known and unknown codified enums in JSON array should be parsed`() {
        val mixedCodifiedEnums = listOf(
            StringEnum.FOO.codifiedEnum(),
            "unknownCode1".codifiedEnum(),
            StringEnum.BAR.codifiedEnum(),
            "unknownCode2".codifiedEnum()
        )
        val stringifiedCodesArray = mixedCodifiedEnums.joinToString(",") { "\"${it.code()}\"" }
        val stringifiedWrapper = "{\"stringEnums\":[$stringifiedCodesArray]}"
        val expectedWrapper = StringEnumListWrapper(mixedCodifiedEnums)
        val parsedWrapper = json.decodeFromString(StringEnumListWrapper.serializer(), stringifiedWrapper)
        parsedWrapper.shouldEqual(expectedWrapper)
    }
}
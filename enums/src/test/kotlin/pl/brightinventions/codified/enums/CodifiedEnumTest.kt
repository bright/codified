package pl.brightinventions.codified.enums

import org.junit.jupiter.api.Test
import pl.brightinventions.codified.Codified
import pl.miensol.shouldko.shouldEqual
import pl.miensol.shouldko.shouldNotEqual

class CodifiedEnumTest {

    companion object {
        const val FOO_CODE = "oof"
        const val BAR_CODE = "rab"
    }

    enum class StringEnum(override val code: String) : Codified<String> {
        FOO(FOO_CODE), BAR(BAR_CODE)
    }

    @Test
    fun `codified code should equal enum code`() {
        StringEnum.values().forEach {
            val codified = it.codifiedEnum()
            codified.code().shouldEqual(it.code)
        }
    }

    @Test
    fun `codified enum should be known`() {
        StringEnum.values().forEach {
            val codified = it.codifiedEnum()
            val knownEnum = codified.knownOrNull()
            knownEnum.shouldEqual(it)
        }
    }

    @Test
    fun `codified string equal to enum code should be known`() {
        StringEnum.values().forEach {
            val codified = it.code.codifiedEnum<StringEnum>()
            val knownEnum = codified.knownOrNull()
            knownEnum.shouldEqual(it)
        }
    }

    @Test
    fun `codified string equal to inverted case enum code should be unknown`() {
        StringEnum.values().forEach {
            val codified = it.code.toUpperCase().codifiedEnum<StringEnum>()
            val knownEnum = codified.knownOrNull()
            knownEnum.shouldEqual(null)
        }
    }

    @Test
    fun `codified unknown string should be preserved`() {
        val codified = "abc".codifiedEnum<StringEnum>()
        codified.code().shouldEqual("abc")
    }

    @Test
    fun `same codified enums should be equal`() {
        val codified1 = StringEnum.BAR.codifiedEnum()
        val codified2 = StringEnum.BAR.codifiedEnum()
        codified1.shouldEqual(codified2)
    }

    @Test
    fun `different codified enums should not be equal`() {
        val codified1 = StringEnum.BAR.codifiedEnum()
        val codified2 = StringEnum.FOO.codifiedEnum()
        codified1.shouldNotEqual(codified2)
    }
}
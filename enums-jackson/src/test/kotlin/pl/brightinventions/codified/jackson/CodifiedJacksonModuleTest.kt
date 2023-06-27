package pl.brightinventions.codified.jackson

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.junit.jupiter.api.Test
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum
import pl.miensol.shouldko.shouldEqual

class CodifiedJacksonModuleTest {
    val objectMapper = ObjectMapper().findAndRegisterModules()

    @Test
    fun `can serialize and deserialize known value to codified enum with string`() {
        // given
        val input = Colour.Blue

        // when
        val output = serdeCodifiedEnum(input)

        // then
        output.knownOrNull().shouldEqual(input)
    }

    @Test
    fun `can serialize and deserialize known value to codified enum with int`() {
        // given
        val input = Weight.Heavy

        // when
        val output = serdeCodifiedEnum(input)

        // then
        output.knownOrNull().shouldEqual(input)
    }

    @Test
    fun `can deserialize known value to codified enum with int from json`() {
        // given
        val input = 400

        // when
        val output = serdeCodifiedEnum(input)

        // then
        output.knownOrNull().shouldEqual(Weight.Medium)
    }

    @Test
    fun `can serialize and deserialize not known value to codified enum`() {
        // given
        val input = "pink"

        // when
        val output = serdeCodifiedEnum(input)

        // then
        output.knownOrNull().shouldEqual(null)
        output.code().shouldEqual("pink")
    }

    @Test
    fun `can serialize and deserialize known value to enum property with string`() {
        // given
        val input = HasColour(Colour.Blue)

        // when
        val output = serde(input)

        // then
        output.shouldEqual(input)
    }

    @Test
    fun `can serialize and deserialize known value to enum property with int`() {
        // given
        val input = HasWeight().apply { weight = Weight.Medium }

        // when
        val output = serde(input)

        // then
        output.shouldEqual(input)
    }

    @Test
    fun `can deserialize not known value to enum property`() {
        // given
        val input = """{"colour": "pink"}"""
        // when
        val output = objectMapper.readValue(input, HasColour::class.java)
        // then
        output.colour.shouldEqual(null)
    }

    @Test
    fun `can deserialize known value to enum property`() {
        // given
        val input = """{"colour": "blue"}"""
        // when
        val output = objectMapper.readValue(input, HasColour::class.java)
        // then
        output.colour.shouldEqual(Colour.Blue)
    }

    @Test
    fun `can deserialize known value to codified enum property`() {
        // given
        val input = HasCodifiedColour(Colour.Blue)

        // when
        val output = serde(input)

        // then
        output.shouldEqual(input)
    }

    @Test
    fun `can deserialize not known value to codified enum property`() {
        // given
        val input = HasCodifiedColour("pink")
        // when
        val output = serde(input)
        // then
        output.colour!!.knownOrNull().shouldEqual(null)
        output.colour!!.code().shouldEqual("pink")
    }

    private fun serdeCodifiedEnum(input: Colour): CodifiedEnum<Colour, String> {
        val json = objectMapper.writer().writeValueAsString(input)
        return objectMapper.readValue(json, object : TypeReference<CodifiedEnum<Colour, String>>() {})
    }

    private fun serdeCodifiedEnum(input: Weight): CodifiedEnum<Weight, Int> {
        val json = objectMapper.writer().writeValueAsString(input)
        return objectMapper.readValue(json, object : TypeReference<CodifiedEnum<Weight, Int>>() {})
    }

    private fun serdeCodifiedEnum(input: String): CodifiedEnum<Colour, String> {
        val json = objectMapper.writer().writeValueAsString(input)
        return objectMapper.readValue(json, object : TypeReference<CodifiedEnum<Colour, String>>() {})
    }

    private fun serdeCodifiedEnum(input: Int): CodifiedEnum<Weight, Int> {
        val json = objectMapper.writer().writeValueAsString(input)
        return objectMapper.readValue(json, object : TypeReference<CodifiedEnum<Weight, Int>>() {})
    }

    private inline fun <reified T> serde(input: T): T {
        val json = objectMapper.writer().writeValueAsString(input)
        return objectMapper.readValue(json, T::class.java)
    }
}


@JsonDeserialize(using = CodifiedDeserializer::class)
enum class Colour(override val code: String) : Codified<String> {
    Red("red"), Green("green"), Blue("blue");
}

class HasColour() {
    var colour: Colour? = null

    constructor(colour: Colour) : this() {
        this.colour = colour
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HasColour

        return colour == other.colour
    }

    override fun hashCode(): Int {
        return colour?.hashCode() ?: 0
    }
}

class HasCodifiedColour() {
    var colour: CodifiedEnum<Colour, String>? = null

    constructor(colour: Colour) : this() {
        this.colour = CodifiedEnum.Known(colour)
    }

    constructor(colour: String) : this() {
        this.colour = CodifiedEnum.Unknown(colour)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HasCodifiedColour

        return colour == other.colour
    }

    override fun hashCode(): Int {
        return colour?.hashCode() ?: 0
    }
}


@JsonDeserialize(using = CodifiedDeserializer::class)
enum class Weight(override val code: Int) : Codified<Int> {
    Light(200), Medium(400), Heavy(60);
}

class HasWeight {
    var weight: Weight? = null
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HasWeight

        return weight == other.weight
    }

    override fun hashCode(): Int {
        return weight?.hashCode() ?: 0
    }
}


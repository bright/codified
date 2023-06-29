package pl.brightinventions.codified.gson

import com.google.gson.GsonBuilder
import org.junit.jupiter.api.Test
import pl.brightinventions.codified.enums.CodifiedEnum
import pl.brightinventions.codified.enums.codifiedEnum
import pl.miensol.shouldko.shouldEqual


class CodifiedEnumTypeAdapterTest {

    private val gson = GsonBuilder()
        .registerTypeAdapterFactory(CodifiedEnumTypeAdapter.Factory())
        .create()


    @Test
    fun `mixed enum wrapper with known values should be serialized`() {
        val wrapper = MixedEnumWrapper(StringEnum.FOO.codifiedEnum(), IntEnum.QUX.codifiedEnum())

        val string = gson.toJson(wrapper)

        string.shouldEqual("{\"stringEnum\":\"${StringEnum.FOO.code}\",\"intEnum\":${IntEnum.QUX.code}}")
    }

    @Test
    fun `mixed enum wrapper with known values should be deserialized`() {
        val string = "{\"stringEnum\":\"${StringEnum.FOO.code}\",\"intEnum\":${IntEnum.QUX.code}}"

        val wrapper = gson.fromJson(string, MixedEnumWrapper::class.java)

        wrapper.stringEnum.knownOrNull().shouldEqual(StringEnum.FOO)
        wrapper.intEnum.knownOrNull().shouldEqual(IntEnum.QUX)
    }

    @Test
    fun `mixed enum wrapper with unknown values should be serialized`() {
        val wrapper = MixedEnumWrapper(CodifiedEnum.Unknown("hello"), CodifiedEnum.Unknown(123))

        val string = gson.toJson(wrapper)

        string.shouldEqual("{\"stringEnum\":\"hello\",\"intEnum\":123}")
    }

    @Test
    fun `mixed enum wrapper with unknown values should be deserialized`() {
        val string = "{\"stringEnum\":\"hello\",\"intEnum\":123}"

        val wrapper = gson.fromJson(string, MixedEnumWrapper::class.java)

        wrapper.stringEnum.code().shouldEqual("hello")
        wrapper.intEnum.code().shouldEqual(123)
    }

    @Test
    fun `known and unknown enums in a wrapper should be serialized`() {
        val enums = listOf<CodifiedEnum<StringEnum, String>>(
            CodifiedEnum.Known(StringEnum.BAR),
            CodifiedEnum.Unknown("hello"),
            CodifiedEnum.Known(StringEnum.FOO),
            CodifiedEnum.Unknown("world"),
        )

        val wrapper = StringEnumListWrapper(enums)

        val string = gson.toJson(wrapper)

        string.shouldEqual("{\"stringEnums\":[\"rab\",\"hello\",\"oof\",\"world\"]}")
    }

    @Test
    fun `known and unknown enums in a wrapper should be deserialized`() {
        val string = "{\"stringEnums\":[\"rab\",\"hello\",\"oof\",\"world\"]}"

        val wrapper = gson.fromJson(string, StringEnumListWrapper::class.java)

        wrapper.stringEnums.size.shouldEqual(4)
        wrapper.stringEnums[0].knownOrNull().shouldEqual(StringEnum.BAR)
        wrapper.stringEnums[1].code().shouldEqual("hello")
        wrapper.stringEnums[2].knownOrNull().shouldEqual(StringEnum.FOO)
        wrapper.stringEnums[3].code().shouldEqual("world")
    }
}
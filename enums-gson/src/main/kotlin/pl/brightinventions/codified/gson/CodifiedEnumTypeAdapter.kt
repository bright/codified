package pl.brightinventions.codified.gson

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import pl.brightinventions.codified.Codified
import pl.brightinventions.codified.enums.CodifiedEnum
import pl.brightinventions.codified.enums.CodifiedEnumDecoder
import java.lang.reflect.ParameterizedType

class CodifiedEnumTypeAdapter<T, C : Any>(
    enumTypeToken: TypeToken<*>,
    private val codeAdapter: TypeAdapter<C>,
) : TypeAdapter<CodifiedEnum<T, C>>() where T : Enum<T>, T : Codified<C> {

    @Suppress("UNCHECKED_CAST")
    private val enumClass = enumTypeToken.rawType as Class<T>

    class Factory : TypeAdapterFactory {
        override fun <T : Any> create(gson: Gson, typeToken: TypeToken<T>?): TypeAdapter<T>? {
            if (typeToken?.rawType != CodifiedEnum::class.java) {
                return null
            }

            val parameterizedType = typeToken.type as ParameterizedType
            val enumType = parameterizedType.actualTypeArguments[0]
            val enumTypeToken = TypeToken.get(enumType)
            val codeType = parameterizedType.actualTypeArguments[1]
            val codeAdapter = gson.getAdapter(TypeToken.get(codeType))

            @Suppress("UNCHECKED_CAST")
            return CodifiedEnumTypeAdapter(enumTypeToken, codeAdapter) as TypeAdapter<T>
        }
    }

    override fun write(out: JsonWriter, value: CodifiedEnum<T, C>?) {
        if (value != null) {
            codeAdapter.write(out, value.code())
        } else {
            out.nullValue()
        }
    }

    override fun read(`in`: JsonReader): CodifiedEnum<T, C> {
        val code = codeAdapter.read(`in`)
        return CodifiedEnumDecoder.decode(code, enumClass)
    }
}
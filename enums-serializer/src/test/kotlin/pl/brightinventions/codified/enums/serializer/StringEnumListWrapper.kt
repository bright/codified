@file:UseSerializers(CodifiedEnumSerializerTest.StringEnum.CodifiedSerializer::class)

package pl.brightinventions.codified.enums.serializer

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import pl.brightinventions.codified.enums.CodifiedEnum


/**
 * Note: something like this would not compile
 *
 * ```
 * @Serializable
 * data class StringEnumListWrapper(
 *     @Serializable(with = StringEnumsSerializer::class)
 *     val stringEnums: List<CodifiedEnum<StringEnum, String>>
 * ) {
 *     object StringEnumsSerializer : KSerializer<List<CodifiedEnum<StringEnum, String>>>
 *         by ListSerializer(StringEnum.CodifiedSerializer)
 * }
 * ```
 *
 * due to an error saying:
 *
 * > Serializer has not been found for type 'CodifiedEnum<CodifiedEnumSerializerTest.StringEnum, String>'. To use
 * > context serializer as fallback, explicitly annotate type or property with @Contextual
 *
 * Therefore, the class is extracted to a separate file that uses [@file:UseSerializers][UseSerializers]
 *
 */
@Serializable
data class StringEnumListWrapper(
    val stringEnums: List<CodifiedEnum<CodifiedEnumSerializerTest.StringEnum, String>>
)
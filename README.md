[![](https://jitpack.io/v/bright/codified.svg)](https://jitpack.io/#bright/codified)

# codified #

Facilitates objects "codification".

At the moment, the main application of this library is making it easier to encode and decode enum classes in a forward compatible way.

## Installation and usage ##

First, add JitPack to your repositories block in Gradle build script.

```kotlin
repositories {
    maven("https://jitpack.io")
}
```

### CodifiedEnum ###

Add the following dependency in order to access `CodifiedEnum` class.

```kotlin
implementation("com.github.bright.codified:enums:1.6.10")
```

`CodifiedEnum` is a sealed class which represents either "known" or "unknown" enum type depending on the `code`
it is represented by. For example:

```kotlin
enum class Fruit(override val code: String) : Codified<String> {
    APPLE("Apple")
}

val codifiedAppleFromEnum: CodifiedEnum<Fruit, String> = Fruit.APPLE.codifiedEnum()
val codifiedAppleFromString: CodifiedEnum<Fruit, String> = "Apple".codifiedEnum()
val codifiedCherry: CodifiedEnum<Fruit, String> = "Cherry".codifiedEnum()

Assertions.assertEquals(Fruit.APPLE, codifiedAppleFromEnum.knownOrNull())
Assertions.assertEquals(Fruit.APPLE, codifiedAppleFromString.knownOrNull())
Assertions.assertEquals(Fruit.APPLE.code, codifiedAppleFromEnum.code())
Assertions.assertEquals(Fruit.APPLE.code, codifiedAppleFromString.code())
Assertions.assertEquals(codifiedAppleFromEnum, codifiedAppleFromString)
Assertions.assertNotEquals(codifiedAppleFromEnum, codifiedCherry)
Assertions.assertEquals("Cherry", codifiedCherry.code())

when (val orange = "Orange".codifiedEnum<Fruit>()) {
    is CodifiedEnum.Known -> when (orange.value) {
        Fruit.APPLE -> TODO()
    }
    is CodifiedEnum.Unknown -> TODO()
}
```

### CodifiedEnum serialization ###

Add the following dependency in order to access `CodifiedEnum` serializer using
[Kotlin serialization](https://github.com/Kotlin/kotlinx.serialization).

```kotlin
implementation("com.github.bright.codified:enums-serializer:1.6.10")
```

Add `CodifiedSerializer` object for your enum class to handle both known and unknown enum types.

```kotlin
enum class Fruit(override val code: String) : Codified<String> {
    APPLE("Apple");

    object CodifiedSerializer : KSerializer<CodifiedEnum<Fruit, String>> by codifiedEnumSerializer()
}

@Serializable
data class FruitWrapper(
    @Serializable(with = Fruit.CodifiedSerializer::class)
    val fruit: CodifiedEnum<Fruit, String>
)

val json = Json(JsonConfiguration.Stable)

val wrapperWithApple = FruitWrapper(Fruit.APPLE.codifiedEnum())
val string = json.stringify(FruitWrapper.serializer(), wrapperWithApple)
Assertions.assertEquals("{\"fruit\":\"Apple\"}", string)

val jsonWithApple = "{\"fruit\":\"Apple\"}"
val wrapperFromJsonWithApple = json.parse(FruitWrapper.serializer(), jsonWithApple)
Assertions.assertEquals(Fruit.APPLE, wrapperFromJsonWithApple.fruit.knownOrNull())

val jsonWithOrange = "{\"fruit\":\"Orange\"}"
val wrapperFromJsonWithOrange = json.parse(FruitWrapper.serializer(), jsonWithOrange)
Assertions.assertEquals("Orange", wrapperFromJsonWithOrange.fruit.code())
```

#### Serialization of collections with CodifiedEnum ####

When `CodifiedEnum` is a parameter of a collection such as `List`,
`@Serializable` should be applied to `CodifiedEnum` - inside the
collection type:

```kotlin
@Serializable
data class FoodBasket(
    val fruits: List<@Serializable(with = Fruit.CodifiedSerializer::class) CodifiedEnum<Fruit, String>>,
    val vegetables: List<@Serializable(with = Vegetable.CodifiedSerializer::class) CodifiedEnum<Vegetable, String>>
)
```
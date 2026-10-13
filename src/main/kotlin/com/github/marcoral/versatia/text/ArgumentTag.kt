package com.github.marcoral.versatia.text

import kotlin.reflect.KClass
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue

/** The arguments of one [Text.parse] call, as a tag sees them. */
interface Arguments {
    val names: Set<String>

    operator fun contains(name: String): Boolean

    /** The value of the argument [name]; throws [UnknownArgumentException] when there is none. */
    operator fun get(name: String): Any?
}

/** The argument [name] as a [T]; throws when it is missing or of another type. */
inline fun <reified T : Any> Arguments.require(name: String): T = this[name] as? T ?: throw ArgumentTypeException(name, T::class, this[name])

/**
 * A tag registered with [Text.tag]: resolved like any MiniMessage tag, with the arguments of the
 * call in hand, so `<papi:player:kills>` can look up the `player` argument.
 */
fun interface ArgumentTag {
    fun resolve(arguments: ArgumentQueue, context: Context, values: Arguments): Tag?
}

open class TextException(message: String) : IllegalArgumentException(message)

class InvalidArgumentNameException(name: String) :
    TextException("\"$name\" cannot name an argument: letters, digits, _ and - only")

class ReservedTagNameException(name: String) :
    TextException("\"$name\" cannot name a tag: it is a MiniMessage tag, or not a tag name (lower-case letters, digits, _ and -)")

class UnknownArgumentException(name: String, available: Set<String>) :
    TextException("No argument \"$name\"; the arguments are: ${available.listed()}")

class UnknownPropertyException(path: String, property: String, available: Set<String>) :
    TextException("{$path} has no property \"$property\"; ${if (available.isEmpty()) "it has none" else "it has: ${available.listed()}"}")

class NoTextFormException(path: String, available: Set<String>) :
    TextException("{$path} cannot be inserted as text; use one of its properties: ${available.listed()}")

class ArgumentTypeException(name: String, expected: KClass<*>, actual: Any?) :
    TextException("Argument \"$name\" is ${if (actual == null) "null" else "a ${actual.javaClass.name}"}, expected ${expected.qualifiedName}")

private fun Set<String>.listed() = if (isEmpty()) "(none)" else joinToString()

package com.github.marcoral.versatia.text

/**
 * A runtime argument of a text: `{name}` inserts [value] and `{name.property}` one of its
 * properties, as its [Presentation] defines them. Strings are inserted verbatim, never parsed, so
 * player input cannot smuggle tags in; a `Component` is inserted as it is. Names consist of
 * letters, digits, `_` and `-`.
 */
class Argument(val name: String, val value: Any?) {
    override fun toString() = "$name = $value"
}

fun arg(name: String, value: Any?) = Argument(name, value)

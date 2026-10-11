package com.github.marcoral.versatia.messages

open class MessagesException(message: String) : IllegalArgumentException(message)

/** Thrown by [Messages.get]; lists the keys that start like the missing one, or how many there are. */
class MissingMessageException(plugin: String, key: String, available: Set<String>) :
    MessagesException("Plugin $plugin has no message \"$key\"; ${describe(key, available)}")

/** A `messages/` file could not be read: invalid YAML, a non-text value, or a key defined in two files. */
class MessagesLoadException(plugin: String, message: String) : MessagesException("Plugin $plugin: $message")

private fun describe(key: String, available: Set<String>): String {
    val prefix = key.substringBefore('.')
    val siblings = available.filter { it.substringBefore('.') == prefix }
    return when {
        available.isEmpty() -> "it has no messages at all; is there a messages/ directory in its data folder?"
        siblings.isEmpty() -> "it has ${available.size} messages, none starting with \"$prefix.\""
        else -> "the messages under \"$prefix\" are: ${siblings.joinToString()}"
    }
}

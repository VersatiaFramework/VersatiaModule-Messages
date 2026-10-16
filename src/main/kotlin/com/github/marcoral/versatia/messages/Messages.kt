package com.github.marcoral.versatia.messages

/**
 * The messages of one plugin, read from the YAML files under `messages/` in its data folder and
 * reloaded with it. Every plugin gets its own instance by injecting this type.
 * See https://versatiaframework.github.io/VersatiaDocs/docs/modules/messages.
 */
interface Messages {
    /** The keys of every message, in file order. */
    val keys: Set<String>

    operator fun contains(key: String): Boolean

    /** The message at [key]; throws [MissingMessageException] when there is none. */
    operator fun get(key: String): Message

    fun getOrNull(key: String): Message?
}

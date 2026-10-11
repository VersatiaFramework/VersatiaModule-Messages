package com.github.marcoral.versatia.messages

import com.github.marcoral.versatia.text.Argument
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

/**
 * One message: MiniMessage text with [runtime arguments](Argument) in braces. A message that
 * refers to no argument was parsed when the file was read and is returned as it is; one with
 * arguments is parsed for the call, and asking for it without them fails with the first missing
 * argument.
 */
interface Message {
    /** The dotted key, `shop.bought`. */
    val key: String

    /** The text as written in the file; a YAML list is joined with line breaks. */
    val raw: String

    /** The file the message comes from, relative to the plugin's data folder, with `/` separators. */
    val file: String

    fun get(): Component

    fun get(vararg arguments: Argument): Component

    fun send(audience: Audience, vararg arguments: Argument) = audience.sendMessage(get(*arguments))

    /** Sends to every online player and the console. */
    fun broadcast(vararg arguments: Argument)
}

package com.github.marcoral.versatia.messages

import com.github.marcoral.versatia.text.Argument
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

/**
 * One message: MiniMessage text with [runtime arguments](Argument). Without arguments the
 * component was parsed when the file was read and is returned as it is; with arguments the text
 * is parsed for the call.
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

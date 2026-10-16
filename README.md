# VersatiaModule-Messages

The API of the Messages module of the [Versatia framework](https://github.com/VersatiaFramework):
every plugin's messages, read from the YAML files under `messages/` in its data folder, reloaded
with the plugin, and sent with runtime arguments:
`messages["shop.bought"].send(player, arg("item", stack), arg("price", 5))`.

The text is MiniMessage with arguments in braces. Angle brackets are formatting, braces are data:
the code passes values with `arg()`, and the text decides what to show of them, `{player}`,
`{player.name}`, `{player.location.x}`. The same parser is available on its own as `Text`, for
text that does not come from a file.

Documentation: [Messages module](https://versatiaframework.github.io/VersatiaDocs/docs/modules/messages).

Like VersatiaAPI and VersatiaCore, the module comes in two halves. This repository is the public
half, the library plugins compile against, nothing that runs, in two packages:

| Package                                 | What it holds                                                    |
|-----------------------------------------|------------------------------------------------------------------|
| `com.github.marcoral.versatia.messages` | the bank: `Messages`, `Message` and their exceptions             |
| `com.github.marcoral.versatia.text`     | the parser: `Text`, `Argument` and `arg()`, `Presentation`, `presentation {}` and `Presentable`, `ArgumentTag` and `Arguments`, `TextException` and its subclasses |

`text` knows nothing of `messages`; messages only take its arguments. The implementation, the
plugin installed on the server as `VersatiaModule-Messages`, lives in the private repository
VersatiaModule-MessagesImpl; its JAR carries these classes, so a plugin never bundles them.

## How text is read

- In the text a brace inserts the value's component. Inside a tag argument, which MiniMessage reads
  as a plain string, it inserts the value's plain text, escaped for the argument's quotes, so a value
  can neither end the argument nor open a tag: `<click:suggest_command:'/msg {player.name} '>`,
  `<insert:{player.name}>`, `<color:{c}>`. An unquoted argument holding a brace is quoted. Hover
  text (`<hover:show_text:'...'>`), which MiniMessage parses again, keeps the component.
- A backslash escapes a brace, as MiniMessage's `\<` escapes a tag: `\{nick}` shows `{nick}`, in
  the text and inside tag arguments, and `\\{nick}` is a backslash followed by the argument.
  `Text.argumentNames` skips escaped braces.
- A presentation registered with `Text.present` applies to strings and numbers as well.
- `Text` may be used and extended from any thread; the built-in presentations read Bukkit state
  (`{player.location}`, `{world.players}`), which is safe on the main thread only.
- Built in, besides players, entities, items, locations and worlds: any value that is Adventure's
  `Translatable` (`Material`, `EntityType`, `GameMode`, `Enchantment`, `PotionEffectType`, ...)
  inserts a translatable component, shown in the viewer's language; a more specific presentation,
  such as `Player` or `ItemStack`, wins. A collection or array inserts its elements, each in its own
  presentation, joined with `, `, and has the property `size`.
- Messages are parsed when used, never when the files are read, so tags registered later apply.
  A text without braces and without registered tags is parsed once and reused until a tag is
  registered; a text using a registered tag is parsed at every use. A failure names the key and
  the file (`MessageRenderException`).
- A YAML list is a message of several lines, each parsed on its own, so formatting does not carry
  over to the next line. `Message.lines()` gives one component per line (item lore); `get()`,
  `send()` and `broadcast()` join them with line breaks.
- Every YAML scalar is text exactly as written: `Yes`, `off`, `1.10` or a key `no:` stay as they
  are. A key defined twice, in one file or in two, a key that is a message in one place and a group
  of messages in another (`shop: "..."` beside `shop: { open: "..." }`), a list holding anything but
  text lines, and a file that is not UTF-8 fail the load (`MessagesLoadException`).
- A message with legacy colour codes (`&a`, `§l`, which MiniMessage shows as text) is logged as one
  warning through the plugin's logger at every load, naming the key, the file and the tags to use
  (`&a -> <green>`). A code counts when no letter or digit precedes it, other codes aside, so
  `&a&lSale` warns and `R&D` does not. `legacy-color-warnings: false` in `messages/.versatia.yml`
  turns it off; files whose names start with a dot hold no messages.

## Using it

```kotlin
dependencies {
    compileOnly("com.github.marcoral.versatia:VersatiaModule-Messages:0.0.1")
}
```

```yaml
depend:
  - VersatiaCore
  - VersatiaModule-Messages
```

## Building

Requires JDK 21. The artifact is not on a Maven repository yet:

```bash
./gradlew publishToMavenLocal
```

publishes `com.github.marcoral.versatia:VersatiaModule-Messages` to your local Maven repository.

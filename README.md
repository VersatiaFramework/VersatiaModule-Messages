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
| `com.github.marcoral.versatia.text`     | the parser: `Text`, `Argument` and `arg()`, `Presentation` and `Presentable`, `ArgumentTag` and their exceptions |

`text` knows nothing of `messages`; messages only take its arguments. The implementation, the
plugin installed on the server as `VersatiaModule-Messages`, lives in the private repository
VersatiaModule-MessagesImpl; its JAR carries these classes, so a plugin never bundles them.

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

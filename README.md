# VersatiaModule-Messages

The API of the Messages module of the [Versatia framework](https://github.com/VersatiaFramework):
every plugin's messages, read from the YAML files under `messages/` in its data folder, reloaded
with the plugin, and sent with [Text](https://github.com/VersatiaFramework/VersatiaModule-Text)
arguments: `messages["shop.bought"].send(player, arg("item", stack), arg("price", 5))`.

Documentation: [Messages module](https://versatiaframework.github.io/VersatiaDocs/docs/modules/messages).

Like VersatiaAPI and VersatiaCore, the module comes in two halves. This repository is the public
half, the library plugins compile against: `Messages`, `Message` and the exceptions, nothing that
runs. The implementation, the plugin installed on the server as `VersatiaModule-Messages`, lives in
the private repository VersatiaModule-MessagesImpl; its JAR carries these classes, so a plugin
never bundles them.

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

The Text module API comes with this artifact; on the server, VersatiaModule-Messages depends on
VersatiaModule-Text.

## Building

Requires JDK 21 and VersatiaModule-Text in your local Maven repository
(`./gradlew publishToMavenLocal` in its checkout). The artifact is not on a Maven repository yet:

```bash
./gradlew publishToMavenLocal
```

publishes `com.github.marcoral.versatia:VersatiaModule-Messages` to your local Maven repository.

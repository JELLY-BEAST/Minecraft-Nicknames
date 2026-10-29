# Minecraft Nicknames (Minecraft Java 26.2)

A small server-side Fabric mod that adds one command:

```text
/nickname <player> <nickname>
```

Example:

```text
/nickname Steve The Legend
```

The command is restricted to the Minecraft gamemaster permission tier (the normal operator level-2 tier). It changes the target player's visible entity name and tab-list display name without changing their account username or UUID.

## Compatibility

- Minecraft Java Edition 26.2
- Java 25
- Fabric Loader and Fabric API versions pinned in `gradle.properties`

Install the resulting JAR on the **server**. The command and tab-list display name are server-controlled. Nicknames are stored in memory for the current server session and are not persisted after restart or player reconnect.

## Build

The GitHub Actions workflow builds the project with Gradle 9.5.1 and Java 25, then verifies the output JAR contains the mod metadata, initializer, and mixin configuration.

For a local build with Java 25 and Gradle 9.5.1 installed:

```bash
gradle build --no-daemon
```

Download the `minecraft-nicknames-26.2` artifact from a successful GitHub Actions run.

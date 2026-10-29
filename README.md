# Minecraft Nicknames (Minecraft Java 26.2)

A small Fabric server-side mod that adds one command:

```text
/nickname <UserToNickName> <Nickname>
```

Example:

```text
/nickname Steve The Legend
```

The command requires permission level 2 (operator). The target must be online.
It sets the player's visible entity name and tab-list display name. It does not
change their Minecraft account username or UUID. Nicknames are currently kept
in memory and are not persisted across server restarts.

## Target versions

- Minecraft Java Edition: **26.2**
- Java: **25**
- Fabric Loom: **1.17.21**
- Gradle: **9.5.1**
- Fabric Loader: **0.19.5**
- Fabric API: **0.161.0+26.2**
- Official Mojang mappings (the 26.1+ unobfuscated namespace)

These are intentionally pinned for the 26.2 target. Do not change `minecraft_version`
to 26.3 unless you are deliberately porting the mod.

## Build locally

Install JDK 25 and Gradle 9.5.1, then run:

```bash
gradle build --no-daemon
```

The mod JAR is written to `build/libs/`.

## GitHub Actions

Upload the project contents to a repository. The workflow in `.github/workflows/build.yml`
runs the build and uploads the JAR as a workflow artifact.

## Note

The source project is pinned to Minecraft 26.2. A successful GitHub Actions build is
still required to confirm compilation against the resolved upstream artifacts.


## Minecraft 26.2 build configuration note

Minecraft 26.2 ships unobfuscated game classes. This project therefore does not declare a Yarn or Mojang mappings dependency, and sets `fabric.loom.disableObfuscation=true`. Fabric Loader and Fabric API are on the regular `implementation` configuration for this unobfuscated target.

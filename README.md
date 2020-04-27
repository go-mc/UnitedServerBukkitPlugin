# UnitedServerBukkitPlugin [![JitPack](https://jitpack.io/v/go-mc/UnitedServerBukkitPlugin.svg)](https://jitpack.io/#go-mc/UnitedServerBukkitPlugin)

A Bukkit plugin for UnitedServer.

## Command

`/unitedserver <server> <player>`

## Permission

- unitedserver.use

## Build

```bash
git clone https://github.com/go-mc/UnitedServerBukkitPlugin.git

gradlew jar
```

## Usage

Gradle:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    compileOnly 'com.github.go-mc:UnitedServerBukkitPlugin:-SNAPSHOT'
}
```

## Author

Shirasawa

## License

[MIT](./LICENSE)

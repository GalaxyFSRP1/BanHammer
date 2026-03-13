# Minecraft BanHammer Plugin

## Features
- Netherite sword "Ban Hammer" deals infinite damage (1000 + wither)
- On kill, bans player with custom reason & optional IP ban
- Command: `/banhammer give <player> [<reason>] [true for IP ban]`
- Permissions: `banhammer.give` (OP default), `banhammer.use`
- Configurable defaults in config.yml

## Build (no Maven install needed)
1. Double-click `build.bat` or run `cmd` then `build.bat`
2. JAR in `target/BanHammer-1.0.jar`

## Deploy
Copy JAR to `plugins/`, restart server.

## Usage
1. `/op <yourname>`
2. `/banhammer give <target> "hacking" true`
3. Hit target with Ban Hammer → death → banned

Compatible with Paper 1.21.1, LuckPerms.

## Source Structure
```
.
├── pom.xml
├── build.bat
├── README.md
├── TODO.md
└── src/main/
    ├── java/com/banhammer/
    │   ├── BanHammer.java (main)
    │   ├── BanHammerCommand.java
    │   └── BanHammerListener.java
    └── resources/
        ├── plugin.yml
        └── config.yml
```

Plugin complete!

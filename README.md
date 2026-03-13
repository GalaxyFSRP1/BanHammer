# Minecraft BanHammer Plugin

## Features
- Netherite sword infinite damage + ban on kill
- Configurable reason/IP-ban
- /banhammer give <player> [reason] [true|false]

## Hot Reload (no restart!)
1. **PlugMan** (install from Spigot): `/plugman load BanHammer`
2. **Lib's Reloaded**: `/libreload`
3. **Paper /reload confirm** (risky)

## Deploy
1. `./mvnw.cmd clean package` → **target/BanHammer-1.0.jar**
2. Copy to `/plugins/`
3. `/plugman load BanHammer`

## GitHub CI
Push → Actions → Download JAR from Artifacts/Release!

## Test
```
/op me
/banhammer give <friend> test true
Hit friend → banned!
```


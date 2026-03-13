# BanHammer Plugin TODO

## Steps:
[x] 1. Create pom.xml
[x] 2. Create plugin.yml
[x] 3. Create config.yml
[x] 4. Create main Java class (BanHammer.java)
[x] 5. Create BanHammerCommand.java
[x] 6. Create BanHammerListener.java
[x] 7. Update TODO, build instructions
[x] 8. Enhanced commands + NamespaceKey fix
[ ]

## Build & Deploy:
1. Install Maven if not: Download from https://maven.apache.org/download.cgi, add to PATH.
2. Run: `mvn clean package`
3. JAR in `target/BanHammer-1.0.jar` - copy to server /plugins/
4. Restart server or /plugman reload BanHammer
5. Test: /op <yourname>, /banhammer give <target> "test reason" true
6. Hit target with sword, confirm death + ban/IP ban.

Plugin ready!


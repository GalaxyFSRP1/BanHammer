@echo off
echo Building JAR without Maven...
javac -cp target/classes -d target/classes src/main/java/com/banhammer/*.java
cd target/classes
jar cf ../BanHammer-1.0.jar *
cd ../..
echo JAR created: target/BanHammer-1.0.jar
pause


# Deploy Guide

## GitHub JAR
1. Push repo
2. Actions → "Build BanHammer JAR" → Artifacts → Download BanHammer-1.0.jar
3. plugins/ → restart

## Release
`git tag v1.0 && git push --tags` → auto release JAR!

## Reload
/plugman load BanHammer

## Test
/op me
/banhammer give <player> hacked true

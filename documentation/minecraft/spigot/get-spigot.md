Documentation of [scripts/minecraft/spigot/get-spigot.kts](/scripts/minecraft/spigot/get-spigot.kts)
=

## Description
A Kotlin Script that downloads and deploys the specified version of [Spigot](https://www.spigotmc.org/) using [BuildTools](https://www.spigotmc.org/wiki/buildtools/).

## Download & Install
```shell script
curl -o get-spigot.kts https://raw.githubusercontent.com/Rotbjorn/scripts/master/scripts/minecraft/spigot/get-spigot.kts?token=ALBAE3G4XQR7JXKW6SHVZAS6674RG
``` 

## Usage
Using [kscript](https://github.com/holgerbrandl/kscript):

`kscript get-spigot.kts <version>`

**or**

`./get-spigot.kts <version>`

\
Alternatively using the standard Kotlin compiler:

`kotlinc -script get-spigot.kts`

## Parameters

##### Required
- <version\> - All available **versions** can be found on the Spigot website [here](https://www.spigotmc.org/wiki/buildtools/#versions).





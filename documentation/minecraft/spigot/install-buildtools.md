Documentation of [scripts/minecraft/spigot/install-buildtools.kts](/scripts/minecraft/spigot/install-buildtools.kts)
=

##Description
A Kotlin Script that downloads and deploys the latest version of [BuildTools](https://www.spigotmc.org/wiki/buildtools/).

##Download
```shell script
curl -o install-buildtools.kts https://raw.githubusercontent.com/Rotbjorn/scripts/master/scripts/minecraft/spigot/install-buildtools.kts?token=ALBAE3G4XQR7JXKW6SHVZAS6674RG
``` 

##Usage
Using [kscript](https://github.com/holgerbrandl/kscript):

`kscript install-buildtools.kts <version>`

**or**

`./install-buildtools.kts <version>`

\
Alternatively using Kotlin Compiler:

`kotlinc -script install-buildtools.kts`

##Parameters
- `<version>`

All available **versions** can be found on the Spigot website [here](https://www.spigotmc.org/wiki/buildtools/#versions).



Documentation of [scripts/minecraft/spigot/get-spigot.kts](/scripts/minecraft/spigot/get-spigot.kts)
=

## Description
A Kotlin Script that downloads and deploys the specified version of [Spigot](https://www.spigotmc.org/) using [BuildTools](https://www.spigotmc.org/wiki/buildtools/).

## Download & Install
```shell script
curl -o get-spigot.kts https://raw.githubusercontent.com/Rotbjorn/scripts/master/scripts/minecraft/spigot/get-spigot.kts
``` 

## Usage
Using [kscript](https://github.com/holgerbrandl/kscript):

`kscript get-spigot.kts -v <version>`

_**or** with shebang:_

`./get-spigot.kts -v <version>`

\
Alternatively using the standard Kotlin compiler:

`kotlinc -script get-spigot.kts -- -v <version>`\
_Note: make sure to insert program arguments after `--`._

## Example


Using [kscript](https://github.com/holgerbrandl/kscript): \
`kscript get-spigot.kts -v 1.16.1 -nodebug`\
`./get-spigot.kts -nodebug -v 1.15.2`

Using the standard Kotlin compiler: \
`kotlinc -script get-spigot.kts -- -v 1.14`

## Parameters

##### Required
* `-v <version>` - All available **versions** can be found on the Spigot website [here](https://www.spigotmc.org/wiki/buildtools/#versions).

##### Optional
* `-nodebug` - Disables building and cleanup output.



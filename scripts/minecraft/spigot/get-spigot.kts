#!/usr/bin/env kscript

import java.io.*
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.system.exitProcess
import kotlin.system.measureTimeMillis



if (args.size < 2) {
    System.err.println("Specify what version; ")
    System.err.println("eg:    ./get-spigot.kts -- -v 1.16.1")
    System.err.println("You can find what versions are acceptable on: https://www.spigotmc.org/wiki/buildtools/#versions")
    System.err.println("")
    exitProcess(0)
}

var debug = true
var version = ""

//region Parameter
data class Command(var command: String, var argument: String) {
    class CommandBuilder(private val noParam: Boolean = false) {

        var commandName: String? = null
            private set
        var commandArgument: String? = null
            private set
        var isActive = false

        fun setCommand(_commandName: String): CommandBuilder {
            isActive = true
            commandName = _commandName
            if (noParam) {
                isActive = false
                commandArgument = "true"
            }

            return this
        }

        fun setArgument(_arg: String): CommandBuilder {
            isActive = true
            commandArgument = _arg
            return this
        }

        fun build(): Command {
            isActive = false
            return Command(commandName!!, commandArgument!!)
        }

    }
}

var commands = mutableMapOf<String, String>()
var cb = Command.CommandBuilder()
for (arg in args) {
    if (arg.startsWith('-')) {
        if (arg.length < 2) {
            System.err.println("Invalid command!")
            exitProcess(0)
        }

        if (cb.isActive) {
            System.err.println("No parameter specified after: ${cb.commandName}")
            exitProcess(0)
        }

        if (arg == "-nodebug") {
            cb = Command.CommandBuilder(true)
            cb.setCommand(arg.substring(1))
            if (!cb.isActive) {
                val command = cb.build()
                commands[command.command] = command.argument
            }
        } else
            cb = Command.CommandBuilder()
        cb.setCommand(arg.substring(1))

    } else if (cb.isActive) {
        cb.setArgument(arg)
        val command = cb.build()
        commands[command.command] = command.argument
    } else {
        System.err.println("Illegal parameter usage!")
        exitProcess(0)
    }
}

if(cb.isActive) {
    System.err.println("No parameter specified after: ${cb.commandName}")
    exitProcess(0)
}

if(!commands.contains("v")) {
    System.err.println("No version specified!")
    exitProcess(0)
}

version = commands["v"].toString()
if (commands["nodebug"].toString() == "true") {
    debug = false
}
//endregion Parameter

val startTime = System.currentTimeMillis()


val currentDirectory: Path = Paths.get("").toAbsolutePath()
val buildToolsFile = File("$currentDirectory/BuildTools/BuildTools.jar")


// Creates ./BuildTools/ folder
if (!buildToolsFile.parentFile.exists())
    buildToolsFile.parentFile.mkdirs()


// Gets the latest BuildTools.jar build and deploys it at ./BuildTools/BuildTools.jar
Files.copy(URL("https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar").openStream(), buildToolsFile.toPath())


// Automatically starts the BuildTools.jar
val process: Process = ProcessBuilder(listOf<String>("java", "-jar", buildToolsFile.absolutePath, "--rev", version))
        .directory(buildToolsFile.parentFile.absoluteFile)
        .redirectErrorStream(true)
        .start()



val reader = BufferedReader(InputStreamReader(process.inputStream))

var line: String? = ""
do {
    line = reader.readLine()
    if(line == null)
        break
    printDebugLn(line!!)

}while(true)

println("""
    
    
    
    
    BuildTools.jar DONE
    
    
    
    
""".trimIndent())

process.waitFor() // Wait for BuildTools.jar to finish

// Moves the spigot.jar file to the script's directory
for (file in buildToolsFile.parentFile.listFiles()!!) {
    if (file.name.startsWith("spigot")) {
        Files.move(file.toPath(), Paths.get("$currentDirectory/${file.name}"))
        break
    }
}


// Deletes the ./BuildTools/ directory
buildToolsFile.parentFile.deleteRecursively()

val timeTaken = (System.currentTimeMillis().toDouble() - startTime.toDouble()) / 1000

println("get-script.kts execution time: ${timeTaken}s")

fun File.deleteRecursively() {
    val files = listFiles()
    if (!files.isNullOrEmpty()) {
        for (file in files) {
            file.deleteRecursively()
            printDebugLn("Deleted ${file.name} in ${file.absolutePath}")
        }
    }
    delete()
}

fun printDebugLn(str: Any) {
    if(debug) {
        println(str)
    }
}
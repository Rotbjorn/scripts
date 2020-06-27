#!/usr/bin/env kscript

import java.io.*
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.system.exitProcess

if(args.size < 1) {
    System.err.println("Specify what version; ")
    System.err.println("eg:    ./install-buildtools.kts 1.16.1")
    System.err.println("You can find what versions are acceptable on: https://www.spigotmc.org/wiki/buildtools/#versions")
    exitProcess(0)
}

val inputStream: InputStream = URL("https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar").openStream()

println(Paths.get("").toAbsolutePath().toString())

val currentDirectory = Paths.get("").toAbsolutePath().toString()

val buildTools = File("$currentDirectory/BuildTools/BuildTools.jar")

if(!buildTools.exists()) {
    buildTools.parentFile.mkdirs()
}
Files.copy(inputStream, buildTools.toPath())

execJar(buildTools, args[0])

fun execJar(jarFile: File, vararg arguments: String) {

    val commandArguments = mutableListOf<String>(
            "java",
            "-jar",
            jarFile.absolutePath
    )
    commandArguments.addAll(args)

    val builder = ProcessBuilder(commandArguments)
    builder.directory(jarFile.parentFile.absoluteFile)
    builder.redirectErrorStream(true)
    val process = builder.start()

    val sc = Scanner(process.inputStream)
    val sb = StringBuilder()

    while(sc.hasNextLine())
        sb.append(sc.nextLine()).append("\n")

    sc.close()

    val result = process.waitFor()
    println("Process exited with result: $result, and output: $sb")




}

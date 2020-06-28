#!/usr/bin/env kscript

import java.io.*
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.system.exitProcess

if (args.isEmpty()) {
    System.err.println("Specify what version; ")
    System.err.println("eg:    ./get-spigot.kts 1.16.1")
    System.err.println("You can find what versions are acceptable on: https://www.spigotmc.org/wiki/buildtools/#versions")
    exitProcess(0)
}

val inputStream: InputStream = URL("https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar").openStream()

val currentDirectory: Path = Paths.get("").toAbsolutePath()

val buildToolsDirectory = File("$currentDirectory/BuildTools/")

val buildToolsFile = File("$currentDirectory/BuildTools/BuildTools.jar")

if (!buildToolsFile.exists()) {
    buildToolsFile.parentFile.mkdirs()
}

Files.copy(inputStream, buildToolsFile.toPath())

execJar(buildToolsFile, args[0])

for (file in buildToolsDirectory.listFiles()!!) {
    if (file.name.startsWith("spigot")) {
        Files.move(file.toPath(), Paths.get("$currentDirectory/${file.name}"))
        println("Moved ${file.name} to the script directory")
        break
    }
}

buildToolsDirectory.deleteRecursively()


/*////////////////////////////////////////////////////////////////////////////

                                   Methods

*/////////////////////////////////////////////////////////////////////////////


fun File.deleteRecursively() {
    val files = this.listFiles()
    if(!files.isNullOrEmpty()) {
        for (file in files) {
            file.deleteRecursively()
            println("Deleted ${file.name} in ${file.absolutePath}")
        }
    }
    this.delete()
}




fun execJar(jarFile: File, vararg arguments: String) {
    val commandArguments = mutableListOf<String>(
            "java",
            "-jar",
            jarFile.absolutePath,
            "--rev"
    )
    commandArguments.addAll(arguments)

    val builder = ProcessBuilder(commandArguments)
    builder.directory(jarFile.parentFile.absoluteFile)
    builder.redirectErrorStream(true)
    val process = builder.start()

    val sc = Scanner(process.inputStream)

    while (sc.hasNextLine())
        println(sc.nextLine())

    sc.close()

    process.waitFor()
}

package configuration

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class Settings {
    companion object {
        val dictionary = hashMapOf<String, Any>()

        init {
            val homePath = System.getenv("HOME")

            // TODO: add check if home path exists - if not, use current path
            val lines = Files.readAllLines(Paths.get(homePath, ".kattis"), StandardCharsets.UTF_8)

            lines.map { it.trim() }
                    // filter empty lines
                    .filterNot { it.isEmpty() }
                    // filter comments
                    .filterNot { it.startsWith("#") }
                    // create list of key-value pairs
                    .map { it.split(":").let {
                        dictionary.put(it[0].trim(), it[1].trim())
                    } }
        }

        val user = dictionary["username"]
        val token = dictionary["token"]
    }
}
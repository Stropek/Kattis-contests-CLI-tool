import interfaces.IFileReader

import java.io.FileNotFoundException

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class ResourceFileReader : IFileReader {
    override fun read(path: String): MutableList<String> {
        val resource = ResourceFileReader::class.java.classLoader.getResource(path)

        if (resource != null) {
            val fileContent = resource.readText(StandardCharsets.UTF_8)
            return fileContent.split("\r\n").toMutableList()
        }

        throw FileNotFoundException(path)
    }
}
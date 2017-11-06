import interfaces.IFileReader

import java.io.FileNotFoundException

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class FileReader : IFileReader {
    override fun read(path: String): MutableList<String> {
        val filePath = Paths.get(path)
        if (Files.exists(filePath))
            return Files.readAllLines(filePath, StandardCharsets.UTF_8)

        throw FileNotFoundException(path)
    }
}
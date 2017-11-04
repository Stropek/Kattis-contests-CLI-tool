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

    override fun readKattisConfiguration(): MutableList<String> {
        // TODO: add check if home path exists
        //   - if not, use current path to look for .kattis file
        //   - if no .kattis file is found -throw an exception
        val homePath = System.getenv("HOME")

        return Files.readAllLines(Paths.get(homePath, ".kattis"), StandardCharsets.UTF_8)
    }
}
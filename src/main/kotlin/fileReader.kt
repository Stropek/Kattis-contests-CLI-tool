package configuration

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class FileReader : IFileReader {
    override fun readKattisConfiguration(): MutableList<String> {
        // TODO: add check if home path exists
        //   - if not, use current path to look for .kattis file
        //   - if no .kattis file is found -throw an exception
        val homePath = System.getenv("HOME")

        return Files.readAllLines(Paths.get(homePath, ".kattis"), StandardCharsets.UTF_8)
    }
}
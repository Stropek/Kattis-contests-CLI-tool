package interfaces

interface IFileReader {
    fun read(path: String): MutableList<String>
}
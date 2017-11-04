package configuration

interface IFileReader {
    fun readKattisConfiguration() : MutableList<String>
}
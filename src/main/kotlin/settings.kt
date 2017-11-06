package settings

import com.xenomachina.argparser.ArgParser

import interfaces.IFileReader

class KattisCliArgs(parser: ArgParser) {
    // TODO: add argument for verbose logging: https://github.com/MicroUtils/kotlin-logging
    val test by parser.flagging("-t", "--test", help = "test flag")
}

class Settings {
    var user: String
    var token: String

    constructor(user: String, token: String) {
        this.user = user
        this.token = token
    }

    constructor(fileReader: IFileReader) {
        val dictionary = hashMapOf<String, Any>()

        val lines = fileReader.readKattisConfiguration()

        lines.map { it.trim() }
                // filter empty lines
                .filterNot { it.isEmpty() }
                // filter comments
                .filterNot { it.startsWith("#") }
                // create list of key-value pairs
                .map {
                    it.split(":").let {
                        dictionary.put(it[0].trim(), it[1].trim())
                    }
                }

        user = dictionary["username"].toString()
        token = dictionary["token"].toString()
    }
}


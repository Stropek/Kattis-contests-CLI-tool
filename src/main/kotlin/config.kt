import com.ufoscout.properlty.Properlty

object Config {
    private val properlty = Properlty.builder()
            .add("classpath:config.properties")
            .build()

    var KattisBaseUrl: String? = ""
        get() {
            return if (Port.isBlank()) {
                properlty["KattisBaseUrl"]
            } else {
                "${properlty["KattisBaseUrl"]}:$Port"
            }
        }

    var Port = ""
}
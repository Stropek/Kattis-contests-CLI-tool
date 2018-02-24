import com.ufoscout.properlty.Properlty

object Config {
    private val properlty = Properlty.builder()
            .add("classpath:config.properties")
            .build()

    val KattisBaseUrl = properlty["KattisBaseUrl"]
}
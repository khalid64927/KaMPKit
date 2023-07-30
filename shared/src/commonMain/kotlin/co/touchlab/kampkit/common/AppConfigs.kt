package co.touchlab.kampkit.common


/**
 * Note: Not keeping it object because I'd like the capability
 * to change the environment at runtime
 *
 * Override these variable from build script
 * */
class AppConfigs {
    companion object {
        val baseUrl = "https://dog.ceo/"
        val environment: Environment = Environment.DEV
    }
}

enum class Environment {
    PRODUCTION, UAT, SIT, DEV
}
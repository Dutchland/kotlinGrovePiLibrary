package nl.dutchland.grove.temperaturerepository

data class DatabaseCredentials(
        private val url: String,
        private val driver: String,
        val user : String,
        val password: String) {
}
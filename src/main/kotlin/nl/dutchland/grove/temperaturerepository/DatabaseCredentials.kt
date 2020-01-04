package nl.dutchland.grove.temperaturerepository

data class DatabaseCredentials(
        val url: String,
        val driver: String,
        val user : String,
        val password: String) {
}
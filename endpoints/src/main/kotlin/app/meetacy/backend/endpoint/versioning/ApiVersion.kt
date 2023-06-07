package app.meetacy.backend.endpoint.versioning

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


@JvmInline
value class ApiVersion(val int: Int) {
    companion object {
        const val Header = "Api-Version"

        // First versioning was implemented
        val VersioningFeature = ApiVersion(0)
    }
}

@Serializable
data class Address(val city: String, val street: String)
@Serializable
data class Person(val address: Address)

private val json1 = Json { ignoreUnknownKeys = true }

fun main() {
    val json = """{"name": "John", "age": 30, "address": {"city": "New York", "street": "Broadway"}}"""
    val person = json1.decodeFromString<Person>(json)
    println(person.address.city)
}

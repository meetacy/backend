package app.meetacy.backend.types.serializable.address

import app.meetacy.backend.types.address.Address
import app.meetacy.backend.types.serializable.address.Address as AddressSerializable

fun AddressSerializable.type() = Address(country, city, street, placeName)
fun Address.serializable() = AddressSerializable(country, city, street, placeName)

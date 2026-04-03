package de.cyanbaz.nucleus.entry.domain

@JvmInline
value class Title(
    val value: String,
) {
    init {
        require(value.isNotBlank()) { "Title must not be blank" }
        require(value.length <= 200) { "Title must not be longer than 200 characters" }
    }

    override fun toString(): String = value
}

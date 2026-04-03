package de.cyanbaz.nucleus.entry.domain

@JvmInline
value class Content(
    val value: String,
) {
    init {
        require(value.isNotBlank()) { "Content must not be blank" }
        require(value.length <= 20_000) { "Content must not be longer than 20000 characters" }
    }

    override fun toString(): String = value
}

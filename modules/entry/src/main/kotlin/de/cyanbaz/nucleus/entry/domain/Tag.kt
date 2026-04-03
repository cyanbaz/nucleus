package de.cyanbaz.nucleus.entry.domain

@JvmInline
value class Tag(
    val value: String,
) {
    init {
        require(value.isNotBlank()) { "Tag must not be blank" }
        require(value.length <= 50) { "Tag must not be longer than 50 characters" }
    }

    fun normalized(): Tag = Tag(value.trim().lowercase())

    override fun toString(): String = value
}

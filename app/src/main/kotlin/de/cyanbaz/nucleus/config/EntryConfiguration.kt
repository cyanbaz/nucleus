package de.cyanbaz.nucleus.config

import EntryService
import de.cyanbaz.nucleus.adapter.out.memory.entry.InMemoryEntryRepository
import de.cyanbaz.nucleus.application.entry.port.`in`.CreateEntryUseCase
import de.cyanbaz.nucleus.domain.entry.EntryRepository
import java.time.Clock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EntryConfiguration {
    @Bean
    fun entryRepository(): EntryRepository = InMemoryEntryRepository()

    @Bean
    fun createEntryUseCase(
        entryRepository: EntryRepository,
        clock: Clock,
    ): CreateEntryUseCase = EntryService(entryRepository, clock)

    @Bean
    fun clock(): Clock = Clock.systemUTC()
}

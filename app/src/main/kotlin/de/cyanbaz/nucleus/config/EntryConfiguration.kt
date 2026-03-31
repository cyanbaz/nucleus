package de.cyanbaz.nucleus.config

import EntryService
import de.cyanbaz.nucleus.adapter.out.memory.entry.InMemoryEntryRepository
import de.cyanbaz.nucleus.application.entry.port.`in`.CreateEntryUseCase
import de.cyanbaz.nucleus.application.entry.port.`in`.GetEntryUseCase
import de.cyanbaz.nucleus.application.entry.port.`in`.ListEntriesUseCase
import de.cyanbaz.nucleus.domain.entry.EntryRepository
import java.time.Clock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EntryConfiguration {
    @Bean
    fun entryRepository(): EntryRepository = InMemoryEntryRepository()

    @Bean
    fun entryService(
        entryRepository: EntryRepository,
        clock: Clock,
    ): EntryService = EntryService(entryRepository, clock)

    @Bean
    fun createEntryUseCase(entryService: EntryService): CreateEntryUseCase = entryService

    @Bean
    fun getEntryUseCase(entryService: EntryService): GetEntryUseCase = entryService

    @Bean
    fun listEntriesUseCase(entryService: EntryService): ListEntriesUseCase = entryService

    @Bean
    fun clock(): Clock = Clock.systemUTC()
}

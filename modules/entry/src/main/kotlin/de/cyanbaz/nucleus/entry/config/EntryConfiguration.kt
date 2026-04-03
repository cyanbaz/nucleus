package de.cyanbaz.nucleus.entry.config

import de.cyanbaz.nucleus.entry.adapter.outbound.memory.InMemoryEntryRepository
import de.cyanbaz.nucleus.entry.application.CreateEntryService
import de.cyanbaz.nucleus.entry.application.GetEntryService
import de.cyanbaz.nucleus.entry.application.ListEntriesService
import de.cyanbaz.nucleus.entry.application.port.outbound.EntryRepository
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
    ): CreateEntryService = CreateEntryService(entryRepository, clock)

    @Bean
    fun getEntryUseCase(
        entryRepository: EntryRepository,
        clock: Clock,
    ): GetEntryService = GetEntryService(entryRepository, clock)

    @Bean
    fun listEntryUseCase(
        entryRepository: EntryRepository,
        clock: Clock,
    ): ListEntriesService = ListEntriesService(entryRepository, clock)

    @Bean
    fun clock(): Clock = Clock.systemUTC()
}

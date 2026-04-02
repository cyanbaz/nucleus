package de.cyanbaz.nucleus.config

import de.cyanbaz.nucleus.adapter.outbound.memory.entry.InMemoryEntryRepository
import de.cyanbaz.nucleus.application.entry.port.inbound.CreateEntryUseCase
import de.cyanbaz.nucleus.application.entry.port.inbound.GetEntryUseCase
import de.cyanbaz.nucleus.application.entry.port.inbound.ListEntriesUseCase
import de.cyanbaz.nucleus.application.entry.port.outbound.EntryRepository
import de.cyanbaz.nucleus.application.entry.service.EntryService
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

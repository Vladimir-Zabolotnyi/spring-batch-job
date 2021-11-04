package zabolotnyi.springbatchjob

import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class CustomWriter(
    private val playerRepository: PlayerRepository
) : ItemWriter<Player> {

    override fun write(items: MutableList<out Player>) {
        playerRepository.saveAll(items);
    }
}
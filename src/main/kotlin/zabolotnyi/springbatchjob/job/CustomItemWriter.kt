package zabolotnyi.springbatchjob.job

import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component
import zabolotnyi.springbatchjob.player.Player
import zabolotnyi.springbatchjob.player.PlayerRepository

class CustomItemWriter(
    private val playerRepository: PlayerRepository
) : ItemWriter<Player> {

    override fun write(items: MutableList<out Player>) {
        playerRepository.saveAll(items);
    }
}
package zabolotnyi.springbatchjob.player

import org.springframework.batch.item.ItemWriter

class DatasourceWriter(
    private val playerRepository: PlayerRepository
) : ItemWriter<Player> {

    override fun write(items: MutableList<out Player>) {
        playerRepository.saveAll(items);
    }
}
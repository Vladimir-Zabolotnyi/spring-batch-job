package zabolotnyi.springbatchjob.player

import org.springframework.batch.item.ItemProcessor

class DatasourceProcessor : ItemProcessor<List<String>, Player> {

    override fun process(item: List<String>): Player {
        return Player(playerId = item[0], age = item[1].toInt(), email = item[2])
    }
}
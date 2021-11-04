package zabolotnyi.springbatchjob.player

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/players")
class PlayerResource(
    private val playerService: PlayerService,
) {
    val logger: Logger = LoggerFactory.getLogger(PlayerResource::class.java)

    @GetMapping("/all/get")
    @ResponseStatus(HttpStatus.OK)
    fun getAllPlayers() = playerService.getAllPlayers()

    @GetMapping("/last/get")
    @ResponseStatus(HttpStatus.OK)
    fun getLastAddedPlayers() = playerService.getLastAddedPlayer()

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/mapping/execute")
    fun executeImportPlayerJob() = playerService.executeImportPlayerJob()
}
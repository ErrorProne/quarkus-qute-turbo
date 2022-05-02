package qqte

import io.quarkus.qute.Location
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import io.smallrye.mutiny.Multi
import org.jboss.resteasy.reactive.RestStreamElementType
import java.time.Duration
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/sse")
class SseResource {
    @Inject
    lateinit var sse : Template

    @Location("cards/infoCard.ts-html")
    lateinit var infoCardStream : Template

    @Inject
    lateinit var systemService: SystemService

    @GET
    fun index() : TemplateInstance {
        val nameDto = InfoCardDto()
        nameDto.name = "OS"
        nameDto.icon = "nc-tag-content"
        nameDto.value = systemService.getOs()!!

        return sse
            .data(
                "nameDto", nameDto
            )
    }

    @GET
    @Path("cpu")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestStreamElementType("text/vnd.turbo-stream.html")
    fun cpuStream(): Multi<String> {
        return Multi.createFrom().ticks()
            .every(Duration.ofSeconds(1))
            .onItem()
            .transform { sysValueTemplate("cpu", "CPU", "%s%%".format(systemService.getCpuLoad())).render() }
    }

    @GET
    @Path("mem")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestStreamElementType("text/vnd.turbo-stream.html")
    fun memStream(): Multi<String> {
        return Multi.createFrom().ticks()
            .every(Duration.ofSeconds(1))
            .onItem()
            .transform { sysValueTemplate("mem", "FREE MEM", "%sMB".format(systemService.getFreeMem())).render() }
    }

    private fun sysValueTemplate(id: String, name: String, value: String) : TemplateInstance {
        val dto = InfoCardDto()
        dto.contentId = id
        dto.name = name
        dto.value = value

        return infoCardStream.data("dto", dto)
    }
}

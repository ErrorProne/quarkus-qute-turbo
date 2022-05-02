package qqte

import io.quarkus.qute.Location
import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("/turbo")
class TurboResource {
    @Inject
    lateinit var turbo : Template

    @Location("cards/infoCard.html")
    lateinit var infoCard : Template

    @Inject
    lateinit var systemService: SystemService

    @GET
    fun index() : TemplateInstance {
        val nameDto = InfoCardDto()
        nameDto.name = "OS"
        nameDto.icon = "nc-tag-content"
        nameDto.value = systemService.getOs()!!

        return turbo
            .data(
                "nameDto", nameDto
            )
    }

    @GET
    @Path("cpu")
    fun cpu() : TemplateInstance {
        Thread.sleep(1000)
        return sysValueTemplate("cpu", "CPU", "%s%%".format(systemService.getCpuLoad()), "/turbo/cpu")
    }

    @GET
    @Path("mem")
    fun mem() : TemplateInstance {
        return sysValueTemplate("mem", "FREE MEM", "%sMB".format(systemService.getFreeMem()), "/turbo/mem")
    }

    private fun sysValueTemplate(id: String, name: String, value: String, src: String) : TemplateInstance {
        val dto = InfoCardDto()
        dto.contentId = id
        dto.name = name
        dto.refreshUrl = src
        dto.value = value

        return infoCard.data("dto", dto)
    }
}

package qqte;

import io.quarkus.qute.Template
import io.quarkus.qute.TemplateInstance
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path


@Path("/")
class AppResource {

	@Inject
	lateinit var about : Template

	@GET
	fun about() : TemplateInstance {
		return about.instance()
	}

}

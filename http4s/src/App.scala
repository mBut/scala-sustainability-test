package sustainability
package http4s

import cats.effect._
import com.comcast.ip4s._
import org.http4s.ember.server._
import org.http4s.server.Router
import org.http4s._
import org.http4s.dsl.io._
import fs2.io.file.Path
import sustainability.util.ProgramArguments

object App extends IOApp {
  def service(workloadFilename: String) = HttpRoutes.of[IO] {
    case GET -> Root / "health" =>
      Ok("OK")
    case request @ GET -> Root / "sustainability-test" =>
      StaticFile.fromString(workloadFilename, Some(request)).getOrElseF(NotFound())
  }.orNotFound

  def run(args: List[String]): IO[ExitCode] = {
    val extractedArgs = ProgramArguments.extractArguments(args)

    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"127.0.0.1")
      .withPort(port"8080")
      .withHttpApp(service(extractedArgs.workloadFilename))
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}

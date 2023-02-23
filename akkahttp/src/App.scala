package sustainability
package akkahttp

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import java.io.File
import scala.io.{StdIn, Source}
import scala.util.{Success, Failure}
import util.ProgramArguments

object App {

  def main(args: Array[String]): Unit = {
    val extractedArgs = ProgramArguments.extractArguments(args)

    implicit val system = ActorSystem(Behaviors.empty, "test-system")
    implicit val executionContext = system.executionContext

    val workLoadFile = new File(extractedArgs.workloadFilename)

    val route = concat(
      path("health") {
          get {
              complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "OK"))
          }
      },
      path("sustainability-test") {
        get {
          getFromFile(workLoadFile, ContentTypes.`application/json`)
        }
      }
    )

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    bindingFuture.onComplete {
      case Success(bound) =>
        println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
      case Failure(e) =>
        println(s"Error starting the server: $e")
    }
  }
}

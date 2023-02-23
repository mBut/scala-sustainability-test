package sustainability
package ziohttp

import java.io.File
import util.ProgramArguments
import zio._
import zio.http._
import zio.http.model.Method

object App extends ZIOAppDefault {

  def app(workloadFilename: String) = Http.collectRoute[Request] {
    case Method.GET -> !! / "health" => Handler.ok.toHttp
    case Method.GET -> !! / "sustainability-test" => Http.fromFile(new File(workloadFilename))
  }

  override val run = for {
    extractedArgs <- getArgs.map(args => ProgramArguments.extractArguments(args.toArray))
    _ <- Console.printLine("Server online at http://localhost:8080")
    server <- Server.serve(app(extractedArgs.workloadFilename).withDefaultErrorResponse).provide(Server.default)
  } yield server
}

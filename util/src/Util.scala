package sustainability
package util

import scala.util.{Try, Failure}
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets

object Util {

  case class ExtractedArguments(workloadFilename: String)

  def extractArguments(args: Seq[String]): ExtractedArguments = {
    val result = for {
      workloadFilename <- Try(args(0)).recoverWith {
        case _: Throwable => Failure(new IllegalArgumentException("A path to the workload file is not specified"))
      }
    } yield ExtractedArguments(workloadFilename)

    result.get
  }

  // This method is used to flush JSON from the corresponding tests
  // and ensure that lazy evalution (if any) doesn't affect the test results.
  def writeToNull(value: String): Unit =
    Files.write(Paths.get("/dev/null"), value.getBytes(StandardCharsets.UTF_8))

}

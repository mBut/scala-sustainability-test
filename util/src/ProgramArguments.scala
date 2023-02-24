package sustainability
package util

import scala.util.{Try, Failure}

object ProgramArguments {

  case class ExtractedArguments(workloadFilename: String)

  def extractArguments(args: Seq[String]): ExtractedArguments = {
    val result = for {
      workloadFilename <- Try(args(0)).recoverWith {
        case _: Throwable => Failure(new IllegalArgumentException("A path to the workload file is not specified"))
      }
    } yield ExtractedArguments(workloadFilename)

    result.get
  }

}

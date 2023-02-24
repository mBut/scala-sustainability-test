package sustainability
package circe

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import scala.io.Source
import sustainability.util.{Util, ADT}

object App {
  import ADT._

  def main(args: Array[String]): Unit = {
    val extractedArgs = Util.extractArguments(args.toIndexedSeq)
    val json = Source.fromFile(extractedArgs.workloadFilename).getLines().mkString

    decode[PetStore](json) match {
      case Right(decoded) => Util.writeToNull(decoded.asJson.toString)
      case Left(e) => throw new Exception(s"Unable to parse JSON: ${e}")
    }
  }
}

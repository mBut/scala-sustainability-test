import mill._, scalalib._

val scala2: String = "2.13.10"
val scala3: String = "3.2.2"

object util extends Cross[UtilModule](scala2, scala3)
class UtilModule(val crossScalaVersion: String) extends CrossScalaModule {}

object akkahttp extends Cross[AkkaHTTPModule](scala2, scala3)
class AkkaHTTPModule(val crossScalaVersion: String) extends CrossScalaModule {
  def moduleDeps = Seq(util(crossScalaVersion))

  val AkkaVersion = "2.7.0"
  val AkkaHttpVersion = "10.5.0"

  def ivyDeps = Agg(
    ivy"com.typesafe.akka::akka-actor-typed:$AkkaVersion",
    ivy"com.typesafe.akka::akka-stream:$AkkaVersion",
    ivy"com.typesafe.akka::akka-http:$AkkaHttpVersion"
  )
}

object ziohttp extends Cross[ZioHttpModule](scala2, scala3)
class ZioHttpModule(val crossScalaVersion: String) extends CrossScalaModule {
  def moduleDeps = Seq(util(crossScalaVersion))

  val ZioHttpVersion = "0.0.4"

  def ivyDeps = Agg(
    ivy"dev.zio::zio-http:$ZioHttpVersion",
  )
}

object http4s extends Cross[Http4sModule](scala2, scala3)
class Http4sModule(val crossScalaVersion: String) extends CrossScalaModule {
  def moduleDeps = Seq(util(crossScalaVersion))

  val Http4sVersion = "0.23.6"

  def ivyDeps = Agg(
    ivy"org.http4s::http4s-ember-server:$Http4sVersion",
    ivy"org.http4s::http4s-dsl:$Http4sVersion",
  )
}

// *****************************************************************************
// Projects
// *****************************************************************************

lazy val squba =
  project
    .in(file("."))
    .enablePlugins(AutomateHeaderPlugin, GitVersioning)
    .settings(settings)
    .settings(
      libraryDependencies ++= Seq(
        library.unicomplex,
        library.actormonitor,
        library.httpclient,
        library.admin,
        library.akkaHttpJson,
        library.scalaCheck % Test,
        library.scalaTest  % Test
      )
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val library =
  new {
    object Version {
      val scalaCheck = "1.13.5"
      val scalaTest  = "3.0.4"
      val squbs = "0.10.0-SNAPSHOT"
    }
    val unicomplex = "org.squbs"         %% "squbs-unicomplex"     % Version.squbs
    val actormonitor = "org.squbs"         %% "squbs-actormonitor"   % Version.squbs
    val httpclient = "org.squbs"         %% "squbs-httpclient"     % Version.squbs
    val admin = "org.squbs"         %% "squbs-admin"          % Version.squbs
    val akkaHttpJson = "de.heikoseeberger" %% "akka-http-circe" % "1.18.0"

    val scalaCheck = "org.scalacheck" %% "scalacheck" % Version.scalaCheck
    val scalaTest  = "org.scalatest"  %% "scalatest"  % Version.scalaTest
  }

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val settings =
  commonSettings ++
  gitSettings ++
  scalafmtSettings

lazy val commonSettings =
  Seq(
    // scalaVersion from .travis.yml via sbt-travisci
    // scalaVersion := "2.12.4",
    organization := "com.example",
    organizationName := "ksilin",
    startYear := Some(2017),
    licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding", "UTF-8"
    ),
    unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value)
)

mainClass in (Compile, run) := Some("org.squbs.unicomplex.Bootstrap")

lazy val gitSettings =
  Seq(
    git.useGitDescribe := true
  )

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true,
    scalafmtOnCompile.in(Sbt) := false,
    scalafmtVersion := "1.3.0"
  )

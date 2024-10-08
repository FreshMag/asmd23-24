ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.0"

// ================================================================================================
//  PROJECTS
// ================================================================================================

lazy val root = (project in file("."))
  .settings(
    name := "asmd23-24"
  ).aggregate(
    mvcEngineerLab4,
    verifierLab6,
    chemistLab7
  )

lazy val mvcEngineerLab4 = project
  .settings(
    name := "mvcEngineerLab4",
    libraryDependencies ++= commonDependencies
  )

lazy val verifierLab6 = project
  .settings(
    name := "verifierLab6",
    libraryDependencies ++= commonDependencies
  )


lazy val chemistLab7 = project
  .settings(
    name := "chemistLab7",
    libraryDependencies ++= commonDependencies ++
      Seq(dependencies.jFreeChart)
  )
  .dependsOn(
    mvcEngineerLab4,
    verifierLab6
  )

// ================================================================================================
//  DEPENDENCIES
// ================================================================================================

lazy val dependencies =
  new {
    val junitInterfaceV = "0.13.3"
    val scalatestV = "3.2.18"
    val junitV = "4.13.2"
    val scalacheckV = "1.18.1"
    val jFreeChartV = "1.5.5"
    //val jChart3dV = "2.1.0"
    //val jSvgV = "3.4.3"

    val junitInterface = "com.github.sbt" % "junit-interface" % junitInterfaceV
    val scalatest = "org.scalatest" %% "scalatest" % scalatestV
    val junit = "junit" % "junit" % junitV
    val scalacheck = "org.scalacheck" %% "scalacheck" % scalacheckV
    val jFreeChart = "org.jfree" % "jfreechart" % jFreeChartV
    //val jChart3d = "org.jfree" % "org.jfree.chart3d" % jChart3dV
    //val jSvg = "org.jfree" % "jfreesvg" % jSvgV
  }

lazy val commonDependencies = Seq(
  dependencies.junitInterface % Test,
  dependencies.scalacheck % Test,
  dependencies.junit % Test,
  dependencies.scalatest % Test
)

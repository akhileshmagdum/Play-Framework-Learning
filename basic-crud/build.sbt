name := """basic-crud"""
organization := "learn.akhilesh"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.10"

libraryDependencies ++= Seq(
  "org.projectlombok" % "lombok" % "1.18.26",
  guice
)

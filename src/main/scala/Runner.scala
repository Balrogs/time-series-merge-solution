import java.io._

import model.TimeSeriesRecord

import scala.collection.{AbstractIterator, Iterator}
import scala.concurrent.Future
import scala.io.Source
import scala.concurrent.ExecutionContext.Implicits.global

object Runner {

  val filesLimit = 100

  def main(args: Array[String]) : Unit = {

    if(args.length != 2) {
        println("USAGE: time-series-merge-solution <input_directory> <output_file_path>")
        System.exit(0)
    }

    val inputDir = new File(args(0))
    val outputFile = new File(args(1))

    val fileList = if (inputDir.exists && inputDir.isDirectory) {
      inputDir.listFiles.filter(f => f.isFile && f.getPath.endsWith(".txt")).toList
    } else {
      Nil
    }

    if(fileList.isEmpty){
      println("Input directory is empty")
      System.exit(0)
    }

    val sources = fileList.take(filesLimit).map(Source.fromFile(_).getLines.map(TimeSeriesRecord.apply))

    val pw = new PrintWriter(outputFile)

    sources.reduce(reducer).foreach(r => pw.write(r.toString + "\n"))

    pw.close()
  }


  def reducer = (iter1: Iterator[TimeSeriesRecord], iter2: Iterator[TimeSeriesRecord]) => {
    new AbstractIterator[TimeSeriesRecord] {

      var buff1: Option[TimeSeriesRecord] = None
      var buff2: Option[TimeSeriesRecord] = None

      def hasNext: Boolean = iter1.hasNext || iter2.hasNext || buff1.isDefined || buff1.isDefined
      def next(): TimeSeriesRecord = {

        if(buff1.isEmpty && iter1.hasNext){
          buff1 = Some(iter1.next())
        }

        if(buff2.isEmpty && iter2.hasNext){
          buff2 = Some(iter2.next())
        }

        if(buff1.isDefined && buff2.isDefined){
          val r1 = buff1.get
          val r2 = buff2.get

          r1.date.compareTo(r2.date) match {
            case 0 =>
              buff1 = None
              buff2 = None
              r1.copy(value = r1.value + r2.value)

            case x if x < 0 =>
              buff1 = None
              r1

            case x if x > 0 =>
              buff2 = None
              r2
          }
        } else {
          buff1 match {
            case Some(v) =>
              buff1 = None
              v

            case None =>
              val r2 = buff2.get
              buff2 = None
              r2
          }
        }
      }
    }
  }
}
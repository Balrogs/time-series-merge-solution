import java.io._

import scala.io.Source

object Runner {

  val filesLimit = 100

  def main(args: Array[String]) : Unit = {

    if(args.length != 2) {
        println("USAGE")
        System.exit(0)
    }

    val inputDir = new File(args(0))
    val outputFile = new File(args(1))

    val fileList = if (inputDir.exists && inputDir.isDirectory) {
      inputDir.listFiles.filter(f => f.isFile && f.getPath.endsWith(".txt")).toList
    } else {
      Nil
    }

    val pw = new PrintWriter(outputFile)
    val sources = fileList.map(Source.fromFile(_).getLines)

    var recordsRow = sources.map(safeNext)

    while(sources.exists(_.hasNext)){


    }



    pw.close
  }

  def safeNext(iterator: Iterator) : Option[TimeSeriesRecord] = {
    if(iterator.hasNext){
      Some(TimeSeriesRecord(iterator.next()))
    } else {
      None
    }
  }

}
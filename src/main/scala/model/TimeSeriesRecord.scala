import java.text.SimpleDateFormat
import java.util.Date

case class TimeSeriesRecord(date: Date, value: Int){
  override def toString = {
    "%s:%d".format(date.toString, value)
  }
}

object TimeSeriesRecord {
  def apply(str: String) = {
    val splits = str.split(":")
    new TimeSeriesRecord(new SimpleDateFormat("yyyy-MM-dd").parse(splits(0)), splits(1).toInt)
  }
}
package model


import java.text.SimpleDateFormat
import java.util.{Date, TimeZone}

import org.joda.time.{DateTime, DateTimeZone}

case class TimeSeriesRecord(date: Date, value: Int){
  override def toString: String = {
    val d = new DateTime(date.getTime, DateTimeZone.UTC)

    "%d-%02d-%02d-%02d-%02d:%d".format(d.getYear, d.getMonthOfYear, d.getDayOfMonth, d.getHourOfDay, d.getMinuteOfHour, value)
  }
}

object TimeSeriesRecord {
  def apply(str: String): TimeSeriesRecord = {
    val splits = str.split(":")

    val inputDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm")
    inputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))

    new TimeSeriesRecord(inputDateFormat.parse(splits(0)), splits(1).toInt)
  }
}
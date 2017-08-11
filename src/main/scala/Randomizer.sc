import java.io.PrintWriter
import scala.util.Random

val pw = new PrintWriter("part-14.txt")
Iterator.range(1970, 2017).foreach(year => {
  Iterator.range(1, Random.nextInt(12)).foreach(month => {
    Iterator.range(1, 31).foreach(day => {
      Iterator.range(0, Random.nextInt(23)).foreach(hour => {
        Iterator.range(0, Random.nextInt(59)).foreach(minute => {
            pw.write("%d-%02d-%02d-%02d-%02d:%d".format(year, month, day, hour, minute, Random.nextInt(100)))
            pw.write("\n")
        })
      })
    })
  })
})

pw.close
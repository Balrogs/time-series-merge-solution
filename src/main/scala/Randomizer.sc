import java.io.PrintWriter
import scala.util.Random

val pw = new PrintWriter("part-4.txt")

(1970 to 2017).flatMap(year => {
  (1 to 12).flatMap(month => {
    (0 to 31).map(day => {
      pw.write("%d-%02d-%02d:%d".format(year, month, day, Random.nextInt(100)))
    })
  })
})
pw.close
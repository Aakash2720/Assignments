import org.apache.tika.parser.microsoft.ooxml.OOXMLParser
import org.apache.tika.sax.BodyContentHandler
import java.io.FileInputStream
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.ParseContext

object Question1 extends App {

    val file = new FileInputStream("src/main/scala/exam_data.docx")
    val handler = new BodyContentHandler()
  val parser = new OOXMLParser()
  val metadata = new Metadata()
  val parseContext = new ParseContext()
    try {
      parser.parse(file, handler, metadata, parseContext)

      val lines = handler
        for(line <-lines.toString.split("\n")){
            val Array(k, l, m) = line.split(",").map(_.trim).map(_.toInt)
            val result = if ((k * l) >= m) "yes" else "no"
            println(s"Values: $k, $l, $m -> $result")
        }


    } finally {
      file.close()
    }

}








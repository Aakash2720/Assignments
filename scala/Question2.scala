import org.apache.tika.parser.microsoft.ooxml.OOXMLParser
import org.apache.tika.sax.BodyContentHandler
import java.io.FileInputStream
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.ParseContext

object Question2 extends App{
  val file = new FileInputStream("src/main/scala/thief_data.docx")
  val handler = new BodyContentHandler(-1)
  val parser = new OOXMLParser()
  val metadata = new Metadata()
  val parseContext = new ParseContext()
  try {
    parser.parse(file, handler, metadata, parseContext)

    val lines = handler
    for (line <- lines.toString.split("\n")) {
        println(line+" count is "+isSolve(line,0))

    }

  } finally {
    file.close()
  }
  def isSolve(str:String,ind:Int):Int={
    var count=0;
      if(ind==str.length) 0
      else if(str.charAt(ind)=='0'){
        val ans:Int =isSolve(isReplace(str),ind+1)+1
        count +=ans
        count
      }
      else {
        isSolve(str,ind+1)
      }}
  def isReplace(str:String):String={
//   val s1=new String(str.replaceAll("0","x"))
//   val s2=new String(s1.replaceAll("1","0"))
//    new String(s2.replaceAll("x","1"))
    str.replaceAll("0","x").replaceAll("1","0").replaceAll("x","1")
  }
}

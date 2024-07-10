import scala.io.Source
object Question3 extends App{
  val filename = "src/main/scala/CoreData.txt" // Replace with your file path
  val source = Source.fromFile(filename).mkString.trim.split(" ")
  for(i<-source){
      var result=isSolve(i);

      while(result>=10 ){
        result=isSolve(String.valueOf(result))
      }
    println(i+" "+result)
    }

  def isSolve(num:String):Int={
    var count=0;
//    if(num.length==ind) count
//    else {
//      val result = isSolve(num, ind + 1) + num.charAt(ind).toInt
//      count = count + result
//      count
//    }
    if (num.length == 1) {
      num.toInt
    } else {
      val sum = num.map(_.asDigit).sum
      isSolve(sum.toString)
    }
  }

  }


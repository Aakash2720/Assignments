import scala.collection.mutable.Map
object CompletionOfInterface extends App{

  trait Test {
    def Initialize(array: Array[Char]): Unit
    def FindOccurance(input: Char): Int
    def FindLetter(input: Int): Char
  }
  object completionOfInterface extends Test{
    var hashMapForOccurance:Map[Char,Int] = Map[Char, Int]()
    //    var len=0;
    //    val tempArray: Array[Char] = new Array[Char](len)
    var tempArray:Array[Char]=_
    def Initialize(array: Array[Char]): Unit = {
      //len=array.length
      tempArray=array
//      array.foreach(char=>hashMapForOccurance(char)=hashMapForOccurance.getOrElse(char,0)+1)
      hashMapForOccurance = array.foldLeft(Map[Char, Int]()) {
        case (acc, char) =>acc + (char -> (acc.getOrElse(char, 0) + 1))
}
    }
    def FindOccurance(input: Char): Int= {
           hashMapForOccurance(input)
    }
    def FindLetter(input: Int): Char= {
          tempArray(input)
    }
  }

  completionOfInterface.Initialize(Array('a', 'b', 'c', 'd', 'e', 'a'))
  println(completionOfInterface.FindOccurance('a'))
  try{
    println(completionOfInterface.FindLetter(8))
    }
  catch{
    case e:IndexOutOfBoundsException=>println(e.getMessage)
    }
  }



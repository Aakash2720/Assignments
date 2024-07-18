object MiddleElementOfList extends App{

//  def middle[T](list: List[T]): Option[T] = {
//      if(list.isEmpty) None
//      else Some(size(list))
//  }
// def size[T](list:List[T]):T={
//   def isSize[T](fast: List[T], slow: List[T]): T = {
//     if ( fast.isEmpty || fast.tail.isEmpty) slow.head
//     else isSize(fast.tail.tail, slow.tail)
//   }
//   isSize(list,list)
// }
//
//  println(middle(List(1,2,3,4,5,6)))


   def middle[T](list: List[T]): Option[T] = {
     def findMiddleCount(slow:List[T],fast:List[T],count : Int) : Int = fast match{
       case _::tail => findMiddleCount(slow.tail,tail,count+1)
       case _ =>count
     }
     def get[T](list: List[T], index: Int): Option[T] = {
       def loop(lst: List[T], idx: Int): Option[T] = lst match {
         case Nil => None
         case head :: _ if idx == 0 => Some(head)
         case _ :: tail => loop(tail, idx - 1)
       }
      loop(list, index)
     }
     list match {
       case Nil => None
       case _ =>
         val ind = findMiddleCount(list, list,0)
         get(list,if( ind%2 ==0) ind/2 -1 else ind/2)

     }
   }
  println(middle(List(1, 4, 3, 2, 5)) )
  println(middle(List(1,2,3,4,5,6)))
  println(middle(List(1)))
  println(middle(List()))

}

object Question5 extends App {
  abstract class MyList[+A] {

    def head: A
    def tail: MyList[A]
    def isEmpty: Boolean
    def append[B >: A](x: B): MyList[B]
    def printElement: String
    def size: Int
    def sort(compare: (A, A) => Int): MyList[A]
  }

  object Empty extends MyList[Nothing] {
    def head: Nothing = throw new NoSuchElementException
    def tail: MyList[Nothing] = throw new NoSuchElementException
    def isEmpty: Boolean = true
    def append[B >: Nothing](x: B): MyList[B] = new Cons(x, Empty)
    def printElement: String = ""
    def size: Int = 0
    def sort(compare: (Nothing, Nothing) => Int) = Empty
  }

  class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
    def head: A = h
    def tail: MyList[A] = t
    def isEmpty: Boolean = false
    def append[B >: A](x: B): MyList[B] = new Cons(x, this)
    def printElement: String = if (t.isEmpty) " " + h else h.toString + " " + t.printElement
    def size: Int = t.size + 1
    def sort(compare: (A, A) => Int) = {
      def insert(x: A, sortedList: MyList[A]): MyList[A] = {
        if (sortedList.isEmpty) new Cons(x, Empty)
        else if (compare(x, sortedList.head) <= 0) new Cons(x, sortedList)
        else new Cons(sortedList.head, insert(x, sortedList.tail))
      }

      val sorted = t.sort(compare)
      insert(h, sorted)
    }





  }

  class Comparator[T](comparefn: (T, T) => Int) {
    def compare(o1: T, o2: T) = comparefn(o1, o2)
  }

  val comp = new Comparator[Int]((o1, o2) => {
    if (o1 < o2) -1
    else if (o1 > o2) 1
    else 0
  })
  println(comp.compare(5, 4))

  val list: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, new Cons(4, new Cons(5, Empty)))));
  println(list.size)
  val list2 = list.sort((x, y) => y - x)
  println(list2.printElement)
}

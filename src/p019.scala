import scala.util.matching.Regex

trait Solution {
  val pattern = new Regex("""(.*)\$(.*)\$""", "problem", "solutionName");
  def simpleName:String=pattern.findFirstMatchIn(this.getClass.getSimpleName).get.group ("solutionName")
}
trait SolutionP019 extends Solution {
  def rotate[T] (n:Int, l:List[T]):List[T]
}

trait Solutions[Problem] {
  def doc():String
  def tests ()
  def implementations ():List[Problem]
}

object p019 extends Solutions[SolutionP019]{
  def main(args: Array[String]) = {
    println (doc)
    tests
  }

  def doc = """
    http://aperiodic.net/phil/scala/s-99/p19.scala
    P19 (**) Rotate a list N places to the left.
    Examples:
    scala> rotate(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))
    res0: List[Symbol] = List('d, 'e, 'f, 'g, 'h, 'i, 'j, 'k, 'a, 'b, 'c)

    scala> rotate(-2, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))
    res1: List[Symbol] = List('j, 'k, 'a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i)
  """
  
  def tests()={
    for (sol <- implementations) {
      print ("Test implementation " + sol.simpleName)


      val expected=List('d, 'e, 'f, 'g, 'h, 'i, 'j, 'k, 'a, 'b, 'c)
      val result=sol.rotate(3, List('a, 'b, 'c, 'd, 'e, 'f, 'g, 'h, 'i, 'j, 'k))
      require(expected==result)
      println (" : OK ")
    }
  }

  def implementations ()=List(DavidBernard, AlexisAgahi, AriéBénichou )

  object DavidBernard extends SolutionP019 {
    def rotate[A](k:Int, l:List[A]) = if(l.isEmpty) Nil else {
      val nk = k % l.length
      val pk = if(k < 0) l.length + k else k
      l.drop(pk) ::: l.take(pk)
    }
  }

  object BenjaminLerman extends SolutionP019 {
    def rotate[A](k:Int, l:List[A]) = if(l.isEmpty) Nil else {
      val nk = k % l.length
      val pk = if(k < 0) l.length + nk else nk
      l.drop(pk) ::: l.take(pk)
    }
  }
  
  object AlexisAgahi extends SolutionP019 {
    def rotate[T]( n:Int, list:List[T]):List[T] = {
      val size = list.size
      require( size > 0 )
      val i = if( n < 0) size + n % size else n % size
      val (l1, l2 ) = list.splitAt( i )
      l2 ::: l1
    }
  }

  object AriéBénichou extends SolutionP019 {


    def rotate[T](n: Int, ls: List[T]): List[T] = {
      if (ls == null)
	return Nil

      val length = ls.length      
      if (length == 0)
	return Nil
      
      val (l, r) = ls.splitAt(if (n < 0) length + n % length else n % length);
      r ::: l      
    }
  }
}


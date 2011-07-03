object p001 {

  val doc = """
    http://groups.google.com/group/paris-scala-user-group/browse_thread/thread/eac9319f94b244a8
    
    P01 (*) Find the last element of a list.
    Example:
    scala> last(List(1, 1, 2, 3, 5, 8))
    res0: Int = 8
  """
  
  object BenjaminLerman {
    
    def last(l: List[Int]): Option[Int] = {
     if(l.length == 0)
       None
     else
       Some(l(l.length - 1))
    }
    
  }

}

import p001._
println(doc)

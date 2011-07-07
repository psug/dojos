import scala.util.matching.Regex

trait Solution {
  val pattern = new Regex("""(.*)\$(.*)\$""", "problem", "solutionName");
  def simpleName:String=pattern.findFirstMatchIn(this.getClass.getSimpleName).get.group ("solutionName")
}
trait Solutions[Problem] {
  def doc():String
  def tests ()
  def implementations ():List[Problem]

  def main(args: Array[String]) = {
    println (doc)
    tests
  }
}



trait SolutionP019 extends Solution {
  def rotate[T] (n:Int, l:List[T]):List[T]
}

trait SolutionP020 extends Solution {
  def removeAt[T] (n:Int, l:List[T]):(List[T], T)
}

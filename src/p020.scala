
object p020 extends Solutions[SolutionP020]{
  def doc = """
  scala> removeAt(1, List('a, 'b, 'c, 'd))
  res0: (List[Symbol], Symbol) = (List('a, 'c, 'd),'b)
  """

  def tests ()={
    for (sol <- implementations) {
      print ("Test implementation " + sol.simpleName)
      val expected=(List('a, 'c, 'd),'b)
	val result=sol.removeAt(1, List('a, 'b, 'c, 'd))
      require(expected==result)
      println (" : OK ")
    }
  }

  def implementations ()=List(BenjaminLerman)
  
  object BenjaminLerman extends SolutionP020 {
    def removeAt[A](k:Int, l:List[A]) = (l.take(k):::l.drop(k+1), l(k))
  }
}

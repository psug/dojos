def time[T](f : => T, tries : Int = 500 ) = {
  var count = 0
  var max : Long = Long.MinValue
  var min : Long = Long.MaxValue
  var total : Long = 0
  while (count < tries && max != Long.MaxValue) {
    val time = try{ 
      val start = System.nanoTime
      f 
      val end = System.nanoTime 
      (end - start) 
    } catch{ 
      case ex : StackOverflowError => Long.MaxValue 
    }
    count += 1
    total += time
    min = math.min(min, time)
    max = math.max(max, time)
  }
  def toStr(v : Long) = if (v == Long.MaxValue) "\u221E" else String.valueOf(v/1000000)
  "x %5d | min %9s | avg %9s | max %9s".format(count, toStr(min), toStr(total/count), toStr(max))
} 

def printTimingOf[T]( name : String , f : => T, tries : Int = 500 ) {
  println("timing " + name )
  println("\t\t\t" + time(f, tries))
}


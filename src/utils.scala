def time[T]( name : String , f : => T, tries : Int = 500 ) = { 
  var count = 0
  var max : Long = Long.MinValue
  var min : Long = Long.MaxValue
  var total : Long = 0
  while (count < tries && max != Long.MaxValue) {
    val time = try{ 
      val start = System.currentTimeMillis
      f 
      val end = System.currentTimeMillis 
      (end - start) 
    } catch{ 
      case ex : StackOverflowError => Long.MaxValue 
    }
    count += 1
    total += time
    min = math.min(min, time)
    max = math.max(max, time)
  }
  def toStr(v : Long) = if (v == Long.MaxValue) "Infinite" else String.valueOf(v)
  "%s\tx %d : min %s\t< avg %s\t< max %s".format(name, count, toStr(min), toStr(total/count), toStr(max))
} 

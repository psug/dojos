package logic

object S99Logic {
  def and(a: Boolean, b: Boolean) = a && b
  def or(a: Boolean, b: Boolean) = a || b
  def nor(a: Boolean, b: Boolean) = a || !b
  def nand(a: Boolean, b: Boolean) = a && !b
  def impl(a: Boolean, b: Boolean) = !a || b
  def equ(a: Boolean, b: Boolean) = a == b

  def table2(f: ((Boolean, Boolean) => Boolean)) {
    table(2, { s: Seq[Boolean] => f(s(0), s(1)) })
  }

  def table(size: Char, f: (Seq[Boolean] => Boolean)) {
    def pow(n: Int, m: Int): Int = {
      import scala.annotation.tailrec
      @tailrec
      def _pow(m: Int, acc: Int): Int = m match {
        case 0 => acc
        case _ => _pow(m - 1, acc * n)
      }
      _pow(m, 1)
    }
    printTitles(size)
    //loops through all combination of boolean of length size
    for (i <- 0 until pow(2, size)) printValues(intToBooleanSeq(i, size), f)
  }

  private def printn(a: Seq[Any]) {
    val b = new StringBuilder
    for (i <- a) b.append(i).append("	")
    println(b)
  }

  private def printTitles(n: Char) {
    val end = ('A' + n).toChar
    val titles = ('A' until end) map (_.toString)
    printn(titles :+ "result")
  }

  private def printValues(s: Seq[Boolean], f: (Seq[Boolean] => Boolean)) {
    printn(s :+ f(s))
  }

  /**
   * Converts the number to a Seq[Boolean] of length size based on binary representaion.
   */
  private def intToBooleanSeq(number: Int, size: Int): Seq[Boolean] = {
    0 until size map { pos: Int => (number & (1 << pos)) != 0 }
  }

  def main(args: Array[String]) {
    def f(a: Seq[Boolean]) = and(a(0), or(a(1), a(2)))
    table(3, f)
    for (f <- Seq(and _, or _, nor _, nand _, impl _, equ _)) {
      table2(f)
    }
  }
}
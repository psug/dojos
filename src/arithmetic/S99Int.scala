package arithmetic {
  class S99Int(val n: Int) {
    import S99Int._
    import scala.collection.mutable.ListBuffer

    def isPrime = {
      S99Int.updatePrimesTo(n)
      S99Int.knownPrimes.contains(n)
    }

    def isCoprimeTo(i: Int): Boolean = gcd(n, i) == 1

    @annotation.tailrec
    private def factorize(n: Int, primes: Seq[Int], buffer: ListBuffer[Int]) {
      if (n > 1 && !primes.isEmpty) {
        val p = primes(0)
        if (n % p == 0) factorize(n / p, primes, buffer + p) else factorize(n, primes.tail, buffer)
      }
    }

    def primeFactors: List[Int] = {
      if (n < 0) error("primeFactors called on a number < 0")
      updatePrimesTo(n)
      val buffer = new ListBuffer[Int]
      factorize(n, knownPrimes, buffer)
      buffer.toList
    }
  }

  object S99Int {
    implicit def int2S99Int(i: Int): S99Int = new S99Int(i)

    var knownPrimes = Seq[Int](2, 3)

    def isPrimeWithKnown(i: Int) = !knownPrimes.exists(i % _ == 0)

    def updatePrimesTo(max: Int) {
      val lastPrime = knownPrimes.last
      if (max > lastPrime)
        lastPrime to max foreach (i => if (isPrimeWithKnown(i)) knownPrimes = knownPrimes :+ i)
    }

    def gcd(i: Int, j: Int): Int = {
      if (j == 0)
        i
      else
        gcd(j, i % j)
    }

    def main(args: Array[String]) {
      println(0 to 1000 map (_.primeFactors))
    }
  }

}
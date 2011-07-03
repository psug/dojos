object p009 {

  val doc = """
    http://groups.google.com/group/paris-scala-user-group/browse_thread/thread/fe8e24ae6ed944c7
    
    P009 (**) : Pack consecutive duplicates of list elements into sublists.
    If a list contains repeated 
    elements they should be placed in separate sublists. 
    
    Example: 
    
    scala> pack(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) 
    res0: List[List[Symbol]] = List(List('a, 'a, 'a, 'a), List('b), 
    List('c, 'c), List('a, 'a), List('d), List('e, 'e, 'e, 'e)) 
  """
  
  object BenjaminLerman {
    def pack[A](l: List[A]): List[List[A]] = { 
      l match { 
        case Nil => Nil 
        case x :: Nil => List(List(x)) 
        case x :: y :: tail if(x != y) => List(x) :: pack(y :: tail) 
        case x :: y :: tail => 
          val s  = pack(y :: tail) 
          (x :: s.head) :: s.tail 
      } 
    }
    
    def pack2[T](list: List[T]): List[List[T]] = { 
      case class State(result: List[List[T]] = Nil, 
                       currentList: List[T] = Nil) { 
        def append(t: T) = { 
          currentList match { 
            case Nil => State(Nil, List(t)) 
            case u :: _ if(u == t) => State(result, t :: currentList) 
            case u :: _ => State(currentList :: result, List(t)) 
          } 
        } 
        def endResult: List[List[T]] = { 
          if ( currentList == Nil) Nil else currentList :: result; 
        } 
      } 
      list.foldRight(State())((t,s) => s.append(t)).endResult 
    }
  }
  
  object SteveGury {
    def pack( input : List[Symbol] ) = { 
      @annotation.tailrec
      def rec( list : List[Symbol] , subList : List[Symbol] = Nil , res : List[List[Symbol]] = Nil ) : List[List[Symbol]] = { 
        if( list.isEmpty ) 
          subList :: res 
        else { 
          val x = list.head 
          val (newSubList, newRes) = if( subList.isEmpty ) 
            (x :: Nil, res) 
          else if( subList.head == x ) 
            (x :: subList, res) 
          else 
            (x :: Nil, subList :: res) 
          rec( list.tail , newSubList , newRes ) 
        } 
      } 
      rec( input ).reverse 
    }
  }
      
  object AlexisAgahi {
    def pack[T](list: List[T]): List[List[T]] = { 
    list.foldRight( List[List[T]]() ){ 
      ( e, result ) => 
        result.headOption match { 
          case Some( l ) => 
            ( if( e == l.head ) List( e :: l ) else List( List( e ), l) ) ::: result.tail 
          case None => List( List( e ) ) 
        } 
      }
    }
  }
  
  object Francois {
    import scala.collection.mutable.Buffer
    
    def pack[T](l:List[T]) : List[List[T]] = if(l.isEmpty) Nil else { 
      val res = Buffer.empty[Buffer[T]] 
      var current = l.head 
      var buf = Buffer(current) 
      l.foreach { x => if(x == current) buf += x else { res += buf ; buf = Buffer(x) ; current = x } } 
      res += buf 
      res.toList.map( _.toList )
    } 
  }
  
  object StefanChris {
    def pack[T](l: List[T]): List[List[T]] = l match { 
      case Nil => Nil 
      case x => x.takeWhile(_ == x.head) :: pack(x.dropWhile(_ == x.head)) 
    }
  }
  
  object JérômeMainaud {
    def pack[T](l: List[T]): List[List[T]] = { 
      @annotation.tailrec 
      def recPack (l: List[T], accum: List[List[T]]): List[List[T]] = l match { 
       case Nil => accum.reverse 
       case x => recPack(x.dropWhile(_ == x.head), x.takeWhile(_ == x.head) :: accum) 
      } 
      recPack(l, Nil) 
    }
  }

  object AriéBenichou {
    import scala.collection.mutable.ListBuffer
    
    def pack[T](l: List[T]): List[List[T]] = { 
      val r = new ListBuffer[List[T]] 
      if (l != null && !l.isEmpty) { 
        val p = new ListBuffer[T] 
        p += l.head 
        l.tail.foreach(e => { if (e != p.head) { r += p.toList; p.clear }; p += e }) 
        r += p.toList 
      } 
      r.toList 
    } 
  }

  object DavidBernard {
    import org.scalacheck.Prop.forAll
    import org.scalacheck.Gen
    import org.scalacheck.Gen._
    import org.scalacheck.Arbitrary.arbitrary
    
    private val r = new util.Random
    
    val genListSameSymbol = for { 
      i <- choose(1,200) 
      s <- Symbol(r.nextInt(10).toString) 
    } yield List.fill(i)(s)
    
    val genListOfSuiteSameSymbol = for {
      l <- Gen.listOf(genListSameSymbol)
    } yield l.flatten
    
    def propFlattenPackIsIdentity[T](pack : List[Symbol] => List[List[Symbol]]) = {
      forAll(genListOfSuiteSameSymbol) { (l1: List[Symbol]) => 
        pack(l1).flatten == l1 
      }
    }
  }
  
  val solutions = List[(String, List[Symbol] => List[List[Symbol]])](
    ( "BenjaminLerman (1)" , BenjaminLerman.pack[Symbol]) 
    , ("SteveGury", SteveGury.pack)
    , ("AlexisAgahi" , AlexisAgahi.pack[Symbol])
    , ( "Francois" , Francois.pack[Symbol])
    , ( "BenjaminLerman (2)" , BenjaminLerman.pack2[Symbol]) 
    , ( "StefanChris" , StefanChris.pack[Symbol] )
    , ( "JérômeMainaud", JérômeMainaud.pack[Symbol] )
    , ( "AriéBenichou", AriéBenichou.pack[Symbol] )
  )
  
  def miniBench() {
    val r = new util.Random
    val input = (1 to 100000).map{ _ => Symbol( r.nextInt(10).toString ) }.toList
    for (s <- solutions) {
      System.gc()
      printTimingOf(s._1, s._2(input))
    }
  }

  def miniTest() {
    for (s <- solutions) {
      println("test : " + s._1)
      println("-- énonce : " +
        (s._2(List('a, 'a, 'a, 'a, 'b, 'c, 'c, 'a, 'a, 'd, 'e, 'e, 'e, 'e)) == List(List('a, 'a, 'a, 'a), List('b), List('c, 'c), List('a, 'a), List('d), List('e, 'e, 'e, 'e)))
      )
      println("-- Nil : " +
        (s._2(Nil) == Nil)
      )
      println("-- DavidBernard.propFlattenPackIsIdentity")
      DavidBernard.propFlattenPackIsIdentity(s._2).check
    }
  }

}

import p009._
println(doc)

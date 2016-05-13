import scala.language.implicitConversions

object Implicit {

  trait TestAbs {
    def log()
  }

  implicit object Test extends TestAbs {
    def log() {
      println("Test.log")
    }
  }

  implicit def int2string(x: Int) = {
    println("int2string(" + x + ")")
    x.toString()
  }

  def print(x: String) (implicit test: TestAbs) {
    test.log()
    println(x)
  }

  implicit def list2list(list: List[Int]) (implicit f: Int => String): List[String] = {
    println("list2list")
    list.map(f)
  }

  implicit def list2ordered[A](x: List[A]) (implicit elem2ordered: A => Ordered[A]): Ordered[List[A]] = new Ordered[List[A]] {
    def compare(y : List[A]) : Int = {
      for(i <- 0 to x.length - 1) {
        for(j <- 0 to y.length - 1) {
          val b = x(i).compareTo(y(i))
          if(b != 0)
            return b
        }
      }

      return 0
    }
  }

  def main(args: Array[String]) {
    //print(120)

    if("10" == 10) println("10 == \"10\"")

    val xs = List(1)
    val ys = List("1")

    //if(ys == xs) println("ys == xs")
    if(ys <= xs) println("ys <= xs")
    //if(ys >= xs) println("ys >= xs")
    //if(ys >  xs) println("ys >  xs")
    //if(ys <  xs) println("ys <  xs")
  }

}


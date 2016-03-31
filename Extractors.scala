
object Extractors {
  def main(args: Array[String]) {
    val v: String = Extractors("Hello World")
    println(v)

    v match {
      case Extractors(str) => println(str)
      case _ => println("what?")
    }
  }

  def apply(v: String): String = v + "!"
  def unapply(v: String): Option[String] = Some(v + "!")
}


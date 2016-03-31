
object ObjectA {
  def method() {
    println("ObjectA method")
  }

  override def toString() = "ObjectA"
}

object ObjectB {
  def method() {
    println("ObjectB method")
    ObjectA.method()
  }

  override def toString() = "ObjectB"
}

object Objects {
  def main(args: Array[String]) {
    ObjectB.method()

    println(ObjectA)
    println(ObjectB)
  }
}


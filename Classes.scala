
class Complex(real: Double, img: Double) {
  override def toString() = "(" + real + "," + img + ")"
}

object Classes {
  def main(args: Array[String]) {
    println(new Complex(10, 20));
  }
}


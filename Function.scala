
object Function {
  def foreach(f : Char => Unit) {
    f('x')
    f('y')
    f('z')
  }

  def myFunc(str: String) = 10

  def func1() = 10

  def func2 = 20

  def main(args: Array[String]) {
    val f0: String => Int = myFunc
    val f1: () => Int = func1
    val f2: Int = func2

    println(f0(""))

    println(func1())
    println(func2)

    foreach( (c: Char) => { println(c) } )
    foreach { println }
  }
}


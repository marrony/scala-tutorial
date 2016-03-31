
abstract class Tree
case class Leaf(value: String) extends Tree
case class Branch(left: Tree, right: Tree) extends Tree

object PatternMatching {
  def stringify(tree: Tree): String = tree match {
    case Leaf(value) => value
    case Branch(left, right) => stringify(left) + " " + stringify(right)
  }

  def main(args: Array[String]) {
    val left: Tree = Leaf("Hello")
    val right: Tree = Leaf("World")
    val branch: Tree = Branch(left, right)

    println(branch);
    println(stringify(branch));
  }
}



trait Runnable {
  def run()
}

class Lola extends Runnable {
  def run() {
    println("Run Lola, run")
  }
}

class Thread extends Runnable {
  def run() {
    println("Thread run")
  }
}

object Traits {
  def run(runnable: Runnable) {
    runnable run
  }

  def main(args: Array[String]) {
    run( new Lola )
    run( new Thread )
  }
}


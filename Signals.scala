import scala.io.StdIn

class Signal[T](name: String) {
  var value: T = _
  var children: Array[Signal[_]] = Array()
  var update: (Signal[_], Boolean) => Unit = null

  def mapMany[U](refresh: () => U, signals: Array[Signal[_]]): Signal[U] = {
    val signal: Signal[U] = new Signal[U]("")

    var count = 0
    var update = false

    signal.update = (parent: Signal[_], parentUpdate: Boolean) => {
      count = count + 1
      update = update || parentUpdate

      if(count == signals.length) {
        if(update) signal.value = refresh()

        signal.sendChildren(update)
        update = false
        count = 0
      }
    }

    for( s <- signals )
      s.children = s.children :+ signal

    return signal
  }

  def sendChildren(update: Boolean) {
    for( c <- children.reverse )
      c.update(this, update)
  }

  def map[U](f: T => U): Signal[U] = {
    val refresh = () => f(value)

    return mapMany[U](refresh, Array(this))
  }

  def map2[T2, U](f: (T, T2) => U, s2: Signal[T2]): Signal[U] = {
    val refresh = () => f(value, s2.value)

    return mapMany[U](refresh, Array(this, s2))
  }

  def map3[T2, T3, U](f: (T, T2, T3) => U, s2: Signal[T2], s3: Signal[T3]): Signal[U] = {
    val refresh = () => f(value, s2.value, s3.value)

    return mapMany[U](refresh, Array(this, s2, s3))
  }

}

class Input[T](name: String) extends Signal[T](name) {

  def setValue(newValue: T, update: Boolean): Input[T] = {
    if(update) value = newValue
    sendChildren(update)
    return this
  }
}

object Timer {
  import scala.collection.mutable.HashMap

  val _every : HashMap[Long, Input[Long]] = new HashMap[Long, Input[Long]]

  def notifySignal(signal: Input[Long], value: Long) {
    for( s <- _every.values ) {
      s.setValue(value, s == signal);
    }
  }

  def every(t: Long) : Signal[Long] = {
    return _every.getOrElseUpdate(t, {
      val input: Input[Long] = new Input[Long]("")

      new Thread(new Runnable {
        def run() {
          var count: Long = 0
          while(true) {
            Thread.sleep(t)
            count = count + t
            notifySignal(input, count)
          }
        }
      }).start

      input
    })
  }
}

object Console {
  val stdin: Input[String] = {
    new Input[String]("Console").setValue("", true)
  }

  {
    new Thread(new Runnable {
      def run() {
        while(true)
          stdin.setValue(StdIn.readLine(), true)
      }
    }).start
  }
}

object Signals {
  def main(args: Array[String]) {
    val s: Signal[Double] = Console.stdin.map3((txt: String, time1: Long, time2: Long) => {
      println(txt + " " + time1 + " " + time2)
      10
    }, Timer.every(2000), Timer.every(100)).map((v: Int) => {
      println(v)
      2.0 * v
    })
  }
}


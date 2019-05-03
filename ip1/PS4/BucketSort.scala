// For tutorial question 4.8

// Bucket sort N numbers
object BucketSort{
  val bag = new ArrayBag
  val MAX = bag.MAX
  val N = 100; // or whatever

  /** Choose a sequence of random numbers */
  def init : Array[Int] = {
    val random = new scala.util.Random
    val xs = new Array[Int](N)
    for(i <- 0 until N) xs(i) = random.nextInt(MAX)
    xs
  }

  /** Sort the array xs using bucket sort */
  def sort(xs: Array[Int]) : Array[Int] = {
    for(x <- xs) bag.add(x)
    val ys = new Array[Int](xs.size) 
    var j = 0
    // We build up the result in ys, using j as a high-water mark
    for(x <- 0 until MAX; i <- 0 until bag.count(x)){
      ys(j) = x; j += 1
    }
    assert(j == xs.size)
    ys
  }

  def main(args:Array[String]) = {
    val xs = init; println(xs.mkString(", "))
    val ys = sort(xs); println(ys.mkString(", "))
  }
}

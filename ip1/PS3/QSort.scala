// For tutorial question 3.6, question 3.7 and question 3.8


// Quicksort, using in-line partitioning

object QSort{
  var MAX = 10000000 // upper bound on array entries
  var a : Array[Int] = null


  /** Partition the segment a[l..r)
    * @return k s.t. a[l..k) < a[k..r) and l <= k < r */
  def partitionOrig(l: Int, r: Int) : Int = {
    val x = a(l) // pivot
    // Invariant   a[l+1..i) < x = a(l) <= a[j..r) && l < i <= j <= r
    //          && a[0..l) = a_0[0..l) && a[r..N) = a_0[r..N)
    //          && a[l..r) is a permutation of a_0[l..r)
    var i = l+1; var j = r
    while(i < j){
      if(a(i) < x) i += 1
      else{ val t = a(i); a(i) = a(j-1); a(j-1) = t; j -= 1 }
    }
    // swap pivot into position
    a(l) = a(i-1); a(i-1) = x
    i-1 // position of the pivot
  }

  // Partition with fewer swaps
  def partition(l:Int, r:Int) : Int = {
    val x = a(l); // pivot
    // Invariant a[l+1..i) < x <= a[j..r) && l < i <= j <= r
    var i = l+1; var j = r
    while(i<j){
      while(i<j && a(i)<x) i += 1
      while(i<j && a(j-1)>=x) j -= 1
      // i=j or a(i) >= x > a(j-1)
      if(i<j){ val t = a(i); a(i) = a(j-1); a(j-1) = t; i += 1; j -= 1 }
    }
    // swap pivot into position
    a(l) = a(i-1); a(i-1) = x
    i-1
  }
  // Sort the segment a[l..r), in situ
  def QSortOrig(l: Int, r: Int) : Unit = {
    println("Recurse")
    if(r-l > 1){ // nothing to do if segment empty or singleton
      val k = partition(l,r)
      QSortOrig(l,k); QSortOrig(k+1,r)
    }
  }
  def QSortLeft(L:Int, r:Int) : Unit = {
    println("Recurse")
    var l = L //(So we can reassign to l)
    while(r-l > 1){ 
      val k = partition(l, r)
      QSortLeft(l,k); l = k+1
    }
  }
  def QSortMinStack(L:Int, R:Int) : Unit = {
    println("Recurse")
    var l=L; var r=R
    while(r-l > 1){ 
      val k = partition(l,r)
      if(k-l < r-k){ QSortMinStack(l,k); l = k+1 }
      else{ QSortMinStack(k+1, r); r = k }
    }
  }

  def partitionDutch(l:Int, r:Int) : (Int,Int) = {
    // Invariant a[l..i) < x = a[i..j) < a[k..r) && l <= i < j <=k <= r
    val x = a(l); var i = l; var j = l+1; var k = r
    while(j<k){
      if(a(j)==x) j += 1
      else if(a(j)<x){ // swap a(j) and a(i) = x
        a(i) = a(j); a(j) = x; i += 1; j += 1
      }
      else if(x < a(j)){ // swap a(j) and a(k-1)
        val t = a(j); a(j) = a(k-1); a(k-1) = t; k -= 1
      }
    }
    (i,j)
  }
  def QSort(l:Int, r:Int) : Unit = {
    if(r-l > 1){ // nothing to do if segment empty or singleton
      val (i,j) = partitionDutch(l,r)
      QSort(l,i); QSort(j,r)
    }
  }

  // Initialise array randomly
  def init(N: Int) = {
    a = new Array[Int](N)
    val random = new scala.util.Random
    for(i <- 0 until N) a(i) = random.nextInt(MAX)
  }

  // Print array
  def printArray(a : Array[Int]) = {
    for(i <- 0 until a.size) print(a(i)+"\t")
    println
  }

  def main(args: Array[String]) = {
    assert (args.size > 0, "Usage: scala QSort #ItemsInArray #Iterations #Range")
    var N = args(0).toInt // # elements in array
    val iters = if(args.length == 1) 1 else args(1).toInt // # iterations
    if(args.size>2) MAX = args(2).toInt

    for(_ <- 0 until iters){
      init(N)
      if(N <= 100) printArray(a)
      //QSortOrig(0,N)
      //QSortLeft(0,N)
      //QSortMinStack(0,N)
      QSort(0,N)
      if(N <= 100) {printArray(a);println("====")}
    }
  }
}

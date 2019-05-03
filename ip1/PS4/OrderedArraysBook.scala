// For tutorial question 4.6

object OrderedArraysBook extends Book{
  private val MAX = 1000 // max number of names we can store
  private val names = new Array[String](MAX)
  private val numbers = new Array[String](MAX)
  private var count = 0
  // These variables together represent the mapping
  // { names(i) -> numbers(i) | i <- [0..count) }.
  // datatype invariant: count <= MAX && 
  // the entries in names[0..count) are distinct and increasing.

  /** Return the index i<=count s.t. 
    * names[0..i) < name <= names[i..count). */
  private def find(name: String) : Int = {
    // Invariant: names[0..i) < name <= names[j..count) && 
    // 0 <= i <= j <= count
    var i = 0; var j = count
    while(i< j){
      val m = (i+j)/2 // i <= m < j
      if(names(m) < name) i = m+1 else j = m
    }
    i
  }

  /** Return the number stored against name. */
  def recall(name: String) : String = {
    val i = find(name)
    assert(i<count && names(i)==name)
    numbers(i) 
  }

  /** Add the maplet name -> number to the mapping. */
  def store(name: String, number: String) = {
    val i = find(name)
    if(i < count && names(i)==name) numbers(i) = number
    else{
      // this is a new name
      assert(count < MAX)
      // copy names[i..count) into names[i+1..count+1) and copy 
      // numbers[i..count) into numbers [i+1..count+1)
      for(j <- count-1 to i by -1){
        names(j+1) = names(j); numbers(j+1) = numbers(j)
      }
      names(i) = name; numbers(i) = number; count += 1
    }
  }

  /** Is name in the book? */
  def isInBook(name: String) : Boolean = {
    val i = find(name)
    i< count && names(i)==name
  }

  /** Delete the number stored against name (if it exists).  Return true if
    * it was in the book. */
  def delete(name: String) : Boolean = {
    val i = find(name)
    if(i< count && names(i)==name){
      // copy names[i+1..count) into names[i..count-1) and copy 
      // numbers[i+1..count) into numbers [i..count-1)
      for(j <- i until count-1){
        names(i) = names(i+1); numbers(i) = numbers(i+1)
      }
      count -= 1; true
    }
    else false
  }
}

/*
Design decisions:
• Do you use a dummy header node?
Yes
• Can your linked list store the same integer several times, or do you
avoid repetitions?
Avoid repitions, there are no reptitions in a set
• Do you store the elements of the set in increasing order?
Yes, contains will be called upon frequently and will be logn rather than n if elements are ordered.

*/


// A class of objects to represent a set

class IntSet{
  // State: S : P(Int) (where "P" represents power set)

  // The following lines just define some aliases, so we can subsequently
  // write "Node" rather than "IntSet.Node".
  private type Node = IntSet.Node
  // Constructor
  private def Node(datum: Int, next: Node) = new IntSet.Node(datum, next)

  // Init: S = {}
  private var theSet : Node = new Node(0) // or however empty set is represented

  // Size of theSet. Initially set to 0.
  private var cardinality = 0

  // Checks if theSet is empty
  def isEmpty : Boolean = {
    if(theSet.next == null) true else false
  }

  /** Convert the set to a string.
    * complexity: O(n) where n is the size of the set */
  override def toString : String = {
    var string = "{" + theSet.next.datum
    //I: string = "first datum, second datum, ... up to datum of nn"
    //nn (think of next) is a variable to iterate through the set
    var nn = theSet.next.next
    while(nn != null) {
      string += ", " + nn.datum
      nn = nn.next
    }   
    return string +"}"
  }

  /** Add element e to the set
    * Post: S = S_0 U {e} */
  def add(e: Int) = {
    theSet.next = new Node(e,theSet.next)
    cardinality += 1
  }

  /** Length of the list
    * Post: S = S_0 && returns #S */
  def size : Int = ???

  /** Does the set contain e?
    * Post: S = S_0 && returns (e in S) */
  def contains(e: Int) : Boolean = ???

  /** Return any member of the set.  (This is comparable to the operation
    * "head" on scala.collection.mutable.Set, but we'll use a name that does
    * not suggest a particular order.)
    * Pre: S != {}
    * Post: S = S_0 && returns e s.t. e in S */
  def any : Int = ???

  /** Does this equal that?
    * Post: S = S_0 && returns that.S = S */
  override def equals(that: Any) : Boolean = that match {
    case s: IntSet => ???
    case _ => false
  }

  /** Remove e from the set; result says whether e was in the set initially
    * Post S = S_0 - {e} && returns (e in S_0) */
  def remove(e: Int) : Boolean = ???
    
  /** Test whether this is a subset of that.
    * Post S = S_0 && returns S subset-of that.S */
  def subsetOf(that: IntSet) : Boolean = ???


}


// The companion object
object IntSet{
  /** The type of nodes defined in the linked list */
  private class Node(var datum: Int, var next: Node = null)

  /** Factory method for sets.
    * This will allow us to write, for example, IntSet(3,5,1) to
    * create a new set containing 3, 5, 1 -- once we have defined 
    * the main constructor and the add operation. 
    * post: returns res s.t. res.S = {x1, x2,...,xn}
    *       where xs = [x1, x2,...,xn ] */
  def apply(xs: Int*) : IntSet = {
    val s = new IntSet; for(x <- xs) s.add(x); s
  }
}

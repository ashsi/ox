
import org.scalatest.FunSuite
//import IntSet._

/*
Design decisions:
• Do you use a dummy header node?
Yes
• Can your linked list store the same integer several times, or do you
avoid repetitions?
Avoid repitions, there are no reptitions in a set
• Do you store the elements of the set in increasing order?
Yes, makes subset O(n) instead of O(n^2)
• Do you include anything else in your state, for efficiency?
cardinality, isEmpty, find


abs: set {x | x is an element of theSet}
DTI: theSet is ordered, the data are ints
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

  // Checks if theSet is empty. O(1)
  def isEmpty : Boolean = {
    if(theSet.next == null) true else false
  }

  /** Convert the set to a string.
    * complexity: O(n) where n is the size of the set */
  override def toString : String = {
  	if(isEmpty) return "{}"
    var string = "{" + theSet.next.datum
    //I: string = "first datum, second datum, ..., datum of nn"
    //nn (think of next) is a variable to iterate through the set
    var nn = theSet.next.next
    while(nn != null) {
      string += "," + nn.datum
      nn = nn.next
    }   
    return string +"}"
  }

  /** find an element e and return the previous node such that node.next >= e
    * Post: S = S_0 && returns i s.t. i.next.datum >= e 
    * O(n) */
  def find(e: Int) : Node = {
  	var i = theSet
  	while (i.next != null && i.next.datum < e) {
  		i = i.next
  	}
  	i
  }

  /** Add element e to the set
    * Post: S = S_0 U {e} and S is ordered 
    * O(n) */
  def add(e: Int) = {
  	val a = find(e)
  	if(a.next == null || a.next.datum != e) {
    	a.next = new Node(e,a.next)
    	cardinality += 1
    }
  }

  /** Length of the list
    * Post: S = S_0 && returns #S 
    * O(1) */
  def size : Int = cardinality

  /** Does the set contain e?
    * Post: S = S_0 && returns (e in S) 
    * O(n) */
  def contains(e: Int) : Boolean = {
  	val a = find(e)
  	if(a.next == null || a.next.datum != e) false else true
  }

  /** Return any member of the set.  (This is comparable to the operation
    * "head" on scala.collection.mutable.Set, but we'll use a name that does
    * not suggest a particular order.)
    * O(1)
    * Pre: S != {}
    * Post: S = S_0 && returns e s.t. e in S */
  def any : Int = {
  	require(!isEmpty, "The set is empty")
  	theSet.next.datum
  }
  /** Does this equal that?
    * Post: S = S_0 && returns that.S = S 
    * O(n) */
  override def equals(that: Any) : Boolean = that match {
    case s: IntSet => {
    	if(subsetOf(s) && cardinality == s.size) return true
    	return false
    }
    case _ => false
  }

  /** Remove e from the set; result says whether e was in the set initially
    * Post S = S_0 - {e} && returns (e in S_0) 
    * O(n) */
  def remove(e: Int) : Boolean = {
  	var a = find(e)
  	if(a.next == null || a.next.datum != e) return false
  	a.next = a.next.next
  	cardinality -= 1
  	return true
  }
    
  /** Test whether this is a subset of that.
    * Post S = S_0 && returns S subset-of that.S 
    * O(n) */
  def subsetOf(that: IntSet) : Boolean = {
  	if(isEmpty) return true
  	var i = theSet
  	var iCounter = 0
  	var j = that.find(i.datum)
  	while(i.next != null && j.next != null) {
  		if(i.next.datum == j.next.datum) {i = i.next; iCounter += 1; j = j.next}
  		else {j = j.next}
  	}
  	if(iCounter == cardinality && i.datum == j.datum) return true
  	return false
  }
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



class IntTest extends FunSuite{
  //toString
  test("Print a set as a string"){
    var set = IntSet.apply(3,2,1) 
    assert(set.toString === "{1,2,3}") 
  }
  test("The empty set is {}"){ 
  	var emptySet = new IntSet
    assert(emptySet.toString === "{}") 
  }
  //add
  test("Add an element to an empty set"){ 
  	var set = new IntSet
  	set.add(1)
    assert(set.toString === "{1}") 
  }
  test("Does not add duplicates"){ 
  	var set = IntSet.apply(3,2,2,1)
    assert(set.toString === "{1,2,3}") 
  }
  //contains
  test("The empty set does not contain 1"){ 
  	var set = new IntSet
    assert(set.contains(1) === false) 
  }
  test("The set {1,2,3} does contain 2"){ 
  	var set = IntSet.apply(3,2,1)
    assert(set.contains(2) === true) 
  }
  test("The set {1,2,3} does not contain 4"){ 
  	var set = IntSet.apply(3,2,1)
    assert(set.contains(4) === false) 
  }
  //any
  test("Get any element from {1,2,3}"){ 
  	var set = IntSet.apply(3,2,1)
    assert(set.any === 1 || set.any === 2 || set.any === 3) 
  }
  test("Error for getting any element from an empty set"){ 
  	var set = new IntSet
    val error = intercept[IllegalArgumentException](set.any) 
    assert(error.getMessage.contains("The set is empty"))
  }
  //subset
  test("The empty set is a subset of {1,2,3}"){
  	val emptySet = new IntSet
  	val set = IntSet.apply(3,2,1)
  	assert(emptySet.subsetOf(set) === true)
  }
  test("{2} is a subset of {1,2,3,4}"){
  	val set1 = IntSet.apply(2)
  	val set2 = IntSet.apply(4,3,2,1)
  	assert(set1.subsetOf(set2) === true)
  }
  test("{1,3} is a subset of {1,2,3,4}"){
  	val set1 = IntSet.apply(3,1)
  	val set2 = IntSet.apply(4,3,2,1)
  	assert(set1.subsetOf(set2) === true)
  }
  test("{1,2,7} is not a subset of {1,2}"){
  	val set1 = IntSet.apply(7,2,1)
  	val set2 = IntSet.apply(2,1)
  	assert(set1.subsetOf(set2) === false)
  }
  //equals
  test("{1,2,3} equals {1,3,2}"){
  	val set1 = IntSet.apply(3,2,1)
  	val set2 = IntSet.apply(2,3,1)
  	assert(set1.equals(set2) === true)
  }
  test("{1,2,3} does not equal {1,2}"){
  	val set1 = IntSet.apply(3,2,1)
  	val set2 = IntSet.apply(2,1)
  	assert(set1.equals(set2) === false)
  }
  test("{1,2,3} does not equal the string '1,2,3'"){
  	val set1 = IntSet.apply(3,2,1)
  	val string = "1,2,3"
  	assert(set1.equals("1,2,3") === false)
  }
  //remove
  test("Remove returns false when removing 4 from {1,2,3}"){
  	var set = IntSet.apply(3,2,1)
  	assert(set.remove(4) === false)
  }
  test("Remove returns false when removing 4 from {1,3,5}"){
  	var set = IntSet.apply(5,3,1)
  	assert(set.remove(4) === false)
  }
  test("Remove returns true when removing 3 from {1,2,3}"){
  	var set = IntSet.apply(3,2,1)
  	assert(set.remove(3) === true)
  }
  test("Once 3 is removed from {1,2,3} the set becomes {1,2} - using .toString"){
  	var set1 = IntSet.apply(3,2,1)
  	set1.remove(3)
  	val set2 = IntSet.apply(2,1)
  	assert(set1.toString === set2.toString)
  }
  test("Once 3 is removed from {1,2,3} the set becomes {1,2} - using .equals"){
  	var set1 = IntSet.apply(3,2,1)
  	set1.remove(3)
  	val set2 = IntSet.apply(2,1)
  	assert(set2.equals(set1) === true)
  }
}
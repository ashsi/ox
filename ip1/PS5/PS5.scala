// Q1

class Node(val datum: Int, var next: Node){
	override def toString : String = {
		var string = s"$datum"
		//I: string = "$datum0 -> $next0 -> ..." until the current value of next
		while(next != null) {
			string = string + s" -> $next"
			next = next.next
		}		
		return string
	}
}

/*
def myList : Node = {
	var list = new Node(1, null)
	var j = 2
	while(j < 13) { 
	// 2 <= j <=13 & a node exists for each value up to j that is linked to the previous nodes
		list = new Node(j, list)
		j += 1
	}
	list
}

def reverseList(list: Node) : Node ={
	var n = list
	while(n.next.next != null) {
		//I: n0.next...n.next != null & n0.next...n is reversed
		println(n)
		n.next.next = n
		n = n.next
	}
	
	n.next.next = n
	return n
}
*/

// Q2

/*
  /** Add the maplet name -> number to the mapping */
  def store(name: String, number: String) = {
    val n = find(name)
    if(n.next == null){ // store new info after the last node
      n.next = new LinkedListHeaderBook.Node(name, number, null)
    }
    else n.next.number = number
  }
*/

// Q3

// L(a) is defined to be the list of nodes reachable from a by following next references
// Abs: book = empty header node + {n.name → n.number | n ∈ L(list)} ordered by name
// DTI: L(list) is finite. The names in L(list) are distinct and the nodes are ordered by name.


class LinkedListHeaderBook extends Book{
  private var list = new LinkedListHeaderBook.Node("?", "?", null)
  // list represents the mapping composed of (n.name -> n.number)
  // maplets, when n is a node reached by following 1 or more 
  // next references.

  /** Return the node before the one containing name.
    * Post: book = book_0 && returns n s.t. n in L(list) &&
    * (n.next.name=name or n.next=null if no such Node exists)*/
  private def find(name:String) : LinkedListHeaderBook.Node = {
    var n = list
    // Invariant: name does not appear in the nodes up to and  
    // including n; i.e., 
    // for all n1 in L(list.next, n.next), n1.name != name
    while(n.next != null && n.next.name < name) n = n.next
    n
  }

  /** Is name in the book? */
  def isInBook(name: String): Boolean = find(name).next != null

  /** Return the number stored against name */
  def recall(name: String) : String = {
    val n = find(name); assert(n.next != null); n.next.number
  }

  /** Add the maplet name -> number to the mapping */
  def store(name: String, number: String) = {
    val n = find(name)
    var m = list
    if(new String n.next > new String name){ 
      while(m.next.name < name) {
      	m.name = m.next.name; m.number = m.next.number
      	m = m.next
 	  }
 	  m.name = name; m.number = number
      list = new LinkedListHeaderBook.Node("?", "?", list)
    }
    else n.next.number = number
  }

  /** Delete the number stored against name (if it exists); 
    * return true if the name existed. */
  def delete(name: String) : Boolean = {
    val n = find(name)
    if(n.next != null){ n.next = n.next.next; true }
    else false
  }
}

// Companion object
object LinkedListHeaderBook{
  private class Node(var name:String, var number:String, var next:Node)
}

// Q4 
/*

(a)

work done = k * no. of names checked (with some constant k)
work done is minimised when the no. of names checked is minimised
expected work done = sum of (p * k * no. of names checked) 
				   = sum from 0 <= i <= n-1 (k * p(i) * (i+1))
				   e.w.d smallest when the largest p is paired with the smallest no. of names checked (i+1)
min(e.w.d) = k (p(max)*1 + p(2ndmax)*2 + ... + p(min)*n)
this is the case when the names are arranged in order of decreasing probability

(b)

for LinkedListHeaderBook
in the recall function, where n is the entry just before the recalled entry, set n.next to n.next.next
 assign the entry values (originally of n.next) to the header
 create a new dummy list header
*/

// Q5

// abs: queue = seqInt s.t. elements are added to the end and removed from the start
// DTI: elements are finite (maximum 100)

class ArrayQueue extends Queue[Int]{
	val MAX = 100 // max number of pieces of data
	var q = new Array[Int]()

	def enqueue(x: Int) = {
		require(q.size < MAX)
		q = q :+ x
	}
	//different to spec because we do not want more than 100 elements in the queue

	/* Add x to the back of the queue 
	post: q=q0++[x] if  q.size !> MAX-1 */

	def dequeue: Int = {
		e = q.head
		q = q.tail
		return e
	}

	def isEmpty: Boolean = {
		q.size == 0
	}

	def isFull: Boolean = {
		q.size == MAX
	}
}

// Q6

// abs: queue = linked list of Ints s.t. elements are added to the end and removed from the start
// DTI: elements are finite

class IntQueue extends Queue[Int]{
	private var list = new IntQueue.Node("?", null)

	def enqueue(x: Int): Int = {
		list.number = x
    	list = new IntQueue.Node("?", list)
	}

	def dequeue: Int = ...

	def isEmpty: Boolean ={
		list.next == null
	}
}

object IntQueue{
  private class Node(var number:Int, var next:Node)
}

// Q7

//state: s:seqInt
//init: s = []

// abs: queue = seqInt s.t. elements are added and removed from both ends
// DTI: elements are finite, no. of elements is finite, elements where they exist are integers

class DoubleEndedQueue{
	var deq = Array[Int]()

	/** Is the queue empty?
	  * post: s = s0 & returns s==[] */
	def isEmpty : Boolean = {
		deq.isEmpty
	}
	
	/** add x to the start of the queue. 
	  * post: s = [x] ++ s0 */
	def addLeft(x:Int) = {
		deq == x +: deq
	}
	
	/** get and remove element from the start of the queue. 
	  * pre: s != []
	  * post: q = tail q0 & returns head q0 */
	def getLeft : Int = {
		assert(!deq.isEmpty)
		val e = deq(0)
		deq = deq.drop(1)
		return e
	}

	/** add element to the end of the queue. 
	  * post: s = s0 ++ [x] */
	def addRight(x: Int) = {
		deq == deq :+ x
	}
  
	/** get and remove element from the end of the queue.
	  * pre: s != []
	  * post: s = take (s0.length-2) s0 & returns s0!!(length-1) */
	def getRight : Int = {
		assert(!deq.isEmpty)
		val e = deq(deq.size-1)
		deq = deq.dropRight(1)
		return e
	}

}
















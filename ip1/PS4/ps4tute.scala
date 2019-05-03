[Q2]

trait: specification of pres and posts of functions and DTI - abstract definition (not exhaustive) in terms of code (e.g. of a class)

trait
    v
   student (class)
 v     v      v
me  julius  charlie (objects)

state: s : seqA
init: s = []
(s = stack)

example of a trait:

trait Stack[A] {
	//post: s = s0 && returns s == []
	def isEmpty : Boolean
	//post: s = [x] ++ s0
	/* order of [x] and s0 matters for functionality here*/
	def push (x :A)
	/*need pre condition unlike others*/
	//pre: s != [], post: returns x s.t. s0 = [x] ++ //?
	def pop : A
}

[Q3]

state : general pattern of all the states the program could be in - (essentially gives the domain in some cases)

(a) add needs to be changed to restrict poss. inputs, state space: take power set of 1 to n
(b) (ask Ismail for code to check)

[Q4]

(a) no first element of a set (set are always unordered)
(b) first element unless set is empty then nothing
(c)

[Q6]

see OrderedArraysBook

def find - private function
def recall 1
def store 2
def isInBook 1
def delete 2

1 retrieving element in binary tree(ordered) is Ologn
2 O(n)

-> ordered is a win for retrieving but lose out on time for store and delete (would be logn if you dindt have to shift them all down)

[Q7]

//Defining a trait:

state: Bag: Int -> Int
DTI: for all x in set s.t. bag(x) >= 0
&& for all x not between 0 and max, bag(x) = 0

init: for all x bag(x) = 0

trait Bag {
	val Max: Int
	//pre: 0 <= MAX, post: bag = bag0 (+) {x -> bag0(x) +1}
	def add(x:Int)
	//pre: 0<= x < MAX
	//post: bag = bag0 && returns bag(x)
	def count(x:Int):Int
}

//Defining a class:

class ArrayBag extend Bag {
	val MAX = 100
	private val c = new Array[Int](MAX)
	def add(x: Int) = { 
		assert(0<= x && x < MAX)
		c(x) += 1
	}
	def count(x: Int): Int {
		assert(...)
		c(x)
	}
}

[Q8]

BucketSort
check out radix sort
countsort
/* [Q1] */
// (a) the while loop runs until i = j
// search returns some value of i (0 <= i < N) which likely will not not give the closest a(i) value to x
// (b) if N = 0, search returns the value of i which is (unchanged from its intial value) 0
// (c) Does not work correctly with large arrays.
// fix: val m = i/2 + j/2

def search(a: Array[Int], x: Int) : Int = {
  val N = a.size
  // invariant I: a[0..i) < x <= a[j..N) && 0 <= i <= j <= N
  var i = 0; var j = N
  while(i < j){
    val m = (i+j)/2 // i <= m < j
    if(a(m) < x) i = m+1 else j = m
  }
  // I && i = j, so a[0..i) < x <= a[i..N)
i }

/*** ?? [Q2] */

//Find a s.t. a^2 ≤ y < (a+1)^2
//Pre: y >= 0
def sqRt(y: Int) : Int = {
	assert(y>=0)
	var a = 0; var b = y+1
	while(a+1 < b) {
		val m = (a+b)/3
		val n = b - (a+b)/3 
		if(m*m <= y) {
			if(n*n <= y) a=n else {
				a=m
				b=n}
		} else b=m
		println("m: "+m," n: "+n," a: "+a," b: "+b)
	}
	return a
}


// Find a s.t. a^2 <= y < (a+1)^2.  Precondition: y >= 0.
  def sqrt(y:Int) : Int = {
    assert(y>=0)
    // Invariant I: a^2 <= y < b^2 and a<b
    var a = 0; var b = y+1
    while(a+1 < b){
      val m = (a+b) /2 // a < m < b
      if(m*m <= y) a=m else b=m
    }
    // I and a+1=b, so a^2 <= y < (a+1)^2
    return a
  }

/* [Q3] */

def tooBig(y: BigInt) : Boolean = {
	val X = 2048
	if(y>X) true else false
}

//*(a)

//naive version
def conFindX() : Int = {
	assume(tooBig(1) == false)
	var i = 1001
	while(tooBig(i)) {
		//I: 1 <= i <= 1001 && tooBig(i..1001] = true
		i -= 1
	}
	//post: tooBig(i) == false && tooBig[i+1..1001] = true
	return i
}

//max calls: 1000 calls

//improved version
def aFindX() : Int = {
	var i = 0
	var j = 1001
	var y = 500
	//var k = 0 //k = no. of calls
	while(i+1 != j) {
		// I: 0 <= i < j <= 1001 && tooBig[j..1001] = true && tooBig[0..i] = false && 1001 > j-i >= 1
		if(tooBig(y)) {
			j = y
			y = (i+j)/2
		} else {
			i = y
			y = (i+j)/2
		}
		//k += 1
	//post: tooBig[j..1001] = true && tooBig[0..i] = false && j-i == 1
	}
	//println(k) 
	return i
}

// max calls: 10
// range per call (j-i): 1000 -> 500 -> 250 -> 125 -> 62 -> 31 -> 15 -> 7 -> 3 -> 1
// => 10 calls every time

//*(b)

def bFindX() : Int = {
	var i = 0
	var j = 1025
	var y = 512
	//var k = 0 //k = no. of calls
	while(i+1 != j) {
		/// I: 0 <= i < j < infinity && tooBig[0..i] = false && infinity > j-i >= 1
		if(tooBig(j)) {
			if(tooBig(y)) {
				j = y
				y = (i+j)/2
			} else {
				i = y
				y = (i+j)/2
			}
		} else {
			i = j; j = j*2; y = (i+j)/2
		}
		//k += 1
	//post: tooBig[j..infinity) = true && tooBig[0..i] = false && j-i == 1
	}
	//println(k) 
	return i
}

// k is the number of loop calls
//X = 512: k=10
//X = 1024: k=11
//X = 2048: k=12
//X = 4096: k=13
//-> the above function is proportional to log_2X

//sidenote:
//when j is intitialised at 1024 instead of 1025, these results were received:
//X = 512: k=10
//X = 1024: k=11
//X = 2048: k=13
//X = 4096: k=15
//this is not proportional to log_2X above 1024


/* [Q4] */

//insertion sort
def iSort(a: Array[Int]) : Array[Int] = {
	//pre: a is non-empty
	var i = 1
	while(i < a.size) {
		//I1: 1 <= i <= a.size && a[0..i) is sorted
		var x = a(i)
		var j = i-1
		while(j >= 0 && x < a(j)) {
			//I2: a.size-1 > j >= -1 && x < a[j+2..i-1]
			a(j+1) = a(j)
			j -= 1
		}
		//Post2: a[0..j+1) < x < a[j+2..i-1]
		a(j+1) = x
		i += 1
	}
	//Post1: 1 <= i <= a.size && a[0..a.size) is sorted
	a
}

// order of comparisons growth: quadratic //sum of comparisons: n(n-1)/2
// order of time growth: quadratic







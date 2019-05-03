/* [Q1]

(a)
def swap(x: Int, y: Int) = { val t = x; x = y; y = t }

AshleyPS2.scala:16: error: reassignment to val
def swap(x: Int, y: Int) = { val t = x; x = y; y = t }
                                          ^
AshleyPS2.scala:16: error: reassignment to val
def swap(x: Int, y: Int) = { val t = x; x = y; y = t }
                                                 ^
Compile error.
Inputs are 'val's.
Cannot reassign vals.

(b)
def swapEntries(a: Array[Int], i: Int, j: Int) = {
  val t = a(i); a(i) = a(j); a(j) = t
}
scala> a
res5: Array[Int] = Array(0, 1, 2, 3, 4)
scala> swapEntries(a,0,3)
scala> a
res7: Array[Int] = Array(3, 1, 2, 0, 4)

a itself is a value parameter but the entries of a can be changed.
The code works as intended - the entries of a are swapped. */


/* [Q2]
7 is printed since nasty(x) + y = 2*x + 1
the y is reassigned to 1 when nasty() is called
if switched then 11 is printed since y + nasty(x) = 5 2*x

object SideEffects{
  var x = 3; var y = 5
  def nasty(x: Int) : Int = { y = 1; 2 * x }
  def main(args: Array[String]) = println(y + nasty(x))
} */

/* [Q4] 
(a) numSteps + 1
(b) time = actual time + timeStep - ε^(numSteps+1)
(c) 

while (time < (timeEnd - timeStep/2))
{
// Inv: 0 <= time <= timeEnd and time=k*timeStep for some k∈ N
  time += timeStep
}

How inaccurate might time be after the loop now?
time = actual time - ε^(numSteps)
*/

/* [Q6] */

// Question is vague as to whether something that recurs can have the recursed pattern more than twice because "s[0..N-n) = s[n..N)" implies only one reptition of the initial pattern (e.g. 123123) but "in other words, s starts to repeat after the first n characters" allows for more reptiions
// I have made it so something that 'recurs' may have the pattern more than twice but it has to have the whole pattern repeated every time

def recursWith(s: Array[Int]): Int = {
	//finds the smallest value of n such that s recurs with period n
	val N = s.size
	var n = 1
	var j = 1
	var k = 0
	var recurs = false

	if(N == 0) {
		n = 0 
	}
		else{
			while(n < N && recurs == false){
				//I = 1 ≤ n ≤ N && 0 ≤ k ≤ N-n && j = n && recurs = s[N-2n..N-n) == s[n..N)
				var search = true
				k = 0
				while(k < N-n && search == true){
					//I = 1 ≤ j ≤ N && 0 ≤ k ≤ N-n && search = s(k) == s(j)
					if(s(k) == s(j)) {
            	    	k += 1
                		j += 1
					} else {
						search = false
					}
				}

				if(k == N-n && j == N && s(n-1) == s(j-1) && s(N-1) == s(k-1)){
					recurs = true
				} else {
					n += 1
					j = n
				}
			}
		}
	n
}


/* [Q7] */

// This function returns true if a (positive) integer has 3 digits in decimal
val threedigits:(Int=>Boolean) = i => {i >= 100 && i < 1000}

// This function tests whether p(i) holds for some i ∈ [0..N)
def exists(p : Int => Boolean, N : Int): Boolean = {
	//Pre: N >= 0
	var i = 0
	var j = false
	while(i < N && j == false) {
		// I: 0 <= i <= min(first instance s.t. p(i) = true, N) && j = p(i) = ∃i∈[0..i] (black dot) p(i)
		j = p(i)
		i += 1
		//println(j)
	}
	//Post: j = ∃i∈[0..N) (black dot) p(i)
	//Justification:
	//For there to exist some i ∈ [0..N) for which p(i) holds, 
	//for only one i in the range p(i) needs to hold true 
	//[0..N) (black dot) p(i) is always equal to 
	//[0..k) (black dot) p(i), 1 <= k <= N 
	//where p(k-1) = first instance where some p(i) = j == false
	j
}

/* [Q8] */

//Pre: 0 < p < q
// m = ceiling(q/p)
def m(p: Int, q: Int): Int = q/p + (if(q%p == 0) 0 else 1)

def reciprocalSum(P: Int, Q:Int): Array[Int] = {
	var i = 0
	var p = P
	var q = Q
	var c = new Array[Int](10)
	//10 is an arbitrary size
	while(p > 0 && i < 10) {
		// I: 0 <= p <= P && 0 <= i <= 10 && P/Q >= p/q + 1/sum(c[0..i))
		c(i) = m(p,q)
		println("i: "+i," p: "+p," q: "+q," c(i): "+c(i))
		p = p * c(i) - q
		q = q * c(i)
		i += 1
		println("p: "+p," q: "+q)
		//post: P/Q == 1/sum(c[0..k))
		//where k is the first i such that c[i] = 0 
	}
	val d = c.takeWhile(_ != 0)
	d
	
	
}

/* (c) 

c(i) is positive while p > 0 (from loop condition, so true while the loop iterates): c(i) == q[i]/p[i] >= 0 and Pre: 0 < p[0] < q[0]

Proof :-
In loop body:
p[i+1] = p[i] * c(i) - q[i]

case 1: c(i) = q[i]/p[i]
//happens when p = 1
p[i+1] = p[i] * q[i]/p[i] - q[i] = q[i] - q[i] = 0
-> loop terminates

case 2: c(i) = q[i]/p[i] + 1
//when q[i]%p[i] =! 0
p[i+1] = p[i] * (q[i]divp[i] + 1) - q[i] 
       < p[i] * (q[i]/p[i] + 1) - q[i]
       since q[i]%p[i] =! 0
       < p[i] * (q[i] + p[i]/p[i]) - q[i]
       < p[i]
-> p[i+1] < p[i]
case 2 repeated until a case 1 or case 2 where p[i+1] < 1

-> p is reduced by the loop body and the loop always terminates


* (d)

d = c[0..k)
where k is the first i such that c[i] == 0
to show d is strictly increasing, need to show c[0..k) is strictly increasing

c(i) = m(p,q)
     >= q[i]/p[i] 
     = q[i-1] * c(i-1) /p[i]

at i = 0, q > p by restriction on input
-> c(0) > 0
-> q[i] = q[i-1] * c(i-1) > q[i-1]
-> q is strictly increasing
from (c) p is decreasing, and p is strictly decreasing while i < k
-> q[i]/p[i] is strictly increasing
-> c(i) is strictly increasing for i < k
-> d is strictly increasing

*/

/* [Q9] */

def logThree(x: Int): Int = {
	require(x >= 1)
	var y = 1
	var z = 3
	if(x < 3) y = 0
	else {while((z*3) <= x) {
			//I: z == 3^y <= x
			z = z*3
			y += 1
		}
	}
	y
}

/* [Q10] */

def eval(a: Array[Double], x: Double) : Double = {
	val n = a.size
	var p = 1.0
	var i = 0
	var s = 0.0
	while(i < n) {
		//I: 0 <= i <= n && s = sum(a(j)*x^j) where j = [0..i) 
		s = s + a(i) * p
		p = p * x
		i += 1
	}
	//Post: s = sum(a(j)*x^j) where j = [0..n-1]
	s
}













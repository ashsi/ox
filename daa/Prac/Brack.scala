//Brack.scala DAA Practical
/** Import is for readLine so that we can write input directly to the program */
import scala.io.StdIn

object Brack{
	//Maximum length of word so we can define our arrays in dynamic programming
	val MAXWORD = 30

	//Operation to take 'A', 'B' and 'C' to corresponding Ints
  def LetterToInt(a: Char) : Int = {
		if(a == 'A' || a == 'B' || a == 'C'){
			return (a.toInt - 'A'.toInt);
		} else{
			println("Please only Letters from A,B,C.")
			sys.exit
		}
	}
	
  //Defining the op array for everything to use
  val op = Array.ofDim[Int](3,3)  
  op(0)(0) = 1; op(0)(1) = 1; op(0)(2) = 0
	op(1)(0) = 2; op(1)(1) = 1; op(1)(2) = 0
	op(2)(0) = 0; op(2)(1) = 2; op(2)(2) = 2

  /** Read file into array (discarding the EOF character) */
  def readFile(fname: String) : Array[Char] = 
    scala.io.Source.fromFile(fname).toArray.init

 
  /* Functions below here need to be implemented */


	//TASK 1
	//PossibleRec checks whether bracketing to something is possible recursively
	//Checks whether w(i,j) can be bracketed to z
	
	def PossibleRec(w: Array[Int], i: Int, j: Int, z:Int): Boolean = {
		if(j-i == 1) return w(i) == z
		var possible = false
		for(u <- 0 to 2; v <- 0 to 2) { 
			if(op(u)(v) == z) {
				for(x <- i+1 to j-1) {
					possible |= PossibleRec(w,i,x,u) && PossibleRec(w,x,j,v)
					if(possible) return true
				}			
			}
		}
		possible
	} 

	
	//TASK 2
	//NumberRec which checks the ways you get a result recursively
	//Computes number of ways w(i,j) can be bracketed to get z
	
	def NumberRec(w: Array[Int], i: Int, j: Int, z:Int): Int = {
		if(j-i == 1) if(w(i) == z) return 1; else return 0
		var number = 0
		for(u <- 0 to 2; v <- 0 to 2) {
			if(op(u)(v) == z) {
				for(x <- i+1 to j-1) {
					number += NumberRec(w,i,x,u) * NumberRec(w,x,j,v)
				}
			}
		}
		number
	} 

	
	//TASK 3
	//Runtime analysis of recursive solution along with tests
	/*
		For NumberRec: as w increases in length, the runtime increases exponentially, O(7^n)
		
		Derivation:
		Tn = 3 * Σ(i=1 to n-1) Ti + Tn-i
		Tn = 6 * Σ(i=1 to n-1) Ti
		//looking at a step in length (n-1)
		Tn - Tn-1 = 6 * Σ(i=1 to n-1) Ti - 6 * Σ(i=1 to n-2)
		Tn - Tn-1 = 6Tn-1
		Tn = 7Tn-1

		Tn = O(7^n)
		
			$ time scala Brack -NumberRec
			BBBBBBBBBB
			Bracketing values for BBBBBBBBBB
			A can be achieved in 0 ways
			B can be achieved in 4862 ways
			C can be achieved in 0 ways
			user	0m6.217s

			$ time scala Brack -NumberRec
			BBBBBBBBBBB
			Bracketing values for BBBBBBBBBBB
			A can be achieved in 0 ways
			B can be achieved in 16796 ways
			C can be achieved in 0 ways
			user	0m37.303s


			Tn = 7Tn-1
			Tn = 7 * 6 = 42 sec
			From 6sec to 37sec, 37 is approx 42 -> allowing for the overhead time this supports the complexity prediction

		For PossibleRec: the worst case is also exponential, however the runtime is a lot better, usually linear, in practice because the loops break once a single possibility is found

			$ time scala Brack -PossibleRec
			ABCA
			Bracketing values for ABCA
			A is possible
			B is possible
			C is possible
			user	0m1.469s

			$ time scala Brack -PossibleRec
			ABBCABBCACACABACABCAABCA
			Bracketing values for ABBCABBCACACABACABCAABCA
			A is possible
			B is possible
			C is possible
			user	0m1.861s

			Better than directly proportional because of the early loop breaking when z is possible

	*/
	
	//You may find the following class useful for Task 7
	// Binary tree class
	abstract class BinaryTree
	case class Node (left : BinaryTree, right : BinaryTree) extends BinaryTree
	case class Leaf (value : Char) extends BinaryTree

	//Printing for a binary tree
	def print_tree(t : BinaryTree) {
	//TODO(optional)
	}

	//These arrays should hold the relevant data for dynamic programming
	var poss = Array.ofDim[Boolean](MAXWORD, MAXWORD, 3)
	var ways = Array.ofDim[Int](MAXWORD, MAXWORD, 3)
	var exp = Array.ofDim[BinaryTree](MAXWORD, MAXWORD, 3)


	//Task 4, 5, and 7(optional)
	//Fill out arrays with dynamic programming solution
	
	
	def Tabulate(w: Array[Int], n: Int): Unit = {
		for(i <- 0 until n) {
			poss(i)(i+1)(w(i)) = true
			ways(i)(i+1)(w(i)) = 1
		}

		for(l <- 2 to n; i <- 0 to n-l; z <- 0 to 2; u <- 0 to 2; v <- 0 to 2) {
			if(op(u)(v) == z) {
				for(x <- 1 to l-1) {
					//p = no. of ways you can get u * no. of ways you can get v
					val p = ways(i)(i+x)(u) * ways(i+x)(i+l)(v)
					if(p > 0) {
						ways(i)(i+l)(z) += p
						poss(i)(i+l)(z) = true
					}
				}
			}
		}
	}

	//Task 6
	//Runtime analysis of dynamic programming version with tests

/* What is the maximum word length for which your program can correctly determine the number of bracketings?
MAXWORD (= 30) because the arrays only store up to MAXWORD no. of letters
BUT may stop working at less than 30 letters because of an integer overflow - the number of brackets is too high


It should take O(n^3) because the l and i and x loops are linear in n (z, u, v loops are constant = 3)

The dynamic programming version scales much better since it is polynomial whereas the recursive version is exponential
this is because in the recursive version the same calculations are repeated when needed for further calculations
whereas in the dynamic programming version the smaller calculations are saved and called upon from memory

		$ time scala Brack -Tabulate
		ABCA
		Bracketing values for ABCA
		A can be achieved 1 way
		B can be achieved 2 ways
		C can be achieved 2 ways
		user	0m1.511s

		$ time scala Brack -Tabulate
		ABCAABCA
		Bracketing values for ABCAABCA
		A can be achieved 113 ways
		B can be achieved 201 ways
		C can be achieved 115 ways
		user	0m1.525s

		$ time scala Brack -Tabulate
		ABCAACBAAACBABCA
		Bracketing values for ABCAACBAAACBABCA
		A can be achieved 2662635 ways
		B can be achieved 4593361 ways
		C can be achieved 2438849 ways
		user	0m1.921s

The test results are better than O(n^3) time


*/
  

/** The main method just selects which piece of functionality to run */
  def main(args: Array[String]) = {

    // string to print if error occurs
    val errString = 
      "Usage: scala Brack -PossibleRec [file]\n"+
      "     | scala Brack -NumberRec [file]\n"+
      "     | scala Brack -Tabulate [file]\n"
		
		if (args.length > 2){
			println(errString)
			sys.exit
		}

    //Get the plaintext, either from the file whose name appears in position
    //pos, or from standard input
    def getPlain(pos: Int) = 
      if(args.length==pos+1) readFile(args(pos)) else StdIn.readLine.toArray

    // Check there are at least n arguments
    def checkNumArgs(n: Int) = if(args.length < n){println(errString); sys.exit}

    // Parse the arguments, and call the appropriate function
    checkNumArgs(1)
		val plain = getPlain(1)
    val command = args(0)

		//Making sure the letters are of the right type
		val len = plain.length
		var plainInt = new Array[Int](len)
		if (len > MAXWORD){
			println("Word Too Long! Change MAXWORD")
			sys.exit;
		} else {
    	for (i <- 0 until len){
				plainInt(i) = LetterToInt(plain(i))
			}
		}
		
		//Executing appropriate command
    if(command=="-PossibleRec"){
		println("Bracketing values for "+ plain.mkString(""))
		for(i<-0 to 2){
			if(PossibleRec(plainInt, 0, len, i)){
				println(('A'.toInt + i).toChar + " is possible");
			}
			else{
				println(('A'.toInt + i).toChar + " is not possible");
			}
		}
    }
    else if(command=="-NumberRec"){
		var z: Int = 0
		println("Bracketing values for "+ plain.mkString(""))
		for(i<-0 to 2){
			z = NumberRec(plainInt, 0, len, i)
			if(z == 1){
				printf(('A'.toInt + i).toChar+ " can be achieved in %d way\n", z)
			}
			else{
				printf(('A'.toInt + i).toChar+ " can be achieved in %d ways\n", z)
			}
		}
    }

    else if(command=="-Tabulate"){
		Tabulate(plainInt,len)
		println("Bracketing values for "+ plain.mkString(""))
		for(v<-0 to 2){
		var z: Int = ways(0)(len)(v)
			if(z==0){
			println(('A'.toInt + v).toChar+ " cannot be achieved")
			}
			else if(z==1){
				printf(('A'.toInt + v).toChar+ " can be achieved %d way\n", z)
				printf("For example:")
				print_tree(exp(0)(len)(v))
				printf("\n")
			}
			else if (z > 1){
				printf(('A'.toInt + v).toChar+ " can be achieved %d ways\n", z)
				printf("For example:")
				print_tree(exp(0)(len)(v))
				printf("\n")
			}
		}
    }      
    else println(errString)
  }
}



def sq(n : Int) : Int = {
	n*n
}

def modThree(n : Int) : Int = {
	n - ((n/3)*3)
}

/* def perfSq(n : Int) : Int = {
	var i = n/2
	var j = 0
	while(j == 0) {
		if(i*i == n) {
		 j = 1
		 return i
		} else {
			i = i-1
		}
	}
}
*/

def recurFib(n : Int) : Int = {
	if(n == 0) {
		0
	} else if(n == 1) {
		1
	} else {
		recurFib(n-2) + recurFib(n-1)
	}
}

object DivMod {
	def main(args : Array[Int]) = {
		val x = args(0)
		val y = args(1)
		println(divMod(x,y))
	}

	def divMod(x : Int, y : Int) : Int = {
		var q = 0; var r = x
		while (r > y-1) {
			r = r - y
			q += 1
		}
		q; r;
	}
}
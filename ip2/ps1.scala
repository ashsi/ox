
//[Q1]
abstract class Shape {
  def isCircle = false
  def isSquare = false
}

class Elipse(var minor_axis: Int, var major_axis: Int) extends Shape{
	override def isCircle : Boolean = minor_axis == major_axis
}

class Rectangle(var width: Int, var height: Int) extends Shape {
	override def isSquare : Boolean = width == height
}

//Having a class for Shape allows you to count the total number of shapes
//To check how many circles/squares there are in a set of shapes, make an array of shapes and an iterative loop that goes through each shape and increases a count if A[i].isCircle/isSquare == true

//Q: Could have a single Shape class with another variable (string?) denoting whether it is a square, rectangle, ellipse or circle
//A: About being able to have a different number of variable for each shape, separating tasks, evolution of software

//Possible issue with having more classes is changing the size of an elipse or a rectangle may, respectively, make the shape a circle or a square. This is not a rigid relationship between the object and the subclass.

//[Q2]

// "var area = width * height" will give you the initial area but will not update if the height and width changes.
// To fix this you make area a function that recalculates the area when called.

//[Q3]

// The width and height of Reactangle are variables so _dimension.width and _dimension.height can be changed
// the val _area does not update to a new area if either the width or the height changes
// this can be improved by making _area a function

//[Q4]


// "Accepted for rendering."
// "Accepted for ray-trace rendering."
// It prints this because accept(a) is overloaded not overriden for r1: Renderer
// to change it: override def accept(a: OpaqueTriangle) = println("Accepted for ray-trace rendering.")


//[Q5]

class Ellipse(private var _a: Int, private var _b: Int) {
    def a = _a
    //getter
    def a_=(a: Int) = {_a = a}
    //setter
    def b = _b
    def b_=(b: Int) = {_b = b}

    def exchange = {
    	var temp = a
    	a = b
    	b = temp
    }


    /* version that would need to be overriden because it calls the base values (_a and _b), which the subclass setter do not change, rather than the getters: 
    def exchange = {
    	var temp = _a
    	a = _b
    	b = temp
    }
    */

}

class LoggedEllipse(private var _a: Int, private var _b: Int) extends Ellipse(_a, _b) {
	//remember to pass on the arguments _a _b to the superclass -> how does 2 copies of _a and _b work if the superclass has them as private?
	private var increases: Int = 0
	override def a_=(newA: Int)= {
		if(newA > a) increases += 1
		super.a = newA
	}
	override def b_=(newB: Int)= {
		if(newB > b) increases += 1
		super.b = newB
	}
	def getIncreases : Int = increases
}

//[Q6]

// Each line could be an different object with a different instance of Text containing the text of that line.

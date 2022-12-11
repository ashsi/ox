# ! Question 4: (Original text)
# List as many bad points as you can think of in the milk bill program in Figure 1,
# and where possible suggest ways of improving on them.

# Figure 1:
# object Milk{
#    def findSum(a : Array[Int]) : Int = {
#       val n = a.size
#       var total = 0; var i = 0
#       while(i < n){
#           total += a(i)
#           i += 1
#       }
#       total
#    }
#
#    def main(args : Array[String]) = {
#       val n = args.size
#       val a = new Array[Int](n)
#       for(i <- 0 until n) a(i) = args(i).toInt
#       println(findSum(a))
#    }
# }


# ! |------|
# ! |*-Q4-*|
# ! |------|

# * Some bad points and suggestions.

# * Add Explanation
# Add a comment at the top of the file explaining the problem. From the lecture notes the problem is given as:
#   "We have a sequence containing the no. of bottles of milk delivered each day, and we want to calculate the total."
# Add a comment to explain what the method findSum does e.g. "Calculate sum of a."
# Add post condition (describe output) of findSum e.g. "Post: returns sum(a): Int"
# Add comments describing the key steps, above each step.
#   -> The makers of this course love pre conditions, invariants, and post conditions.
#      These could be seen as the "key steps" to describe in comments.
#   An alternative: the steps outline what the code does such that someone reading file does not need to read the code
#                   to understand what the code is doing; they can understand from the comments in plain English alone.

# * Improve Style for Readability
# (Line 9)  Each variable declaration should be on a new line.
# (Line 17) Give variable "args" a descriptive name.
# (Line 20) Indent the contents of the for loop onto a new line, rather than put it on one.

# * Improve concision
# Remove repeated code: n is assigned twice to the same value inside main and inside findSum (Line 8 and Line 18)
#   -> In findSum, a.size is more descriptive than n and calling a.size is not costly if stored as a value in object a.
# This code solves the general problem of finding the total of a sequence of numbers.
#   -> Decide:
#      - If it is useful to have the findSum function for objects other than Milk (for total number of milks delivered)
#        then make findSum a method of a superclass of Milk. This is the neatest option and future-proofs new features.
#      - If it will only be used for Milk, forever, then rename input a to something descriptive like milkPerDay.

# * Class Structure
# Here we have an object Milk without a class. As objects are instances of classes, thus dependent on classes,
# we should have "class Milk{" on Line 1. The main method of the program, then, instead could instantiate a Milk object
# with the array as input and a variable called sum. sum can be first set (and updated when the array changes) by 
# calling the function findSum and assigning the return value to the variable sum. (If the array should never change, 
# in Python, call the variable SUM to denote a constant. In Scala, use the val keyword.) 

# I don't know what a method called main is doing inside "object Milk".


# * TODO: Rewrite the code, in Python.



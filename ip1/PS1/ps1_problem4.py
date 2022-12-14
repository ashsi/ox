#! Question 4: (Original text)
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
# (Line 7)  Remove the space before the colon to get "a: Array[Int]"
# (Line 9)  Each variable declaration should be on a new line.
# (Line 17) Remove the space before the colon to get "args: Array[String]"
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
# Here we have an object Milk without a class. As objects are instances of classes (types), thus dependent on classes,
# we should have "class Milk{" on Line 1. The main method of the program, then, instead could instantiate a Milk object
# with the array as input and a variable called sum. This is more sensible as we can have multiple milk objects, each
# corresponding to a different milk bill. sum can be first set (and updated when the array changes) by
# calling the function findSum and assigning the return value to the variable sum. (If the array should never change,
# in Python, call the variable SUM to denote a constant. In Scala, use the val keyword.)

# * Note on Python classes (dynamic)
# Python classes are dynamic as they are created at runtime and can be modified after creation.
# Source: https://python.readthedocs.io/en/latest/tutorial/classes.html
# In Scala, classes are created at compilation time and are not normally modifiable at runtime.


# ! ------------------------------------------------------------------------- ! #
# ! Rewriting the code, in Python.
# * With added functionality: unique id, price calculation, print bill.
# ! ------------------------------------------------------------------------- ! #

""" Calculate the total number of milks for a given sequence of milks per day."""
import itertools


def find_sum(iterable):
    """ Calculate the sum.
        input:    int or float iterable
        output:   sum(iterable)             """
    total = 0
    for item in iterable:
        total += item
    return total


class MilkBill:
    """ Total bill for milk over a number of days. """
    # Current price per milk.
    milk_price = 2.00
    # Unique id for each object. (First id is "0".)
    id_iter = itertools.count()

    def __init__(self):
        self.id = next(MilkBill.id_iter)
        # User inputs the number of milks per day.
        milks_per_day = [int(milks) for milks in input("Enter the # of milk bottles per day, separated by a space.\n")
                         .split()]
        self.total_milks = find_sum(milks_per_day)
        self.total_price = round(self.total_milks * self.milk_price, 2)

    def print(self):
        print(f"Bill {self.id} total number of milk bottles: {self.total_milks}.")
        print(f"Bill {self.id} total cost: £{self.total_price:.2f}.")


# Example script of MilkBill object creation.
bill0 = MilkBill()
print("Milk bill created.\n")
bill0.print()


# * Example console log:
# Enter the # of milk bottles per day, separated by a space.
# 3 1 9 22 30 100
# Milk bill created.

# Bill 0 total number of milk bottles: 165.
# Bill 0 total cost: £330.00.

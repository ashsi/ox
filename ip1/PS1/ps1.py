# Readability note: Comments here were written with a plug-in in mind. The plug-in colour-codes comments, so they're not all a dull, amorphous grey. 
# I used the Comments Highlighter plug-in for PyCharm. 
# "# !" colours comments red. "# *" colours comments green.

# ! Question 1: (Original text)
# Write short Scala functions which use just standard integer arithmetic
# (* + - /) and, on integer input:
# (a) square the input;
# (b) compute the remainder on dividing the input by 3;
# and (c) find the largest perfect square no more than the input.


# ! |------|
# ! |*-Q1-*|
# ! |------|

# ! (a)

# * Scala/Python Difference (Typing):
# Scala is a strongly statically typed language whereas Python is a strongly dynamically typed language.
# Meaning: In Scala variables/names have a type, and not in Python. In both, run-time objects (values) have types.
#   Dynamic languages like Python don't have type declarations, but the duck test can be used.
#   The Duck Test: "If it walks like a duck and quacks like a duck, then it must be a duck."
#   In my own words, a preference for dynamic typing over static typing can be seen as "observation over say-so".
# Consequence for 'typed' functions in Python 3: By default, there is no TypeError for passing input of another type
#   to my 'integer-input' functions. In this sense, Python has type annotation/type hints, and it is up to programmers
#   to know what can be passed to a function and what cannot.

def square(n: int) -> int:
    return n * n


# ! (b)

# * Scala/Python Difference (Operators):
# In Scala '/' returns the quotient.
# '//' is the equivalent operator in Python to '/' in Scala.
def mod_3(n: int) -> int:
    highest_multiple = (n//3)*3
    return n - highest_multiple


# ! (c)

# * Question Interpretation ("just"):
# The problem sheet says "just standard integer arithmetic".
# I take this to mean that loops, ifs, comparisons are allowed but inbuilt math functions other than * + - / are not
# because the question is likely impossible if limited to exactly * + - / for the logic.
# * Output Decision (Dealing with negative int input):
# Input   n: int
# Output  for n >= 0    the largest perfect square no more than the input: int
#         for n < 0     -1
def floor_perf_sq(n: int) -> int:
    if n < 0:
        return -1
    i = n//2
    while True:
        if i*i <= n:
            return i*i
        else:
            i -= 1


# ! Slap-Dash Black-Box Tests for Q1
# (All checks out.)

print("square tests:")
print(f"Input: -1.\tOutput: {square(-1)}.\t\tExpected Output: 1.")
print(f"Input: 0.\tOutput: {square(0)}.\t\tExpected Output: 0.")
print(f"Input: 20.\tOutput: {square(20)}.\tExpected Output: 400.")
print("")

print("mod_3 tests:")
print(f"Input: -1.\tOutput: {mod_3(-1)}.\tExpected Output: {-1 % 3}.")
print(f"Input: -2.\tOutput: {mod_3(-2)}.\tExpected Output: {-2 % 3}.")
print(f"Input: -3.\tOutput: {mod_3(-3)}.\tExpected Output: 0.")
print(f"Input: 0.\tOutput: {mod_3(0)}.\tExpected Output: 0.")
print(f"Input: 11.\tOutput: {mod_3(11)}.\tExpected Output: {11 % 3}.")
print("")

print("floor_perf_sq tests:")
print(f"Input: -1.\tOutput: {floor_perf_sq(-1)}.\tExpected Output: -1.")
print(f"Input: 0.\tOutput: {floor_perf_sq(0)}.\tExpected Output: 0.")
print(f"Input: 2.\tOutput: {floor_perf_sq(2)}.\tExpected Output: 1.")
print(f"Input: 4.\tOutput: {floor_perf_sq(4)}.\tExpected Output: 4.")
print(f"Input: 16.\tOutput: {floor_perf_sq(16)}.\tExpected Output: 16.")
print(f"Input: 28.\tOutput: {floor_perf_sq(28)}.\tExpected Output: 25.")



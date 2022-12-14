# ! Question 5: (Original text)
# [Programming] The Fibonacci numbers are defined by
#       fib(0) = 0
#       fib(1) = 1
#       fib(k) = fib(k−2)+fib(k−1) for k≥2

# Write a recursive function
#       def fib(n : Int) : Int = ...
# that calculates the nth Fibonacci number fib(n).

# Now modify it to produce as a side effect output which displays the tree of procedure calls like this:-
# fib(3)
# | fib(2)
# | | fib(1)
# | | = 1
# | | fib(0)
# | | = 0
# | = 1
# | fib(1)
# | = 1
# = 2

# You might have a solution that passes a depth parameter to fib. Can you do it without adding any extra
# parameters to the calls?


# ! |------|
# ! |*-Q5-*|
# ! |------|

# The following code requires Python 3.10 or above for the pattern matching syntax.


def fib(n: int) -> int:
    match n:
        case 0:
            return 0
        case 1:
            return 1
        case _:
            return fib(n-2) + fib(n-1)


def fib_tree(n: int, depth=0) -> int:
    print("| "*depth + f"fib({n})")
    match n:
        case 0:
            print("| "*depth + "= 0")
            return 0
        case 1:
            print("| "*depth + "= 1")
            return 1
        case _:
            n_1, n_2 = fib_tree(n-1, depth+1), fib_tree(n-2, depth+1)
            sln = n_1 + n_2
            print("| "*depth + f"= {sln}")
            return sln


print("Question 5 Fibonacci Trees:\n")
fib_tree(0)
print()
fib_tree(1)
print()
fib_tree(2)
print()
fib_tree(3)

# Can I do this without the depth parameter?
# I experimented with using a global variable, but I do not think it is possible to not pass on a parameter.
# I think it's not possible because the function is recursive, meaning it calls itself, and when it calls itself
# it needs a way to keep track of the depth. If the depth is not passed on as a parameter, its value needs to be set
# or updated in the function, but there is no way of doing this correctly without passing on the depth (as a parameter).
# Else, if the initial depth is set at the start of the function, any changes to the depth before a recursive call will
# be rebooted to the original constant value set at the start of the function.


# ! Question 6: (Original text)

# Write a non-recursive function
#       def fib(n : Int) : Int = ...
# that calculates the nth Fibonacci number fib(n). You should give an invariant for your code.


# ! |------|
# ! |*-Q6-*|
# ! |------|

def fib_loop(n: int) -> int:
    if n == 0 or n == 1:
        return n
    i = 2
    prev, curr = 0, 1
    while i <= n:
        # Invariant: curr == fib(i-1) && i <= n+1
        prev, curr = curr, curr + prev
        i += 1
    return curr


assert(fib_loop(0) == 0)
assert(fib_loop(1) == 1)
assert(fib_loop(2) == 1)
assert(fib_loop(7) == 13)
assert(fib_loop(12) == 144)

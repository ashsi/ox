Practical 1: Factoring Numbers

Here is a simple method for finding the smallest prime factor of a positive
integer:

> factor :: Integer -> (Integer, Integer)
> factor n = factorFrom 2 n

> factorFrom :: Integer -> Integer -> (Integer, Integer)
> factorFrom m n | r == 0    = (m,q)
>                | otherwise = factorFrom (m+1) n
>    where (q,r) = n `divMod` m

for example

*Main> factor 7654321
(19,402859)

because 

*Main> 19 * 402859
7654321

Repeatedly extracting tyhe smallest factor will return a list
of prime factors:

> factors :: Integer -> [Integer]
> factors n = factorsFrom 2 n

> factorsFrom :: Integer -> Integer -> [Integer]
> factorsFrom m n | n == 1    = []
>                 | otherwise = p:factorsFrom p q
>    where (p,q) = factorFrom m n

for example

*Main> factor 123456789
(3,41152263)
*Main> factors 123456789
[3,3,3607,3803]


-----------------
--  Exercises  --
-----------------

-- Exercise 1 --

factor 0
factorFrom 2 0
(q, r) = 0 `divMod` 2 = (0, 0 - 0 * 2) = (0, 0)
r == 0 so output is (m, q)
(2, 0)

factor 1
factorFrom 2 1
(q, r) = 1 `divMod` 2 = (0,1)
(q, r) = 1 `divMod` 3 = (0,1)
...
infinite computation


-- Exercise 2 --

*Main> factor 0
(2, 0)
*Main> factor 1
^CInterrupted.


-- Exercise 3 --

I am taking 1 to be a trivial factor, not considered here to be the smallest factor.

If n is a square number, then sqrt n is an integer that is a factor of n. In this case, the smallest factor of n would be at least as small as sqrt n. Hence, the smallest factor of a square number n cannot be both bigger than sqrt n and less than n.

If n is prime, the smallest factor of n is n, which is not less than n.

If n is not square or prime, there exists two distinct factors that multiply to get n. Two numbers greater than sqrt n multiply to form a product larger than n. If there exists a factor m of n larger than sqrt n then to achieve a product equal to n, there must be another factor smaller than sqrt n that multiplies with m. Therefore, the smallest factor of a number n cannot be both bigger than sqrt n and less than n.


> factor1 :: Integer -> (Integer, Integer)
> factor1 n = factorFrom1 2 n

> factorFrom1 :: Integer -> Integer -> (Integer, Integer)
> factorFrom1 m n | r == 0    = (m,q)
>                 | n <= m*m  = (n,1)                    
>                 | otherwise = factorFrom1 (m+1) n
>     where (q,r) = n `divMod` m

The guarded equation starting with 'otherwise' should be last, since no guarded equation will be evaluated after this. r == 0 must come before n <= m*m because n <= m*m includes the case where n is a square number or a prime number, in each case the smallest factor would respectively be m or n, so r == 0 comes first to catch the case where n is square.

In the worst case, about n-1 recursive calls are needed to evaluate factor1.


-- Exercise 4 --

while n <= m*m is true you are still in the first first half of possible multiples (i.e. you are checking numbers up to half of n). The same is true for q < m.  It is more efficient because both q and m have already been evaluated whereas m*m takes additional time to evaluate.

> factor2 :: Integer -> (Integer, Integer)
> factor2 n = factorFrom2 2 n

> factorFrom2 :: Integer -> Integer -> (Integer, Integer)
> factorFrom2 m n | r == 0    = (m,q)
>                 | q < m     = (n,1)                    
>                 | otherwise = factorFrom2 (m+1) n
>     where (q,r) = n `divMod` m 


-- Exercise 5 --

> factor3 :: Integer -> (Integer, Integer)
> factor3 n = factorFrom1 2 n

> factorFrom3 :: Integer -> Integer -> (Integer, Integer)
> factorFrom3 m n | r == 0    = (m,q)
>                 | q < m     = (n,1)
>                 | m == 2    = factorFrom3 3 n                
>                 | otherwise = factorFrom3 (m+2) n
>     where (q,r) = n `divMod` m 

Twice as efficient for large n.


-- Exercise 6 --

*Main> factor3 1
(1,1)
(0.06 secs, 64,728 bytes)
*Main> factor3 2
(2,1)
(0.00 secs, 64,440 bytes)
*Main> factor3 3
(3,1)
(0.00 secs, 64,480 bytes)
*Main> factor3 4
(2,2)
(0.00 secs, 64,440 bytes)
*Main> factor3 5
(5,1)
(0.01 secs, 64,840 bytes)
*Main> factor3 10
(2,5)
(0.00 secs, 64,304 bytes)
*Main> factor3 50
(2,25)
(0.00 secs, 64,336 bytes)
*Main> factor3 100
(2,50)
(0.00 secs, 64,344 bytes)
*Main> factor3 1000
(2,500)
*Main> factor3 10000000
(2,5000000)
(0.00 secs, 68,512 bytes)
*Main> factor3 99999999999
(3,33333333333)
(0.00 secs, 72,800 bytes)
*Main> factor3 1000000000000000000000
(2,500000000000000000000)
(0.00 secs, 81,008 bytes)


-- Exercise 7 --

> factor4 :: Integer -> (Integer, Integer)
> factor4 n = factorFrom4 2 n 2

> factorFrom4 :: Integer -> Integer -> Integer -> (Integer, Integer)
> factorFrom4 m n s | r == 0    = (m,q)
>                   | q < m     = (n,1)
>                   | m == 2    = factorFrom4 3 n 2
>                   | m == 3    = factorFrom4 5 n 2               
>                   | otherwise = factorFrom4 (m+s) n (6-s)
>     where (q,r) = n `divMod` m


-- Exercise 8 --

We do not know the pattern for the occurance of prime factors in the set of natural numbers follows. Even for a finite set of numbers, which is all we would realistically need, the pattern is not very simple to mathematically code as it is not regular. The pattern of occurances of prime numbers is a 'negative' pattern of all the multiples and there are many multiples to consider, which is not worth the hassle. We are writing a program to check for factors and that is what would be needed to calculate successive prime numbers.

-- Exercise 9 --

Instead of calling it factors2 I called it factors' to be consistent with factorFrom' (since factorFrom2 has already been defined).

> factors' :: Integer -> [Integer]
> factors' n = factorsFrom' 2 n

> factorsFrom' :: Integer -> Integer -> [Integer]
> factorsFrom' m n | n == 1    = []
>                  | otherwise = p:factorsFrom' p q
>    where (p,q) = factorFrom' m n 2

> factorFrom' :: Integer -> Integer -> Integer -> (Integer, Integer)
> factorFrom' m n s | r == 0    = (m,q)
>                   | q < m     = (n,1)
>                   | m == 2    = factorFrom' 3 n 2
>                   | m == 3    = factorFrom' 5 n 2               
>                   | otherwise = factorFrom' (m+s) n (6-s)
>     where (q,r) = n `divMod` m

-- Exercise 10 --

λ> factors 50
[2,5,5]
(0.00 secs, 72,408 bytes)
λ> factors' 50
[2,5,5]
(0.00 secs, 72,264 bytes)

λ> factors 12345678
[2,3,3,47,14593]
(0.02 secs, 5,799,840 bytes)
λ> factors' 12345678
[2,3,3,47,14593]
(0.00 secs, 105,400 bytes)

λ> factors 12345678912345
[3,5,43,2371,8072791]
(5.65 secs, 3,164,616,704 bytes)
λ> factors' 12345678912345
[3,5,43,2371,8072791]
(0.01 secs, 689,392 bytes)

Prime numbers

λ> factors 31
[31]
(0.00 secs, 82,776 bytes)
λ> factors' 31
[31]
(0.00 secs, 71,680 bytes)

λ> factors 92377
[92377]
(0.08 secs, 36,283,224 bytes)
λ> factors' 92377
[92377]
(0.00 secs, 137,904 bytes)

λ> factors 59534743
[59534743]
(41.51 secs, 23,337,691,568 bytes)
λ> factors' 59534743
[59534743]
(0.01 secs, 1,721,904 bytes)

λ> factors 101740496633
[^CInterrupted. 
(went over 4 minutes)
λ> factors' 101740496633
[101740496633]
(0.17 secs, 68,125,104 bytes)

(7*7*7)

λ> factors (7*7*7)
[7,7,7]
(0.01 secs, 73,104 bytes)
λ> factors' (7*7*7)
[7,7,7]
(0.00 secs, 72,888 bytes)

Negative numbers

λ> factors (-17)
[17,^CInterrupted.
λ> factors' (-17)
[-17]
(0.00 secs, 70,432 bytes)

Jevons' problem

λ> factors 8616460799
[89681,96079]
(0.08 secs, 37,735,296 bytes)
λ> factors' 8616460799
[89681,96079]
(0.06 secs, 19,207,848 bytes)

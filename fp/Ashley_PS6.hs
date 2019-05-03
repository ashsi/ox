-- [11.1] --

data Day = Sunday | Monday | Tuesday | Wednesday | Thursday | Friday | Saturday

foldBool :: a -> a -> Bool -> a
foldBool false true False = false
foldBool false true True  = true

foldDay :: a -> a -> a -> a -> a -> a -> a -> Day -> a
foldDay su m tu w th f sa Sunday    = su
foldDay su m tu w th f sa Monday    = m
foldDay su m tu w th f sa Tuesday   = tu
foldDay su m tu w th f sa Wednesday = w
foldDay su m tu w th f sa Thursday  = th
foldDay su m tu w th f sa Friday    = f
foldDay su m tu w th f sa Saturday  = sa

-- Is there a way to do this derving enum? 
-- I feel like this way (above) is the best way but not sure

-- [11.2] --

lteq :: Bool -> Bool -> Bool
lteq True False = False
lteq _ _        = True

-- [11.3] --

data Set a = Empty | Singleton a | Union (Set a) (Set a)

foldSet :: a -> (b -> a) -> (a -> a -> a) -> Set b -> a
foldSet e s u Empty         = e
foldSet e s u (Singleton x) = s x
foldSet e s u (Union x y)   = u (foldSet e s u x) (foldSet e s u y)
-- why (a -> a -> a)?

isIn :: Eq a => a -> Set a -> Bool
isIn val set = foldSet False ((==) val) unionOr set
    where unionOr False False = False
          unionOr _ _         = True

subset :: Eq a => Set a -> Set a -> Bool
subset sub sup = foldSet True f unionAnd sub
    where f x = isIn x sup
          unionAnd True True = True
          unionAnd _ _       = False

instance Eq a => Eq (Set a) where
    xs == ys = (xs `subset` ys) && (ys `subset` xs)

-- [11.4] --

data BTree a = Leaf a | Fork (BTree a) (BTree a)
    deriving Show

data Direction = L | R
    deriving Show

type Path = [ Direction ]

isInTr :: Eq a => a -> BTree a -> Bool
isInTr val tr = foldBTr ((==) val) (||) tr

find :: Eq a => a -> BTree a -> Maybe Path
find val tr | isInTr val tr == True = Just (lp val tr)
            | otherwise = Nothing

lp :: Eq a => a -> BTree a -> Path
lp val (Leaf x)     = []
lp val (Fork ls rs) | isInTr val ls == True = (:) L (lp val ls)
                    | isInTr val rs == True = (:) R (lp val rs)
-- this is a fold, could use functions below but the above function is easy to follow

-- Some functions I didn't use:
flatten :: BTree a -> [a]
flatten (Leaf x)     = [x]
flatten (Fork ls rs) = flatten ls ++ flatten rs

foldBTr :: (b -> a) -> (a -> a -> a) -> BTree b -> a
foldBTr l f (Leaf x)   = l x
foldBTr l f (Fork x y) = f (foldBTr l f x) (foldBTr l f y)

mapBTr :: (a -> b) -> BTree a -> BTree b
mapBTr f (Leaf x)     = Leaf (f x)
mapBTr f (Fork ls rs) = Fork (mapBTr f ls) (mapBTr f rs)

-- [12.1] --

data Queue a = Qempty | Qons a (Queue a)
    deriving (Eq, Show)

empty :: Queue a
empty = Qempty
-- 1 step

isEmpty :: Eq a => Queue a -> Bool
isEmpty q | q == empty = True
          | otherwise  = False
-- up to 2 steps

add :: a -> Queue a -> Queue a
add x Qempty     = Qons x Qempty
add x (Qons f l) = Qons f (add x l)
-- linear efficiency
-- around n steps where n is length of queue

-- does pattern matching count as a step?
-- I don't think it really does so have been answering under that assumption

get :: Queue a -> (a, Queue a)
get (Qons first rest) = (first, rest)
-- 1 step

{- Expense of operations would not be different if the
queue were represented by a list of its remaining elements 
in the reverse order in which they join the queue 
(but it would be more expensive if the list had to be reversed)-}

type Queue2 a = [a]

empty2 :: Queue2 a
empty2 = []
-- 1

isEmpty2 :: Eq a => Queue2 a -> Bool
isEmpty2 q | q == empty2 = True
           | otherwise   = False
-- up to 2 steps

front :: Queue2 a -> [a]
front q = [head q]

back :: Queue2 a -> [a]
back q = reverse (tail q)

add2 :: a -> Queue2 a -> Queue2 a
add2 x q = (head q) : (reverse (x : (back q)))
-- more costly, quadratic
-- takes about 1/2n^2 steps to reverse a list of length n
-- reverse is done twice here
-- cost is about n^2 + 2 (+2 for the obvious cons calls in add2 function)
-- could have (front q) ++ (reverse (x : (back q)))

get2 :: Queue2 a -> (a, Queue2 a)
get2 q = (head q, tail q) 
-- similar cost as get, 2 steps
-- 1 step in head and 1 step in tail 
-- as both use (x:xs) pattern matching to produce their respective results in one step


-- [12.2] --

fib :: Integer -> Integer
fib 0 = 0
fib 1 = 1
fib n = fib (n-1) + fib (n-2)

{- 

*Main> fib 10
55
(0.01 secs, 126,408 bytes)
*Main> fib 20
6765
(0.03 secs, 7,561,856 bytes)
*Main> fib 30
832040
(2.60 secs, 921,591,808 bytes) 

The later calls are slow because 

-}

two :: Integer -> (Integer, Integer)
two 0 = (0, 1)
two n = (fir, (+) prev2 fir)
   where prev1 = fst (two (n-1))
         prev2 = snd (two (n-1))
         fir   = (+) prev1 prev2
-- not used:  where fir = uncurry (+) (two (n-1))

fib' :: Integer -> Integer
fib' n | even n = fst (two (n`div`2)) 
       | odd n  = snd (two ((n-1)`div`2))

{- 

*Main> fib' 30
832040
(0.05 secs, 19,993,056 bytes)

The time it takes to calculate fib' n is the same as before divided by 2

-}

roughly :: Integer -> String
roughly n = [head xs] ++ "e" ++ show (length xs - 1)
    where xs = show n



{-

*Main> 1.6 ^ 9999
Infinity
(0.01 secs, 73,968 bytes)

*Main> roughly (2^9999)
"9e3009"

The 10000th Fibonacci number is roughly 1800 digits (10000 div by 5 or 6)

Let F^n-1 be:  F^n is:
    (a b)      (0a+1b 1a+1b)  =  (b a+b)
    (c d)      (0c+1d 1c+1d)     (d c+d)

The bottom line of the matrix calculates the next fibonacci number
The top line shows that if we start with b = c then the b/c diagonal are both values equal to the current value in the series
	(in this case the fibonacci series) which is set to the two previous numbers in the series added to each other (the previous value of d)



The 1000000th Fibonacci number is roughly 180000 digits

-}




-- [6.8]

data Tree a = Fork (Tree a) a (Tree a) | Empty

bsort :: Ord a => [a] -> [a]
bsort = flatten . foldr insert Empty

insert :: Ord a => a -> Tree [a] -> Tree [a]
insert x Empty = Fork Empty [x] Empty
insert x (Fork lt y rt)
    | [x] <  y = Fork (insert x lt) y rt
    | [x] >  y = Fork lt y (insert x rt)
    | [x] == y = Fork lt (x:y) rt

flatten :: Ord a => Tree [a] -> [a]
flatten Empty          = []
flatten (Fork lt y rt) = flatten lt ++ y ++ flatten rt

-- question: what is a datatype really?
-- I don't understand Geraint Jones's use of Both Left Right and Either in the lecture notes from previous weeks.
-- question: why does my solution work without making an instance Tree [a] of Tree a? What are the instances for?

-- [7.1]

-- Given function:
cp' :: [[a]] -> [[a]]
cp' []       = [[]]
cp' (xs:xss) = [ x:ys | x <- xs, ys <- cp' xss ]

-- Solution:
cp :: [[a]] -> [[a]]
cp = foldr f [[]]
    where f xs yss = [ x:ys | x <- xs, ys <- yss]

-- [7.2]

-- cols [[1,2],[3,4],[5,6]]
-- zipWith (:) [1,2] (zipWith (:) [3,4] [[5],[6]])

-- Solution Part 1:
cols :: [[a]] -> [[a]]
cols     []   = []
cols    [xs]  = [ [x] | x <- xs]
cols (xs:xss) = zipWith (:) xs (cols' xss)

-- Solution Part 2:

cols' :: [[a]] -> [[a]] 
cols' [] = [] 
cols' xss = foldr f (replicate n []) (map (take n) xss) 
 where n = minimum (map length xss) 
       f ys acc = [((ys!!i):(acc!!i))|i<-[0..(n-1)]]

-- spent a long time on this by myself and got some understanding,
-- worked with Joel, combining our ideas, to get a working function

-- question: why is pattern matching [] needed for a definition of a (function that uses) fold if the input type is [[a]]?


-- [8.1]

rjustify :: Int -> String -> String
rjustify n str = gap (n - (length str)) " " ++ str
    where gap 0 sp = []
          gap n sp = sp ++ (gap (n-1) sp)

ljustify :: Int -> String -> String
ljustify n str = str ++ gap (n - (length str)) " "
    where gap 0 sp = []
          gap n sp = sp ++ (gap (n-1) sp)

-- When the string is wider than the target length infinte spaces are printed.
-- We would want just the string to be returned
-- Improvement:

rjustify' :: Int -> String -> String
rjustify' n str = gap (n - (length str)) " " ++ str
    where gap n sp 
           | n <= 0 = []
           | otherwise = sp ++ (gap (n-1) sp)


ljustify' :: Int -> String -> String
ljustify' n str = str ++ gap (n - (length str)) " "
    where gap n sp
           | n <= 0 = []
           | otherwise = sp ++ (gap (n-1) sp)

-- [8.2]

type Matrix a = [[a]]

scale :: Num a => a -> Matrix a -> Matrix a
scale s xss = foldr f [] xss
    where f xs yss = ([ (x*s) | x <- xs ]) : yss

dot :: Num a => [a] -> [a] -> a
dot v1 v2 = sum (zipWith f v1 v2)
    where f r1 r2 = r1 * r2

add :: Num a => Matrix a -> Matrix a -> Matrix a
add m1 m2 = zipWith f m1 m2
    where f r1 r2 = zipWith (+) r1 r2

mul :: Num a => Matrix a -> Matrix a -> Matrix a
mul m1 m2 = map f m1
    where f r1 = map (\ r2 -> dot r1 r2) (cols m2)

table :: Show a => Matrix a -> String
-- too much to think about in too little time but some notes:
-- use rjustify
-- need to transpose using cols before this then cols it back later
-- 2 map functions used for lsits of lists.






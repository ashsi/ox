song :: Int -> String
song n
    | n > 10    = "Too many men went to mow"
    | n < 1     = ""
    | otherwise = song (n-1) ++ verse n ++ "\n\n"

verse :: Int -> String
verse n = line1 n ++ "\n" ++ line ++ "\n" ++ line3 n ++ "\n" ++ line

line :: String
line = "Went to mow a meadow"

line1 :: Int -> String
line1 1 = "One man went to mow"
line1 n = numUpper n ++ " men went to mow"

line3 :: Int -> String
line3 1 = "One man and his dog"
line3 n = numUpper n ++ " men, " ++ line3a (n-1)

line3a :: Int -> String
line3a 1 = "one man and his dog"
line3a n = numLower n ++ " men, " ++ line3a (n-1)

numUpper :: Int -> String
numUpper 2 = "Two"
numUpper 3 = "Three"
numUpper 4 = "Four"
numUpper 5 = "Five"
numUpper 6 = "Six"
numUpper 7 = "Seven"
numUpper 8 = "Eight"
numUpper 9 = "Nine"
numUpper 10 = "Ten"

numLower :: Int -> String
numLower 2 = "two"
numLower 3 = "three"
numLower 4 = "four"
numLower 5 = "five"
numLower 6 = "six"
numLower 7 = "seven"
numLower 8 = "eight"
numLower 9 = "nine"
numLower 10 = "ten"
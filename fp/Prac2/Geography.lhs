> module Geography(Place, Direction(N,S,E,W), move, opposite, Wall, Size)
> where

> type Place = (Int, Int)

> data Direction = N | S | E | W 
>     deriving (Eq, Show)

> opposite :: Direction -> Direction
> opposite N = S
> opposite S = N
> opposite E = W
> opposite W = E

> move :: Direction -> Place -> Place
> move N (i,j) = (i,j+1)
> move S (i,j) = (i,j-1)
> move E (i,j) = (i+1,j)
> move W (i,j) = (i-1,j)

> type Wall = (Place, Direction)

> type Size = (Int, Int)
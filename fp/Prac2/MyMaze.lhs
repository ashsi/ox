> module MyMaze where

> import Geography

> data Maze = AMaze Size [Place] [Place] [Place] [Place]
>     deriving Show

-- N S E W

> makeMaze :: Size -> [Wall] -> Maze
> makeMaze (x,y) walls = 
>   let nBoundary = [ (i,y-1) | i <- [0..x-1]]
>       sBoundary = [ (i,0)   | i <- [0..x-1]]
>       eBoundary = [ (x-1,j) | j <- [0..y-1]]
>       wBoundary = [ (0,j)   | j <- [0..y-1]]
>       nWalls = n ++ nBoundary ++ map (reflect S) (s ++ sBoundary)
>       sWalls = s ++ sBoundary ++ map (reflect N) (n ++ nBoundary)
>       eWalls = e ++ eBoundary ++ map (reflect W) (w ++ wBoundary)
>       wWalls = w ++ wBoundary ++ map (reflect E) (e ++ eBoundary)
>       n = [ fst pd | pd <- walls, snd pd == N ]
>       s = [ fst pd | pd <- walls, snd pd == S ]
>       e = [ fst pd | pd <- walls, snd pd == E ]
>       w = [ fst pd | pd <- walls, snd pd == W ]
>  in AMaze (x,y) nWalls sWalls eWalls wWalls


> reflect :: Direction -> Place -> Place
> reflect d (i,j) = move d (i,j)

> hasWall :: Maze -> Place -> Direction -> Bool
> hasWall (AMaze _ n _ _ _) pos N = pos `elem` n
> hasWall (AMaze _ _ s _ _) pos S = pos `elem` s
> hasWall (AMaze _ _ _ e _) pos E = pos `elem` e
> hasWall (AMaze _ _ _ _ w) pos W = pos `elem` w

> sizeOf :: Maze -> Size
> sizeOf (AMaze size _ _ _ _) = size

[Q5]

*Main> solveMaze smallMaze (0,0) (3,2)
[E,N,E,S,E,N,N]
(0.02 secs, 126,688 bytes)
*Main> solveMaze largeMaze (0,0) (22,21)
[N,N,N,N,N,N,N,N,N,E,E,E,N,W,W,W,N,E,E,E,N,W,W,W,N,E,E,E,E,E,N,N,N,W,S,S,W,W,W,W,N,N,N,E,S,S,E,E,N,W,N,N,W,W,N,E,E,E,E,E,E,N,W,W,W,W,W,W,N,E,E,E,E,E,E,E,S,S,S,S,E,E,N,N,N,N,E,E,E,E,S,W,W,W,S,S,S,E,N,N,E,E,E,S,W,W,S,S,W,W,W,W,W,S,E,E,E,S,W,W,W,S,S,S,E,S,S,S,E,N,N,N,E,S,S,S,S,W,W,W,S,E,E,E,S,W,W,W,S,E,E,E,E,S,S,E,E,E,E,E,E,E,S,E,E,E,N,W,W,N,N,N,E,S,S,E,E,N,W,N,E,N,N,W,S,W,W,W,W,S,W,N,N,N,W,W,W,N,N,N,E,S,S,E,N,N,N,W,W,N,N,N,N,N,E,S,S,S,S,E,E,E,E,E,E,E,S,W,W,W,W,W,S,E,E,E,E,E,E,N,N,N,W,W,W,W,N,E,E,N,W,W,N,E,E,N,W,W,W,N,N,N,E,S,S,E,N,N,E,E,E]
(0.04 secs, 8,235,560 bytes)
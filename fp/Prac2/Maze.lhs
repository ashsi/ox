Module to define the type of a maze

> module Maze (
>   Maze, 
>   makeMaze, -- :: Size -> [Wall] -> Maze
>   hasWall,  -- :: Maze -> Place -> Direction -> Bool
>   sizeOf    -- :: Maze -> Size
> )
> where

> import Geography

--We will represent a maze by its size and a list of its walls.

> data Maze = Maze Size [Wall]
>    deriving Show

--The list of walls will be complete in the sense that we record
--both sides of the wall; for example, if the list includes 
--((3,4), N), then it will also include ((3,5),S).

--This function creates a maze given its size and a list of walls; 
--the list of walls might not be complete in the above sense.

> makeMaze :: Size -> [Wall] -> Maze
> makeMaze (x,y) walls = 
>   let boundaries = -- the four boundaries
>         [((0,j),   W) | j <- [0..y-1]] ++ -- westerly boundary
>         [((x-1,j), E) | j <- [0..y-1]] ++ -- easterly boundary
>         [((i,0),   S) | i <- [0..x-1]] ++ -- southerly boundary
>         [((i,y-1), N) | i <- [0..x-1]]    -- northerly boundary
>       allWalls = walls ++ boundaries ++ map reflect (walls ++ boundaries)
>  in Maze (x,y) allWalls

-- The following function "reflects" a wall; i.e. gives the representation as
-- seen from the other side; for example, reflect ((3,4), N) = ((3,5),S)

-- Q1.5 Making a maze:
*Main> makeMaze (3,2) [((1,0),N), ((1,2),S), ((2,2),W)]
Maze (3,2) [((1,0),N),((1,2),S),((2,2),W),((0,0),W),((0,1),W),((2,0),E),((2,1),E),((0,0),S),((1,0),S),((2,0),S),((0,1),N),((1,1),N),((2,1),N),((1,1),S),((1,1),N),((1,2),E),((-1,0),E),((-1,1),E),((3,0),W),((3,1),W),((0,-1),N),((1,-1),N),((2,-1),N),((0,2),S),((1,2),S),((2,2),S)]


> reflect :: Wall -> Wall
> reflect ((i,j), d) = (move d (i,j), opposite d)

-- The following function tests whether the maze includes a wall in a particular
-- direction from a particular place:

> hasWall :: Maze -> Place -> Direction -> Bool
> hasWall (Maze _ walls) pos d = (pos,d) `elem` walls

-- The following function returns the size of a maze:

> sizeOf :: Maze -> Size
> sizeOf (Maze size _) = size
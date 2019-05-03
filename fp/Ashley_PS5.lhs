- [9.1] -

> data Nat = Zero | Succ Nat

> int :: Nat -> Int
> int Zero      = 0
> int (Succ n)  = 1 + (int n)

> nat :: Int -> Nat
> nat 0 = Zero
> nat n = Succ (nat (n-1))

> add, mul, pow, tet :: Nat -> Nat -> Nat

> add n Zero       = n
> add Zero n       = n
> add (Succ n) m   = add n (Succ m)

> mul _ Zero            = Zero
> mul Zero _            = Zero
> mul m (Succ Zero)     = m
> mul (Succ Zero) m     = m
> mul m (Succ n)        = add m (mul m n)

> pow _ Zero            = (Succ Zero)
> pow Zero _            = Zero
> pow m (Succ Zero)     = m
> pow (Succ Zero) m     = m
> pow m (Succ n)        = mul m (pow m n)

> tet _ Zero            = Zero
> tet Zero _            = Zero
> tet n (Succ Zero)     = pow n n
> tet (Succ Zero) n     = Succ Zero
> tet m (Succ n)        = pow m (tet m n)

> showNat :: Nat -> String
> showNat Zero        = "Zero"
> showNat (Succ Zero) = "(Succ Zero)"
> showNat (Succ n)    = "(Succ " ++ showNat n ++ ")"


- [9.2] -

foldNat is recursive and strict

> foldNat :: (Nat -> a -> a) -> a -> Nat -> a
> foldNat cons nil Zero     = nil
> foldNat cons nil (Succ n) = cons (Succ n) (foldNat cons nil n)

Deconstructors for Nat are int (the discriminator) and nat.

unfoldNat is recursive and strict on the first 3 arguments

> unfoldNat :: (a -> Bool) -> (a -> Nat -> Nat) -> (a -> a) -> a -> Nat
> unfoldNat n h t x
>     | n x       = Zero
>     | otherwise = h x (unfoldNat n h t (t x))

 nat' :: Int -> Nat
 nat' n = unfoldNat ()

> int' :: Nat -> Int
> int' n = foldNat f 0 n
>     where f x acc = (+) 1 acc


- [10.1] -

Finite Base:

fold c n ([] ++ ys)
= {definition of ++}
fold c n ys

fold c (fold c n ys) []
= {definition of fold: fold c n [] = n}
fold c n ys

LHS == RHS. Therefore true for P([]).

Assume:
fold c n (xs ++ ys) = fold c (fold c n ys) xs

Inductive step:

fold c n ((x:xs) ++ ys)
= {definition of ++}
fold c n (x:(xs++ys))
= {definition of fold}
c x (fold c n (xs++ys))
= {assumption}
c x (fold c (fold c n ys) xs)
= {definition of fold}
fold c (fold c n ys) (x:xs)

Therefore true for all finite lists xs and ys.

Partial Base:

fold c n (⊥ ++ ys)
= {strictness of ++ due to pattern matching on first argument}
fold c n ⊥
= {strictness of fold due to pattern matching on third argument}
⊥

fold c (fold c n ys) ⊥
= {strictness of fold}
⊥

Therefore true for P(⊥).

True for P(x:xs) assuming (xs).
Therefore true for all partial lists.

LHS is Haskell code, RHS is Haskell code.
Therefore eqn is chain complete.
Therefore true for infinite lists.


- [10.2] -

f . fold g a = fold h b

(++) bs ⊥
= {definition of ++}
⊥

So f ⊥ = ⊥

(++) bs []
= {definition of (++)}
bs




- [10.4] -

> data Liste a = Snoc (Liste a) a | Lin

> cat :: Liste a -> Liste a -> Liste a
> cat Lin ys        = ys
> cat ys Lin        = ys
> cat ys (Snoc a b) = Snoc (cat ys a) b

> folde :: (b -> a -> a) -> a -> Liste b -> a
> folde c n Lin        = n
> folde c n (Snoc a b) = c b (folde c n a)

 catf :: Liste a -> Liste a -> Liste a

 catf xs ys = folde f xs ys
     where f acc y = Snoc (acc) y

> list :: Liste a -> [a]
> list = folde (:) []

> liste :: [a] -> Liste a
> liste = foldl Snoc Lin


- [10.5] -

> unfolde :: (t -> Bool) -> (t -> Liste a) -> (t -> t) -> t -> Liste a
> unfolde n h t x
>     | n x = Lin
>     | otherwise = cat (h x) (unfolde n h t (t x))


module Practica1 where

    fact :: Int -> Int
    fact n = if n == 0 then 1 else n * fact (n - 1)

    prodEntre :: Int -> Int -> Int
    prodEntre m n = if m > n then 1 else m * prodEntre (m + 1) n

    antes :: Int -> [Int] -> [Int]
    antes x [] = []
    antes x (y:ys) = if x == y then [] else y : antes x ys

    desde :: Int -> [Int] -> [Int]
    desde e [] = []
    desde e (x:xs) = if e == x then x : xs else desde e xs
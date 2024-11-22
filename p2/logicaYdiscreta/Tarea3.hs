{-#LANGUAGE GADTs, EmptyDataDecls, EmptyCase, NPlusKPatterns #-}
module Tarea3 where 

import Prelude

-- Agustin Ferres 323408


type Nombre = String

data FS = A Nombre | Dir Nombre [FS]
 deriving Eq

instance Show FS where
    show fs = prettyPrintFSHelper 0 False fs

prettyPrintFSHelper :: Int -> Bool -> FS -> String
prettyPrintFSHelper indent isLast (A nombre) =
    replicate (indent * 2) ' ' ++ (if isLast then "\\-" else "|-") ++ " " ++ nombre ++ "\n"
prettyPrintFSHelper indent isLast (Dir nombre contenido) =
    replicate (indent * 2) ' ' ++ (if isLast then "\\-" else "|-") ++ " " ++ nombre ++ "\n" ++
    concatMap (\(fs, i) -> prettyPrintFSHelper (indent + 1) (i == length contenido - 1) fs) (zip contenido [0..])

-- aux

-- to handle empty lists with maximum
safeMaximum :: (Ord a, Num a) => [a] -> a
safeMaximum [] = 0
safeMaximum xs = maximum xs

-- get FS content, if it is a directory get the files inside, if it is a file return the file
getFS :: FS -> [FS]
getFS fs = case fs of
    A _ -> [fs]
    Dir _ f -> f

-- sort FS content by name, descending order
sortFS :: [FS] -> [FS]
sortFS [] = []
sortFS (f:fs) = sortFS (filter (\fs' -> nombre fs' < nombre f) fs) ++ [f] ++ sortFS (filter (\fs' -> nombre fs' >= nombre f) fs)

----
-- 1
djazz :: FS 
djazz = Dir "jazz" [A "mumbles.mp3"]

drock :: FS 
drock = Dir "rock" [ A "clones.mp3", A "bajan.mp3", A "clara.mp3"]

dmusica :: FS
dmusica = Dir "musica" [djazz, drock, A "clara.mp3"]

-- Completar el resto de los componentes del FS:

dort :: FS
dort = Dir "ort" [dobls, A "notas.txt"]

dobls :: FS
dobls = Dir "obls" [A "p2.txt", A "p2.jar", A "fc.hs"]

dsys :: FS
dsys = Dir "sys" [A "sys.txt", Dir "sys" []]

draiz :: FS
draiz = Dir "raiz" [dmusica, A "notas.txt", dort, dsys]

----
-- 2
nombre :: FS -> Nombre
nombre fs = case fs of
    A n -> n
    Dir n _ -> n
----
-- 3
contenido :: FS -> [Nombre]
contenido fs = case fs of
    A _ -> error "no es un directorio"
    Dir _ f -> map nombre f
----
-- 4
cantA :: FS -> Int 
cantA fs = case fs of
    A _ -> 1
    Dir _ f -> sum $ map cantA f
----
-- 5
nivelesD :: FS -> Int 
nivelesD fs = case fs of
    A _ -> 0
    Dir _ f -> 1 + safeMaximum (map nivelesD f)
----
-- 6
pertenece :: Nombre -> FS -> Bool 
pertenece n fs = case fs of
    A n' -> n == n'
    Dir _ f -> any (\fs' -> pertenece n fs') f
    -- any :: (a -> Bool) -> [a] -> Bool, checks if any element of the list satisfies the predicate
    -- (\fs -> pertenece n fs) lambda function as predicate
----
-- 7
cambiarNom :: Nombre -> Nombre -> FS -> FS 
cambiarNom n n' fs = case fs of
    A n'' -> if n == n'' then A n' else A n''
    Dir n'' f -> Dir (if n == n'' then n' else n'') (map (\fs' -> cambiarNom n n' fs') f)
----
-- 8
unirFS :: Nombre -> FS -> FS -> FS
unirFS n fs1 fs2 = Dir n (getFS fs1 ++ getFS fs2)
----
-- 9
ordenar :: FS -> FS
ordenar fs = case fs of
    A _ -> fs
    Dir n f -> Dir n (map ordenar (sortFS f))

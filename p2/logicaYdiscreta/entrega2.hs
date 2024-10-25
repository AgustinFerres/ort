-- Agustín Ferrés / 323408


module Entrega2 where

    type N = Integer

    -- Ejercicio 1

    suma_entre :: N -> N -> N 
    suma_entre m n = if m > n then 0 else m + suma_entre (m + 1) n

    {--
        1. La suma entre 2 y 5 es 14. (2 + 3 + 4 + 5 = 14) 
        2. La función está bien fundada porque siempre existe un caso base alcanzable (m > n). En cada llamada recursiva, m aumenta en 1, por lo que eventualmente m > n.
           El "tamaño" del problema que decrece es la diferencia n - m, que se reduce en 1 en cada llamada recursiva.
    --}

    suma_entre' :: N -> N -> N
    suma_entre' m n = if n > m then 0 else n + suma_entre (n + 1) m

    suma_entre_f :: (N -> N) -> N -> N -> N
    suma_entre_f f m n = if m > n then 0 else f m + suma_entre_f f (m + 1) n

    suma_i :: N -> N
    suma_i n = suma_entre_f id 0 n  

    -- Ejercicio 2

    es_divisor :: N -> N -> Bool
    es_divisor n k = n `mod` k == 0

    primer_divisor :: N -> N
    primer_divisor n = head [k | k <- [2..n], es_divisor n k]

    {--
        head: Devuelve el primer elemento de una lista.
        [k | k <- [2..n], es_divisor n k]: lista por comprensión, su formato es [expresion | patron <- lista, condicion]
        [2..n]: lista de enteros desde 2 hasta n

        En conjunto, retorna el primer elemento de una lista de divisores de n, la cual se genera filtrando los enteros desde 2 hasta n, y seleccionando aquellos que son divisores de n.
    --}

    es_primo :: N -> Bool
    es_primo n = n == primer_divisor n

    -- Ejercicio 3

    minimo_acotado :: (N -> Bool) -> N -> N -> N
    minimo_acotado p m n = if m > n then m else if p m then m else minimo_acotado p (m + 1) n

    maximo_acotado :: (N -> Bool) -> N -> N -> N
    maximo_acotado p m n = if m > n then m else if p n then n else maximo_acotado p m (n - 1)

    primer_divisor' :: N -> N
    primer_divisor' n = minimo_acotado (es_divisor n) 2 n

    minimo_p :: (N -> Bool) -> N -> N
    minimo_p p n = if p n then n else minimo_p p (n + 1)

    {--
        1. En caso de que ningún número entre m y n cumpla con la condición p, la función retorna n + 1.
        4. La ejecucion solo terminara en el caso de que exista un número natural que cumpla con la condición p.
           Si no existe, la función se ejecutará indefinidamente.
    --}

    -- Ejercicio 4

    cantidad_p :: (N -> Bool) -> N -> N -> N
    cantidad_p p m n = if m > n then 0 else if p m then 1 + cantidad_p p (m + 1) n else cantidad_p p (m + 1) n

    suma_p :: (N -> Bool) -> N -> N -> N
    suma_p p m n = if m > n then 0 else sum [k | k <- [m..n], p k]

    suma2_p :: (N -> Bool) -> N -> N -> N
    suma2_p p m n = if m > n then 0 else sum [k*k | k <- [m..n], p k]

    sumaf_p :: (N -> Bool) -> (N -> N) -> N -> N -> N
    sumaf_p p f m n = if m > n then 0 else sum [f k | k <- [m..n], p k]

    todos_p :: (N -> Bool) -> N -> N -> Bool
    todos_p p m n = all p [m..n]

    existe_p :: (N -> Bool) -> N -> N -> Bool
    existe_p p m n = any p [m..n]


{--

    sum: Función de orden superior que recibe una lista de números y retorna la suma de todos los elementos de la lista.
         sum :: Num a => [a] -> a
         sum [] = 0
         sum (x:xs) = x + sum xs

    all: Función de orden superior que recibe una lista de booleanos y retorna True si todos los elementos de la lista son True.
         all :: (a -> Bool) -> [a] -> Bool
         all p [] = True
         all p (x:xs) = p x && all p xs

    any: Función de orden superior que recibe una lista de booleanos y retorna True si al menos un elemento de la lista es True.
         any :: (a -> Bool) -> [a] -> Bool
         any p [] = False
         any p (x:xs) = p x || any p xs
--}
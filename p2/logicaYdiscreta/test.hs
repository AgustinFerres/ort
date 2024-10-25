module Bool where 

import Prelude (Show)

data Bool = True | False deriving (Show)

id :: Bool -> Bool
id = \b -> b

not :: Bool -> Bool
not = \b -> case b of {
    True -> False;
    False -> True
}
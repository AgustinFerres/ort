module Bool where 

import Prelude (Show, error)

data Bool = True | False deriving (Show)

undefined :: Bool
undefined = error "Undefined"

id :: Bool -> Bool
id = \b -> b

not :: Bool -> Bool
not = \b -> case b of {
    True -> False;
    False -> True
}

-- Conectivas binarias

top :: Bool -> Bool -> Bool
top p q = True

or :: Bool -> Bool -> Bool
or p q = case p of {
    True -> True;
    False -> case q of {
        True -> True;
        False -> False
    }
}

reci :: Bool -> Bool -> Bool
reci p q = case q of {
    True -> case p of {
        True -> True;
        False -> False
    };
    False -> True
}
    
idp :: Bool -> Bool -> Bool
idp p q = p

imp :: Bool -> Bool -> Bool
imp p q = case p of {
    True -> case q of {
        True -> True;
        False -> False
    };
    False -> True
}

idq :: Bool -> Bool -> Bool
idq p q = q

sii :: Bool -> Bool -> Bool
sii p q = case p of {
    True -> case q of {
        True -> True;
        False -> False
    };
    False -> case q of {
        True -> False;
        False -> True
    }
}

and :: Bool -> Bool -> Bool
and p q = case p of {
    True -> case q of {
        True -> True;
        False -> False
    };
    False -> False
}

nand :: Bool -> Bool -> Bool
nand p q = case p of {
    True -> case q of {
        True -> False;
        False -> True
    };
    False -> True
}

xor :: Bool -> Bool -> Bool
xor p q = case p of {
    True -> case q of {
        True -> False;
        False -> True
    };
    False -> case q of {
        True -> True;
        False -> False
    }
}

nq :: Bool -> Bool -> Bool
nq p q = case q of {
    True -> False;
    False -> True
}

nimp :: Bool -> Bool -> Bool
nimp p q = case p of {
    True -> case q of {
        True -> False;
        False -> True
    };
    False -> False
}

np :: Bool -> Bool -> Bool
np p q = case p of {
    True -> False;
    False -> True
}

nreci :: Bool -> Bool -> Bool
nreci p q = case q of {
    True -> case p of {
        True -> False;
        False -> True
    };
    False -> False
}

nor :: Bool -> Bool -> Bool
nor p q = case p of {
    True -> False;
    False -> case q of {
        True -> False;
        False -> True
    }
}

bottom :: Bool -> Bool -> Bool
bottom p q = False

-- Conectivas ternarias

unan :: Bool -> Bool -> Bool -> Bool
unan p q r = case p of {
    True -> and q r;
    False -> case q of {
        True -> False;
        False -> sii q r
    }
}

conec :: Bool -> Bool -> Bool -> Bool
conec p q r = case sii p q of {
    True -> p;
    False -> not r
}

fun :: Bool -> Bool -> Bool -> Bool
fun p q r = case p of {
    True -> case q of {
        True -> r;
        False -> True
    };
    False -> case r of {
        True -> False;
        False -> True
    }
}

unoSolo :: Bool -> Bool -> Bool -> Bool
unoSolo p q r = case p of {
    True -> and (and p (not q)) (and p (not r));
    False -> case q of {
        True -> and q (not r);
        False -> r
    }
}
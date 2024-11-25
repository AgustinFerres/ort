package obligatorio2p2.service;

import obligatorio2p2.model.Editorial;

import java.util.Set;


/**
 * @author Agustin Ferres - n° 323408
 */
public class EditorialService extends Service {

    public EditorialService () {

        super();
    }

    public void registerEditorial (
        String name,
        String country
    ) throws IllegalArgumentException {

        if ( database.getEditorial(name) != null ) {
            throw new IllegalArgumentException("Ya existe una editorial con ese nombre");
        }

        database.saveEditorial(new Editorial(name, country));
    }

    public Editorial getEditorial ( String name ) {

        return database.getEditorial(name);
    }

    public Set<Editorial> getEditorials () {

        return database.getEditorials();
    }
}

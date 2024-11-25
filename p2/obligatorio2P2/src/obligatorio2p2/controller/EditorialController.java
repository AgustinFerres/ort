package obligatorio2p2.controller;

import obligatorio2p2.model.Editorial;
import obligatorio2p2.service.EditorialService;

import java.util.Set;


/**
 * @author Agustin Ferres - n° 323408
 */
public class EditorialController {

    private final EditorialService editorialService;

    public EditorialController () {

        editorialService = new EditorialService();
    }

    public void registerEditorial (
        String name,
        String country
    ) throws IllegalArgumentException {

        editorialService.registerEditorial(name, country);
    }

    public Editorial getEditorial ( String name ) {

        return editorialService.getEditorial(name);
    }

    public Set<Editorial> getEditorials () {

        return editorialService.getEditorials();
    }
}

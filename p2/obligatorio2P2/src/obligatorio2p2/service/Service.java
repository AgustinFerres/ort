package obligatorio2p2.service;

import obligatorio2p2.model.Database;


/**
 * @author Agustin Ferres - n° 323408
 */
public abstract class Service {

    protected final Database database;

    public Service () {

        this.database = Database.getInstance();
    }

}

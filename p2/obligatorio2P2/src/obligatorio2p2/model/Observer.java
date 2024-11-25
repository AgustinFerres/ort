package obligatorio2p2.model;

import obligatorio2p2.types.ObserverType;

import java.util.List;


/**
 * @author Agustin Ferres - n° 323408
 */
public interface Observer {

    void update ();

    List<ObserverType> getOTypes ();
}

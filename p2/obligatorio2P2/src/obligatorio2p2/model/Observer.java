package obligatorio2p2.model;

import obligatorio2p2.types.ObserverType;

import java.util.List;


public interface Observer {

    void update ();

    List<ObserverType> getOTypes ();
}

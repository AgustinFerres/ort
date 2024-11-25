package obligatorio2p2.model;

import obligatorio2p2.types.ObserverType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author Agustin Ferres - n° 323408
 */
public abstract class Subject {

    private transient final Map<ObserverType, Set<Observer>> observers = new HashMap<>();

    public void addObserver ( Observer observer ) {

        observer.getOTypes().forEach(observerType -> {
            // If the key is present, add the observer to the list
            observers.computeIfPresent(observerType, ( k, v ) -> {
                v.add(observer);
                return v;
            });

            // Else, create a new list with the observer
            observers.putIfAbsent(observerType, new HashSet<>(Set.of(observer)));
        });
    }

    public void removeObserver ( Observer observer ) {

        observer.getOTypes().forEach(observerType -> {
            // If the key is present, remove the observer from the list
            observers.computeIfPresent(observerType, ( k, v ) -> {
                v.remove(observer);
                return v;
            });

            // If the list is empty, remove the key
            if ( observers.get(observerType) != null && observers.get(observerType).isEmpty() ) {
                observers.remove(observerType);
            }
        });
    }

    protected void notifyObservers ( ObserverType observerType ) {

        Set<Observer> observer = observers.get(observerType);
        if ( observer != null ) {
            observer.forEach(Observer::update);
        }
    }

}

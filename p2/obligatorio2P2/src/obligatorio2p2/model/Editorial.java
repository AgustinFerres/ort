package obligatorio2p2.model;

import java.io.Serializable;


/**
 * @author Agustin Ferres - n° 323408
 */
public class Editorial implements Serializable {
    private String name;
    private String country;

    public Editorial (
        String name,
        String country
    ) {

        this.name = name;
        this.country = country;
    }

    public String getName () {

        return name;
    }

    public String getCountry () {

        return country;
    }

    @Override
    public String toString () {

        return name;
    }
}

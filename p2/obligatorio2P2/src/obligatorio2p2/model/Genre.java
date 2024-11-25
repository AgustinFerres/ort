package obligatorio2p2.model;

import java.io.Serializable;


/**
 * @author Agustin Ferres - n° 323408
 */
public class Genre implements Serializable {

    private String name;
    private String description;

    public Genre (
        String name,
        String description
    ) {

        this.name = name;
        this.description = description;
    }

    public String getName () {

        return name;
    }

    public String getDescription () {

        return description;
    }

    @Override
    public String toString () {

        return name;
    }
}

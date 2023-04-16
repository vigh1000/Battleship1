package battleship;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    protected String name;
    protected int size;
    protected boolean alive = true;
    protected List<Field> occupiedFields = new ArrayList<>();

    public Ship(String name, int size) {
        this.size = size;
        this.name = name;
    }

    public void addShipCoordinate(Field shipField) {
        occupiedFields.add(shipField);
    }

    public void removeShipCoordinate(Field shipField) { occupiedFields.remove(occupiedFields.indexOf(shipField)); }

    public List<Field> getOccupiedFields() {
        return occupiedFields;
    }

    public boolean isAlive() {
        return alive;
    }
}

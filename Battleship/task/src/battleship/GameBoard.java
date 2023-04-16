package battleship;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private String[][] gameBoard = {
            {" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
            {"A", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
            {"B", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
            {"C", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
            {"D", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
            {"E", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
            {"F", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
            {"G", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
            {"H", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
            {"I", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
            {"J", "~", "~", "~", "~", "~", "~", "~", "~", "~", "~",},
    };
    private List<Field> shipFields = new ArrayList<>();
    private List<Field> initialShipFields = new ArrayList<>();
    private List<Ship> ships = new ArrayList<>();
    private final String playerName;


    private List<Field> getShipFields() {
        return shipFields;
    }
    private void setShipFields() {
        this.shipFields = getAllShipCoordinates();
    }
    public boolean areShipFieldsLeft() {
        return !shipFields.isEmpty();
    }

    public GameBoard(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public boolean positionShip(String name, int size, String coordinates) {
        Field coordinates1 = new Field();
        Field coordinates2 = new Field();

        String[] splittedCoordinates = coordinates.split(" ");
        coordinates1.setFieldCoordinates(getCoordinatesFromInput(splittedCoordinates[0]));
        coordinates2.setFieldCoordinates(getCoordinatesFromInput(splittedCoordinates[1]));

        if (!checkIfCoordinatesMatchToShipType(size, coordinates1, coordinates2)) {
            System.out.println(String.format("Error! Wrong length of the %s! Try again:", name));
            return false;
        }

        if (!isPlacementStraight(coordinates1, coordinates2)) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }

        if ((isShipNearby(coordinates1) || isShipNearby(coordinates2))) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return false;
        }

        Ship ship = new Ship(name, size);

        if (coordinates1.getRow() == coordinates2.getRow()) {
            // Row is the same, so place ship in row
            if (coordinates1.getColumn() < coordinates2.getColumn()) {
                for (int i = coordinates1.getColumn(); i <= coordinates2.getColumn(); i++) {
                    gameBoard[coordinates1.getRow()][i] = "O";
                    ship.addShipCoordinate(new Field(coordinates1.getRow(), i));
                }
            } else {
                for (int i = coordinates2.getColumn(); i <= coordinates1.getColumn(); i++) {
                    gameBoard[coordinates1.getRow()][i] = "O";
                    ship.addShipCoordinate(new Field(coordinates1.getRow(), i));
                }
            }
        }
        if (coordinates1.getColumn() == coordinates2.getColumn()) {
            // Column is the same, so place ship in column
            if (coordinates1.getRow() < coordinates2.getRow()) {
                for (int i = coordinates1.getRow(); i <= coordinates2.getRow(); i++) {
                    gameBoard[i][coordinates1.getColumn()] = "O";
                    ship.addShipCoordinate(new Field(i, coordinates1.getColumn()));
                }
            } else {
                for (int i = coordinates2.getRow(); i <= coordinates1.getRow(); i++) {
                    gameBoard[i][coordinates1.getColumn()] = "O";
                    ship.addShipCoordinate(new Field(i, coordinates1.getColumn()));
                }
            }
        }
        ships.add(ship);
        setShipFields();
        initialShipFields.addAll(getShipFields());
        return true;
    }

    private boolean isShipNearby(Field coordinates) {
        List<Field> shipCoordinates = new ArrayList<>();
        shipCoordinates.addAll(getAllShipCoordinates());

        for (int i = coordinates.getRow() - 1; i <= coordinates.getRow() + 1; i++) {
            if (shipCoordinates.contains(new Field(i,coordinates.getColumn()))) {
                return true;
            }
        }

        for (int i = coordinates.getColumn() - 1; i <= coordinates.getColumn() + 1; i++) {
            if (shipCoordinates.contains(new Field(coordinates.getRow(), i))) {
                return true;
            }
        }

        return false;
    }

    private List<Field> getAllShipCoordinates() {
        List<Field> shipCoordinates = new ArrayList<>();

        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                if ("O".matches(gameBoard[i][j])) shipCoordinates.add(new Field(i,j));
            }
        }

        return shipCoordinates;
    }

    private boolean checkIfCoordinatesMatchToShipType(int size, Field coordinates1, Field coordinates2) {
        if (!(getLengthOfCoordinates(coordinates1, coordinates2) == size)) {
            return false;
        }
        return true;
    }

    private int getLengthOfCoordinates(Field coordinates1, Field coordinates2) {
        int size = 0;
        if (coordinates1.getRow() == coordinates2.getRow()) {
            size = Math.abs(coordinates1.getColumn() - coordinates2.getColumn()) + 1;
        }

        if (coordinates1.getColumn() == coordinates2.getColumn()) {
            size = Math.abs(coordinates1.getRow() - coordinates2.getRow()) + 1;
        }
        return size;
    }

    private boolean isPlacementStraight(Field coordinates1, Field coordinates2) {
        if (!(coordinates1.getRow() == coordinates2.getRow() || coordinates1.getColumn() == coordinates2.getColumn())) {
            return false;
        }
        return true;
    }

    private Field getCoordinatesFromInput(String inputCoordinates) {
        // 65 = A => -64
        // 49 = 1 => -48
        int row = inputCoordinates.charAt(0) - 64;
        int column = inputCoordinates.charAt(1) - 48;

        if (inputCoordinates.length() > 2) {
            column = Integer.parseInt(inputCoordinates.substring(1, inputCoordinates.length()));
        }

        Field field = new Field(row, column);
        return field;
    }

    public String takeShot(String inputCoordinates) {
        Field inputField = getCoordinatesFromInput(inputCoordinates);

        if (initialShipFields.contains(inputField)) {
            gameBoard[inputField.getRow()][inputField.getColumn()] = "X";
            if (shipFields.contains(inputField)) {
                shipFields.remove(shipFields.indexOf(inputField));
                for (Ship ship : this.ships) {
                    if (ship.getOccupiedFields().contains(inputField)) {
                        ship.removeShipCoordinate(inputField);
                        break;
                    }
                }
            }
            if (wasShipSunk()) {
                return "You sank a ship!";
            }
            return "You hit a ship!";
        } else {
            gameBoard[inputField.getRow()][inputField.getColumn()] = "M";
            return "You missed!";
        }
    }

    public boolean isInputCorrect(String inputCoordinates) {
        if (inputCoordinates.isEmpty()) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }

        Field inputField = getCoordinatesFromInput(inputCoordinates);

        if (inputField.getRow() > 10 || inputField.getColumn() > 10) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }

        return true;
    }

    public boolean wasShipSunk() {
        List<Ship> shipsToCheck = this.ships;
        for (Ship ship : shipsToCheck) {
            if (ship.getOccupiedFields().isEmpty()) {
                this.ships.remove(this.ships.indexOf(ship));
                return true;
            }
        }

        return false;
    }


    public void hideShips() {
        for (Field shipField : shipFields) {
            gameBoard[shipField.getRow()][shipField.getColumn()] = "~";
        }
    }

    public void showShips() {
        for (Field shipField : shipFields) {
            gameBoard[shipField.getRow()][shipField.getColumn()] = "O";
        }
    }

    public void printGameBoard() {
        for (int row = 0; row <= 10; row++) {
            for (int column = 0;  column <= 10; column++ ) {
                //System.out.print(gameBoard[row][column] + " ");
                System.out.print(gameBoard[row][column] + " ");
            }
            System.out.print("\n");
        }
    }
}
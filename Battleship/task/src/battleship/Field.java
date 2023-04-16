package battleship;

public class Field {
    private int row;
    private int column;
    private Character state;

    public Field(int row, int column) {
        this.row = row;
        this.column = column;
        this.state = '~';
    }

    public Field() {
    }

    public void setFieldCoordinates(Field field) {
        this.row = field.getRow();
        this.column = field.getColumn();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getState() {
        return state;
    }

    public void setState(Character state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Field) {
            Field field = (Field) o;
            if (this.row == field.getRow() && this.column == field.getColumn()) return true;
        }
        return false;
    }

     @Override
    public int hashCode() {
        Integer codeToHash = this.row + this.column;
        return codeToHash.hashCode();
     }

}

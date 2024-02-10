package model;

public class CornerCubelet extends Cubelet {
    public CornerCubelet(Position pos) {
        super(pos);
    }

    @Override
    public void scrambleColors() {
        String[] scramble = new String[] {getColorX(), getColorY(), getColorZ()};
        int shift = (int)(Math.random() * 3);

        setColorX(scramble[shift]);
        setColorY(scramble[(shift + 1) % 3]);
        setColorZ(scramble[(shift + 2) % 3]);
    }
}

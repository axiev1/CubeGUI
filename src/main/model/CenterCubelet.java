package model;

public class CenterCubelet extends Cubelet {

    public CenterCubelet(Position pos) {
        super(pos);
    }

    public CenterCubelet(CenterCubelet c) {
        super(c);
    }

    @Override
    public void scrambleColors() {
        return;
    }
}

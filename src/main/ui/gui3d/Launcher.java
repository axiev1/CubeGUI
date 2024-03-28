package ui.gui3d;

public class Launcher {
    private static WindowManager window;
    private static TestGame game;

    public static void main(String[] args) {
        window = new WindowManager("Rubik's Cube", 1600, 900, false);
        game = new TestGame();
        EngineManager engine = new EngineManager();
        try {
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static WindowManager getWindow() {
        return window;
    }

    public static TestGame getGame() {
        return game;
    }
}

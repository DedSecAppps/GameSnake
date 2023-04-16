import org.example.GameField;

import javax.swing.*;

/**
 * Created by infuntis on 15/01/17.
 */
public class Main extends JFrame {

    public Main(){
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320,345);
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        Main mw = new Main();
    }
}
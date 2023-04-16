 package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameField extends JPanel implements ActionListener, KeyListener {
    private final int SIZE = 300;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 250;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer = new Timer(250,this);
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = false;

    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(this);
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer.start();
        createApple();
        inGame = true;
    }

    public void createApple(){
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon imgApple = new ImageIcon("Green.png");
        apple = imgApple.getImage();
        ImageIcon imgIron = new ImageIcon("Red.png");
        dot = imgIron.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame){
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i], y[i], this);
            }
        } else {
            String message = "Game Over";
            Font font = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metrics = getFontMetrics(font);
            g.setColor(Color.RED);
            g.setFont(font);
            g.drawString(message, (SIZE - metrics.stringWidth(message)) / 2, SIZE / 2);

            String message2 = "Нажмите ENTER для продолжения!";
            FontMetrics metrics2 = getFontMetrics(font);
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(message2,30,190);
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left){
            x[0] -= DOT_SIZE;
        }
        if (right){
            x[0] += DOT_SIZE;
        }
        if (up){
            y[0] -= DOT_SIZE;
        }
        if (down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                timer.stop();
            }
        }

        if(x[0] >= SIZE){
            inGame = false;
        }
        if(x[0] < 0){
            inGame = false;
        }
        if(y[0] >= SIZE){
            inGame = false;
        }
        if(y[0] < 0){
            inGame = false;
        }
    }

    public void checkApple(){
        if (x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT && !right) {
            left = true;
            up = false;
            down = false;
        }
        if (key == KeyEvent.VK_RIGHT && !left) {
            right = true;
            up = false;
            down = false;
        }
        if (key == KeyEvent.VK_UP && !down) {
            up = true;
            left = false;
            right = false;
        }
        if (key == KeyEvent.VK_DOWN && !up) {
            down = true;
            left = false;
            right = false;
        }
        if (key == KeyEvent.VK_ENTER && !inGame) {

            initGame();
            timer.start();
            inGame = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}


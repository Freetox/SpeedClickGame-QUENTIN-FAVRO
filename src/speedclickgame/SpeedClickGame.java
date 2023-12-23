package speedclickgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SpeedClickGame extends JFrame {

    private JButton[][] buttons;
    private int gridSize = 4; // Change the grid size as needed
    private int score = 0;
    private int timeLeft = 10; // Initial time in seconds
    private Timer timer;

    public SpeedClickGame() {
        setTitle("Speed Click Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttons = new JButton[gridSize][gridSize];
        timer = new Timer(1000, new TimerActionListener());

        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(gridSize, gridSize));

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setEnabled(false);
                buttons[i][j].addActionListener(new ButtonClickListener());
                add(buttons[i][j]);
            }
        }

        startGame();

        setVisible(true);
    }

    private void startGame() {
        score = 0;
        timeLeft = 10;
        updateScoreLabel();

        timer.start();
        enableRandomButton();
    }

    private void enableRandomButton() {
        Random rand = new Random();
        int row = rand.nextInt(gridSize);
        int col = rand.nextInt(gridSize);

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                buttons[i][j].setEnabled(false);
            }
        }

        buttons[row][col].setEnabled(true);
    }

    private void updateScoreLabel() {
        setTitle("Speed Click Game - Score: " + score + " | Time left: " + timeLeft + "s");
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();

            if (clickedButton.isEnabled()) {
                score++;
                updateScoreLabel();
                enableRandomButton();
            } else {
                score--;
                updateScoreLabel();
            }
        }
    }

    private class TimerActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timeLeft--;

            if (timeLeft <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(SpeedClickGame.this, "Game Over! Your score is: " + score);
                startGame();
            } else {
                updateScoreLabel();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SpeedClickGame();
        });
    }
}


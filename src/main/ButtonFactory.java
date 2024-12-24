package src.main;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ButtonFactory {

    public static JButton createGameButton(String label, ActionListener action) {
        JButton button = new JButton(label);
        button.addActionListener(action);
        return button;
    }

    public static ActionListener takeback = e -> {
        System.out.println("Takeback almost implemented!");
        GamePanel.lastMove.undo();
    };

    public static ActionListener newGame = e -> { //BLUNDER, expcedted problem is castling implementation of undo
        if (GamePanel.moveList.size() > 1) {
            while (GamePanel.moveList.size() > 1) {
                GamePanel.lastMove.undo();
            }
            GamePanel.lastMove.undo();
        };
    };
}
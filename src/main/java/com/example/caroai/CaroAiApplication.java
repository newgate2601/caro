package com.example.caroai;

import com.example.caroai.game.Game;
import com.example.caroai.gui.Board;
import com.example.caroai.gui.MainGUI;
import com.example.caroai.gui.SynthesisGUI;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// spring betch
@SpringBootApplication
public class CaroAiApplication {
	public static void main(String[] args) {
		// Create the MainGUI instance.
		final int width = 760;
		final MainGUI gui = new MainGUI(width, width, "Caro");

		// Create a 19x19 game board.
		Board board = new Board(width, 19);
		// Create the Game manager instance.
		final Game game = new Game(board);
		// Attach the game board's GUI component to the main frame.
		gui.attachBoard(board.getGUI());
		// Make the frame wrap the contents and set it visible.
		gui.pack();
		gui.setVisible(true);

		gui.listenSynthesisButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SynthesisGUI(gui);
			}
		});

		// Ấn vào start
		gui.listenGameStartButton(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Get the settings from the Main GUI manager.
				Object[] settings = gui.fetchSettings();
				int depth = (Integer)(settings[0]);
				boolean computerStarts = (Boolean)(settings[1]);

				System.out.println("Depth: " + depth + " AI Makes the first move: " + computerStarts );
				// Make the game board visible to the user.
				gui.showBoard();
				// Apply the settings.
				game.setAIDepth(depth);
				game.setAIStarts(computerStarts);
				// Start the game.
				game.start();
			}
		});
	}
}

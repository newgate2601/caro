package com.example.caroai.gui;

import javax.swing.*;
import java.awt.event.ActionListener;


public class MainGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private int difficulty;
	private boolean computerStarts;
	
	private JPanel boardPanel;
	private final JPanel setupPanel;
	private final JPanel difficultyPanel = new JPanel();
	private final JPanel startingPlayerPanel = new JPanel();
	private final JPanel buttonPanel = new JPanel();
	
	// button in GUI
	private final JButton buttonStart = new JButton("Bắt đầu chơi");
	private final JButton buttonSynthesis = new JButton("Bảng xếp hạng người chơi");
	private final JRadioButton rbNormal = new JRadioButton("Trung bình");
	private final JRadioButton rbHard = new JRadioButton("Khó");
	private final JRadioButton rbHuman = new JRadioButton("Đi trước");
	private final JRadioButton rbComputer = new JRadioButton("Đi sau");
	private final JLabel taDifficulty = new JLabel("Độ khó: ");
	private final JLabel taStartingPlayer = new JLabel("Lượt đi: ");

	private final ButtonGroup bgDifficulty = new ButtonGroup();
	private final ButtonGroup bgStartingPlayer = new ButtonGroup();

	public MainGUI(int width, int height, String title) {
		setSize(width, height);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setupPanel = new JPanel();
		setupPanel.setLayout(new BoxLayout(setupPanel, BoxLayout.PAGE_AXIS));

		// thêm 2 button độ khó vào 1 nhóm
		bgDifficulty.add(rbNormal);
		bgDifficulty.add(rbHard);

		// thêm 2 button chọn người đi trước vào 1 nhóm
		bgStartingPlayer.add(rbHuman);
		bgStartingPlayer.add(rbComputer);

		// mặc định chọn cho 2 button này khi vào GUI
		rbNormal.setSelected(true);
		rbComputer.setSelected(true);

		// thêm Difficult, Normal, Hard vào 1 hàng
		difficultyPanel.add(taDifficulty);
		difficultyPanel.add(rbNormal);
		difficultyPanel.add(rbHard);

		// thêm Computer, Human, start first vào 1 hàng
		startingPlayerPanel.add(taStartingPlayer);
		startingPlayerPanel.add(rbComputer);
		startingPlayerPanel.add(rbHuman);

		buttonPanel.add(buttonStart);
		buttonPanel.add(buttonSynthesis);

		// thêm hàng Độ khó, Chọn lượt đi, button bắt đầu game vào giao diện
		setupPanel.add(difficultyPanel);
		setupPanel.add(startingPlayerPanel);
		setupPanel.add(buttonPanel);

		add(setupPanel);
		pack();
	}
	/*
	 * 	Reads components to fetch and return the chosen settings.
	 */
	public Object[] fetchSettings() {
		if( rbHard.isSelected() ) {
			difficulty = 4;
		} else difficulty = 3;
		
		computerStarts = rbComputer.isSelected();
		Object[] x = {difficulty, computerStarts};
		return x;
	}
	public void listenGameStartButton(ActionListener listener) {
		buttonStart.addActionListener(listener);
	}

	public void listenSynthesisButton(ActionListener listener){
		buttonSynthesis.addActionListener(listener);
	}

	public void attachBoard(JPanel board) {
		boardPanel = board;
	}
	public void showBoard() {
		setContentPane(boardPanel);
		invalidate();
		validate();
		pack();
	}
}

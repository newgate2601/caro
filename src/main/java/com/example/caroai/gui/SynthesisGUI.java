package com.example.caroai.gui;

import com.example.caroai.communicate.MSCommunicateHelper;
import com.example.caroai.dto.SynthesisDTO;
import com.example.caroai.dto.SynthesisDTOList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

public class SynthesisGUI extends JFrame {
    private JTable playerTable;
    private DefaultTableModel tableModel;
    // Thêm biến này để lưu trữ tham chiếu của giao diện trước đó
    private JFrame previousGUI;

    public SynthesisGUI(JFrame jFrame) {
        previousGUI = jFrame;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Player List");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo một bảng để hiển thị thông tin về người chơi
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Hạng");
        tableModel.addColumn("Tên Người Chơi");
        tableModel.addColumn("Số Trận Thắng");
        tableModel.addColumn("Số Trận Thua");

        playerTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(playerTable);
        add(scrollPane, BorderLayout.CENTER);

        // Thêm một số dữ liệu mẫu để hiển thị trong bảng
        List<SynthesisDTO> synthesisDTOs = MSCommunicateHelper.httpGetMethodExecutive(
                "http://localhost:8086/api/v1/synthesis/list",
                SynthesisDTOList.class
        ).getSynthesisDTOList();

        int i = 1;
        for (SynthesisDTO synthesisDTO : synthesisDTOs){
            addPlayer(i, synthesisDTO.getUserName(), synthesisDTO.getWinCount(), synthesisDTO.getLostCount());
            i++;
        }

        // Thêm nút "Trở lại"
        JButton backButton = new JButton("Trở lại");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goBackToPreviousGUI();
            }
        });
        add(backButton, BorderLayout.NORTH);

        setVisible(true);
    }

    // Phương thức để thêm một người chơi vào bảng
    private void addPlayer(Integer number, String name, int wins, int rank) {
        Vector<Object> row = new Vector<>();
        row.add(number);
        row.add(name);
        row.add(wins);
        row.add(rank);
        tableModel.addRow(row);
    }

    // Phương thức để thiết lập giao diện trước đó
    public void setPreviousGUI(JFrame previousGUI) {
        this.previousGUI = previousGUI;
    }

    // Phương thức để trở lại giao diện trước đó
    public void goBackToPreviousGUI() {
        setVisible(false); // Ẩn giao diện hiện tại
        if (previousGUI != null) {
            previousGUI.setVisible(true); // Hiển thị giao diện trước đó
        }
    }
}

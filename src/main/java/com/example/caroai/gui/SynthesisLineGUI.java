package com.example.caroai.gui;

import com.example.caroai.communicate.MSCommunicateHelper;
import com.example.caroai.dto.SynthesisDTO;
import com.example.caroai.dto.SynthesisDTOList;
import com.example.caroai.dto.SynthesisLineDTO;
import com.example.caroai.dto.SynthesisLineDTOList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SynthesisLineGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable detailTable;

    public SynthesisLineGUI(Long id) {
        initializeUI(id);
    }

    private void initializeUI(Long id) {
        setTitle("Chi Tiết Trận Đấu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table model and table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Số Thứ Tự");
        tableModel.addColumn("Trạng Thái Trận Đấu");
        tableModel.addColumn("Số Nước Đi");
        tableModel.addColumn("Số Nước Đi Của Đối Thủ");
        tableModel.addColumn("Level");
        tableModel.addColumn("Người Đi Trước");
        tableModel.addColumn("Thời Gian Bắt Đầu Chơi");

        detailTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(detailTable);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch synthesis data and populate the table
        List<SynthesisLineDTO> synthesisDTOs = MSCommunicateHelper.httpGetMethodExecutive(
                "http://localhost:8086/api/v1/synthesis/line/list?id=" + id,
                SynthesisLineDTOList.class
        ).getSynthesisLineDTOs();

        int i = 1;
        for (SynthesisLineDTO synthesisDTO : synthesisDTOs) {
            addSynthesisDetail(synthesisDTO, i);
        }

        setVisible(true);
    }

    private void addSynthesisDetail(SynthesisLineDTO synthesisDTO, int i) {
        Object[] rowData = {
                i,
                synthesisDTO.getIsWin() ? "Thắng" : "Thua",
                synthesisDTO.getYourNumberOfMoves(),
                synthesisDTO.getBotNumberMoves(),
                synthesisDTO.getLevel(),
                synthesisDTO.getYouFirst() ? "Người" : "Máy",
        };
        tableModel.addRow(rowData);
    }
}

package com.example.caroai.gui;

import com.example.caroai.communicate.MSCommunicateHelper;
import com.example.caroai.dto.SynthesisDTO;
import com.example.caroai.dto.SynthesisDTOList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class SynthesisGUI extends JFrame {
    private JTable playerTable;
    private DefaultTableModel tableModel;
    private JFrame previousGUI;
    private Map<Integer, Long> idMap; // Map to store id for each row

    public SynthesisGUI(JFrame jFrame) {
        previousGUI = jFrame;
        idMap = new HashMap<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Player List");
        setSize(500, 400); // Increased size to accommodate the new column
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Hạng");
        tableModel.addColumn("Tên Người Chơi");
        tableModel.addColumn("Số Trận Thắng");
        tableModel.addColumn("Số Trận Thua");
        tableModel.addColumn("Hành động"); // Empty column header for the button

        playerTable = new JTable(tableModel);

        // Set renderer for the button column
        playerTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        // Set editor for the button column
        playerTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(playerTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Trở lại");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                goBackToPreviousGUI();
            }
        });
        add(backButton, BorderLayout.NORTH);

        setVisible(true);

        fetchDataAndPopulateTable();
    }

    private void fetchDataAndPopulateTable() {
        List<SynthesisDTO> synthesisDTOs = MSCommunicateHelper.httpGetMethodExecutive(
                "http://localhost:8086/api/v1/synthesis/list",
                SynthesisDTOList.class
        ).getSynthesisDTOList();

        int i = 1;
        for (SynthesisDTO synthesisDTO : synthesisDTOs) {
            addPlayer(i, synthesisDTO.getUserName(), synthesisDTO.getWinCount(), synthesisDTO.getLostCount(), synthesisDTO.getId());
            i++;
        }
    }

    private void addPlayer(Integer number, String name, int wins, int rank, Long id) {
        Vector<Object> row = new Vector<>();
        row.add(number);
        row.add(name);
        row.add(wins);
        row.add(rank);
        row.add("Chi tiết"); // Button text
        tableModel.addRow(row);
        // Store id with the table row index
        idMap.put(playerTable.getRowCount() - 1, id);
        System.out.println(playerTable.getRowCount() - 1 + " //// " + id);
    }

    public void setPreviousGUI(JFrame previousGUI) {
        this.previousGUI = previousGUI;
    }

    public void goBackToPreviousGUI() {
        setVisible(false);
        if (previousGUI != null) {
            previousGUI.setVisible(true);
        }
    }

    // Renderer for the button column
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor for the button column
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    // Get the row of the button
                    int row = playerTable.convertRowIndexToModel(playerTable.getEditingRow());
                    System.out.println(row);
                    // Get id from idMap based on the row
                    Long id = idMap.get(row);
                    new SynthesisLineGUI(id);
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            return button;
        }

        public Object getCellEditorValue() {
            return null;
        }

        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
package ru.vsu.cs.eliseev;

import util.ArrayUtils;
import util.JTableUtils;
import util.SwingUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class MainForm extends JFrame {

    private JPanel mainPanel;
    private JTable tableInput;
    private JTable tableOutput;
    private JButton buttonPrevious;
    private JButton buttonStop;
    private JButton buttonNext;
    private JButton solveButton;
    private JButton buttonRandomInput;

    public boolean timeContinue;
    public ArrayList<SortState> states;
    public int count = 0;
    public int tempJ = 0;
    public static final Color EMPTY_CELL_COLOR = UIManager.getColor("TextField.background");
    public static final Color FIRST_CELL_COLOR = Color.GREEN;
    public static final Color SECOND_CELL_COLOR = Color.YELLOW;
    public static final Color ERROR_CELL_COLOR = Color.RED;


    private Timer timer = new Timer(1000, e -> {
        try {
            JTableUtils.writeArrayToJTable(tableOutput, states.get(count).getArr());
            tempJ = states.get(count).getTempJ();
            count++;
        } catch (Exception except) {
            SwingUtils.showInfoMessageBox("Ура!!!", "Массив отсортирован");
            stopTime();
        }
    });

    public void stopTime(){
        timer.stop();
    }

    public MainForm(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        JTableUtils.initJTableForArray(tableInput, 40, true, true, false, true);
        JTableUtils.initJTableForArray(tableOutput, 40, true, true, false, false);


        tableInput.setRowHeight(25);
        tableOutput.setRowHeight(25);
        class CellRenderer extends DefaultTableCellRenderer {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Color color = EMPTY_CELL_COLOR;
                try {
                    if (column == tempJ) {
                        color = FIRST_CELL_COLOR;
                    } else if (column == tempJ + 1) {
                        color = SECOND_CELL_COLOR;
                    }
                } catch (Exception e) {
                    color = ERROR_CELL_COLOR;
                }
                comp.setBackground(color);
                return comp;
            }
        }
        tableInput.setDefaultRenderer(Object.class, new CellRenderer());
        tableOutput.setDefaultRenderer(Object.class, new CellRenderer());

        buttonPrevious.addActionListener(e -> {
            try {
                count--;
                JTableUtils.writeArrayToJTable(tableOutput, states.get(count).getArr());
                tempJ = states.get(count).getTempJ();
            } catch (Exception except) {
                SwingUtils.showInfoMessageBox("Эхх..", "Вы вернулись в начало сортировки");
                count = 0;
            }
        });
        buttonNext.addActionListener(e -> {
            try {
                count++;
                JTableUtils.writeArrayToJTable(tableOutput, states.get(count).getArr());
                tempJ = states.get(count).getTempJ();
            } catch (Exception except) {
                SwingUtils.showInfoMessageBox("Ура!!!", "Массив отсортирован");
            }
        });
        buttonRandomInput.addActionListener(e -> {
            try {
                int[] array = ArrayUtils.createRandomIntArray(tableInput.getColumnCount(), 100);
                JTableUtils.writeArrayToJTable(tableInput, array);
            } catch (Exception exception) {
                SwingUtils.showInfoMessageBox("Error", "Error");
            }
        });
        solveButton.addActionListener(e -> {
            try {
                count = 0;
                int[] arr = JTableUtils.readIntArrayFromJTable(tableInput);
                assert arr != null;
                states = new ArrayList<>(BubbleSort.sort(arr));
                JTableUtils.writeArrayToJTable(tableOutput, states.get(count).getArr());
                tempJ = states.get(count).getTempJ();
                count++;
                timer.start();
                timeContinue = true;
            } catch (Exception except) {
                SwingUtils.showInfoMessageBox("ERROR", "error");
            }

        });
        buttonStop.addActionListener(e -> {
            try {
                if (timeContinue) {
                    timer.stop();
                    timeContinue = false;
                } else {
                    timer.start();
                }
            } catch (Exception except) {
                SwingUtils.showInfoMessageBox("ERROR", "error");
            }
        });
    }
}

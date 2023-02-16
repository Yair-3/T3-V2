package edu.touro.cs.mcon364;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.ArrayList;


class T3view extends JFrame {
    private JPanel title_panel = new JPanel();
    private JPanel button_panel = new JPanel();
    private JPanel bottom = new JPanel();
    private final JLabel bottom_text = new JLabel();
    private final JLabel top_bar_text = new JLabel();
    private final JCheckBox cpu_check_box = new JCheckBox();
    private final JButton[][] buttons = new JButton[3][3];
    private final JButton save = new JButton();
    private final JButton restore = new JButton();
    private EventHandler eventHandler = new EventHandler();
    private EventHandler2 eventHandler2 = new EventHandler2();
    private EventHandler3 eventHandler3 = new EventHandler3();
    private EventHandler4 eventHandler4 = new EventHandler4();

    private final JButton reset = new JButton("NEW GAME");

    T3 model = new T3();
    T3Controller controller = new T3Controller(model, this);


    class EventHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isNewGame(e)) {
                if (!controller.recWinnerLocal().isEmpty()) {
                    fillButtons(getBackground());
                    controller.recWinnerLocal().clear();
                }
                controller.setRequest();
                controller.setGameCpu(T3.GameType.ONE_V_ONE);
                top_bar_text.setText("Tic-Tac-Toe");
                bottom_text.setText("Player X will start the game");

            } else {
                ArrayList<Integer> position = getPosition(e);
                controller.setRequest(position);
                if (controller.getInvalidMove()){
                    return;
                }
                buttons[position.get(0)][position.get(1)].setText(controller.receiveInfo());
                bottom_text.setText(controller.receiveStatus() + "'S TURN");

                if (controller.getGameType() == T3.GameType.CPU) {
                    controller.setRequestCpu();
                    buttons[controller.receiveCpuPosition().get(0)][controller.receiveCpuPosition().get(1)].setText(controller.receiveCpuInfo());
                    bottom_text.setText("CPU MODE");
                } else {
                    cpu_check_box.setEnabled(false);
                    controller.setGameCpu(T3.GameType.ONE_V_ONE);
                }
                if (controller.receiveWinner()) {
                    top_bar_text.setText(controller.winner() + " WINS THE GAME!");
                    fillButtons(Color.green);
                    return;
                }
                if (controller.receiveTie()) {
                    top_bar_text.setText("It's a tie :(");
                }

            }

        }
    }

    class EventHandler2 implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            controller.setGameCpu(T3.GameType.CPU);
            cpu_check_box.setEnabled(false);
        }
    }

    class EventHandler3 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("T3.bin");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(controller.getModel());
                fos.close();
                oos.close();

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    class EventHandler4 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream("T3.bin");
                ObjectInputStream ois = new ObjectInputStream(fis);
                controller.setModel((T3) ois.readObject());
                ois.close();
                fis.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (controller.getBoard()[i][j] == T3.CellValue.NONE){
                        buttons[i][j].setText(null);
                    } else {
                        buttons[i][j].setText(controller.getBoard()[i][j].toString());
                    }
                }
            }
            if (controller.getGameType() == T3.GameType.CPU){
                controller.setGameCpu(T3.GameType.CPU);
                cpu_check_box.setEnabled(false);
                cpu_check_box.setSelected(true);
            } else{
                controller.setGameCpu(T3.GameType.ONE_V_ONE);
                cpu_check_box.setEnabled(false);
            }
        }
    }

    private void fillButtons(Color a) {
        buttons[controller.recWinnerLocal().get(0)][controller.recWinnerLocal().get(1)].setBackground(a);
        buttons[controller.recWinnerLocal().get(2)][controller.recWinnerLocal().get(3)].setBackground(a);
        buttons[controller.recWinnerLocal().get(4)][controller.recWinnerLocal().get(5)].setBackground(a);
    }


    T3view() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(750, 700);
        this.setLayout(new BorderLayout());

        top_bar_text.setBackground(new Color(25, 25, 25));
        top_bar_text.setForeground(new Color(25, 255, 0));
        top_bar_text.setFont(new Font("Ink Free", Font.BOLD, 50));
        top_bar_text.setHorizontalAlignment(JLabel.CENTER);
        top_bar_text.setText("Tic-Tac-Toe");
        top_bar_text.setOpaque(true);
        title_panel.setLayout(new FlowLayout());
        title_panel.setBackground(new Color(25, 25, 25));
        title_panel.setBounds(0, 0, 800, 100);
        cpu_check_box.setSize(20, 20);
        cpu_check_box.setText("Computer Opponent");
        cpu_check_box.setForeground(new Color(25, 255, 0));
        cpu_check_box.setBackground(new Color(25, 25, 25));

        restore.setText("Restore Game");
        save.setText("Save Game");


        bottom_text.setBackground(new Color(25, 25, 25));
        bottom_text.setForeground(new Color(25, 255, 0));
        bottom_text.setBorder(BorderFactory.createEmptyBorder());
        bottom_text.setFont(new Font("Ink Free", Font.BOLD, 25));
        bottom_text.setHorizontalAlignment(JLabel.CENTER);
        bottom_text.setText("Player X will start the game");
        bottom_text.setOpaque(true);
        bottom.setLayout(new FlowLayout());
        bottom.add(bottom_text);
        bottom.setBackground(new Color(25, 25, 25));


        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(new Color(150, 150, 150));


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                button_panel.add(buttons[i][j]);
                buttons[i][j].setFont(new Font("MV Boli", Font.BOLD, 120));
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(eventHandler);
            }
        }
        cpu_check_box.addItemListener(eventHandler2);
        reset.setSize(20, 20);
        reset.addActionListener(eventHandler);
        bottom.add(reset);
        bottom.add(save);
        save.addActionListener(eventHandler3);
        bottom.add(restore);
        restore.addActionListener(eventHandler4);
        title_panel.add(top_bar_text);  //BorderLayout.CENTER);
        title_panel.add(cpu_check_box);
        this.add(title_panel, BorderLayout.NORTH);
        this.add(button_panel);
        this.add(bottom, BorderLayout.SOUTH);
        this.setVisible(true);

    }

    public boolean isNewGame(ActionEvent e) {
        return e.getSource() == reset;
    }

    public ArrayList<Integer> getPosition(ActionEvent e) {
        ArrayList<Integer> position = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == buttons[i][j]) {
                    position.add(i);
                    position.add(j);
                }
            }
        }
        return position;
    }

    public void resetView() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(null);
                buttons[i][j].setEnabled(true);
            }
        }
        cpu_check_box.setSelected(false);
        if (controller.getGameType() == T3.GameType.CPU) {
            controller.setGameCpu(T3.GameType.CPU);
        }
        cpu_check_box.setEnabled(true);
    }

}


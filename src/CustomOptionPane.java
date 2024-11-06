package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomOptionPane extends Frame {

    public static final int YESNOOption = 0;
    public static final int YESNOCANCELOption = 1;
    public static final int OKOption = 2;
    public static final int OKCancelOption = 3;
    public static final int ANYWAYCANCELOption = 4;

    public static final int YES = 0;
    public static final int NO = 1;
    public static final int CANCEL = 2;
    public static final int OK = 3;
    public static final int ANYWAY = 4;

    private String title = "CustomOptionPane.title";
    private String label = "CustomOptionPane.label";
    private int res = CANCEL;

    public CustomOptionPane(String title, String label) {
        this.title = title;
        this.label = label;
        this.setSize(300, 200);
        this.setBackground(new Color(234, 234, 234));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setTitle(title);
        this.setLayout(null);
        JLabel lbl = new JLabel(SplitText(label), JLabel.CENTER);
        lbl.setBounds(10, 65, 285, 25);
        this.add(lbl);
    }

    public CustomOptionPane() {
        this.setSize(300, 200);
        this.setBackground(new Color(234, 234, 234));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setTitle(title);
        this.setLayout(null);
        JLabel lbl = new JLabel(SplitText(label), JLabel.CENTER);
        lbl.setBounds(10, 65, 285, 25);
        this.add(lbl);
    }

    private String SplitText(String text) {
        StringBuilder output = new StringBuilder();
        output.append("<html>");

        int length = text.length();
        for (int i = 0; i < length; i += 47) {
            output.append(text, i, Math.min(length, i + 47));
            if (i + 47 < length) {
                output.append("<br>");
            }
        }

        output.append("</html>");
        return output.toString();
    }

    public int showChoiceDialog(int state) {

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        Button Yes = new Button("Yes");
        Yes.setBounds(20, 150, 100, 25);
        Yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeResTo(0);
            }
        });

        Button No = new Button("No");
        No.setBounds(180, 150, 100, 25);
        No.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeResTo(1);
            }
        });

        Button Yes1 = new Button("Yes");
        Yes1.setBounds(20, 150, 75, 25);
        Yes1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeResTo(0);
            }
        });

        Button No1 = new Button("No");
        No1.setBounds(112, 150, 75, 25);
        No1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeResTo(1);
            }
        });

        Button Cancel = new Button("Cancel");
        Cancel.setBounds(205, 150, 75, 25);
        Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeResTo(2);
            }
        });

        Button OK = new Button("OK");
        OK.setBounds(100, 150, 100, 25);
        OK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeResTo(3);
            }
        });

        Button OK1 = new Button("OK");
        OK1.setBounds(40, 150, 100, 25);
        OK1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeResTo(3);
            }
        });

        Button Cancel1 = new Button("Cancel");
        Cancel1.setBounds(160, 150, 100, 25);
        Cancel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeResTo(2);
            }
        });

        Button Anyway = new Button("Do anyway");
        Anyway.setBounds(40, 150, 100, 25);
        Anyway.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeResTo(4);
            }
        });

        Button Cancel2 = new Button("Cancel");
        Cancel2.setBounds(160, 150, 100, 25);
        Cancel2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeResTo(2);
            }
        });

        switch (state) {

            case 0:
                this.add(Yes);
                this.add(No);
                break;

            case 1:
                this.add(Yes1);
                this.add(No1);
                this.add(Cancel);
                break;

            case 2:
                this.add(OK);
                break;

            case 3:
                this.add(OK1);
                this.add(Cancel1);
                break;

            case 4:
                this.add(Anyway);
                this.add(Cancel2);
                break;

            default: throw new IllegalArgumentException("State " + state + " doesn't exit. Must be between 0 and 4");
        }

        this.setVisible(true);
        return res;
    }

    private void changeResTo(int res) {
        this.res = res;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
package src;

import java.awt.*;
import java.awt.event.*;

public class CustomOptionPane extends Frame {

    public static final int YESNOOption = 0;
    public static final int YESNOCANCELOption = 1;
    public static final int OKOption = 2;
    public static final int OKCancelOption = 3;
    public static final int ANYWAYCANCELOption = 4;

    public static final int YESOption = 0;
    public static final int NOOption = 1;
    public static final int CancelOption = 2;
    public static final int OKOption = 3;
    public static final int ANYWAYOption = 4;

    private String title = "CustomOptionPane.title";
    private String label = "CustomOptionPane.label";

    public CustomOptionPane() {
        this.setSize(300, 200);
        this.setBackground(new Color(234, 234, 234));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setTitle(title);
        this.setLayout(null);

    }

    private String SplitText(String text) {
        String FinalText = "";
        for (int i = 0; i != text.length(); i++) {
            if ((i+1)%48 == 0) {
                FinalText += text.substring(0, i+1);
                text = text.substring(i+1);
            }
        }

        return FinalText;
    }

    public int showChoiceDialog(int state) {
        final boolean[] isClosed = {false};
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                isClosed[0] = true;
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        switch (state) {
            Label lbl = new Label(SplitText(label));
            lbl.setBounds(10, 35, 300, 25);
            this.add(lbl);
            
            Button Yes = new Button("Yes");
            Yes.setBounds(20, 105, 100, 25);
            Yes.addActionListener(new ActionListener()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    return YESOption;
                }
            }
            Button No = new Button("No");
            No.setBounds(180, 105, 100, 25);
            No.addActionListener(new ActionListener()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    return NOOption;
                }
            }
            
            case 0:
                this.add(Yes);
                this.add(No);
                break;

            case 1:
                Button Yes = new Button("Yes");
                Yes.setBounds(10, 105, 75, 25);
                Button No = new Button("No");
                No.setBounds(102, 105, 75, 25);
                Button Cancel = new Button("Cancel");
                Cancel.setBounds(215, 105, 75, 25);
                this.add(Yes);
                this.add(No);
                this.add(Cancel);
                break;

            case 2:
                Button OK = new Button("OK");
                OK.setBounds(50, 105, 100, 25);
                this.add(OK);
                break;

            case 3:
                Button OK = new Button("OK");
                Yes.setBounds(20, 105, 100, 25);
                Button Cancel = new Button("Cancel");
                No.setBounds(180, 105, 100, 25);
                this.add(OK);
                this.add(Cancel);
                break;

            case 4:
                Button Anyway = new Button("Do anyway");
                Yes.setBounds(20, 105, 100, 25);
                Button Cancel = new Button("Cancel");
                No.setBounds(180, 105, 100, 25);
                this.add(Anyway);
                this.add(Cancel);
                break;

            default: throw new IllegalArgumentException("State " + state + " doesn't exit");
        }

        this.setVisible(true);

        if (isClosed[0]) {
            return;
        }
    }

    public static void main(String[] args) {
        new CustomOptionPane().showChoiceDialog(YESNOOption);
    }
}

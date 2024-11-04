package src;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CustomOptionPane extends Frame {

    Button Yes = new Button("Yes");
    Button No = new Button("No");
    Yes.setBounds(20, 105, 100, 25);
    No.setBounds(180, 105, 100, 25);

    public static final int YESNOOption = 0;
    public static final int YESNOCANCELOption = 1;
    public static final int OKOption = 2;
    public static final int OKNOOption = 3;
    public static final int ANYWAYCANCELOption = 4;
    public static final int CUSTOMOption = 5;

    private String title = "Choosing option";
    private String label = "Tlkqisnbutgorezuctgocrezoguoczreouygecdrtb nyhoe";

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

    public void showChoiceDialog(int state) {
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
            case 0:
                Label lbl = new Label(SplitText(label));
                lbl.setBounds(10, 35, 300, 25);
                this.add(lbl);
                this.add(Yes);
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

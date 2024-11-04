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
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                isClosed[0] = true;
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

            Button Yes1 = new Button("Yes");
            Yes1.setBounds(10, 105, 75, 25);
            Yes1.addActionListener(new ActionListener()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    return YESOption;
                }
            }

            Button No1 = new Button("No");
            No1.setBounds(102, 105, 75, 25);
            No1.addActionListener(new ActionListener()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    return NOOption;
                }
            }

            Button Cancel = new Button("Cancel");
            Cancel.setBounds(215, 105, 75, 25);
            Cancel.addActionListener(new ActionListener()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    return CancelOption;
                }
            }

            Button OK = new Button("OK");
            OK.setBounds(50, 105, 100, 25);
            OK.addActionListener(new ActionListener()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    return OKOption;
                }
            }

            Button OK1 = new Button("OK");
            OK1.setBounds(20, 105, 100, 25);
            OK1.addActionListener(new ActionListener()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    return OKOption;
                }
            }

            Button Cancel1 = new Button("Cancel");
            Cancel1.setBounds(180, 105, 100, 25);
            Cancel1.addActionListener(new ActionListener()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    return CancelOption;
                }
            }

            Button Anyway = new Button("Do anyway");
            Anyway.setBounds(20, 105, 100, 25);
            Anyway.addActionListener(new ActionListener()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    return ANYWAYOption;
                }
            }

            Button Cancel2 = new Button("Cancel");
            Cancel2.setBounds(180, 105, 100, 25);
            Cancel2.addActionListener(new ActionListener()) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    return CancelOption;
                }
            }

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
                this.add(Cancel);
                break;

            default: throw new IllegalArgumentException("State " + state + " doesn't exit. Must be between 0 and 4");
        }

        this.setVisible(true);

        if (isClosed[0]) {
            return CancelOption;
        }
    }

    public static void main(String[] args) {
        new CustomOptionPane().showChoiceDialog(YESNOOption);
    }
}

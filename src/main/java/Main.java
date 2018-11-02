import javax.swing.*;

public class Main {


    public static void main(String[] args) {
        JFrame jframe = new JFrame("MonteCarlo");
        jframe.setContentPane(new MonteCarlo().PanelMain);
        jframe.setDefaultCloseOperation(3);
        jframe.pack();
        jframe.setVisible(true);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TikTakToeView extends JFrame {
    public JButton[][] buttons;
    private JButton newGame;
    private JLabel indicator;
    private JTextArea messageArea;
    private JPanel gamePanel;
    private JPanel messagePanel;


    public TikTakToeView() {
        super("Tic Tak Toe");
        setLayout(new BorderLayout());
        setSize(650,400);

        this.gamePanel = new JPanel();
        this.messagePanel = new JPanel();

        this.messageArea = new JTextArea("Playing some 'tak");
//        messageArea.setSize(new Dimension(300,300));
        messagePanel.setSize(600,300);
        messagePanel.setLayout(new GridLayout(1,2));

        gamePanel.setLayout(new GridLayout(3,3));
        gamePanel.setSize(300,300);
        initComponents();

        messagePanel.add(gamePanel);
        messagePanel.add(messageArea);
//        add(gamePanel,BorderLayout.CENTER);
        add(messagePanel, BorderLayout.CENTER);
        add(newGame, BorderLayout.SOUTH);
//        pack();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(false);
    }

    private void initComponents(){
        initButtons();
        this.indicator = new JLabel("X");
        this.newGame = new JButton("New Game");
        this.newGame.setEnabled(false);
    }

    private void initButtons(){
        this.buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton(Character.toString(Constants.SPACE_CHAR));
                buttons[i][j].setActionCommand(Integer.toString(i) + " " + Integer.toString(j));
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gamePanel.add(buttons[i][j]);
            }
        }
    }

    public void addButtonListeners(int i, int j, ActionListener actionListener) {
        buttons[i][j].addActionListener(actionListener);
    }

    public void addNewGameListener( ActionListener actionListener) {
        newGame.addActionListener(actionListener);
    }

    public void setMark(int i, int j, char mark) {
        buttons[i][j].setText(Character.toString(mark));
    }


    public void setMessage(String s) {
        messageArea.setText(s);
        this.newGame.setEnabled(true);
    }
}

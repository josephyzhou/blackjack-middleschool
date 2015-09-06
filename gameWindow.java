import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.imageio.*;
import java.io.*;

public class gameWindow extends Frame implements ActionListener {
	
	public gameWindow(String title, int width, int height, boolean resizeable, BlackJack blackjack) throws Exception {
		if(title.equals("") || title.equals(null) || width <= 0 || height <=0)
		  throw new Exception("Inapropriated parameters in gameWindow().");
		this.blackjack = blackjack;
    setTitle(title);
    setSize(width, height);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    bPanel = new Panel();
    bPanel.setBackground(new Color(0,120,0));
		bet1 = new Button("Bet 1$");
		bet5 = new Button("Bet 5$");
		bet10 = new Button("Bet 10$");
		bet25 = new Button("Bet 25$");
		resetBet = new Button("Reset Bet");
		deal = new Button("Deal");
		stand = new Button("Stand");
		newRound = new Button("New Round");
		newGame = new Button("New Game");
		bPanel.add(bet1);
		bPanel.add(bet5);
		bPanel.add(bet10);
		bPanel.add(bet25);
		bPanel.add(resetBet);
		bPanel.add(deal);
		bPanel.add(stand);
		bPanel.add(newRound);
		bet1.addActionListener(this);
		bet5.addActionListener(this);
		bet10.addActionListener(this);
		bet25.addActionListener(this);
		resetBet.addActionListener(this);
		deal.addActionListener(this);
		stand.addActionListener(this);
		newRound.addActionListener(this);
		add(bPanel, BorderLayout.SOUTH);
	  cards = new Image[53];
		for(int i = 0; i < 53; i++) {
			BufferedImage img = null;
      try {
         img = ImageIO.read(new File("Image/"+imgName[i]+".png"));
      } catch (IOException e) {
      }
      cards[i] = img;
		}
    setVisible(true);
    toFront();   
    gfx = getGraphics();
	}  
    
  public void actionPerformed(ActionEvent evt) {
  	String a = evt.getActionCommand();
  	blackjack.actionPerformed(a);  
  } 

  public void updateGraphics(Image img) throws Exception {
  	setButton();
    gfx.drawImage(img, 0, 0, new Color(0,120,0), this);
  }
  
  private final void setButton() {
  	int i = blackjack.gameStatus;
  	bet1.setEnabled(i == 0);
  	bet5.setEnabled(i == 0); 
  	bet10.setEnabled(i == 0);
  	bet25.setEnabled(i == 0);
  	resetBet.setEnabled(i == 0); 
  	deal.setEnabled(i == 0  || i == 1);
  	stand.setEnabled(i == 1);
  	newRound.setEnabled(i == 2  || i == 3);
  }
  
  protected final Image getCardImage(String card) {
  	int pos = 0;
  	for(int i = 0; i < 53; i++) {
  	  if(imgName[i].equals(card)) {
  	    pos = i;
  	    break;
  	  } else {
  	    continue;
  	  }
  	}
  	return cards[pos];
  }
  
	private BlackJack blackjack;
	private Graphics gfx;
	private Font bigFont;
	private Panel bPanel;
	private Button bet1, bet5, bet10, bet25, resetBet, deal, stand, newRound, newGame;
	private Image cards[];
	private final String imgName[] = {
		"ac", "as", "ah", "ad",
		"2c", "2s", "2h", "2d",
		"3c", "3s", "3h", "3d",
		"4c", "4s", "4h", "4d",
		"5c", "5s", "5h", "5d",
		"6c", "6s", "6h", "6d",
		"7c", "7s", "7h", "7d",
		"8c", "8s", "8h", "8d",
		"9c", "9s", "9h", "9d",
		"10c", "10s", "10h", "10d",
		"jc", "js", "jh", "jd",
		"qc", "qs", "qh", "qd",
		"kc", "ks", "kh", "kd", "back"	
	};
}
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class BlackJack extends Deck {
	
	public static final void main(String args[]) {
		new BlackJack();
	}
	
	private BlackJack() {
		try {
			bigFont = new Font("Serif", Font.BOLD, 14);	  		  		  
		  game = new gameWindow("BlackJack "+version+" by TerryC", 525, 375, false, this);
		  gameImg = game.createImage(525, 375);
		  gfx = gameImg.getGraphics();
		  resetHands();
		  update();
		  inProgress = true;
		  while(true) {
		  	update();
		  }
	  } catch(Exception e) {
	  	e.printStackTrace();
	  }	
	}
	
	private final void resetHands() {
		playerHand = new String[5];
		dealerHand = new String[5];
		for(int i = 0; i < 5; i++) {
		  playerHand[i] = "";
		  dealerHand[i] = "";
	  }
	}
	
	private final void update() {
		try {
		  gfx.setColor(new Color(0,120,0));
      gfx.fillRect(0, 0, game.getWidth(), game.getHeight());
      gfx.setFont(bigFont);
      gfx.setColor(Color.green);
      gfx.drawString(message, 10, 300);
      gfx.drawString("Your Money: "+cash+" Your Bet: "+bet, 10, 320);
      gfx.drawString("Dealer's Cards:", 10, 43);
      gfx.drawString("Your Cards:", 10, 173);  
      for(int i = 0; i < 5; i++) {
      	if(!playerHand[i].equals("")) 
          gfx.drawImage(game.getCardImage(playerHand[i]), 10 + i * 90, 180, null);
        if(!dealerHand[i].equals("")) {
        	if(inProgress) {      		
        	  gfx.drawImage(game.getCardImage(dealerHand[0]), 10 + 0 * 90, 50, null);
        	  gfx.drawImage(game.getCardImage("back"), 10 + 1 * 90, 50, null);
          } else
            gfx.drawImage(game.getCardImage(dealerHand[i]), 10 + i * 90, 50, null);
        }                 
      }
      game.updateGraphics(gameImg);
    } catch(Exception e) {
    	e.printStackTrace();
    }
  }  
  
  public void actionPerformed(String a) {
    if(a.equals("Deal")) {
    	doDeal();
    } else if(a.equals("Stand")) {
    	doStand();
    } else if(a.equals("Bet 1$")) {
    	doBet(1);
    } else if(a.equals("Bet 5$")) {
    	doBet(5);
    } else if(a.equals("Bet 10$")) {
    	doBet(10);
    } else if(a.equals("Bet 25$")) {
    	doBet(25);
    } else if(a.equals("Reset Bet")) {
    	doBet(0);
    } else if(a.equals("New Round")) {
    	doNewRound();
    }    
  }
  
  private final void doDeal() {
  	if(gameStatus == 0) { //done betting
  		gameStatus = 1;
  		resetHands();
  		shuffle();
  		playerHand[0] = getCard();
  		dealerHand[0] = getCard();
  		playerHand[1] = getCard();
  		dealerHand[1] = getCard();
  		if(getSum(playerHand) == 21) {
  			message = "BlackJack! You won "+bet*1.5+"$!";
  			cash += bet*3.5;
  			gameStatus = 2;
  			bet = 0;
  			return;
  		} else if(dealerHand[0].startsWith("a")) {
  			int i = JOptionPane.showConfirmDialog(
    game,
    "Would you like dealer BlackJack insurance?",
    "BlackJack Version: "+version+" by TerryC.",
    JOptionPane.YES_NO_OPTION);
  			switch(i) {  				
          case JOptionPane.YES_OPTION:
            cash -= bet / 2;
            if(getSum(dealerHand) == 21) {
            	message = "Dealer has BlackJack. You win "+bet/2+"$.";
            	cash += (bet*2) + (bet/2);
            	gameStatus = 2;
            	bet = 0;
            	inProgress = false;
            } else
              message = "Dealer does not have BlackJack.";
          case JOptionPane.NO_OPTION:
            break;
  			}
  		} else if(getSum(dealerHand) == 21) {  			
  			message = "Dealer has BlackJack! You lost "+bet+"$!";
  			bet = 0;
  			gameStatus = 2;
  			inProgress = false;
  			return;
  		} else {
  			message = "You've "+getSum(playerHand)+". Deal or Stand?";
  			return;
  		}
  	} else if(gameStatus == 1) {
  		for(int i = 0; i < 5; i++) {
  			if(playerHand[i].equals("")) {
  			  playerHand[i] = getCard();
  			  break;
  			}
  		}
  		if(getSum(playerHand) > 21) {
  			message = "You've busted. You lost "+bet+"$!";
  			gameStatus = 2;
  			return;
  		} else if(getCount(playerHand) == 5) {
  			message = "You win "+bet+"$ by taking 5 cards without going over 21.";
  			cash += bet*2;
  			gameStatus = 2;
  			return;
  		} else if(getSum(playerHand) == 21) {
  			message = "You've 21, let's se what dealer has.";
  			gameStatus = 2;
  			inProgress = false;
  			doDealer();
  			return;
  		} else {
  			message = "You've "+getSum(playerHand)+". Deal or Stand?";
  			return;
  		}
  	} else { //shouldn't ever happend.
  		message = "You've "+getSum(playerHand)+". Deal or Stand?";
  		return;
  	}
  }
  
  private final void doStand() {
  	if(gameStatus == 1) {
  	  message = "You've standed, let's se what the dealer has.";
  	  gameStatus = 2;
  	  inProgress = false;
  	  doDealer();
  	  return;
  	} else { //shouldn't ever happend.
  		return;
  	}
  }
  
  private final void doBet(int i) {
  	if(i == 0) {
  		bet = 0;
  		return;
  	} else {
  		if(i <= cash) {
  			bet += i;
  			cash -= i;
  			return;
  		} else {
  			message = "You don't have enought money to bet "+i+"$!";
  			return;
  		}
  	}
  }
  
  private final void doNewRound() {
  	if(gameStatus == 2 || gameStatus == 3) {
  		bet = 0;
  		cash = cash == 0 ? 1000 : cash;
  	  resetHands();
  	  gameStatus = 0;
  	  message = "Place your bet.";
  	  inProgress = true;
  	  return;
  	} else { //shouldn't ever happend
  		return;
  	}
  } 
  
  private final int getCount(String a[]) {
  	int c = 0;
  	for(int i = 0; i < a.length; i++)
  	  if(!a[i].equals(""))
  	    c++;
  	return c;
  } 
  
  private final void doDealer() {
  	while(getSum(dealerHand) < 17 && getCount(dealerHand) < 6) {
  		for(int i = 0; i < 5; i++) {
  		  if(dealerHand[i].equals("")) {
  		    dealerHand[i] = getCard();
  		    break;
  		  }
  		}
  	}
  	if(getSum(dealerHand) > 21) {
  		message = "Dealer has busted. You won "+bet+"$!";
  		cash += bet*2;
  		bet = 0;
  		gameStatus = 2;
  		return;
  	} else if(dealerHand.length == 4) {
  		message = "Dealer won by taking 5 cards without going over 21. You lost "+bet+"$!";
  		gameStatus = 2;
  		return;
  	} else if(getSum(playerHand) > getSum(dealerHand)) {
  		message = "You win "+bet+"$, "+getSum(playerHand)+" to "+getSum(dealerHand)+".";
  		gameStatus = 2;
  		cash += bet*2;
  		bet = 0;
  		return;
  	} else if(getSum(playerHand) < getSum(dealerHand)) {
  		message = "You lost "+bet+"$, "+getSum(dealerHand)+" to "+getSum(playerHand)+".";
  		gameStatus = 2;
  		bet = 0;
  		return;
  	} else if(getSum(playerHand) == getSum(dealerHand)) {
  		message = "Push.";
  		gameStatus = 2;
  		cash += bet;
  		bet = 0;
  		return;
  	} else { //shouldn't ever happend.
  		return;
  	}
  } 
  
  private Graphics gfx;  
  private Image gameImg;
  private gameWindow game;
  private String version = "0.2"; 
  private String message = "Press New Round to start.";
  private int cash = 0;
  private int bet = 0;  
  protected int gameStatus = 3;
  private Font bigFont;
  private String playerHand[];
  private String dealerHand[];
  private boolean inProgress = true;
}
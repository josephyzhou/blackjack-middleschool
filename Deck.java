public class Deck {	 
	
  public Deck() {
  	playDeck = null;
    shuffle();	
  }
   
  public final void shuffle() {
  	currentCard = 0;
		String tempDeck[] = deck;
		playDeck = new String[deck.length];
		for(int l = 0; l < 100; l++) {
		  for(int i = 0; i < deck.length; i++) {
			  int r = i + (int) (Math.random() * (tempDeck.length - i)); 
        String swap = tempDeck[i];
        tempDeck[i] = tempDeck[r];
        tempDeck[r] = swap;
      }
    }
    playDeck = tempDeck;
  }
  
  private final int cardValue(String card) {
		if(card.startsWith("a")) return 11; 
		else if(card.startsWith("2")) return 2;
		else if(card.startsWith("3")) return 3;
		else if(card.startsWith("4")) return 4;
		else if(card.startsWith("5")) return 5;
		else if(card.startsWith("6")) return 6;
		else if(card.startsWith("7")) return 7;
		else if(card.startsWith("8")) return 8;
		else if(card.startsWith("9")) return 9;
	  else if(card.startsWith("10") || card.startsWith("j") || card.startsWith("q") || card.startsWith("k")) return 10;
		else return 999999;
	}
	
	protected final String getCard() {
		if(currentCard == playDeck.length)
		  shuffle();
		String card = playDeck[currentCard];
		currentCard++;
		return card;
	}
	
	protected final int getSum(String[] hand) {
		int aCount = 0;
		int tempSum = 0;
		for(int i = 0; i < hand.length; i++) {
			if(!hand[i].equals(""))
			  tempSum += cardValue(hand[i]);
		  if(!hand[i].equals("") && hand[i].startsWith("a"))
		    aCount++;
		}
		if(tempSum <= 21)
		  return tempSum;
		switch(aCount) {
			case 0:
			break;
			case 1:
			if(tempSum > 21 && tempSum - 10 <= 21)
			  tempSum -= 10;
			break;
			case 2:
			if(tempSum > 21 && tempSum - 21 <= 21)
			  tempSum -= 22;
			break;
			case 3:
			if(tempSum > 21 && tempSum - 32 <= 21)
			  tempSum -= 33;
			break;
			case 4:
			if(tempSum > 21 && tempSum - 43 <= 21)
			  tempSum -= 44;
			break;
		}
		return tempSum;
	}
    
  private int currentCard;
  private String playDeck[];
  private final String deck[] = {
    	"ac", "as", "ah", "ad", "2c", "2s", "2h", "2d", "3c", "3s", "3h", "3d", "4c", "4s", "4h", "4d", "5c", "5s", "5h", "5d",
		  "6c", "6s", "6h", "6d", "7c", "7s", "7h", "7d", "8c", "8s", "8h", "8d", "9c", "9s", "9h", "9d", "10c", "10s", "10h", "10d", 
		  "jc", "js", "jh", "jd", "qc", "qs", "qh", "qd", "kc", "ks", "kh", "kd"
  };
} 
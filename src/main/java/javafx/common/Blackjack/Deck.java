package javafx.common.Blackjack;

public class Deck {
    private Card[] deck;
    private int currentCardIndex;
    
	//stocke les cartes dans  Card[ ] deck et ensuite il appelle la fonction shuflleDeck(). 
    public Deck() {
        deck = new Card[52];
        int currentCardIndex = 0;
        String [] allRanks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String [] allSuits = {"Spades", "Hearts", "Diamonds", "Clubs"};
        for(String suit: allSuits) {
            for(String rank: allRanks) {
                int points = switch (rank) {
                    case "Ace" -> 11;
                    case "Jack", "Queen", "King" -> 10;
                    default -> Integer.parseInt(rank);
                };
                deck[currentCardIndex] = new Card(suit, rank, points);
                currentCardIndex++;
            }
        }
        shuffleDeck();
    }
    
	//shuffleDeck, pour mélanger les cartes à l'aide de l'algorithme de mélange de Fisher-Yates:
	//https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
    private void shuffleDeck() {
    	for(int i = 51; i > 0; i = i-1) {
            int j = (int)(Math.random()*52); //cards number
            Card tempCard = deck[i];
            deck[i] = deck[j];
            deck[j] = tempCard;
        }
    }
    
    public Card drawCard() {
        if(currentCardIndex == 51) {
            Card currCard = deck[currentCardIndex];
            shuffleDeck();
            return currCard;
        }
        else {
            return deck[currentCardIndex++];        
        }
    }

}

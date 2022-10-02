package javafx.common.Blackjack;

import java.util.ArrayList;

public class Hand {
    //private Card [] hand;
    private ArrayList<Card> hand = new ArrayList<>();
    @SuppressWarnings("unused")
	private String user; //Soit le le joueur soit le courtier. Selon comment on appelle le constrcuteur et le paramertre quand utilise
    
    public Hand(String user) {
        this.user = user;
    }
    
	//retroune le tableau hand
    public ArrayList<Card>  getCards() {
        return this.hand;
    }
    
	// retourne la somme des cartes dans le tableau hand. Si la somme est >21, il faut recompter les cartes pour verifier si il y a un ACE. Si oui on le considere comme 1, sinon on ajoute la somme des points
    public int getPoints() {
        int total = 0;
        for(Card card : hand) 
            total = total + card.getPoints();
        if(total > 21) {
        	total = 0;
            for(Card card : hand) {
                if(card.isAce()) 
                    total = total + 1;
                else
                    total = total + card.getPoints(); 
            }
        }
        return total;
    }
    
	// ajouter une carte au tableau
    public void addCard(Card card) {
        this.hand.add(card);
    }
    
	//retourne true si la somme de deux cartes est égale à 21. False sinon
    public boolean isBlackjack() {
        if (this.getPoints() == 21 && this.hand.size() == 2)
            return true;
        else 
            return false;
    }
    
	// retourne true si la somme des points a une valeur supérieur a 21. False sinon.
    public boolean isBust() {
        if (this.getPoints() > 21)
            return true;
        else
            return false;
    }

    // fonction qui va vider les cartes des mains des joueurs quand on rejoue une partie.
    public void clear() {
        this.hand.clear();
    }
}
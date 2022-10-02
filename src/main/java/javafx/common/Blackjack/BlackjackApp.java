package javafx.common.Blackjack;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class BlackjackApp extends Application {
	
	private static BlackjackGame game;

	@Override
	public void start(@SuppressWarnings("exports") Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		
		//Interface
		primaryStage.setTitle("Blackjack");
		
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setPadding(new Insets(25,25,25,25)); //top left bottom right
		
		grid.setVgap(10);
		grid.setHgap(5);
		
		Label rule = new Label("Blackjack payout is 3:2");
		grid.add(rule, 1, 0);
		
		Label money = new Label("Money: ");
		grid.add(money, 0, 2);
		
		Label bet = new Label("Bet: ");
		grid.add(bet, 0, 4);
		
		Label info = new Label("Bet amount is required before click 'Play'");
		grid.add(info, 1, 3);
		
		Label dealer = new Label("============DEALER============");
		grid.add(dealer, 1, 5);
		
		Label cardsDealer = new Label("Cards: ");
		grid.add(cardsDealer, 0, 6);
		
		
		Label pointsDealer = new Label("Points: ");
		grid.add(pointsDealer, 0, 8);
		
		Label player = new Label("=============YOU=============");
		grid.add(player, 1, 9);
		
		Label cardsPlayer = new Label("Cards: ");
		grid.add(cardsPlayer, 0, 10);
		
		Label pointsPlayer = new Label("Points: ");
		grid.add(pointsPlayer, 0, 11);
		
		Label result = new Label("RESULT: ");
		grid.add(result, 0, 13);
		
				
		TextField moneyField = new TextField();
		moneyField.setEditable(false);
		grid.add(moneyField, 1, 2);
		
		TextField betField = new TextField();
		grid.add(betField, 1, 4);
		
		ListView<String> dealerHand = new ListView<>();
        dealerHand.getItems().addAll();
        dealerHand.setMaxSize(350, 70);
		
		
        grid.add(dealerHand, 1, 6);
        HBox hbox1 = new HBox(dealerHand);
        grid.add(hbox1, 1, 6);
        
        ListView<String> playerHand = new ListView<>();
        playerHand.getItems().addAll();
        playerHand.setMaxSize(350, 70);
        
        HBox hbox2 = new HBox(playerHand);
        grid.add(hbox2, 1, 10);
		
		
		TextField pointsDealerField = new TextField();
		grid.add(pointsDealerField, 1, 8);
		pointsDealerField.setEditable(false);
		
		TextField pointsPlayerField = new TextField();
		grid.add(pointsPlayerField, 1, 11);
		pointsPlayerField.setEditable(false);
		
		HBox buttonbox = new HBox(10);
		
		Button buttonHit = new Button("Hit");
        buttonHit.setDisable(true);
		Button buttonStand = new Button("Stand");
        buttonStand.setDisable(true);
		
		buttonbox.getChildren().add(buttonHit);
		buttonbox.getChildren().add(buttonStand);
		
		grid.add(buttonbox, 1, 12);
		
		TextField resultField = new TextField();
		grid.add(resultField, 1, 13);
		resultField.setEditable(false);
		
		HBox buttonbox1 = new HBox(10);
		
		Button buttonPlay = new Button("Play");
        buttonPlay.setDisable(false);
		Button buttonExit = new Button("Exit");
        buttonExit.setDisable(true);
		
		buttonbox1.getChildren().add(buttonPlay);
		buttonbox1.getChildren().add(buttonExit);
		
		buttonbox1.setAlignment(Pos.BOTTOM_LEFT);
		
		grid.add(buttonbox1, 1, 14);

	
		Scene scene = new Scene(grid, 400, 600);
		scene.getRoot().setStyle("-fx-font-family: 'serif'"); // declaration police macOS
		primaryStage.setScene(scene);
		primaryStage.show();//affiche l'interface
		
		
		
		//scenario lancement Blackjack
		game = new BlackjackGame();
		
		moneyField.setText(String.valueOf(game.loadMoney()));
        
         buttonPlay.setOnAction(e -> {
        	      	 
        	 if (!dealerHand.getItems().isEmpty() && !playerHand.getItems().isEmpty()) {
        		 
        		 if (game.isPush() || game.getPlayerHand().isBlackjack() || game.playerWins() || !game.playerWins()){
     	 			moneyField.setText(String.valueOf(game.getTotalMoney()));
     	 		} else{
     	 			moneyField.setText(String.valueOf(game.loadMoney()));
     	 		}
        		  		 
         		// On vide les listes
         		dealerHand.getItems().clear();
         		playerHand.getItems().clear();
         		
         		// On vide les champs
         		betField.clear();
         		pointsDealerField.clear();
         		pointsPlayerField.clear();
         		resultField.clear();
         		
         		// On remet les boutons à leur état initial
         		buttonPlay.setDisable(false);
         		buttonExit.setDisable(true);
         		buttonHit.setDisable(true);
         		buttonStand.setDisable(true);
         		      		
        	 }
        	 
			 
            // Lance le jeu si la mise est à la fois comprise entre mise minimale(5) & somme possedee mais aussi entre la mise minimale(5) & mise maximale(1000)
        	 if(game.isValidBet(Double.parseDouble(betField.getText()))){
        	 
            // Demarre le jeu
            game.deal();
            
            // enregistrer la mise dans le bet et valider le bouton hit et stand
            game.setBet(Double.parseDouble(betField.getText()));
            
            // afficher la liste des cartes du dealer 
            dealerHand.getItems().addAll(game.getDealerShowCard().display());
            
            // affiche les cartes du joueur
             for (Card card : game.getPlayerHand().getCards()) {
                 playerHand.getItems().add(String.valueOf(card.display()));
             }
             
           //desactive le champ de mise apres appui 'Play'
             if(playerHand.getItems() != null) {
             
             betField.setEditable(false);
             }
             
             //Scénario au clic sur le bouton Hit
             	buttonHit.setOnAction(hit -> {
             		
             		// afficher les deux cartes de la main du dealer sur la liste et calcule les points
                     if (game.getPlayerHand().getCards().size() == 2) {
                        dealerHand.getItems().addAll(game.getDealerHand().getCards().get(0).display());
                    }
             		pointsDealerField.setText(String.valueOf(game.getDealerHand().getPoints()));
                    
                    // ajouter une troisieme carte au player et calcul les points
                    game.hit();
                    playerHand.getItems().add(game.getPlayerHand().getCards().get(2).display());
             		pointsPlayerField.setText(String.valueOf(game.getPlayerHand().getPoints()));
             		
             		buttonHit.setDisable(true);
             		buttonStand.setDisable(true);
             		buttonPlay.setDisable(false);
                    buttonExit.setDisable(false);
                    betField.setEditable(true);
                    
                    // Liste des résultats
					resultField.setText(game.getResult());
					moneyField.setText(String.valueOf(game.getTotalMoney()));
					
					//Quand totalMoney est egal a 0 affiche un message pour demander de quitter le jeu
					if(game.getTotalMoney() == 0) {
						buttonPlay.setDisable(true);
			            buttonExit.setDisable(false); 	
						Alert alert = new Alert(Alert.AlertType.WARNING);
						  alert.setHeaderText("Warning!");
						  alert.setContentText("Your money reached 0. Press 'Exit' to leave the game");
						  alert.showAndWait();
						  return;				
					}
					
             	});
             	//Scénario au clic sur le bonton Stand
             	buttonStand.setOnAction(stand -> {
             		//Calcule les points du dealer
             		pointsDealerField.setText(String.valueOf(game.getDealerHand().getPoints()));
             		
             		//Calcule les points du player
             		pointsPlayerField.setText(String.valueOf(game.getPlayerHand().getPoints()));
             		
             		buttonHit.setDisable(true);
             		buttonStand.setDisable(true);
             		buttonPlay.setDisable(false);
                    buttonExit.setDisable(false);
                    betField.setEditable(true);
                    
                    // Liste des résultats
					resultField.setText(game.getResult());
					moneyField.setText(String.valueOf(game.getTotalMoney()));
					
					//Quand totalMoney est egal a 0 affiche un message pour demander de quitter le jeu
					if(game.getTotalMoney() == 0) {
						buttonPlay.setDisable(true);
			            buttonExit.setDisable(false); 	
						Alert alert = new Alert(Alert.AlertType.WARNING);
						  alert.setHeaderText("Warning!");
						  alert.setContentText("Your money reached 0. Press 'Exit' to leave the game");
						  alert.showAndWait();
						  return;					
					}
					
			 	});
                    
           //fonction exit pour quitter le jeu	
           buttonExit.setOnAction(exit -> {
             System.exit(0);
           });
                		 
         // Etat des boutons après le clic sur le bouton play
            buttonHit.setDisable(false);
            buttonStand.setDisable(false);
            buttonPlay.setDisable(true);
            buttonExit.setDisable(true);
            
        }
         
         // Création des alertes	
          else if(Double.parseDouble(betField.getText()) <= (game.getMinBet())) {
		   Alert alert = new Alert(Alert.AlertType.ERROR);
		   alert.getDialogPane().setStyle("-fx-font-family: 'serif'");
		  alert.setHeaderText("Invalid entry");
		  alert.setContentText("Bet amount must be at least 5");
		  alert.showAndWait();
		  return;
		} else if((game.getTotalMoney()) > 1000 && Double.parseDouble(betField.getText()) >= (game.getMaxBet())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.getDialogPane().setStyle("-fx-font-family: 'serif'");
		   alert.setHeaderText("Invalid entry");
		   alert.setContentText("Bet amount must not be over 1000");
		   alert.showAndWait();
		   return;
		} else if(Double.parseDouble(betField.getText()) > (game.getTotalMoney())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.getDialogPane().setStyle("-fx-font-family: 'serif'");
			alert.setHeaderText("Invalid entry");
			alert.setContentText("Bet amount can't be over the amount owned");
			alert.showAndWait();
			return;
		}    
     });  
}
	
	public static void main(String[] args) {
		launch(args); //execute la fonction start
	}
};

	


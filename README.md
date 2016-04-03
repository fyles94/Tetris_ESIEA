# Tetris_ESIEA

Réaliser par Rémy IM et Daren MOOROOGEN

Exécution du programme : 
- lancer le programme depuis l'IDE d'Eclipe

Features utilisées : 
- Oriented-Object, 
- Simple,
- Portable

Commandes de jeu :
- Pour bouger la pièce vers la gauche, touche Q
- Pour bouger la pièce vers la droite, touche D
- Pour faire tomber la pièce plus rapidement, touche S
- Pour faire tourner la pièce, touche Z

Architecture : 
Nous avons utiliser une architecture MVC, pas très difficile à mettre en place. 

Design Pattern/S.O.L.I.D. :
- Facade : la classe Tetris de notre package controller permet d'encapsuler la creation de la fenêtre et le lancement du jeu
- Observer : la classe Piece de notre package model peut faire voir le changement de son état


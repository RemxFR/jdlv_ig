# JEU DE LA VIE
## Front end

### Description

Cette application développée en JavaFX est la partie front du jeu de la vie.
Elle permet la création d'un nouveau compte utilisateur via l'interface graphique,
la connexion à la page de jeu, l'accès et la participation à un tchat en temps réel 
via l'utilisation de Socket, ainsi que le partage et la sauvegarde des règles customisées 
par le joueur.

### Fonctionnement

Développée en Java, via JavaFx, cette interface permet de gérer toutes
les interactions de l'utilisateur avec l'application.
De plus elle gère également l'aspect graphique et la simulation du jeu de la vie.
Connectée avec la partie backend à la fois en Socket et par des requêtes http, 
elle permet de tchatter en live avec les autres utilisateurs, mais aussi de sauvegarder
et de récupérer les règles customisées par l'utilisateur.

### Déploiement

Cette application peut être déployée via Docker ou directement à partir du jar.
Il est à préciser que la version de la jdk à utiliser pour ce faire est la: jdk21.
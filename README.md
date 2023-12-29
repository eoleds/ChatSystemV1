Dans notre projet, nous envoyons des messages string avec un NetworkController en fonction de l'action réalisée, le UserController contient une fonction qui se charge de la réception de chaque message et du choix de l'action à réaliser en fonction de celui-ci.

Nous avons essayé de faire en sorte que le NetworkController se charge de tout le trafic de paquet et le UserController du traitement lié aux utilisateurs (unicité username, update de la liste des userconnectés..)

diagrammes : https://drive.google.com/drive/u/0/folders/1DIh3fH26J1nbQ3RgZTMN5EKk5dP-OfFU

site projet et cahier des charges : 
https://arbimo.github.io/insa-4ir-advanced-prog/

A faire : 
-connexions tcp sur un port 
  -> le prof : "premier phase = je demande la connexion sur port de base d'envoie des messages (chez nous = 8888)
                deuxieme phase = il accepte et me dit qu'il est dipo sur tel port 
                troisieme phase = je me connecte sur son port dispo et lancement de la discussion (recuperation d'historique blabla)

-le thread controller doit lancer le thread initial(application) et permettre de lancer tous les threads fils(dicussions)
-les interfaces (swing)
-le db manager 
-database sqlite avec les historiques

idée : appeler ReceivedUDP dans l'initiation du NetworkController et pas du ThreadController ??????????????


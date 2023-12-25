Dans notre projet, nous envoyons des messages string avec un NetworkController en fonction de l'action réalisée, le UserController contient une fonction qui se charge de la réception de chaque message et du choix de l'action à réaliser en fonction de celui-ci.

Nous avons essayé de faire en sorte que le NetworkController se charge de tout le trafic de paquet et le UserController du traitement lié aux utilisateurs (unicité username, update de la liste des userconnectés..)

# Application d'apprentissage de répertoire et de style en musique irlandaise  

## programme initial de classification par IA :  

Ce projet est disponible sur github :  

[classification musicale en musique irlandaise](https://github.com/erwan29880/classifieur_musique_irlandaise)

## Récupération de la base de données :  

```bash 
docker-compose up -d --build
```

Vérifier que le container est monté : 
```bash 
docker ps
```

Pour le programme java et sa portabilité, récupération des données et création d'une base Sqlite :   

requirements python 3.9 :
```bash 
pip install mysql-connector-python
```
@Test :
```bash 
python app.py --filename test.db
```

Si les données s'affichent en console :   
@Test :
```bash 
rm test.db
```

@prod : créer la base Sqlite pour le programme java
```bash 
python app.py --filename music.db
```

@Prod : supprimer les composants inutiles
```bash 
docker-compose down
rm app.py 
rm docker-compose.yml 
rm -r docker
```

## utilisation du programme java :   
Lors de la première utilisation, le programme demande de sélectionner le dossier IRISH. Un fichier de configuration .ser est alors généré et évite la reconfiguration à chaque lancement de l'application. 
```bash 
java -jar api2.jar
```  

reconfigurer le programme si besoin : 
```bash 
java -jar api2.jar config
```    

Le programme interroge la base de données, selectionne 50 chemins d'accès pseudo-aléatoires, vérifie que les fichiers existent.
Un fichier playlist musicale (m3u) est enregistré sur le bureau. Une notification s'affiche dans une fenêtre en cas d'erreur.  

NB : la base de données musicale (les mp3), ne peut être fournie que si l'utilisateur a les originaux des enregistrements.

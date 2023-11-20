import mysql.connector
import sqlite3
import sys
import argparse

# requirement : 
# pip install mysql-connector-python

# Récupération des données provenant de Mysql (programme d'IA de classification musicale en musique irlandaise)
# Mysql est fourni avec le docker-compose. Le container doit être monté pour l'utilisation de ce programme.
#  Le container peut être détruit si les données ont bien été insérées dans sqlite
# 
# Voir le programme java -> bdd.Requetes() pour plus de détails
# Insertion des données dans une table sqlite pour la portablilité du programme java
#  
# pour lancer le programme : 
# python app.py --filename music.db
# 
# le programme java utilise le fichier music.db, enregistré à la racine du projet
# pour tester le programme d'insertion de données, utiliser une commande autre, par exemple :
# python app.py --filename test.db


# récupérer l'argument --filename
parser = argparse.ArgumentParser()
parser.add_argument('--filename', type=str, required=True, help='nom du fichier sqlite généré')
args = parser.parse_args()


# récupérer les données depuis mysql
class Mysql_data:
    
    
    def __init__(self) -> None:
        self.__mysql_conn = None
        self.__mysql_cursor = None
    
    
    # connextion mysql, cursor
    def __connect_mysql(self):
        try:
            self.__mysql_conn = mysql.connector.connect(host = "localhost",
                                    database = "jee",
                                    user="root", 
                                    password = "pa")
            self.__mysql_cursor = self.__mysql_conn.cursor()
        except Exception as e:
            print("connection mysql : " , e)
            sys.exit()
            
            
    # close cursor, connexion
    def __close_mysql(self):
        try:
            if (self.__mysql_cursor is not None):
                self.__mysql_cursor.close()
            if (self.__mysql_conn is not None):
                self.__mysql_conn.close()
        except Exception as e:
            print("close mysql : " , e)
            sys.exit()
    
    # récupérer les données
    def _get_data_from_mysql(self):
        res = None
        try:
            self.__connect_mysql()
            self.__mysql_cursor.execute("select * from music;")
            res = self.__mysql_cursor.fetchall()
            self.__close_mysql()
        except Exception as e:
            print("get data from mysql : " , e)
            sys.exit()
            
        return res
        

# créer le fichier sqlite, créer la table, insérer les données, vérifier les données
class Sqlite_data(Mysql_data):
    
    def __init__(self, db_name):
        super().__init__()
        self.__res = super()._get_data_from_mysql() 
        self.__conn = None 
        self.__cursor = None
        self.__db_name = db_name
      
    # créé le fichier .db, renvoie une exception si le fichier existe
    def __connect(self):
        try:
            self.__conn = sqlite3.connect(self.__db_name)
            self.__cursor = self.__conn.cursor()    
        except Exception as e:
            print("connection sqlite : " , e)
            sys.exit()
          
    # close connection
    def __close(self):
        try:
            if (self.__cursor is not None):
                self.__cursor.close()
            if (self.__conn is not None):
                self.__conn.close()
        except Exception as e:
            print("close sqlite : " , e)
            sys.exit()

    # création de la table
    def __create_table(self):
        try:
            self.__connect()
            sql = """create table music (id INTEGER PRIMARY KEY AUTOINCREMENT, path TEXT, vgg DOUBLE, cnn DOUBLE);"""
            self.__cursor.execute(sql)
            self.__conn.commit()
            self.__close()
        except Exception as e:
            print("create table sqlite : " , e)
            sys.exit()
            
    # insertion des données   
    def __insert_data(self):
        
        if self.__res == None:
            sys.exit()
        
        sqls = []
        inc = 0
        try:
            # tableau de strings -> requêtes
            for line in self.__res:
                if (inc!=0):
                    s = """insert into music(path, vgg, cnn) values ('{}', {}, {})""".format(line[0], line[1], line[2])
                    sqls.append(s)
                inc+=1
    
            # exécution des requêtes
            self.__connect()
            for i in sqls:
                self.__cursor.execute(i)
            self.__conn.commit()
            self.__close()
        except Exception as e:
            print("insert data sqlite : " , e)
            sys.exit()        

    # vérifier que les données ont été insérées
    def verify_data(self):
        try:
            self.__connect()
            self.__cursor.execute("select * from music;")
            res = self.__cursor.fetchall()
            self.__close()
            
            for entry in res:
                print(entry)
        except Exception as e:
            print("verify data sqlite : " , e)
            sys.exit()

    # méthode de classe pour lancer le programme
    def run(self):
        self.__create_table()
        self.__insert_data()
        self.verify_data()
        

if __name__ == "__main__":
    # lancement du programme
    sqli = Sqlite_data(args.filename)
    sqli.run()
     
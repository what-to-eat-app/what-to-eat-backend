import os
import pathlib
from sqlite3 import connect

file_name = str(pathlib.Path(__file__).parent.resolve()) + r'\resources\what_to_eat.db'



def set_up_connection():
    print(pathlib.Path(__file__).parent.resolve())
    file = open(file_name, 'w+')
    mydb = connect(file.name)

    cursor = mydb.cursor()
    cursor.execute('CREATE TABLE IF NOT EXISTS recipe(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200), url VARCHAR(200), category VARCHAR(200));')
    cursor.execute('CREATE TABLE IF NOT EXISTS ingredient(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200), measure VARCHAR(200), recipeid INTEGER, subtitle VARCHAR(200), FOREIGN KEY(recipeid) REFERENCES recipe(id));')
    cursor.execute('CREATE TABLE IF NOT EXISTS instruction(id INTEGER PRIMARY KEY AUTOINCREMENT, instruction_description VARCHAR(200), recipeid INTEGER, pos INTEGER, FOREIGN KEY(recipeid) REFERENCES recipe(id));')
    return cursor


# insert method from database
def insert(query, values):
    mydb = connect(file_name)
    curs = mydb.cursor()
    curs.execute(query, values)

    mydb.commit()


# select method from database
def select(query, values):
    mydb = connect(file_name)
    cursor = mydb.cursor()
    cursor.execute(query, values)

    results = cursor.fetchall()

    return results


class Database:

    def __init__(self, host, user, password, database):
        self.host = host
        self.user = user
        self.password = password
        self.database = database


query = 'select * from recipe;'
print(select(query, ''))


#include <iostream>
#include <fstream>
#include <string>
#include <regex>
#include "Person.h"
#include <list>

using namespace std;

bool equals(string const &v1, string const &v2)
{
    return v1 == v2;
}

void removeAccent(char &a)
{
    switch (a) {
    case -61: a = 'A'; break;
    }
}

list<Person> sortPersons(list<Person> const &original)
{
    list<Person> sorted;
    
    for(Person o : original)
    {
        if(sorted.empty())
        {
            sorted.push_front(o);
            continue;
        }

        //Creo un iterador para ubicarme en cada persona de la lista.
        list<Person>::iterator iterator;
        bool inserted = false;
        for(iterator = sorted.begin(); !inserted; iterator++)
        {
            //Variables necesarias
            Person s = *iterator;
            string oWord = o.getMiddleName();
            string sWord = s.getMiddleName();

            //Establezco los apellidos o el nombre a utilizar
            if(equals(oWord, sWord))
            {
                oWord = o.getLastName();
                sWord = s.getLastName();
                if(equals(oWord, sWord))
                {
                    oWord = o.getName();
                    sWord = s.getName();
                    if(equals(oWord, sWord))
                    {
                        oWord = o.getId();
                        sWord = s.getId();
                    }
                }
            }
            //Realizo el ordenamiento
            for(int i = 0; i < oWord.length() && i < sWord.length(); i++)
            {
                char alpha = oWord[i];
                char beta = sWord[i];

                if(alpha == -61 && i > 0)
                {
                    alpha = oWord[i + 1];
                }
                if(beta == -61 && i > 0)
                {
                    beta = sWord[i + 1];
                }

                //Elimino los acentos en caso de existir
                removeAccent(alpha);
                removeAccent(beta);

                if(alpha != beta)
                {
                    if (alpha < beta)
                    {
                        sorted.insert(iterator, o);
                        inserted = true;
                        break;
                    }
                    if (iterator == --sorted.end())
                    {
                        sorted.push_back(o);
                        inserted = true;
                    }
                    break;
                }
            }
        }
    }
    return sorted;
}

int main(int argc, char** argv) {
    

    // Open the file
    ifstream file;

    if(argc > 1)
    {
        file = ifstream(argv[1]);
    }else
    {
        file = ifstream("src\\salarios.txt");
    }

    if (!file.is_open()) {
        cout << "No se pudo abrir el archivo." << endl;
        return 1;
    }else
    {
        //Data Model
        list<Person> persons;

        // Read the file line by line
        string line;
        regex expression("^([^\t]+)\t([^\t]+)\t([^\t]+)\t([^\t]+)\t([^\t]+)\t([^\t]+)$");
        while (getline(file, line)) {
            smatch matcher;
            //Captures the regex matching
            if (regex_search(line, matcher, expression))
            {
                string id = matcher[1];
                string middleName = matcher[2];
                string lastName = matcher[3];
                string name = matcher[4];
                string birthDate = matcher[5];
                string salary = matcher[6];

                Person p(id, name, middleName, lastName, birthDate, salary);
                persons.push_back(p);
            }
        }
        file.close();

        persons = sortPersons(persons);
        for(Person p : persons)
        {
            cout << p.toString() << endl;
        }
    }
    return 0;
}

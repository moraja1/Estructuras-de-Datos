#include <iostream>
#include <fstream>
#include <string>
#include <regex>
#include "Person.h"
#include <list>
#include <locale>

using namespace std;

bool equals(string const &v1, string const &v2)
{
    return v1 == v2;
}

void removeAccents(string &a)
{
    char const lookFor = -61;
    string::size_type found = a.find(lookFor);

    while (found != string::npos) {
        char c1 = a[found];
        char c2 = a[++found];

        if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-95)) {
            // Caso de la vocal 'á' (á = -61 - 160)
            a.replace((found-1), 2, "a");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-87)) {
            // Caso de la vocal 'é' (é = -61 - 169)
            a.replace((found - 1), 2, "e");
        }
        else if (c1 == static_cast<char>(-61) && c2 >= static_cast<char>(-83)) {
            // Caso de la vocal 'í' (í = -61 - 173)
            a.replace((found - 1), 2, "i");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-77)) {
            // Caso de la vocal 'ó' (ó = -61 - 179)
            a.replace((found - 1), 2, "o");
        }
        else if (c1 == static_cast<char>(-61) && c2 >= static_cast<char>(-68)) {
            // Caso de la vocal 'ú' (ú = -61 - 163)
            a.replace((found - 1), 2, "u");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-127)) {
            a.replace((found - 1), 2, "A");
        }
        found = a.find(lookFor);
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
            //Busco las tildes y las elimino
            removeAccents(oWord);
            removeAccents(sWord);

            //Realizo el ordenamiento
            for(int i = 0; i < oWord.length() && i < sWord.length(); i++)
            {
                char alpha = oWord[i];
                char beta = sWord[i];

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
    locale::global(locale("en_US.UTF-8"));

    // Open the file
    ifstream file;

    if(argc > 1)
    {
        file = ifstream(argv[1]);
    }else
    {
        file = ifstream("salarios.txt");
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
        /*for(Person p : persons)
        {
            cout << p.toString() << endl;
        }*/
    }
    return 0;
}

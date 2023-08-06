#include <iostream>
#include <fstream>
#include <string>
#include <regex>
#include "Person.h"
#include <list>

using namespace std;

list<Person> sortPersons(list<Person> const &original)
{
    list<Person> sorted;
    
    for(Person o : original)
    {
        if(!sorted.empty())
        {
            for(Person s : sorted)
            {
	            //Cada persona de la lista original la comparo con cada
                //persona de la lista organizada. Así voy insertando
                //cada persona en la lista organizada en el orden correspondiente.
                //De momento tengo cada persona de Original como o y cada persona
                //de sorted como s.
            }
        }else
        {
            sorted.push_front(o);
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
    }
    return 0;
}

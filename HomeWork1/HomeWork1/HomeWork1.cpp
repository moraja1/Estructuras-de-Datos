#include <iostream>
#include <fstream>
#include <string>
#include <regex>
#include "Person.h"
#include <list>
#include <locale>
#include <iomanip>

using namespace std;

bool equals(string const &v1, string const &v2)
{
    return v1 == v2;
}

void removeAccents(string& a)
{
    char const lookFor = -61;
    string::size_type found = a.find(lookFor);

    while (found != string::npos) {
        char c1 = a[found];
        char c2 = a[++found];

        if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-95)) {
            // Caso de la vocal 'á'
            a.replace((found-1), 2, "a");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-87)) {
            // Caso de la vocal 'é'
            a.replace((found - 1), 2, "e");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-83)) {
            // Caso de la vocal 'í'
            a.replace((found - 1), 2, "i");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-77)) {
            // Caso de la vocal 'ó'
            a.replace((found - 1), 2, "o");
        }
        else if (c1 == static_cast<char>(-61) && (c2 == static_cast<char>(-68) || c2 == static_cast<char>(-70))) {
            // Caso de la vocal 'ú'
            a.replace((found - 1), 2, "u");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-127)) {
            // Caso de la vocal 'Á'
            a.replace((found - 1), 2, "A");
        }else if(c1 == static_cast<char>(-61) && c2 == static_cast<char>(-79))
        {
            // Caso de la vocal 'ñ'
            a.replace((found - 1), 2, "ñ");
        }
        found = a.find(lookFor);
    }
}

void normalizeAccents(string& a)
{
    char const lookFor = -61;
    string::size_type found = a.find(lookFor);

    while (found != string::npos) {
        char c1 = a[found];
        char c2 = a[++found];

        if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-95)) {
            // Caso de la vocal 'á'
            a.replace((found - 1), 2, "á");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-87)) {
            // Caso de la vocal 'é'
            a.replace((found - 1), 2, "é");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-83)) {
            // Caso de la vocal 'í'
            a.replace((found - 1), 2, "í");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-77)) {
            // Caso de la vocal 'ó'
            a.replace((found - 1), 2, "ó");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-70)) {
            // Caso de la vocal 'ú'
            a.replace((found - 1), 2, "ú");
        }else if(c1 == static_cast<char>(-61) && c2 == static_cast<char>(-68))
        {
            // Caso de la vocal 'ü'
            a.replace((found - 1), 2, "ü");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-127)) {
            // Caso de la vocal 'Á'
            a.replace((found - 1), 2, "Á");
        }
        else if (c1 == static_cast<char>(-61) && c2 == static_cast<char>(-79))
        {
            // Caso de la vocal 'ñ'
            a.replace((found - 1), 2, "ñ");
        }
        found = a.find(lookFor);
    }
}

void normalizePerson(Person& p)
{
	string middleName = p.getMiddleName(), lastName = p.getLastName(), name = p.getName();
    normalizeAccents(middleName); normalizeAccents(lastName); normalizeAccents(name);
    p.setMiddleName(middleName); p.setLastName(lastName); p.setName(name);
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
            char flag = 'a';
            Person s = *iterator;
            string oWord = o.getMiddleName();
            string sWord = s.getMiddleName();
            
            //Establezco los apellidos o el nombre a utilizar
            if(equals(oWord, sWord))
            {
                oWord = o.getLastName();
                sWord = s.getLastName();
                flag = 'b';
                if(equals(oWord, sWord))
                {
                    oWord = o.getName();
                    sWord = s.getName();
                    flag = 'c';
                    if(equals(oWord, sWord))
                    {
                        oWord = o.getId();
                        sWord = s.getId();
                        flag = 'd';
                    }
                }
            }
            //Busco las tildes y las elimino
            if(flag != 'd')
            {
                removeAccents(oWord);
                removeAccents(sWord);
            }
            //Realizo el ordenamiento
            for(int i = 0; i < oWord.length() && i < sWord.length(); i++)
            {
                char alpha = oWord[i];
                char beta = sWord[i];

                if(alpha != beta)
                {
                    normalizePerson(o);
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

void generateReport(const list<Person>& list)
{
    cout << "+-----------+--------------------------+------------------+----------------+----------------+----------------+---+\n";
    cout << "|        Id | Apellidos                | Nombre           |     Sal. bruto |    Deducciones |      Sal. neto | * |\n";
    cout << "+-----------+--------------------------+------------------+----------------+----------------+----------------+---+\n";

    for(Person p : list)
    {
        double salary = stod(p.getSalary());
        double deductions = 0;
        cout << "| " << std::setw(9) << p.getId() << " ";
        cout << "| " << setw(24) << right << p.getMiddleName() + " " + p.getLastName() << " ";
        cout << "| " << std::setw(16) << p.getName() << " ";
        cout << "| " << std::setw(15) << std::fixed << std::setprecision(2) << p.getSalary();
        cout << "| " << std::setw(15) << std::fixed << std::setprecision(2) << deductions;
        cout << "| " << std::setw(15) << std::fixed << std::setprecision(2) << (salary - deductions);
    	cout << "| " << "* |\n";
    }

    cout << "+-----------+--------------------------+------------------+----------------+----------------+----------------+---+\n";
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
        generateReport(persons);
    }
    return 0;
}

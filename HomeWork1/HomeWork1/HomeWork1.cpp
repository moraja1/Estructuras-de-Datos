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
        list<Person>::iterator it;
        bool inserted = false;
        for(it = sorted.begin(); !inserted; it++)
        {
            //Variables necesarias
            char flag = 'a';
            Person s = *it;
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
                        sorted.insert(it, o);
                        inserted = true;
                        break;
                    }
                    if (it == --sorted.end())
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

void generateReport(const list<Person>& persons)
{
    cout << "+-----------+--------------------------+------------------+----------------+----------------+----------------+---+\n";
    cout << "|        Id | Apellidos                | Nombre           |     Sal. bruto |    Deducciones |      Sal. neto | * |\n";
    cout << "+-----------+--------------------------+------------------+----------------+----------------+----------------+---+\n";

    list<Person>::const_iterator it = persons.cbegin();

    double averageSalary = 0;
    double averageNetSalary = 0;
    double minSalary = 0;
    double maxSalary = 0;
    double minNetSalary = 0;
    double maxNetSalary = 0;

    while(it != persons.cend())
    {
        Person p = *it;

        //Min and Max
        if (it == persons.cbegin())
        {
            minSalary = p.getDSalary();
            minNetSalary = p.getNetSalary();
            maxSalary = p.getDSalary();
            maxNetSalary = p.getNetSalary();
        }
        else
        {
            if (minSalary > p.getDSalary())
            {
                minSalary = p.getDSalary();
            }
            if (maxSalary < p.getDSalary())
            {
                maxSalary = p.getDSalary();
            }
            if (minNetSalary > (p.getDSalary() - p.getDeductions()))
            {
                minNetSalary = (p.getDSalary() - p.getDeductions());
            }
            if (maxNetSalary < (p.getDSalary() - p.getDeductions()))
            {
                maxNetSalary = (p.getDSalary() - p.getDeductions());
            }
        }

        //Sumo promedios
        averageSalary += p.getDSalary();
        averageNetSalary += (p.getDSalary() - p.getDeductions());

    	++it;
    }

    averageSalary = averageSalary / persons.size();
    averageNetSalary = averageNetSalary / persons.size();

    double minDeduction = minSalary - minNetSalary;
    double averageDeduction = averageSalary - averageNetSalary;
    double maxDeduction = maxSalary - maxNetSalary;
    
    for (Person p : persons)
    {
         cout << "| " << setw(9) << p.getId() << " ";
        cout << "| " << setw(24) << left << p.getMiddleName() + " " + p.getLastName() << " ";
        cout << "| " << setw(16) << p.getName() << " ";
        cout.imbue(locale("en_US"));
        cout << right;
        cout << "| " << setw(15) << fixed << setprecision(2) << p.getDSalary();
        cout << "| " << setw(15) << fixed << setprecision(2) << p.getDeductions();
        cout << "| " << setw(15) << fixed << setprecision(2) << (p.getDSalary() - p.getDeductions());
        cout << "| ";
        if(p.getDSalary() < averageSalary)
        {
            cout << "* |" << endl;
        }else
        {
            cout << "  |" << endl;
        }
    }
    cout << "+-----------+--------------------------+------------------+----------------+----------------+----------------+---+\n";
    cout << setw(58) << " ";
    cout << right;
    cout << "| " << setw(15) << fixed << setprecision(2) << minSalary;
    cout << "| " << setw(15) << fixed << setprecision(2) << minDeduction;
    cout << "| " << setw(15) << fixed << setprecision(2) << minNetSalary << "|\n";
    cout << setw(58) << " ";
    cout << right;
    cout << "| " << setw(15) << fixed << setprecision(2) << averageSalary;
    cout << "| " << setw(15) << fixed << setprecision(2) << averageDeduction;
    cout << "| " << setw(15) << fixed << setprecision(2) << averageNetSalary << "|\n";
    cout << setw(58) << " ";
    cout << right;
    cout << "| " << setw(15) << fixed << setprecision(2) << maxSalary;
    cout << "| " << setw(15) << fixed << setprecision(2) << maxDeduction;
    cout << "| " << setw(15) << fixed << setprecision(2) << maxNetSalary << "|\n";
    cout << setw(58) << " " << "+----------------+----------------+----------------+\n";
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

                //Obtains deductions
                p.setDSalary(stod(p.getSalary()));
                if (p.getDSalary() <= 950000.00)
                {
                    p.setDeductions(p.getDSalary() * 0.09);
                }
                else
                {
                    p.setDeductions(p.getDSalary() * 0.09 + (p.getDSalary() - 950000.00) * 0.05);
                }

                p.setNetSalary(p.getDSalary() - p.getDeductions());

                persons.push_back(p);
            }
        }
        file.close();

        persons = sortPersons(persons);
        generateReport(persons);
    }
    return 0;
}

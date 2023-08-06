#include "../Person.h"
#include <sstream>

Person::Person(string id, string name, string middleName, string lastName, string birthDate, string salary) {
    this->id = id;
    this->name = name;
    this->middleName = middleName;
    this->lastName = lastName;
    this->birthDate = birthDate;
    this->salary = salary;
}

string Person::getId() const
{
    return id;
}

string Person::getName() const
{
    return name;
}

string Person::getMiddleName() const
{
    return middleName;
}

string Person::getLastName() const
{
    return lastName;
}

string Person::getBirthDate() const
{
    return birthDate;
}

string Person::getSalary() const
{
    return salary;
}

string Person::toString()
{
    stringstream s;
    s << id + " - " + name + " " + middleName + " " + lastName + " - " + birthDate + " - " + salary;
    return s.str();
}

Person::~Person()
{
}


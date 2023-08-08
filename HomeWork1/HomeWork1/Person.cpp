#include "Person.h"
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

string Person::toString() const
{
    stringstream s;
    s << id + " - " + name + " " + middleName + " " + lastName + " - " + birthDate + " - " + salary;
    return s.str();
}
void Person::setName(string name)
{
    this->name = name;
}

void Person::setMiddleName(string middleName)
{
    this->middleName = middleName;
}

void Person::setLastName(string lastName)
{
    this->lastName = lastName;
}
Person::~Person()
= default;

double Person::getDSalary() const
{
    return dSalary;
}

void Person::setDSalary(double dSalary)
{
    this->dSalary = dSalary;
}


double Person::getDeductions() const
{
    return deductions;
}

void Person::setDeductions(double deductions)
{
    this->deductions = deductions;
}


double Person::getNetSalary() const
{
    return netSalary;
}

void Person::setNetSalary(double netSalary)
{
    this->netSalary = netSalary;
}

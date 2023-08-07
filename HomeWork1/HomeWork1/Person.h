#pragma once
#include <string>

using namespace std;

class Person {
private:
	string id;
	string name;
	string middleName;
	string lastName;
	string birthDate;
	string salary;

public:
	Person(string, string, string, string, string, string);
	string getId() const;
	string getName() const;
	string getMiddleName() const;
	string getLastName() const;
	string getBirthDate() const;
	string getSalary() const;
	string toString();
	void setName(string name);
	void setMiddleName(string middleName);
	void setLastName(string lastName);
	~Person();
};
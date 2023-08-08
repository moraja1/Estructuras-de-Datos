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
	double dSalary;
	double deductions;
	double netSalary;
public:
	Person(string, string, string, string, string, string);
	string getId() const;
	string getName() const;
	string getMiddleName() const;
	string getLastName() const;
	string getBirthDate() const;
	string getSalary() const;
	string toString() const;
	void setName(string name);
	void setMiddleName(string middleName);
	void setLastName(string lastName);
	double getDSalary() const;
	void setDSalary(double dSalary);
	double getDeductions() const;
	void setDeductions(double deductions);
	double getNetSalary() const;
	void setNetSalary(double netSalary);
	~Person();
};
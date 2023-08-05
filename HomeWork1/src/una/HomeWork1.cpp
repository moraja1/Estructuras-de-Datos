#include <iostream>
#include <fstream>
#include <string>
#include <regex>

using namespace std;

int main(int argc, char** argv) {

    // Open the file
    ifstream file;

    if(argc > 1)
    {
        file = ifstream(argv[1]);
    }else
    {
        file = ifstream("D:\\git\\Estructuras-de-Datos\\HomeWork1\\x64\\Debug\\salarios.txt");
    }

    if (!file.is_open()) {
        cout << "No se pudo abrir el archivo." << endl;
        return 1;
    }else
    {
        // Read the file line by line
        string line;
        regex expression("^([^\t]+)\t([^\t]+)\t([^\t]+)\t([^\t]+)\t([^\t]+)\t([^\t]+)$");
        while (getline(file, line)) {
            smatch matcher;
            //Captures the regex matching
            if (regex_search(line, matcher, expression))
            {
                for (int i = 1; i < matcher.length(); i++) {
                    //Obtains variables and creates persons
                    cout << matcher[i] << " ";
                }
                cout << endl;
            }
        }
    }

    return 0;
}

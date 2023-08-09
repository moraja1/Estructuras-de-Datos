package cr.una;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Main program = new Main();
        List<Person> persons = new List<>();

        String fileName;
        if (args.length != 1) {
            fileName = "src/main/java/cr/una/salarios.txt";
        } else {
            fileName = args[0];
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            Pattern regex = Pattern.compile("^([^\\t]+)\\t([^\\t]+)\\t([^\\t]+)\\t([^\\t]+)\\t([^\\t]+)\\t([^\\t]+)$");
            Matcher matcher;
            while ((line = br.readLine()) != null) {
                matcher = regex.matcher(line);
                Person p = null;
                if (matcher.find()) {
                    p = program.createPerson(matcher);
                }

                //Organize List while entering data
                if(persons.isEmpty() && p != null){
                    persons.add(p);
                }else{
                    boolean inserted = false;
                    for(int i = 0; i < persons.getSize(); i++){
                        Person s = persons.get(i);
                        String pWord = p.getMiddleName();
                        String sWord = s.getMiddleName();
                        if(pWord.equals(sWord))
                        {
                            pWord = p.getLastName();
                            sWord = s.getLastName();
                            if(pWord.equals(sWord))
                            {
                                pWord = p.getName();
                                sWord = s.getName();
                                if(pWord.equals(sWord))
                                {
                                    pWord = p.getId();
                                    sWord = s.getId();
                                }
                            }
                        }
                        for(int j = 0; j < pWord.length() && j < sWord.length(); j++)
                        {
                            char alpha = pWord.charAt(j);
                            char beta = sWord.charAt(j);

                            if(alpha != beta)
                            {
                                //normalizePerson(o);
                                if (alpha < beta)
                                {
                                    persons.insert(p, i);
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
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
    }

    private Person createPerson(Matcher matcher) {
        Person p;

        //Obtaining information
        String id = matcher.group(1);
        String middleName = matcher.group(2);
        String lastName = matcher.group(3);
        String name = matcher.group(4);
        String birthDate = matcher.group(5);
        double salary = Double.parseDouble(matcher.group(6));
        p = new Person(id, middleName, lastName, name, birthDate, salary);

        //Calculating deductions
        double deductions;
        deductions = (salary <= 950000.00) ? salary * 0.09 : salary * 0.09 + (salary - 950000.00) * 0.05;

        p.setDeductions(deductions);
        p.setNetSalary(salary - deductions);

        return p;
    }
}

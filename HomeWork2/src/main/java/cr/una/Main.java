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
                if (persons.isEmpty() && p != null) {
                    persons.add(p);
                } else {
                    boolean inserted = false;
                    for (int i = 0; !inserted; i++) {
                        Person s = persons.get(i);
                        String pWord = p.getMiddleName();
                        String sWord = s.getMiddleName();
                        if (pWord.equals(sWord)) {
                            pWord = p.getLastName();
                            sWord = s.getLastName();
                            if (pWord.equals(sWord)) {
                                pWord = p.getName();
                                sWord = s.getName();
                                if (pWord.equals(sWord)) {
                                    pWord = p.getId();
                                    sWord = s.getId();
                                }
                            }
                        }
                        pWord = program.removeAccents(pWord);
                        sWord = program.removeAccents(sWord);

                        for (int j = 0; j < pWord.length() && j < sWord.length(); j++) {
                            char alpha = pWord.charAt(j);
                            char beta = sWord.charAt(j);

                            if (alpha != beta) {
                                //normalizePerson(o);
                                if (alpha < beta) {
                                    persons.insert(p, i);
                                    inserted = true;
                                    break;
                                }
                                if (i == persons.getSize() - 1) {
                                    persons.add(p);
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

        System.out.println(persons);
    }

    private String removeAccents(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c1 = word.charAt(i);
            switch (c1)
            {
                case 'Á': word = word.replace(c1, 'A'); break;
                case 'É': word = word.replace(c1, 'E'); break;
                case 'Í': word = word.replace(c1, 'I'); break;
                case 'Ó': word = word.replace(c1, 'O'); break;
                case 'Ú':
                case 'Ü': word = word.replace(c1, 'U'); break;
                case 'á': word = word.replace(c1, 'a'); break;
                case 'é': word = word.replace(c1, 'e'); break;
                case 'í': word = word.replace(c1, 'i'); break;
                case 'ó': word = word.replace(c1, 'o'); break;
                case 'ú':
                case 'ü': word = word.replace(c1, 'u'); break;
                case 'ñ': word = word.replace(c1, 'n'); break;
            }
        }
        return word;
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

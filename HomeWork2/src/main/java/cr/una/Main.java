package cr.una;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        //Locale.setDefault(new Locale("es", "ES"));

        Main program = new Main();
        List<Person> persons = new List<>();
        double averageSalary = 0;
        double averageNetSalary = 0;
        double minSalary = 0;
        double maxSalary = 0;
        double minNetSalary = 0;
        double maxNetSalary = 0;

        String fileName;
        if (args.length != 1) {
            fileName = "src/main/java/cr/una/salarios.txt";
        } else {
            fileName = args[0];
        }
        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            Pattern regex = Pattern.compile("^([^\\t]+)\\t([^\\t]+)\\t([^\\t]+)\\t([^\\t]+)\\t([^\\t]+)\\t([^\\t]+)$");
            Matcher matcher;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                matcher = regex.matcher(line);
                Person p = null;
                if (matcher.find()) {
                    p = program.createPerson(matcher);

                    //Min and Max
                    if(first){
                        first = false;
                        minSalary = p.getSalary();
                        maxSalary = p.getSalary();
                        minNetSalary = p.getNetSalary();
                        maxNetSalary = p.getNetSalary();
                    }else{
                        if(minSalary > p.getSalary()) minSalary = p.getSalary();
                        if(minNetSalary > p.getNetSalary()) minNetSalary = p.getNetSalary();
                        if(maxSalary < p.getSalary()) maxSalary = p.getSalary();
                        if(maxNetSalary < p.getNetSalary()) maxNetSalary = p.getNetSalary();
                    }

                    //Averages
                    averageSalary += p.getSalary();
                    averageNetSalary += p.getNetSalary();
                }
                //Organize List while entering data
                if (persons.isEmpty() && p != null) {
                    persons.add(p);
                    continue;
                }
                boolean inserted = false;
                for (int i = 0; !inserted; i++) {
                    Person s = persons.get(i);
                    String pWord = p.getMiddleName();
                    String sWord = s.getMiddleName();
                    //Establishes word to use
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
                    for (int j = 0; j < pWord.length() && j < sWord.length(); j++) {
                        char alpha = program.removeAccents(pWord.charAt(j));
                        char beta = program.removeAccents(sWord.charAt(j));

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
        } catch (IOException e) {
            System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
        }
        averageSalary /= persons.getSize();
        averageNetSalary /= persons.getSize();

        printReport(persons, minSalary, averageSalary, maxSalary, minNetSalary, averageNetSalary, maxNetSalary);
    }

    private static void printReport(List<Person> persons, double minSalary, double averageSalary, double maxSalary,
                                    double minNetSalary, double averageNetSalary, double maxNetSalary) {
        String format = "+-----------+--------------------------+------------------+----------------+----------------+----------------+---+\n" +
                        "| %9s | %-24s | %-16s | %14s | %14s | %14s | %1s |\n" +
                        "+-----------+--------------------------+------------------+----------------+----------------+----------------+---+\n";
        System.out.print(String.format(format, "Id", "Apellidos", "Nombre", "Sal. bruto", "Deducciones", "Sal. neto", "*"));
        format = "| %-9s | %-24s | %-16s | %14s | %14s | %14s | %1s |\n";
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(2);

        for(int i = 0; i < persons.getSize(); i++){
            Person p = persons.get(i);
            String salary = numberFormat.format(p.getSalary());
            String deductions = numberFormat.format(p.getDeductions());
            String netSalary = numberFormat.format(p.getNetSalary());
            String overAverage = " ";
            if(p.getSalary() < averageSalary) overAverage = "*";
            System.out.print(String.format(format, p.getId(), p.getMiddleName() + " " + p.getLastName(), p.getName(),
                    salary, deductions, netSalary, overAverage));
        }
        System.out.print("+-----------+--------------------------+------------------+----------------+----------------+----------------+---+\n");
        format = " %-56s | %14s | %14s | %14s |\n";

        String sMinSalary = numberFormat.format(minSalary);
        String sAverageSalary = numberFormat.format(averageSalary);
        String sMaxSalary = numberFormat.format(maxSalary);
        String sMinNetSalary = numberFormat.format(minNetSalary);
        String sAverageNetSalary = numberFormat.format(averageNetSalary);
        String sMaxNetSalary = numberFormat.format(maxNetSalary);
        String sMinDeductions = numberFormat.format((minSalary - minNetSalary));
        String sAverageDeductions = numberFormat.format((averageSalary - averageNetSalary));
        String sMaxDeductions = numberFormat.format((maxSalary - maxNetSalary));

        System.out.print(String.format(format, " ",  sMinSalary, sMinDeductions, sMinNetSalary));
        System.out.print(String.format(format, " ",  sAverageSalary, sAverageDeductions, sAverageNetSalary));
        System.out.print(String.format(format, " ",  sMaxSalary, sMaxDeductions, sMaxNetSalary));
        System.out.print("                                                          +----------------+----------------+----------------+   \n");
    }

    private char removeAccents(char word) {
        switch (word)
        {
            case 'Á': word = 'A'; break;
            case 'É': word = 'E'; break;
            case 'Í': word = 'I'; break;
            case 'Ó': word = 'O'; break;
            case 'Ú':
            case 'Ü': word = 'U'; break;
            case 'á': word = 'a'; break;
            case 'é': word = 'e'; break;
            case 'í': word = 'i'; break;
            case 'ó': word = 'o'; break;
            case 'ú':
            case 'ü': word = 'u'; break;
            case 'ñ': word = 'n'; break;
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
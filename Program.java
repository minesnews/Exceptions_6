import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        String input = entry_data();
        System.out.println("Ваши введенные данные: " + input);

        try {
            verification_data(input);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String entry_data(){
        Scanner in = new Scanner(System.in);
        System.out.println("Введите ваши данные в формате: Фамилия Имя Отчество датарождения номертелефона пол. \nПол m - мужской, f - женский");
        String input = in.nextLine();
        in.close();
        return input;
    }

    public static void verification_data(String input){

        String[] array = input.split(" ");
        if (array.length != 6) {
            throw new NullPointerException("Неправильное число введенных данных.");
        }

        String surname = array[0];
        String name = array[1];
        String middleName = array[2];

        String data = array[3];
        String phone = array[4];
        String gender = array[5];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try {
            LocalDate loc_data = LocalDate.parse(data, formatter);
            Long phone_long = Long.parseLong(phone);

            if (gender.length()!=1 || !(gender.equalsIgnoreCase("f")|| gender.equalsIgnoreCase("m")))
            {
                throw new IllegalArgumentException("Неправильный ввод пола!");
            }
            
            char c_gend = gender.toLowerCase().charAt(0);
                
            
            write_file(surname, name, middleName, loc_data, phone_long, c_gend);

        } catch (DateTimeParseException  e) {
            throw new IllegalArgumentException("Неверный формат даты!");
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException("Неправильный ввод номера телефона!");
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка записи в файл");
        }               

        
    }

    public static void write_file(String surname, String name, String middleName, LocalDate loc_data, Long phone_long, char c_gend) throws IOException
    {
        String file = surname + "_" + name + "_" + middleName + ".txt";

        String line = String.format("%s %s %s %s %d %c", surname, name, middleName, loc_data, phone_long, c_gend);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(line);
            writer.newLine();
        }


    }
}

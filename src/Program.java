import java.io.*;
import java.util.*;

public class Program {
    private String channel;
    private BroadcastsTime time;
    private String name;

    public Program(String channel, BroadcastsTime time, String name) {
        this.channel = channel;
        this.time = time;
        this.name = name;
    }

    public BroadcastsTime getTime(){
        return time;}
    public String getName() {
        return this.name;}
    public String getChannel() {
        return this.channel;}


    @Override
    public String toString() {
        return channel + " " + time + " " + name;
    }

    public static void main(String[] args) throws IOException {
        Map<String, List<Program>> programsMap = new HashMap<>();
        List<Program> allPrograms = new ArrayList<>();

        Scanner scanner = new Scanner(new File("C:/Users/pro/Downloads/Telegram Desktop/schedule.txt"));
        String currentChannel = null;
        BroadcastsTime currentTime = null;


        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.startsWith("#")) {
                currentChannel = line.substring(1).trim();
            } else if (currentTime == null) {
                String[] timeParts = line.split(":");
                byte hour = Byte.parseByte(timeParts[0]);
                byte minutes = Byte.parseByte(timeParts[1]);
                currentTime = new BroadcastsTime(hour, minutes);
            } else {
                String name = line.trim();
                Program program = new Program(currentChannel, currentTime, name);
                allPrograms.add(program);

                programsMap.computeIfAbsent(currentChannel, k -> new ArrayList<>()).add(program);
                currentTime = null;
            }
        }
        scanner.close();

        Collections.sort(allPrograms, Comparator.comparing(Program::getTime));
        //вывести все программы, которые идут сейчас
        for (Program program : allPrograms) {
            System.out.println(program);
        }
        Calendar currentTime1 = Calendar.getInstance();
        int currentHour = currentTime1.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime1.get(Calendar.MINUTE);
        List<Program> currentPrograms = new ArrayList<>();
        for (Program program : allPrograms) {
            BroadcastsTime programTime = program.getTime();
            if (programTime.getHour() == currentHour && programTime.getMinutes() == currentMinute) {
                currentPrograms.add(program);
            }
        }
        System.out.println("Программы, которые идут сейчас:");
        for (Program program : currentPrograms) {
            System.out.println(program);
        }
        //найти все программы по некоторому названию

        Scanner inputScanner = new Scanner(System.in);
        System.out.print("Введите название программы для поиска: ");
        String searchName = inputScanner.nextLine();

        List<Program> foundPrograms = new ArrayList<>();
        for (Program program : allPrograms) {
            if (program.getName().toLowerCase().contains(searchName.toLowerCase())) {
                foundPrograms.add(program);
            }
        }

        System.out.println("Найденные программы с названием '" + searchName + "':");
        if (foundPrograms.isEmpty()) {
            System.out.println("Программы с таким названием не найдены.");
        } else {
            for (Program program : foundPrograms) {
                System.out.println(program);
            }
        }
        //найти все программы определенного канала, которые идут сейчас
        System.out.print("Введите название канала для поиска программ: ");
        String searchChannel = inputScanner.nextLine();

        List<Program> currentProgramsByChannel = new ArrayList<>();
        for (Program program : currentPrograms) {
            if (program.getChannel().equalsIgnoreCase(searchChannel)) {
                currentProgramsByChannel.add(program);
            }
        }

        System.out.println("Программы канала '" + searchChannel + "', которые идут сейчас:");
        if (currentProgramsByChannel.isEmpty()) {
            System.out.println("Программы данного канала, которые идут сейчас, не найдены.");
        } else {
            for (Program program : currentProgramsByChannel) {
                System.out.println(program);
            }
        }
        //найти все программы определенного канала, которые будут идти в некотором промежутке времени
        String searchChannelName = "Название канала";
        BroadcastsTime startTime = new BroadcastsTime((byte) 10, (byte) 0);
        BroadcastsTime endTime = new BroadcastsTime((byte) 12, (byte) 0);

        List<Program> programsByChannelAndTime = new ArrayList<>();
        for (Program program : allPrograms) {
            if (program.getChannel().equalsIgnoreCase(searchChannelName)) {
                BroadcastsTime programTime = program.getTime();
                if (programTime.between(startTime, endTime)) {
                    programsByChannelAndTime.add(program);
                }
            }
        }

        System.out.println("Программы канала '" + searchChannelName + "', которые будут идти в указанном промежутке времени:");
        if (programsByChannelAndTime.isEmpty()) {
            System.out.println("Программы данного канала в указанное время не найдены.");
        } else {
            for (Program program : programsByChannelAndTime) {
                System.out.println(program);
            }
        }
    }
}



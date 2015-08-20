package easylog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Luca Aguzzoli
 */
public class EasyLog {

    private static int currentDay = 0;
    private static File logFile;
    private static FileWriter fwLog;

    private EasyLog() {
    }

    /**
     * metodo che restituisce la data, l'ora, ecc...
     *
     * @return l'ora e la data come stringa
     */
    private static String getDate() {
        Calendar cal = new GregorianCalendar();
        return cal.getTime().toString();
    }

    /**
     * write a generic message into the log file
     *
     * @param message message to write into the log file
     */
    public static void writeMessage(String message) {
        check();
        try {
            fwLog.append("[" + getDate() + "]    INFO: " + message + "\n");
            fwLog.flush();
            System.out.println("[" + getDate() + "] " + message);
        } catch (IOException ex) {
            System.err.println("[" + getDate() + "] Impossibile scrivere messaggio: " + message);
        }
    }

    /**
     * Write an error message into the log file
     *
     * @param message message to write into the log file
     */
    public static void writeErrorMessage(String message) {
        check();
        try {
            fwLog.append("[" + getDate() + "]   ERROR: " + message + "\n");
            fwLog.flush();
            System.err.println("[" + getDate() + "] " + message);
        } catch (IOException ex) {
            System.err.println("[" + getDate() + "] Impossibile scrivere messaggio d'errore: " + message);
        }
    }

    /**
     * Write an error message into the log file
     *
     * @param message message to write into the log file
     */
    public static void writeWarningMessage(String message) {
        check();
        try {
            fwLog.append("[" + getDate() + "] WARNING: " + message + "\n");
            fwLog.flush();
            System.err.println("[" + getDate() + "] " + message);
        } catch (IOException ex) {
            System.err.println("[" + getDate() + "] Impossibile scrivere messaggio d'errore: " + message);
        }
    }

    /**
     * Write raw data without write first the type of the message
     *
     * @param data message to write into the log file
     */
    public static void writeRawData(String data) {
        check();
        try {
            fwLog.append(data + "\n");
            fwLog.flush();
            System.out.println(data);
        } catch (IOException ex) {
            System.err.println("[" + getDate() + "] Impossibile scrivere dati: " + data);
        }
    }

    /**
     * check the status of the log file. Create the file and/or the folders tree
     * if it doesn't exists.
     */
    private static void check() {
        File dir = checkDir();
        if (dir != null) {
            Calendar cal = new GregorianCalendar();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            try {
                if (fwLog != null) {
                    fwLog.flush();
                    fwLog.close();
                }
                dir.mkdirs();
                logFile = new File(dir.getPath() + File.separatorChar + day + ".log");
                logFile.createNewFile();
                fwLog = new FileWriter(logFile, true);
            } catch (IOException ex) {
                System.err.println("Impossibile eseguire il check delle date del file di log");
            }
        }
    }

    /*
     check the date and create the folder tree if doesn't exist
     */
    private static File checkDir() {
        Calendar cal = new GregorianCalendar();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (currentDay == day) {
            return null;
        } else {
            currentDay = day;
            return new File("log" + File.separatorChar + cal.get(Calendar.YEAR) + File.separatorChar + (cal.get(Calendar.MONTH) + 1));
        }
    }

}

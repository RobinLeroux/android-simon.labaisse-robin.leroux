package be.henallux.iesn.namurbynight.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EventDate {
    private Date openDate;
    private Date closeDate;

    public EventDate(Date openDate, Date closeDate) {
        setOpenDate(openDate);
        setCloseDate(closeDate);
    }
    public EventDate(Integer openDate, Integer closeDate) {
        setOpenDate(openDate);
        setCloseDate(closeDate);
    }
    public EventDate(String openDate, String closeDate) {
        setOpenDate(openDate);
        setCloseDate(closeDate);
    }

    //--- Gettors ---//
    public Date getOpenDate() {
        return this.openDate;
    }
    public Date getCloseDate() {
        return this.closeDate;
    }

    //--- Settors ---//
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }
    public void setOpenDate(Integer openDate) {
        this.openDate = new Date(openDate);
    }
    public void setOpenDate(String openDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(openDate);
        } catch (ParseException e) {
            System.out.println("Erreur lors de la conversion de la date en String");
        }
        this.openDate = convertedDate;
    }
    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
    public void setCloseDate(Integer closeDate) {
        this.closeDate = new Date(closeDate);
    }
    public void setCloseDate(String closeDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(closeDate);
        } catch (ParseException e) {
            System.out.println("Erreur lors de la conversion de la date en String");
        }
        this.closeDate = convertedDate;
    }

    //--- ---//
    public String toString() {
        SimpleDateFormat stringFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String convertedOpenDate = stringFormat.format(openDate);
        String convertedCloseDate = stringFormat.format(closeDate);

        String[] splitOpen = convertedOpenDate.split(" ");
        convertedOpenDate = splitOpen[0] + " à " + splitOpen[1];
        String[] splitClose = convertedCloseDate.split(" ");
        convertedCloseDate = splitClose[0] + " à " + splitClose[1];

        return "Du " + convertedOpenDate + " au " + convertedCloseDate;
    }
}

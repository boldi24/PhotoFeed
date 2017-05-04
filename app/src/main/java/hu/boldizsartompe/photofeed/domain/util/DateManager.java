package hu.boldizsartompe.photofeed.domain.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManager {

    private static SimpleDateFormat ft;

    public static String getCurrentDate(){
        Date date = new Date();
        if(ft == null) ft = new SimpleDateFormat("yyyy.MM.dd 'at' hh:mm aa");
        return ft.format(date);
    }
}

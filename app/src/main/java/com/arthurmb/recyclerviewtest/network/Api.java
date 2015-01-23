package com.arthurmb.recyclerviewtest.network;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by saulmm on 15/11/14.
 */
public class Api {

    public static String getLastBooks () {

        return "http://pastebin.com/raw.php?i=stiWy4fS";
    }


    public static String cleanJSON(String content) {

        // replace bad json params
        content = content.replace(")", "}");

//        // translate html entities
        content = content
            .replaceAll("&Atilde;&iexcl;", "&aacute;")
            .replaceAll("&Atilde;&copy;", "&eacute;")
           .replaceAll("&Atilde;&shy;", "&iacute;")
            .replaceAll("&Atilde;&sup3;", "&oacute;")
            .replaceAll("&Atilde;&ordm;", "&uacute;")
            .replaceAll("&Atilde;&plusmn;", "&ntilde;")
            .replaceAll("&Acirc;", "");

        // Expression to extract href links
        String hrefExpression = "<a?\\s?href=\"(\\S*?)\"/?>";

        // replace entities and a tags
//        content = String.valueOf(Html.fromHtml(content));
//        content = extractGroups(content, hrefExpression);

        return content;
    }


    public static String extractGroups (String content, String regEx) {
        Pattern patron = Pattern.compile(regEx);
        Matcher matcher = patron.matcher(content);

        try {
            while (matcher.find())
                content= content.replaceFirst(matcher.group(0), matcher.group(1));
        } catch (IndexOutOfBoundsException e) {
            Log.e("[ERROR] saulmm.open_libra.other.Utils.extractGroups ", "Index of bounds");
        }

        return content;
    }
}
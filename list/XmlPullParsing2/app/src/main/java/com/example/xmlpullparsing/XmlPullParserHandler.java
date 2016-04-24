package com.example.xmlpullparsing;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
 

public class XmlPullParserHandler {
    private List<Transaction> transactions = new ArrayList<Transaction>();
    private Transaction transaction;
    private String text;
 
    public List<Transaction> getTransactions() {
        return transactions;
    }
 
    public List<Transaction> parse(InputStream is) {
           try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser  parser = factory.newPullParser();
 
            parser.setInput(is, null);
 
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (tagname.equalsIgnoreCase("transactions")) {
                        // create a new instance of employee
                        transaction = new Transaction();
                    }
                    break;
 
                case XmlPullParser.TEXT:
                    text = parser.getText();
                    break;

                case XmlPullParser.END_TAG:
                    if (tagname.equalsIgnoreCase("transactions")) {
                        // add employee object to list
                        transactions.add(transaction);
                    }else if (tagname.equalsIgnoreCase("MsgDateTime")) {
                        transaction.setMsgDateTime(text);
                    }  else if (tagname.equalsIgnoreCase("PAN")) {
                        transaction.setPAN(Integer.parseInt(text));
                    } else if (tagname.equalsIgnoreCase("UserId")) {
                        transaction.setUserId(text);
                    } 
                    break;
 
                default:
                    break;
                }
                eventType = parser.next();
            }
 
        } catch (XmlPullParserException e) {e.printStackTrace();} 
        catch (IOException e) {e.printStackTrace();}
 
        return transactions;
    }
}
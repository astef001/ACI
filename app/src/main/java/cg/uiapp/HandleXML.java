package cg.uiapp;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
    public class HandleXML {
        private String PAN = "PAN";
        private String PaymCardDescr = "PaymCardDescr";
        private String FIID = "FIID";
        private String urlString = null;
        private XmlPullParserFactory xmlFactoryObject;
        public volatile boolean parsingComplete = true;

        public HandleXML(String url){
            this.urlString = url;
        }

        public String getPAN(){
            return PAN;
        }

        public String getPaymCardDescr(){
            return PaymCardDescr;
        }

        public String getFIID() {
            return FIID;
        }
        public void parseXMLAndStoreIt(XmlPullParser myParser) {
            int event;
            String text=null;

            try {
                event = myParser.getEventType();

                while (event != XmlPullParser.END_DOCUMENT) {
                    String name=myParser.getName();

                    switch (event){
                        case XmlPullParser.START_TAG:
                            break;

                        case XmlPullParser.TEXT:
                            text = myParser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if(name.equals("PAN")){
                                PAN = text;
                            }

                            else if(name.equals("FIID")){
                                FIID = myParser.getAttributeValue(null,"value");
                            }

                            else if(name.equals("PaymCardDescr")){
                                PaymCardDescr = myParser.getAttributeValue(null,"value");
                            }
                            else{
                            }
                            break;
                    }
                    event = myParser.next();
                }
                parsingComplete = false;
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void fetchXML(){
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        URL url = new URL(urlString);
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                        conn.setReadTimeout(10000 /* milliseconds */);
                        conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("GET");
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream stream = conn.getInputStream();
                        xmlFactoryObject = XmlPullParserFactory.newInstance();
                        XmlPullParser myparser = xmlFactoryObject.newPullParser();

                        myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        myparser.setInput(stream, null);

                        parseXMLAndStoreIt(myparser);
                        stream.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

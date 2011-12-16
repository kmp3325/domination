package net.yura.domination.mapstore;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import net.yura.abba.Events;
import net.yura.abba.persistence.AbbaRepository;
import net.yura.domination.engine.RiskUtil;
import net.yura.domination.engine.core.RiskGame;
import net.yura.domination.engine.translation.TranslationBundle;
import net.yura.domination.mapstore.gen.BinMapAccess;
import net.yura.mobile.gui.ActionListener;
import net.yura.mobile.gui.ButtonGroup;
import net.yura.mobile.gui.DesktopPane;
import net.yura.mobile.gui.Icon;
import net.yura.mobile.gui.components.Button;
import net.yura.mobile.gui.components.Component;
import net.yura.mobile.gui.components.List;
import net.yura.mobile.gui.components.OptionPane;
import net.yura.mobile.gui.components.Panel;
import net.yura.mobile.gui.components.RadioButton;
import net.yura.mobile.gui.components.TextComponent;
import net.yura.mobile.gui.layout.XULLoader;
import net.yura.mobile.gui.plaf.LookAndFeel;
import net.yura.mobile.gui.plaf.SynthLookAndFeel;
import net.yura.mobile.io.ServiceLink.Task;
import net.yura.mobile.util.Properties;
import net.yura.swingme.core.CoreUtil;

/**
 * @author Yura Mamyrin
 */
public class MapChooser implements ActionListener,MapServerListener {

    public Properties resBundle = CoreUtil.wrap(TranslationBundle.getBundle());

    XULLoader loader;
    ActionListener al;

    AbbaRepository repo;
    MapServerClient client;

    Vector mapfiles;

    // Nathans server
    //public static final String SERVER_URL="http://maps.domination.yura.net/xml/"
    //public static final String MAP_PAGE="maps.dot";
    //public static final String CATEGORIES_PAGE="categories.dot";

    // yura test server
    //public static final String SERVER_URL="http://domination.sf.net/maps2/maps/";
    //public static final String MAP_PAGE="";
    //public static final String CATEGORIES_PAGE="maps.xml";
    
    // theos server
    public static final String SERVER_URL="http://maps.yura.net/";
    public static final String MAP_PAGE="maps?format=xml";
    public static final String CATEGORIES_PAGE="categories?format=xml";

    public MapChooser(ActionListener al,Vector mapfiles) {
        this.al = al;
        this.mapfiles = mapfiles;


        // TODO,
        // TODO
        // TODO in android mode we should do this ONLY once!!!
        // TODO but on me4se it should happen each time!
        // TODO
        // TODO
        try {
            LookAndFeel laf = DesktopPane.getDesktopPane().getLookAndFeel();
            if (laf instanceof SynthLookAndFeel) {
                ((SynthLookAndFeel)laf).load( getClass().getResourceAsStream("/tabbar.xml") );
            }
            else {
                System.err.println("LookAndFeel not SynthLookAndFeel "+laf);
            }
        }
        catch(Exception ex) {
            // this is a none faital error, we will go on
            ex.printStackTrace();
        }






        try {
            loader = XULLoader.load( getClass().getResourceAsStream("/maps.xml") , this, resBundle);
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }

        Panel TabBar = (Panel)loader.find("TabBar");
        
        if (TabBar!=null) {
        
            Vector buttons = TabBar.getComponents();

            Icon on,off;

            try {
                on = new Icon("/bar_on.png");
                off = new Icon("/bar_off.png");
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            int w = off.getIconWidth() / buttons.size();
            for (int c=0;c<buttons.size();c++) {
                RadioButton b = (RadioButton)buttons.elementAt(c);
                Icon oni = on.getSubimage(c*w, 0, w, off.getIconHeight());
                Icon offi = off.getSubimage(c*w, 0, w, off.getIconHeight());

                b.setIcon(offi);
                b.setSelectedIcon(oni);
                b.setRolloverIcon(offi);
                b.setRolloverSelectedIcon(oni);

                b.setToolTipText( b.getText() );

                b.setText("");
                b.setHorizontalAlignment(Graphics.HCENTER);
                b.setMargin(0);

            }
        }

        List list = (List)loader.find("ResultList");
        list.setDoubleClick(true);
        list.setCellRenderer( new MapRenderer(this) );
        list.setFixedCellHeight(100);
        list.setFixedCellWidth(10); // will streach

        client = new MapServerClient(this,SERVER_URL);
        client.start();

        repo = new AbbaRepository(256 * 1024, 2 * 1024 * 1024, new BinMapAccess(), client);
        Events.CLIENT_RESOURCE.subscribe(repo);
        Events.SERVER_GET_RESOURCE.resubscribe(client,repo);

        activateGroup("MapView");

    }

    public void destroy() {

        Events.CLIENT_RESOURCE.unsubscribe(repo);
        Events.SERVER_GET_RESOURCE.unsubscribe(repo);

        repo.destroy();
        repo=null;

        client.kill();
        client=null;

    }

    public static Map createMap(String file) {
        
                Hashtable info = RiskUtil.loadInfo(file, false);

                Map map = new Map();
                map.setMapUrl( file );

                String name = (String)info.get("name");
                if (name==null) {
                    if (file.toLowerCase().endsWith(".map")) {
                        name = file.substring(0, file.length()-4);
                    }
                    else {
                        name = file;
                    }
                }
                map.setName(name);
                map.setDescription( (String)info.get("comment") );

                String prv = (String)info.get("prv");
                if (prv!=null) {
                    map.setPreviewUrl( "preview/"+prv );
                }
                else {
                    // TODO how do we differentiate between a preview pic and one we will need to reencode
                    prv = (String)info.get("pic");
                    map.setPreviewUrl( prv );
                }
        
                return map;
        
    }
    
    public static String getFileUID(String mapUrl) {
            int i = mapUrl.lastIndexOf('/');
            return (i>=0)?mapUrl.substring(i+1):mapUrl;
    }
    
    public void actionPerformed(String actionCommand) {
        if ("local".equals(actionCommand)) {
            mainCatList(actionCommand);

            Vector riskmaps = new Vector( mapfiles.size() );
            for (int c=0;c<mapfiles.size();c++) {
                String file = (String)mapfiles.elementAt(c);

                // we create a Map object for every localy stored map
                Map map = createMap(file);
                
                riskmaps.add( map );
            }

            setListData( null, riskmaps );

        }
        else if ("catagories".equals(actionCommand)) {
            mainCatList(actionCommand);

            client.makeRequestXML( CATEGORIES_PAGE );

        }
        else if ("top25".equals(actionCommand)) {
            mainCatList(actionCommand);

            activateGroup("Top25View");
        }
        else if ("search".equals(actionCommand)) {
            mainCatList(actionCommand);

            actionPerformed("doMapSearch");
        }
        else if ("update".equals(actionCommand)) {
            mainCatList(actionCommand);

            // TODO
        }
        else if ("TOP_NEW".equals(actionCommand)) {
            clearList();
            client.makeRequestXML( MAP_PAGE,"sort","TOP_NEW" );
        }
        else if ("TOP_RATINGS".equals(actionCommand)) {
            clearList();
            client.makeRequestXML( MAP_PAGE,"sort","TOP_RATINGS" );
        }
        else if ("TOP_DOWNLOADS".equals(actionCommand)) {
            clearList();
            client.makeRequestXML( MAP_PAGE,"sort","TOP_DOWNLOADS" );
        }
        else if ("listSelect".equals(actionCommand)) {
            List list = (List)loader.find("ResultList");
            Object value = list.getSelectedValue();
            if (value instanceof Category) {
                Category cat = (Category)value;
                clearList();
                client.makeRequestXML( MAP_PAGE,"category",cat.getId() );
            }
            else if (value instanceof Map) {
                Map map = (Map)value;

                String fileUID = getFileUID( map.mapUrl );

                String context = ((MapRenderer)list.getCellRenderer()).getContext();
                
                if (context!=null) { // we have a context, this means this is a remote map

                    if (client.isDownloading(fileUID)) { // we may be doing a update
                        
                        OptionPane.showMessageDialog(null, "already downloading", "message", 0);
                    }
                    else if ( mapfiles.contains(fileUID) ) {

                        Hashtable info = RiskUtil.loadInfo(fileUID, false);

                        String ver = (String)info.get("ver");
                        
                        if (map.getVersion()!=null && !"".equals(map.getVersion()) && !"1".equals(map.getVersion()) && !map.getVersion().equals( ver ) ) {
                            // update needed!!!
                            
                            client.downloadMap( getURL(context, map.mapUrl ) );
                            list.repaint();
                            return;
                        }
                        
                        String pic = (String)info.get("pic");
                        String crd = (String)info.get("crd");
                        String imap = (String)info.get("map");
                        String prv = (String)info.get("prv");
                        
                        if ( !fileExists(pic) || !fileExists(crd) || !fileExists(imap) || (prv!=null && !fileExists("preview/"+prv)) ) {
                            // we are missing a file, need to re-download this map
                        
                            client.downloadMap( getURL(context, map.mapUrl ) );
                            list.repaint();
                            return;
                            
                        }
                        
                        // so we already have this map, just fire event to load it
                        chosenMap(fileUID);
                        
                    }
                    else {

                        client.downloadMap( getURL(context, map.mapUrl ) );
                        list.repaint();
                    }
                }
                else { // this is a local map, we will fire the event right away that we got it
                    
                    chosenMap(fileUID);

                }
            }
            //else value is null coz the list is empty
        }
        else if ("defaultMap".equals(actionCommand)) {

            chosenMap( RiskGame.getDefaultMap() );

        }
        else if ("cancel".equals(actionCommand)) {

            al.actionPerformed(null);

        }
        else if ("doMapSearch".equals(actionCommand)) {
            String text = ((TextComponent)loader.find("mapSearchBox")).getText();
            clearList();
            if (text != null && !"".equals(text)) {
                client.makeRequestXML( MAP_PAGE,"search", text );
            }
        }
        else {
            System.out.println("Unknown command "+actionCommand);
        }
    }
    
    public static boolean fileExists(String fileUID) {
        
        java.io.InputStream file=null;
        try {
            file = RiskUtil.openMapStream(fileUID);
        }
        catch (Exception ex) { } // not found?
        finally{ net.yura.mobile.io.FileUtil.close(file); }
        
        return (file!=null); // we already have this file
    }
    
    private void chosenMap(String mapName) {
        
        selectedMap = mapName;
        al.actionPerformed(null);

    }
    
    public static String getURL(String context,String mapUrl) {
        
        if (mapUrl.indexOf(':')<0 && context!=null) { // we do not have a full URL, so we pre-pend the context
            if (mapUrl.startsWith("/")) {
                mapUrl = context.substring(0, context.indexOf('/', "http://.".length()) ) + mapUrl;
            }
            else {
                mapUrl = context + mapUrl;
            }
        }
        
        return mapUrl;
    }

    public void mainCatList(String actionCommand) {
        Enumeration group = ((ButtonGroup)loader.getGroups().get("MapView")).getElements();
        while (group.hasMoreElements()) {
            Button button = (Button)group.nextElement();
            String action = button.getActionCommand();
            Component panel = loader.find(action+"Bar");
            panel.setVisible( action.equals(actionCommand) );
        }

        clearList();

    }

    void clearList() {
        setListData( null, new Vector(0) ); // todo, use a constant?

        getRoot().revalidate();
        getRoot().repaint();
    }

    public Panel getRoot() {
        return ((Panel)loader.getRoot());
    }

    private String selectedMap;
    public String getSelectedMap() {
        return selectedMap;
    }

    public void gotResultXML(String url,Task task) {
        String method = task.getMethod();


        Object param = task.getObject();
        if ("categories".equals(method)) {
            if (param instanceof Vector) {
                setListData( url, (Vector)param );
            }
        }
        else if ("maps".equals(method)) {
            if (param instanceof Hashtable) {
                Hashtable map = (Hashtable)param;

                map.get("search");
                map.get("author");
                map.get("category");

                map.get("offset");
                map.get("total");

                setListData( url, (Vector)map.get("maps") );
            }
        }

    }
    
    public void onError(String error) {
        // TODO make this better
        OptionPane.showMessageDialog(null, error , "Error!", OptionPane.ERROR_MESSAGE);
    }

    public void downloadFinished(String download) {

        if ( !this.mapfiles.contains( download ) ) {
            this.mapfiles.addElement( download );
        }
        else {
            OptionPane.showMessageDialog(null, "got map, but we already have it "+download, "error", 0);
        }
    }

    public static String getContext(String url) {
        if (url!=null) {
            int i = url.lastIndexOf('/');
            if (i> "http://.".length() ) {
                url = url.substring(0, i+1);
            }
        }
        return url;
    }
    
    private void setListData(String url,Vector items) {
        
        String context = getContext(url);
        
        List list = (List)loader.find("ResultList");
        
        ((MapRenderer)list.getCellRenderer()).setContext(context);
        
        list.setListData( items );
        
        getRoot().revalidate();
        getRoot().repaint();
        
    }

    private void activateGroup(String string) {

        String mincat = ((ButtonGroup)loader.getGroups().get(string)).getSelection().getActionCommand();
        actionPerformed(mincat);

    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
}

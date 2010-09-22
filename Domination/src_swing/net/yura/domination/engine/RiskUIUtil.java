/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.yura.domination.engine;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import net.yura.domination.engine.core.RiskGame;
import net.yura.domination.engine.guishared.AboutDialog;
import net.yura.domination.engine.guishared.BrowserLauncher;
import net.yura.domination.engine.guishared.RiskFileFilter;
import net.yura.domination.engine.translation.TranslationBundle;

/**
 *
 * @author Yura Mamyrin
 */
public class RiskUIUtil {
    // TODO missing:
    //PicturePanel.getImage(
    // setupMapsDir(null) should be called b4 the Risk() object is created

    static {
        // this could have alredy been set by lobby, so only set it if its null
        if (RiskUtil.streamOpener==null) {
            RiskUtil.streamOpener = new RiskIO() {
                public InputStream openStream(String name) throws IOException {
                    return getRiskFileURL(name).openStream();
                }
                public InputStream openMapStream(String name) throws IOException {
                    return new URL(mapsdir,name).openStream();
                }
                public ResourceBundle getResourceBundle(Class c, String n, Locale l) {
                    return ResourceBundle.getBundle(c.getPackage().getName()+"."+n, l );
                }
                public void openURL(URL url) throws Exception {
                    riskOpenURL(url);
                }
                public void openDocs(String doc) throws Exception {
                    riskOpenURL(getRiskFileURL(doc));
                }
                public void saveGameFile(String name,Object obj) throws Exception {
                    saveFile(name,obj);
                }
                public InputStream loadGameFile(String file) throws Exception {
                    return getLoadFileInputStream(file);
                }
            };
        }
    }

    public static URL mapsdir;

    public static Applet applet;
    private static String webstart;

    private static boolean nosandbox;

    public static boolean checkForNoSandbox() {
            return nosandbox;
    }


    private static Map UIImagesReferences = new HashMap();

    public static BufferedImage getUIImage(Class c,String name) {


		try {
			String id = c+" - "+name;
			WeakReference wr = (WeakReference)UIImagesReferences.get(id);

			if (wr!=null) {
				BufferedImage img = (BufferedImage)wr.get();
				if (img!=null) {
					return img;
				}
			}
			BufferedImage img = ImageIO.read( c.getResource(name) );

			UIImagesReferences.put(id,new WeakReference(img));

			return img;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

	}



	/**
	 * Opens the online help
	 * @return boolean Return true if you open the online help, returns false otherwise
	 */
	private static void riskOpenURL(URL docs) throws Exception {



		if (applet != null) {

			applet.getAppletContext().showDocument(docs,"_blank");
		}
		else if (webstart != null) {

			javax.jnlp.BasicService bs = (javax.jnlp.BasicService)javax.jnlp.ServiceManager.lookup("javax.jnlp.BasicService");

			boolean good = bs.showDocument(docs);

			if (!good) {

				throw new Exception("unable to open URL: "+docs);
			}

		}
		else {

			BrowserLauncher.openURL(docs.toString());

		}



/*
		if (applet == null ) {

			File file = new File(docs);
			openURL(new URL("file://" + file.getAbsolutePath()) );

		}
		else {

			URL url = applet.getCodeBase(); // get url of the applet

			openURL(new URL(url+docs));

		}


		try {

			String cmd=null;

			String os = System.getProperty("os.name");

			if ( os != null && os.startsWith("Windows")) {
				cmd = "rundll32 url.dll,FileProtocolHandler file://"+ file.getAbsolutePath();
			}
			else {
				cmd = "mozilla file://"+ file.getAbsolutePath();
			}

			Runtime.getRuntime().exec(cmd);

			return true;
		}
		catch(IOException x) {
			return false;
		}
*/
	}


	private static URL getRiskFileURL(String a) {

		try {

			if (applet!=null) {

				return new URL( applet.getCodeBase(), a );

			}
			else if (webstart!=null) {

				javax.jnlp.BasicService bs = (javax.jnlp.BasicService)javax.jnlp.ServiceManager.lookup("javax.jnlp.BasicService");

				return new URL( bs.getCodeBase() , a);

			}
			else {

				return new File(a).toURI().toURL();

			}



		}
		catch (Exception e) {

			throw new RuntimeException(e);

		}

	}


	public static void setupMapsDir(Applet a) {

		applet = a;

		// only ever call this method once
		if (mapsdir==null) {

		    // if applet is null check for webstart!
		    if (applet==null) {

			webstart = System.getProperty("javawebstart.version");

			if (webstart==null) {

				nosandbox=true;

				// we only want to setup the look and feel outside a sandbox
				// though this WILL work inside too
				setupLookAndFeel();

			}

		    }

		    try {

			if (checkForNoSandbox()) {

				File mapsdir1 = new File("maps");

				// riskconfig.getProperty("default.map")

				String dmname = RiskGame.getDefaultMap();

				while ( !(new File(mapsdir1, dmname ).exists()) ) {

					JOptionPane.showMessageDialog(null,"Can not find map: "+dmname );

					JFileChooser fc = new JFileChooser( new File(".") );

					//RiskFileFilter filter = new RiskFileFilter(RiskFileFilter.RISK_SAVE_FILES);
					//fc.setFileFilter(filter);

					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					fc.setDialogTitle("Select maps directory");

					int returnVal = fc.showOpenDialog(null);
					if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {

						mapsdir1 = fc.getSelectedFile();

					}
					else {

						System.exit(0);

					}


				}


				mapsdir = mapsdir1.toURI().toURL();
			}
			else {

				mapsdir = getRiskFileURL( "maps/");

			}

		    }
		    catch (Exception e) {

			throw new RuntimeException(e);

		    }
		}

	}

	public static Frame findParentFrame(Container c) {


		return (Frame)javax.swing.SwingUtilities.getAncestorOfClass(Frame.class, c);

/*
		// this does not work as using the method setContentPane makes this method return null

		while(c != null) {

			if (c instanceof Frame) return (Frame)c;

			c = c.getParent();
		}
		return (Frame)null;
*/
	}

        public static String getNewFileOLD(Frame f,String a) {

            String dir = mapsdir.toString();
            File md;

            try {

                    md = new File(new URI(dir));

            }
            catch(IllegalArgumentException e) {

                    // this is an attempt at a crazy workaround that should not really work
                    if ( dir.startsWith("file://") ) {

                            md = new File( dir.substring(5,dir.length()).replaceAll("\\%20"," ") );
                    }
                    else {

                            System.err.println("this should never happen! bad file: "+dir);

                            md = new File( "maps/" );

                    }

                    // There is a bug in java 1.4/1.5 where it can not convert a URL like
                    // file://Claire/BIG_DISK/Program Files/Risk/maps/
                    // into a File Object so we will just try and make a simple file
                    // object, and hope it works

                    // java.lang.IllegalArgumentException: URI has an authority component
                    // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5086147
                    // This has been fixed in java 1.6

                    // also can not have %20 in the name on 1.4/1.5, needs to be " ".

            }
            catch(Exception e) {

                    throw new RuntimeException("Cant create file: "+ dir, e);

            }

            JFileChooser fc = new JFileChooser( md );
            RiskFileFilter filter = new RiskFileFilter(a);
            fc.setFileFilter(filter);

            int returnVal = fc.showOpenDialog( f );

            if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {

                    java.io.File file = fc.getSelectedFile();

                    // useless chack, but sometimes this is null
                    if (file==null) { return null; }

                    return file.getName();

            }

            return null;
            
        }



        public static String getNewFile(Frame f,String a) {

            if (checkForNoSandbox()) {
                if ("map".equals(a)) {
                    MapChooserSwingWrapper ch;
                    try {
                        // try and start new map chooser,
                        // on fail revert to using the old one
                        ch = new MapChooserSwingWrapper();
                    }
                    catch (Throwable th) {
                        return getNewFileOLD(f, a);
                    }
                    return ch.getNewMap(f);
                }
                else {
                    return getNewFileOLD(f, a);
                }
            }
            else {

                return getNewFileInSandbox(f, a);
            }

        }

	public static String getNewFileInSandbox(Frame f,String a) {


            String names=null;

            if (applet!=null) {

                    names = applet.getParameter(a);
            }
            else if (webstart!=null) {

                    if ("map".equals(a)) {

                            names = maps;
                    }
                    else if ("cards".equals(a)) {

                            names = cards;
                    }
            }

            Vector namesvector = new Vector();

            StringTokenizer tok = new StringTokenizer( names, ",");
            while (tok.hasMoreTokens()) {

                    namesvector.add( tok.nextToken() );

            }

            JComboBox combobox = new JComboBox( namesvector );

            // Messages
            Object[] message = new Object[] {
                    TranslationBundle.getBundle().getString("core.error.applet"),
                    combobox
            };

            // Options
            String[] options = { "OK","Cancel" };

            int result = JOptionPane.showOptionDialog(
                    f,				// the parent that the dialog blocks
                    message,			// the dialog message array
                    "select "+a,			// the title of the dialog window
                    JOptionPane.OK_CANCEL_OPTION,	// option type
                    JOptionPane.QUESTION_MESSAGE,	// message type
                    null,				// optional icon, use null to use the default icon
                    options,			// options string array, will be made into buttons
                    options[0]			// option that should be made into a default button
            );

            if (result==JOptionPane.OK_OPTION) {
                    return combobox.getSelectedItem()+"";
            }

            return null;

	}

	public static String getLoadFileName(Frame frame,String dir,String extension) {

		if (applet!=null) {

			showAppletWarning(frame);

			return null;
		}
		if (webstart!=null) {

			try {

				javax.jnlp.FileOpenService fos = (javax.jnlp.FileOpenService)javax.jnlp.ServiceManager.lookup("javax.jnlp.FileOpenService");

				javax.jnlp.FileContents fc = fos.openFileDialog(dir, new String[] { extension } );

				if (fc!=null) {

					fileio.put(fc.getName(),fc);

					return fc.getName();
				}
				else {

					return null;
				}
			}
			catch(Exception e) {

				return null;

			}
		}
		else {

			JFileChooser fc = new JFileChooser(dir);

			fc.setFileFilter(new RiskFileFilter(extension));

			int returnVal = fc.showDialog( frame , TranslationBundle.getBundle().getString("mainmenu.loadgame.loadbutton"));
			if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {

				java.io.File file = fc.getSelectedFile();
				// Write your code here what to do with selected file
				return file.getAbsolutePath();

			} else {
				// Write your code here what to do if user has canceled Open dialog
				return null;
			}
		}


	}

	public static InputStream getLoadFileInputStream(String file) throws Exception {

		// it is impossible for a applet to get here

		if (webstart!=null) {

			javax.jnlp.FileContents fc = (javax.jnlp.FileContents)fileio.remove(file);

			return fc.getInputStream();

		}
		else {

			return new FileInputStream(file);

		}

	}


	public static String getSaveFileName(Frame frame,String dir,String extension) {

		if (applet!=null) {

			showAppletWarning(frame);

			return null;
		}
		if (webstart!=null) {

			JOptionPane.showMessageDialog(frame,"Please make sure to select a file name ending with \"."+extension+"\"");
			return dir+"filename."+extension;

		}
		else {

			JFileChooser fc = new JFileChooser(dir);
			fc.setFileFilter(new RiskFileFilter(extension));

			int returnVal = fc.showSaveDialog( frame );
			if (returnVal == JFileChooser.APPROVE_OPTION) {

				java.io.File file = fc.getSelectedFile();
				// Write your code here what to do with selected file

				String fileName = file.getAbsolutePath();

				if (!(fileName.endsWith( "." + extension ))) {
					fileName = fileName + "." + extension;
				}

				return fileName;

			} else {
				// Write your code here what to do if user has canceled Save dialog
				return null;
			}
		}


	}

	public static void saveFile(String name,Object obj) throws Exception {

		// it is impossible for a applet to get here

		if (webstart!=null) {

			ByteArrayOutputStream stor = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(stor);
			out.writeObject(obj);
			out.flush();
			out.close();
			InputStream stream = new ByteArrayInputStream(stor.toByteArray());

			javax.jnlp.FileSaveService fss = (javax.jnlp.FileSaveService)javax.jnlp.ServiceManager.lookup("javax.jnlp.FileSaveService");

			javax.jnlp.FileContents fc = fss.saveFileDialog(name.substring(0,name.indexOf('/')+1), new String[]{ name.substring(name.indexOf('.')+1) }, stream, name.substring(name.indexOf('/')+1,name.indexOf('.')) );

		}
		else {

			FileOutputStream fileout = new FileOutputStream(name);
			ObjectOutputStream objectout = new ObjectOutputStream(fileout);
			objectout.writeObject(obj);
			objectout.close();

		}

	}

	public static void showAppletWarning(Frame frame) {

		JOptionPane.showMessageDialog(frame,
			TranslationBundle.getBundle().getString("core.error.applet")
		);

	}

	public static String getSystemInfoText() {

		ResourceBundle resb = TranslationBundle.getBundle();

		String netInfo,patch,home,cpu,name,info;

		if (checkForNoSandbox()) {
			patch = " " + System.getProperty("sun.os.patch.level") + " (" + System.getProperty("sun.arch.data.model") + "bit)";
			home = System.getProperty("java.home");
			cpu = System.getProperty("sun.cpu.isalist");
			name = System.getProperty("java.runtime.name") + " ("+ System.getProperty("java.runtime.version") +")";
			info = System.getProperty("java.vm.info");

			// we CAN do this outside the sandbox, but for some reason it promps the webstart
			try {
				netInfo = InetAddress.getLocalHost().getHostAddress() + " (" + InetAddress.getLocalHost().getHostName() +")" ;
			}
			catch (UnknownHostException e) {
				netInfo = resb.getString("about.nonetwork");
			}
		}
		else {
			patch = "?";
			home = "?";
			cpu = "?";
			info = "?";

			if (applet!=null) {

				name = "applet";
			}
			else if (webstart!=null) {

				name = "web start ("+webstart+")";
			}
			else {
				name = "?";

			}


			netInfo = "?";
		}


		return		" " + Risk.RISK_VERSION + " (save: " + RiskGame.SAVE_VERSION + " network: "+RiskGame.NETWORK_VERSION+") \n" +
				" " + "system:"+java.util.Locale.getDefault()+" current:" + resb.getLocale() + "\n" +
				" " + netInfo + " \n" +
				" " + System.getProperty("os.name") + " " + System.getProperty("os.version") +" "+ patch + " on " + System.getProperty("os.arch") + " \n" +
				" " + cpu + " \n" +
				" " + UIManager.getLookAndFeel() + " \n" +
				" " + System.getProperty("java.vendor") + " \n" +
				" " + System.getProperty("java.vendor.url") + " \n" +
				" " + name +" \n" +
				" " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.version") +", "+ info +") \n" +
				" " + System.getProperty("java.specification.version") +" ("+ System.getProperty("java.version") +") \n" +
				" " + home + " \n" +
				" " + System.getProperty("java.class.version");


	}


	public static void openAbout(Frame frame,String product,String version) {


		AboutDialog aboutDialog = new AboutDialog( frame , true, product, version);
		Dimension frameSize = frame.getSize();
		Dimension aboutSize = aboutDialog.getSize();
		int x = frame.getLocation().x + (frameSize.width - aboutSize.width) / 2;
		int y = frame.getLocation().y + (frameSize.height - aboutSize.height) / 2;
		if (x < 0) x = 0;
		if (y < 0) y = 0;
		aboutDialog.setLocation(x, y);
		aboutDialog.setVisible(true);

	}


	private static String lobbyAppletURL;
	private static String lobbyURL;

	public static boolean getAddLobby(Risk risk) {

		boolean canlobby = false;

			if (checkForNoSandbox()) {

				try {


					URL url = new URL(RiskUtil.RISK_LOBBY_URL);

					BufferedReader bufferin=new BufferedReader( new InputStreamReader(url.openStream()) );

					StringBuffer buffer = new StringBuffer();

					String input = bufferin.readLine();

					while(input != null) {

						buffer.append(input+"\n");

						input = bufferin.readLine(); // get next line
					}

					String[] lobbyinfo = buffer.toString().split("\\n");

					if (lobbyinfo.length>=1 && lobbyinfo[0].equals("LOBBYOK")) {

						if (lobbyinfo.length>=2) {

							lobbyAppletURL = lobbyinfo[1];
							canlobby = true;
						}
						if (lobbyinfo.length>=3) {

							lobbyURL = lobbyinfo[2];
							canlobby = true;

						}
					}

				}
				catch(Throwable ex) { }



				try {

					//try { Thread.sleep(5000); }
					//catch(InterruptedException e) {}

					URL url = new URL(RiskUtil.RISK_VERSION_URL);

					BufferedReader bufferin=new BufferedReader( new InputStreamReader(url.openStream()) );

					StringBuffer buffer = new StringBuffer();

					String input = bufferin.readLine();

					while(input != null) {

						buffer.append(input+"\n");

						input = bufferin.readLine(); // get next line
					}

					String[] newversion = buffer.toString().split("\\n");

					if (newversion[0].startsWith("RISKOK ")) {

						String v = newversion[0].substring(7, newversion[0].length() );

						if (!v.equals(Risk.RISK_VERSION)) {

							for (int c=1;c<newversion.length;c++) {

								v = v+"\n"+newversion[c];

							}

							risk.showMessageDialog("There is a new version of "+RiskUtil.GAME_NAME+": "+v);

						}
					}

				}
				catch (Throwable e) { }

			}

		return canlobby;
	}



	public static void runLobby(Risk risk) {

		try {

			if (lobbyURL!=null) {

				URLClassLoader ucl = URLClassLoader.newInstance(new URL[] { new URL("jar:"+lobbyURL+"/LobbyClient.jar!/") } );

				Class lobbyclass = ucl.loadClass("org.lobby.client.LobbyClientGUI");

				// TODO: should be like this
				//lobbyclass.newInstance();

				final javax.swing.JPanel panel = (javax.swing.JPanel)lobbyclass.getConstructor( new Class[] { URL.class } ).newInstance( new Object[] { new URL(lobbyURL) } );

				javax.swing.JFrame gui = new javax.swing.JFrame("yura.net Lobby");
				gui.setContentPane(panel);
				gui.setSize(800, 600);
				gui.setVisible(true);



				gui.addWindowListener( new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent evt) { panel.setVisible(false); }
				});
				panel.setVisible(true);

			}
			else if (lobbyAppletURL!=null) {

				RiskUtil.openURL(new URL(lobbyAppletURL));
			}

		}
		catch(Exception e) {

			risk.showMessageDialog("unable to run the lobby: "+e.toString() );

		}

	}

	private static void setupLookAndFeel() {

		// set up system Look&Feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// only do this check if there is NO sandbox
		// as otherwise we will get an exception anyway
		if (checkForNoSandbox()) {

			// check for java bug with JFileChooser
			try {

				new JFileChooser();

			}
			catch (Throwable th) {


				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
/* OLD
		// set up system Look&Feel
		try {

			String os = System.getProperty("os.name");
			String jv = System.getProperty("java.version");

			if ( jv.startsWith("1.4.2") && os != null && os.startsWith("Linux")) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			}
			else {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
*/
	}









	private static String maps;
	private static String cards;
	private static HashMap fileio = new HashMap();

	public static void parseArgs(String[] args) {

		TranslationBundle.parseArgs(args);

		for (int nA = 0; nA < args.length; nA++ ) {

			if (args[nA].length() > 5 && args[nA].substring(0,5).equals( "maps=")) {

				maps = args[nA].substring(5);
			}
			if (args[nA].length() > 6 && args[nA].substring(0,6).equals( "cards=")) {

				cards = args[nA].substring(6);
			}
		}

                // setup the maps dir for none applets
                setupMapsDir(null);
	}

    public static Color getTextColorFor(Color color) {
        return new Color( RiskUtil.getTextColorFor(color.getRGB()) );
    }
}

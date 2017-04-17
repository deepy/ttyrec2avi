/*
 */

package ttyrec2avi;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.net.URISyntaxException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.AttributedString;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The application's main frame.
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	/**
	 * Creates a new main window for the Jettyplay application.
	 */
	public MainFrame() {
		initComponents();
		// set no file to be open
		currentSource = null;
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 */
	@SuppressWarnings("unchecked")
		private void initComponents() {
			/* This was originally generated by NetBean's Form Editor, but it
			 * lost track of the form upon an upgrade to a new version. So now
			 * it's being edited by hand.
			 */
			uiBuilder = new UIBuilder(false);

			mainPanel = uiBuilder.addJPanel(getContentPane(), null);
			JPanel mainToolbarPanel = uiBuilder.addJPanel(mainPanel, BorderLayout.CENTER);

			mainToolbar = uiBuilder.addJToolBar(mainToolbarPanel, BorderLayout.NORTH);

			uiBuilder.addJSeparator(mainToolbar);

			menuBar = new JMenuBar();

			fileMenu = uiBuilder.addJMenu(menuBar, 'f', "File");
			uiBuilder.addJMenuItem(fileMenu, 'o', "Open...", "control O", false,
					new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
					openMenuItemActionPerformed();
					}
					});
			uiBuilder.addJSeparator(fileMenu);
			uiBuilder.addJMenuItem(fileMenu, 'x', "Exit", "control X", false,
					new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
					exitMenuItemActionPerformed();
					}
					});
			setJMenuBar(menuBar);
			uiBuilder.massSetEnabled(false);
		}

	/**
	 * A function that runs when the Open menu item is selected, that opens
	 * a ttyrec file.
	 * @param evt Information on which event was performed
	 */
	private void openMenuItemActionPerformed() {
		InputStreamable iStream = null;

		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		boolean gotstring = false;
		String s = "";
		while (!gotstring) {
			try {
				s = stdin.readLine();
				gotstring = true;
			} catch (IOException e) {
			}
		}


		File f = new File(s);
		iStream = new InputStreamableFileWrapper(f);

		if (iStream == null) return;

		openSourceFromInputStreamable(iStream);

		new SaveAsVideoDialog(currentSource.getTtyrec(), "/tmp/jettyplay_tmp.avi");
	}

	private void autoskipButtonStateChanged(ChangeEvent evt) {
		autoskipMenuItem.setSelected(autoskipButton.isSelected());
	}

	private void autoskipMenuItemStateChanged(ChangeEvent evt) {
		autoskipButton.setSelected(autoskipMenuItem.isSelected());
	}

	private void unicodeEncodingMenuItemStateChanged(ChangeEvent evt) {
		if (unicodeEncodingMenuItem.isSelected())
			setTtyrecFormat(Ttyrec.Encoding.UTF8);
	}

	private void ibmEncodingMenuItemStateChanged(ChangeEvent evt) {
		if (ibmEncodingMenuItem.isSelected())
			setTtyrecFormat(Ttyrec.Encoding.IBM);
	}

	private void latin1EncodingMenuItemStateChanged(ChangeEvent evt) {
		if (latin1EncodingMenuItem.isSelected())
			setTtyrecFormat(Ttyrec.Encoding.Latin1);
	}

	private void fullScreenMenuItemStateChanged(ChangeEvent evt) {
		GraphicsEnvironment.getLocalGraphicsEnvironment().
			getDefaultScreenDevice().setFullScreenWindow(
					fullScreenMenuItem.isSelected() ?
					this : null);
	}

	private void toolBarMenuItemStateChanged(ChangeEvent evt) {
		mainToolbar.setVisible(toolBarMenuItem.isSelected());
	}

	private void menuBarMenuItemStateChanged(ChangeEvent evt) {
		if (menuBarMenuItem.isSelected()) {
			if (!menuBarShowing) {
				//menuBar.setPreferredSize(new Dimension(32767,savedMenuBarHeight));
				menuBar.setPreferredSize(null);
				menuBar.setVisible(false);
				menuBar.setVisible(true);
				menuBarShowing = true;
			}
		} else {
			if (menuBarShowing) {
				menuBar.setPreferredSize(new Dimension(32767,1));
				menuBar.setVisible(false);
				menuBar.setVisible(true);
				menuBarShowing = false;
			}
		}
	}

	private void exitMenuItemActionPerformed() {
		System.exit(0);
	}

	private void autodetectEncodingMenuItemStateChanged(ChangeEvent evt) {
		if (autodetectEncodingMenuItem.isSelected() && getCurrentTtyrec() != null)
			getCurrentTtyrec().resetEncoding();
	}

	private void setTtyrecFormat(Ttyrec.Encoding format) {
		if (getCurrentTtyrec() == null) return;
		if (getCurrentTtyrec().getEncoding() == format) return;
		getCurrentTtyrec().setEncoding(format);
		getCurrentSource().repeatCurrentDecodeWorker();
	}

	private String timeToString(double time) {
		int t = (int)time;
		if (t<0) t = 0;
		String s = (t/60) + ":";
		if (t > 3600) {
			s = (t/3600) + ":";
			if ((t/60)%60 < 10) s += "0";
			s += (t/60)%60 + ":";
		}
		if (t%60 < 10) s += "0";
		s += t%60;
		return s;
	}

	private boolean oldEnabled = false;
	private void massSetEnabled(boolean enabled) {
		if (enabled == oldEnabled) return;
		oldEnabled = enabled;
		if (!enabled) {
			unicodeEncodingMenuItem.setEnabled(enabled);
			ibmEncodingMenuItem.setEnabled(enabled);
			latin1EncodingMenuItem.setEnabled(enabled);
		}
		uiBuilder.massSetEnabled(enabled);
	}

	private void unloadFile() {
		playing = false;
		massSetEnabled(false);
		if (getCurrentSource() != null) getCurrentSource().completeCancel();
		currentSource = null;
		VDUBuffer.resetCaches();
	}

	private void openSourceFromInputStreamable(InputStreamable iStream) {
		unloadFile();
		currentSource = new InputStreamTtyrecSource(iStream);
		getCurrentSource().completeUnpause();
		getCurrentSource().addDecodeListener(new ProgressListener() {
			public void progressMade() {
			}
		});
		getCurrentSource().start();
		massSetEnabled(true);
		previousFrameIndex = 0;
	}

	// Variable declarations
	private JRadioButtonMenuItem allowBoldMenuItem;
	private JRadioButtonMenuItem autodetectEncodingMenuItem;
	private JRadioButtonMenuItem autodetectTerminalSizeMenuItem;
	private JToggleButton autoskipButton;
	private JCheckBoxMenuItem autoskipMenuItem;
	private JRadioButtonMenuItem disallowBoldMenuItem;
	private JMenu encodingMenu;
	private JMenu fileMenu;
	private JRadioButtonMenuItem fixedTerminalSizeMenuItem;
	private JCheckBoxMenuItem fullScreenMenuItem;
	private JRadioButtonMenuItem ibmEncodingMenuItem;
	private JRadioButtonMenuItem latin1EncodingMenuItem;
	private JPanel mainPanel;
	private JToolBar mainToolbar;
	private JMenuBar menuBar;
	private JCheckBoxMenuItem menuBarMenuItem;
	private JMenu screenshotMenu;
	private JCheckBoxMenuItem toolBarMenuItem;
	private JRadioButtonMenuItem unicodeEncodingMenuItem;
	private JMenu viewMenu;
	private UIBuilder uiBuilder;
	private boolean menuBarShowing = true;

	private TtyrecSource currentSource;

	// TODO: Reduce this in the case of excessively time-long ttyrecs,
	// to avoid an integer overflow
	private int timeScaling = 1000;
	private boolean playing = false;
	private int previousFrameIndex = -1;
	private long timeStartedAt;
	private int sliderValueStartedAt;
	private boolean canUpdateTimeStartedAt = true;
	private boolean canUpdateSelectedFrame = true;

	private File lastDirectory = null;

	public double getMaximumTime() {
		if (getCurrentTtyrec() == null) return 0.0;
		return getCurrentTtyrec().getLength();
	}
	private TtyrecFrame getCurrentFrame() {
		try {
			return getCurrentTtyrec().getFrameAtIndex(previousFrameIndex);
		} catch(IndexOutOfBoundsException ex) {
			return null;
		}
	}

	/**
	 * Searches for a given string in the currently open ttyrec; if it's found,
	 * then seeks the current ttyrec to the frame where it was found.
	 * @param searchFor The string to search for.
	 * @param searchForward Whether to search forwards (true) or backwards (false).
	 * @param regex Whether the string to search for is actually a regex.
	 * @param ignoreCase Whether to do a case-insensitive (true) or case-sensitive (false) search.
	 * @param wrapAround Whether to restart the search at one end of the ttyrec if it's finished at the other end.
	 * @return A string that can be displayed to the user, summarising the results of the search.
	 */
	public String searchInTtyrec(String searchFor, boolean searchForward,
			boolean regex, boolean ignoreCase, boolean wrapAround) {
		Pattern p;
		try {
			// Regex.LITERAL would be nice, but it's too new. So we quote the
			// regex by hand, according to Perl 5 quoting rules; all letters
			// and all digits are left as-is, other characters are preceded by
			// a backslash.
			if (!regex) {
				StringBuilder sb = new StringBuilder();
				for (char c: searchFor.toCharArray()) {
					if (!Character.isLetter(c) && !Character.isDigit(c))
						sb.append('\\');
					sb.append(c);
				}
				searchFor = sb.toString();
			}
			p = Pattern.compile(searchFor, (ignoreCase ? Pattern.CASE_INSENSITIVE : 0));
		} catch (PatternSyntaxException e) {
			return "Invalid regular expression.";
		}
		for (int i = previousFrameIndex;
				i < getCurrentTtyrec().getFrameCount() && i >= 0; i += searchForward ? 1 : -1) {
			if (i == previousFrameIndex) {
				continue;
			}
			if (getCurrentTtyrec().getFrameAtIndex(i).containsPattern(p)) {
				return "Found at frame " + i + ".";
			}
		}
		if (wrapAround) {
			for (int i = searchForward ? 0 : getCurrentTtyrec().getFrameCount() - 1;
					i != previousFrameIndex;
					i += searchForward ? 1 : -1) {
				if (getCurrentTtyrec().getFrameAtIndex(i).containsPattern(p)) {
					return "Found at frame " + i + " (wrapped).";
				}
			}
		}
		return "Match not found.";
	}

	/**
	 * Gets the currently visible ttyrec source; that's the selected source from
	 * the playlist.
	 * @return the currently selected ttyrec source.
	 */
	private TtyrecSource getCurrentSource() {
		return currentSource;
	}


	/**
	 * Returns the currently viewed ttyrec.
	 * Even if more than one ttyrec is open, only the one currently showing is
	 * returned.
	 * @return The current ttyrec, or null if there are no open ttyrecs.
	 */
	public Ttyrec getCurrentTtyrec() {
		if (getCurrentSource() == null) return null;
		return getCurrentSource().getTtyrec();
	}

	// This method exists to avoid causing problems with missing fields
	// in early JDK versions.
	private Object safelyGetRenderingHint(String hintName) {
		try {
			return RenderingHints.class.getField(hintName).get(null);
		} catch(NoSuchFieldException | SecurityException |
				IllegalArgumentException | IllegalAccessException e) {
			return RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
		}
	}

	/**
	 * The main entry point for the Jettyplay application.
	 * Parses and applies the effects of command-line arguments; if the
	 * arguments did not request an immediate exit, also creates a new main
	 * window for the Jettyplay application GUI and shows it.
	 * @param args The command-line arguments to parse.
	 */
	public static void main(String[] args) {
		// Look for help or version args, and reply and exit if one is given.
		boolean ddflag = false;
		for(String a : args) {
			if (ddflag) {ddflag = false; continue;}
			if(a.equals("-v") || a.equals("--version")) {
				System.exit(0);
			}
			if(a.equals("-h") || a.equals("--help")) {
				System.err.println("filenames   Load the given file/files");
				System.err.println("-z 80x24    Force terminal size to 80x24 (likewise for other sizes)");
				System.err.println("-f 1200     Jump to frame 1200 upon loading (likewise for other frames)");
				System.err.println("-l          Automatically fast-forward through periods of inactivity");
				System.err.println("--          Treat next arg as a filename even if it starts with -");
				System.err.println("-h          Show this help, then exit");
				System.err.println("-v          Show version and copyright information, then exit");
				System.exit(0);
			}
			if(a.equals("--")) ddflag = true;
		}
		// Set up the GUI.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException |
				IllegalAccessException | UnsupportedLookAndFeelException ex) {
			// if we can't set a system look and feel, just use the default...
		}
		MainFrame me = new MainFrame();
		me.setDefaultCloseOperation(EXIT_ON_CLOSE);
		me.setSize(800, 600);
		me.setTitle("Jettyplay");
		me.setVisible(true);
		// Apply the effects of options
		ddflag = false;
		boolean frameflag = false;
		String pendingFrame = null;
		for (String a : args) {
			// if size or frame is being set this arg, turn on ddflag so the
			// arg isn't interpreted as anything else, and fall past the
			// filename check to the size/frame check
			if (frameflag) {pendingFrame = a; ddflag = true;}
			if (a.equals("-l") && !ddflag) {
				me.autoskipButton.setSelected(true);
				me.autoskipMenuItem.setSelected(true);
				continue;
			}
			if(a.equals("-f") && !ddflag) {frameflag = true; continue;}
			if(a.equals("--") && !ddflag) {ddflag = true; continue;}            
			ddflag = false;
			if (!frameflag) {
				// Looks like it's a filename...
				File f = new File(a);
				me.openSourceFromInputStreamable(new InputStreamableFileWrapper(f));
			}
			// Check to see whether to apply forced size, or to go to a frame.
			frameflag = false;
			if (me.getCurrentSource() != null && pendingFrame != null) {
				try {
					me.getCurrentSource().setWantedFrame(
							Integer.valueOf(pendingFrame) - 1);
					pendingFrame = null;
				} catch (NumberFormatException nfe) {
					// do nothing
				}
			}
		}
	}
}

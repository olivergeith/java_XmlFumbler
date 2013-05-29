package de.og.xmlfumbler.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import og.basics.gui.LToolBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.xmlfumbler.cfg.GlobalSettings;
import de.og.xmlfumbler.cfg.RomPreset;
import de.og.xmlfumbler.gui.cfg.GlobalSettingsPanel;
import de.og.xmlfumbler.gui.cfg.RomSettingsPanel;
import de.og.xmlfumbler.gui.iconstore.IconStore;
import de.og.xmlfumbler.gui.panels.fileset.RecurseFileSetSelector;
import de.og.xmlfumbler.gui.panels.xmlset.RecurseXMLSetSelector;
import de.og.xmlfumbler.zipcreator.ZipElementCollection;
import de.og.xmlfumbler.zipcreator.ZipMaker;

public class XmlFumblerPanel extends JPanel {
	@SuppressWarnings("unused")
	private static final int				TAB_INDEX_GLOBALSETTINGS	= 0;
	@SuppressWarnings("unused")
	private static final int				TAB_INDEX_ROMSETTINGS		= 1;
	private static final int				TAB_INDEX_ADVANCED			= 2;
	private static final Logger				LOGGER						= LoggerFactory.getLogger(XmlFumblerPanel.class);
	private static final long				serialVersionUID			= -2956273745014471932L;

	private final JButton					zipButton					= new JButton(IconStore.zipIcon);
	private final JTabbedPane				tabPane						= new JTabbedPane();
	private final LToolBar					toolBar						= new LToolBar();
	private final RomSettingsPanel			romSettingsPanel			= new RomSettingsPanel();

	private final GlobalSettingsPanel		globalSettingsPanel			= new GlobalSettingsPanel();

	private final RecurseXMLSetSelector		xmlBox						= new RecurseXMLSetSelector();

	private final RecurseFileSetSelector	filesetBox					= new RecurseFileSetSelector();

	// Treadstuff
	private final JProgressBar				progressBar					= new JProgressBar();
	private Thread							t							= null;
	private boolean							isrunning					= false;
	// private boolean stopnow = false;
	private final int						maxsteps					= 5;
	private int								step						= 0;

	private final JFrame					parentFrame;

	private JTabbedPane						advancedTabPane;

	public XmlFumblerPanel(final JFrame parentFrame) {
		this.parentFrame = parentFrame;
		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());
		zipButton.setToolTipText("Create flashble Zip!");
		zipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				startZipThread();
			}
		});

		// Prograssbar int
		progressBar.setIndeterminate(false);
		progressBar.setMinimum(0);
		progressBar.setMaximum(maxsteps);
		progressBar.setStringPainted(true);
		resetProgressBar();

		advancedTabPane = new JTabbedPane();
		tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabPane.addTab("GlobalSettings", IconStore.settings1, globalSettingsPanel, "Program global settings");
		tabPane.addTab("RomSettings", IconStore.cfgIcon, romSettingsPanel, "RomSettings");
		tabPane.addTab("Fumbling", IconStore.folder2Icon, advancedTabPane, "Fumbling");

		advancedTabPane.addTab("XML-Sets", IconStore.folder2Icon, xmlBox, "Add XML-Sets to any apk...Most dangerous!");
		advancedTabPane.addTab("Filesets", IconStore.folder2Icon, filesetBox, "Add Filesets like apk's, lib's, media...");
		tabPane.setSelectedIndex(TAB_INDEX_ADVANCED);
		// Panel zusammensetzen
		add(tabPane, BorderLayout.CENTER);
		// add(configPane, BorderLayout.WEST);
		add(progressBar, BorderLayout.SOUTH);
		makeButtonBar();
		// validate GlobalSettings
		applyGlobalSettingsOnStart();
	}

	private void applyGlobalSettingsOnStart() {
		final GlobalSettings gset = globalSettingsPanel.getSettings();
		if (gset == null) {
			LOGGER.error("No GlobalSettings!!!");
			return;
		}
		// rom preset
		final RomPreset preset = gset.getRomPreset();
		if (preset != null && !preset.getRomName().equals(RomPreset.APPLY)) {
			LOGGER.info("Setting your RomPreset to : {}", preset.getRomName());
			romSettingsPanel.applyRomPresets(preset);
		} else {
			LOGGER.info("No RomPresets choosen to be applied on startup...using Defaults!!!");
		}
		// advanced Tab
		LOGGER.info("Showing advanced toggle = {}", gset.isShowAdvancedButton());

	}

	/**
	 * Creating buttonbar
	 */
	private void makeButtonBar() {
		toolBar.setFloatable(false);
		toolBar.add(new JPanel());
		toolBar.add(zipButton);
		// add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * Zip the flashable Zip!
	 */
	private void doZip() {

		final ZipElementCollection zipCollection = new ZipElementCollection();
		final ZipMaker zipper = new ZipMaker(romSettingsPanel.getSettings().getTemplate());
		final Vector<String> files2add2SystemUI = new Vector<String>();
		final Vector<String> files2add2Framework = new Vector<String>();

		// ZipElementCollection anlegen und alle Zipelemente einfügen
		updateProgressBar(step++, "Creating ZipCollection");
		zipCollection.addElements(files2add2SystemUI, romSettingsPanel.getSettings().getFolderSystemUIInZip());
		zipCollection.addElements(files2add2Framework, romSettingsPanel.getSettings().getFolderFrameworkInZip());

		// Adding XTRAS
		updateProgressBar(step++, "Adding XTRAS to ZipCollection");
		if (filesetBox.getSelectedSet() != null) {
			zipCollection.addElements(filesetBox.getSelectedSet().getFilenamesAndPath(), filesetBox.getSelectedSet().getAllPathInZip());
		}
		// Adding XXML-Set
		updateProgressBar(step++, "Adding XML's to ZipCollection");
		if (xmlBox.getSelectedSet() != null) {
			zipCollection.addElements(xmlBox.getSelectedSet().getFilenamesAndPath(), xmlBox.getSelectedSet().getAllPathInZip());
		}

		// now the actual zipping...
		try {
			updateProgressBar(step++, "Choose ZipFilename....");
			final boolean saved = zipper.addFilesToArchive(zipCollection.getZipelEments(), "XMLFumbler");
			// all ok ? Then Messagebox
			if (saved == true) {
				updateProgressBar(step++, "Done Successfully!");
				JOptionPane.showMessageDialog(XmlFumblerPanel.this, "Zip was created successfully", "Zip creating", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (final Exception e) {
			// Error during zip...
			updateProgressBar(step++, "Done With Error!");
			JOptionPane.showMessageDialog(XmlFumblerPanel.this, "ERROR: Zip was not created successfully!!!\n" + e.getMessage(), "Zip creating ERROR",
					JOptionPane.ERROR_MESSAGE);
			LOGGER.error("" + e.getMessage());
		}
		resetProgressBar();
	}

	/**
	 * Startet den Thread
	 * 
	 * @param startDir
	 */
	private void startZipThread() {
		if (t != null)
			stopThread();
		if (t == null) {
			t = new Thread(new Runnable() {
				@Override
				public void run() {
					parentFrame.setEnabled(false);
					// stopnow = false;
					isrunning = true;
					doZip();
					parentFrame.toFront();
					parentFrame.setEnabled(true);
					parentFrame.toFront();
					isrunning = false;
				}
			});

			t.start();
		}
	}

	public synchronized void stopThread() {
		if (t != null) {
			// stopnow = true;
			t = null;
		}
	}

	/**
	 * @return true, wenn Thread noch läuft
	 */
	public synchronized boolean isTreadRunning() {
		return isrunning;
	}

	private void updateProgressBar(final int value, final String text) {
		LOGGER.info("Progress: " + value + " " + text);
		progressBar.setValue(value);
		progressBar.setString(text);
	}

	private void resetProgressBar() {
		step = 1;
		progressBar.setValue(0);
		progressBar.setString("Create your Icons");
	}

	/**
	 * @return the toolBar
	 */
	public LToolBar getToolBar() {
		return toolBar;
	}

}

package de.og.xmlfumbler.gui.widgets;

import javax.swing.JComboBox;
import de.og.xmlfumbler.cfg.RomPreset;

public class MorphpathSystemUIComboBox extends JComboBox<String> {
	private static final long	serialVersionUID	= 1L;

	public MorphpathSystemUIComboBox() {

		initUI();

	}

	private void initUI() {
		addItem(RomPreset.MORPHPATH_SYSTEMUI);
		addItem(RomPreset.MORPHPATH_SYSTEMUI_PRELOAD);
		addItem(RomPreset.MORPHPATH_VRTHEME_SYSTEMUI);
		addItem(RomPreset.MORPHPATH_VRTHEME_SYSTEMUI_PRELOAD);
		setEditable(false);
	}

}

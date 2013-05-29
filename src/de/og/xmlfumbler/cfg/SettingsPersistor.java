package de.og.xmlfumbler.cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
import og.basics.gui.file.FileDialogs;
import og.basics.util.KPropertyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsPersistor {
	private static final Logger	LOGGER					= LoggerFactory.getLogger(SettingsPersistor.class);
	private static final String	SETTINGS_ROMS_EXTENSION	= ".rcfg";
	private static final String	SETTINGS_DIR			= "./stylSettings/";
	private static final String	SETTINGS_DIR_GLOBAL		= "./settings/";
	public static final String	ROMPRESETS_DIR			= "./rompresets/";
	public static final String	ROMPRESETS_EXTENSION	= ".rompreset";

	// ###############################################################################
	// Persisting Settings
	// ###############################################################################
	public static void saveRomSettings(final String name, final RomSettings settings) {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();
			final String filename = SETTINGS_DIR + name + SETTINGS_ROMS_EXTENSION;
			final File saveFile = FileDialogs.saveFile(pa, new File(filename), SETTINGS_ROMS_EXTENSION, "RomSettings");
			if (saveFile != null) {
				final FileOutputStream file = new FileOutputStream(saveFile);
				final ObjectOutputStream o = new ObjectOutputStream(file);
				o.writeObject(settings);
				o.close();
			}
		} catch (final IOException e) {
			System.err.println(e);
		}
	}

	public static RomSettings loadRomSettings() {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();

			// final String filename = "./stylSettings/" + getName() + ".cfg";
			final File loadFile = FileDialogs.chooseFile(pa, SETTINGS_ROMS_EXTENSION, "RomSettings");
			if (loadFile != null) {
				final FileInputStream file = new FileInputStream(loadFile);
				final ObjectInputStream o = new ObjectInputStream(file);
				final RomSettings settings = (RomSettings) o.readObject();
				o.close();
				return settings;
			}
		} catch (final IOException e) {
			System.err.println(e);
		} catch (final ClassNotFoundException e) {
			System.err.println(e);
		}
		return null;
	}

	// ###############################################################################
	// Persisting Settings
	// ###############################################################################
	public static void saveGlobalSettings(final GlobalSettings settings) {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR_GLOBAL);
			if (!pa.exists())
				pa.mkdirs();
			final String filename = SETTINGS_DIR_GLOBAL + "GlobalSettings.gcfg";
			LOGGER.info("Saving Global Settings: {}", filename);
			final File saveFile = new File(filename);
			if (saveFile != null) {
				final FileOutputStream file = new FileOutputStream(saveFile);
				final ObjectOutputStream o = new ObjectOutputStream(file);
				o.writeObject(settings);
				o.close();
			}
		} catch (final IOException e) {
			LOGGER.error("Error saving Global Settings: " + e);
		}
	}

	public static GlobalSettings loadGlobalSettings() {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR_GLOBAL);
			if (!pa.exists())
				pa.mkdirs();

			final String filename = SETTINGS_DIR_GLOBAL + "GlobalSettings.gcfg";
			LOGGER.info("Loading Global Settings: {}", filename);
			final File loadFile = new File(filename);
			if (loadFile != null) {
				final FileInputStream file = new FileInputStream(loadFile);
				final ObjectInputStream o = new ObjectInputStream(file);
				final GlobalSettings settings = (GlobalSettings) o.readObject();
				o.close();
				return settings;
			}
		} catch (final IOException e) {
			LOGGER.warn("Loading Global Settings not possible (file not found): IOException");
		} catch (final ClassNotFoundException e) {
			LOGGER.error("Error loading Global Settings: ClassNotFoundException");
		}
		return null;
	}

	// ###############################################################################
	// Persisting Settings
	// ###############################################################################
	public static void writePreset(final RomPreset pre) {
		LOGGER.debug("Writing RomPreset: {}", pre.getRomName());
		final KPropertyReader reader = new KPropertyReader(ROMPRESETS_DIR + pre.getRomName() + ROMPRESETS_EXTENSION, true);
		reader.writeProperty("RomName", pre.getRomName());
		reader.writeProperty("FilePattern", pre.getFilePattern());
		reader.writeProperty("FilePatternCharge", pre.getFilePatternCharge());
		reader.writeProperty("FrameworkDrawableFolder", pre.getFrameworkDrawableFolder());
		reader.writeProperty("SystemUIDrawableFolder", pre.getSystemUIDrawableFolder());
		reader.writeProperty("LidroidDrawableFolder", pre.getLidroidDrawableFolder());
		reader.writeProperty("MmsDrawableFolder", pre.getMmsDrawableFolder());
		reader.writeProperty("Template", pre.getTemplate());
		reader.writeIntProperty("Battsize", pre.getBattsize());
		reader.writeIntProperty("EmoSize", pre.getEmoSize());
		reader.writeIntProperty("LockHandleSize", pre.getLockHandleSize());
		reader.writeIntProperty("NotificationHeight", pre.getNotificationHeight());
		reader.writeIntProperty("ToggleSize", pre.getToggleSize());
		reader.writeIntProperty("WeatherSize", pre.getWeatherSize());
		reader.writeBooleanProperty("UseLidroid", pre.isUseLidroid());
		reader.writeBooleanProperty("UseMMSforEmoticons", pre.isUseMMSforEmoticons());
		reader.writeBooleanProperty("UsePreload", pre.isUsePreload());
		reader.writeBooleanProperty("UseVRThemeTemplate", pre.isUseVRThemeTemplate());

	}

	public static RomPreset readPreset(final File fi) {
		LOGGER.debug("Reading RomPreset: {}", fi.getPath());
		final KPropertyReader reader = new KPropertyReader(fi.getPath(), false);
		final String romName = reader.readProperty("RomName", "xxx");
		final String filePattern = reader.readProperty("FilePattern", RomPreset.BATT_ICON_NAME_AOKP);
		final String filePatternCharge = reader.readProperty("FilePatternCharge", RomPreset.BATT_ICON_CHARGE_NAME_AOKP);
		final String frameworkDrawableFolder = reader.readProperty("FrameworkDrawableFolder", RomPreset.DRAWABLE_HDPI);
		final String systemUIDrawableFolder = reader.readProperty("SystemUIDrawableFolder", RomPreset.DRAWABLE_HDPI);
		final String lidroidDrawableFolder = reader.readProperty("LidroidDrawableFolder", RomPreset.DRAWABLE_HDPI);
		final String mmsDrawableFolder = reader.readProperty("MmsDrawableFolder", RomPreset.DRAWABLE_HDPI);
		final String template = reader.readProperty("Template", RomPreset.templateS2);
		final int battsize = reader.readIntProperty("Battsize", RomPreset.BATT_ICON_HEIGHT_HDPI);
		final int emoSize = reader.readIntProperty("EmoSize", RomPreset.EMO_HDPI);
		final int lockHandleSize = reader.readIntProperty("LockHandleSize", RomPreset.LOCK_HDPI);
		final int notificationHeight = reader.readIntProperty("NotificationHeight", RomPreset.NOTIFICATION_HDPI);
		final int toggleSize = reader.readIntProperty("ToggleSize", RomPreset.TOGGLE_HDPI);
		final int weatherSize = reader.readIntProperty("WeatherSize", RomPreset.WEATHER_HDPI);
		final boolean useLidroid = reader.readBooleanProperty("UseLidroid", false);
		final boolean useMMSforEmoticons = reader.readBooleanProperty("UseMMSforEmoticons", true);
		final boolean usePreload = reader.readBooleanProperty("UsePreload", false);
		final boolean useVRThemeTemplate = reader.readBooleanProperty("UseVRThemeTemplate", false);
		return new RomPreset(romName, systemUIDrawableFolder, battsize, frameworkDrawableFolder, lidroidDrawableFolder, mmsDrawableFolder, filePattern,
				filePatternCharge, lockHandleSize, notificationHeight, toggleSize, useLidroid, weatherSize, emoSize, template, useVRThemeTemplate, usePreload,
				useMMSforEmoticons);
	}

	public static void saveRomPresetFromRomSettings(final RomSettings rs) {
		// Pfad anlegen falls nicht vorhanden
		final File pa = new File(ROMPRESETS_DIR);
		if (!pa.exists())
			pa.mkdirs();
		final String filename = ROMPRESETS_DIR + "(Device) Name" + ROMPRESETS_EXTENSION;
		final File saveFile = FileDialogs.saveFile(pa, new File(filename), ROMPRESETS_EXTENSION, "Rom Presets");

		if (saveFile != null) {
			final String romName = SettingsPersistor.stripExtension(saveFile.getName());
			LOGGER.info("Exporting RomPreset = {}", romName);
			final RomPreset pre = new RomPreset(romName, rs.getSystemUIDrawableFolder(), rs.getBattIconSize(), rs.getFrameworkDrawableFolder(),
					rs.getLidroidDrawableFolder(), rs.getEmoticonsDrawableFolder(), rs.getFilePattern(), rs.getFilePatternCharge(), rs.getLockHandleSize(),
					rs.getNotificationHeight(), rs.getToggleSize(), rs.isUseLidroid(), rs.getWeatherSize(), rs.getEmoSize(), rs.getTemplate(),
					rs.isUseVRThemeTemplate(), rs.isUsePreload(), rs.isUseMMSForEmoticons());
			SettingsPersistor.writePreset(pre);
			JOptionPane
					.showMessageDialog(null, "RomPreset " + saveFile.getName()
							+ " was saved successfully!\nRestart 'Rom Fumbler' to make it appear in Dropdown-Box!", "RomPreset saving",
							JOptionPane.INFORMATION_MESSAGE);
			LOGGER.info("Exporting RomPreset = {} finished!", romName);
		}
	}

	public static String stripExtension(final String str) {
		// Handle null case specially.
		if (str == null)
			return null;
		// Get position of last '.'.
		final int pos = str.lastIndexOf(".");
		// If there wasn't any '.' just return the string as is.
		if (pos == -1)
			return str;
		// Otherwise return the string, up to the dot.
		return str.substring(0, pos);
	}

}

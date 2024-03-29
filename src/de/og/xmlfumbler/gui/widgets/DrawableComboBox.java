package de.og.xmlfumbler.gui.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import de.og.xmlfumbler.cfg.RomPreset;

public class DrawableComboBox extends JComboBox<String> {
	private static final long		serialVersionUID		= 1L;
	// systemui
	private final SliderAndLabel	sliderBattSize			= new SliderAndLabel(10, 70);
	private final SliderAndLabel	sliderToggleSize		= new SliderAndLabel(20, 160);
	// frameworkres
	private final SliderAndLabel	sliderLockSize			= new SliderAndLabel(100, 250);
	private final SliderAndLabel	sliderWeatherSize		= new SliderAndLabel(50, 200);
	private final SliderAndLabel	sliderEmoSize			= new SliderAndLabel(10, 200);
	private final SliderAndLabel	sliderNotificationSize	= new SliderAndLabel(1, 8);

	public DrawableComboBox() {

		initUI();

	}

	private void initUI() {
		addItem(RomPreset.DRAWABLE_XHDPI);
		addItem(RomPreset.DRAWABLE_HDPI);
		addItem(RomPreset.DRAWABLE_MDPI);
		addItem(RomPreset.DRAWABLE_600DP_XHDPI);
		addItem(RomPreset.DRAWABLE_720DP_XHDPI);
		addItem(RomPreset.DRAWABLE_600DP_HDPI);
		addItem(RomPreset.DRAWABLE_720DP_HDPI);
		addItem(RomPreset.DRAWABLE_XXHDPI);
		setEditable(true);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (getSelectedItem().equals(RomPreset.DRAWABLE_XHDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_XHDPI);
					sliderLockSize.setValue(RomPreset.LOCK_XHDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_XHDPI);
					sliderEmoSize.setValue(RomPreset.EMO_XHDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_XHDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_XHDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_XXHDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_XXHDPI);
					sliderLockSize.setValue(RomPreset.LOCK_XXHDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_XXHDPI);
					sliderEmoSize.setValue(RomPreset.EMO_XXHDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_XXHDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_XXHDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_HDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_HDPI);
					sliderLockSize.setValue(RomPreset.LOCK_HDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_HDPI);
					sliderEmoSize.setValue(RomPreset.EMO_HDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_HDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_HDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_MDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_MDPI);
					sliderLockSize.setValue(RomPreset.LOCK_MDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_MDPI);
					sliderEmoSize.setValue(RomPreset.EMO_MDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_MDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_MDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_720DP_XHDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_720DP_XHDPI);
					sliderLockSize.setValue(RomPreset.LOCK_720P_XHDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_720DP_XHDPI);
					sliderEmoSize.setValue(RomPreset.EMO_720DP_XHDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_720DP_XHDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_720DP_XHDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_600DP_XHDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_600DP_XHDPI);
					sliderLockSize.setValue(RomPreset.LOCK_600DP_XHDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_600DP_XHDPI);
					sliderEmoSize.setValue(RomPreset.EMO_600DP_XHDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_600DP_XHDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_600DP_XHDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_720DP_HDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_720DP_HDPI);
					sliderLockSize.setValue(RomPreset.LOCK_720P_HDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_720DP_HDPI);
					sliderEmoSize.setValue(RomPreset.EMO_720DP_HDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_720DP_HDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_720DP_HDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_600DP_HDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_600DP_HDPI);
					sliderLockSize.setValue(RomPreset.LOCK_600DP_HDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_600DP_HDPI);
					sliderEmoSize.setValue(RomPreset.EMO_600DP_HDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_600DP_HDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_600DP_HDPI);
				}
			}
		});
	}

	/**
	 * @return the slider
	 */
	public SliderAndLabel getSliderBattSize() {
		return sliderBattSize;
	}

	/**
	 * @return the sliderLockSize
	 */
	public SliderAndLabel getSliderLockSize() {
		return sliderLockSize;
	}

	/**
	 * @return the sliderWeatherSize
	 */
	public SliderAndLabel getSliderWeatherSize() {
		return sliderWeatherSize;
	}

	/**
	 * @return the sliderToggleSize
	 */
	public SliderAndLabel getSliderToggleSize() {
		return sliderToggleSize;
	}

	/**
	 * @return the sliderNotificationSize
	 */
	public SliderAndLabel getSliderNotificationSize() {
		return sliderNotificationSize;
	}

	public SliderAndLabel getSliderEmoSize() {
		return sliderEmoSize;
	}

}

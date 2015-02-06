/**
 * Synesketch 
 * Copyright (C) 2008  Uros Krcadinac
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package synesketch.art.util;

import java.util.Random;

import synesketch.emotion.Emotion;
import synesketch.util.PropertiesManager;

/**
 * Representation of a six-part color palette used for Synesketch sketches. Each
 * part of the palette represents colors -- defined by it's coded hex number --
 * used for visualization of a specific emotion by Ekman: happiness, sadness,
 * anger, fear, disgust, and surprise.
 * <p>
 * Palettes are property documents written in XML. Synesketch provides one
 * standard palette (data/palette/standard.xml), based on some phycology &
 * visual design theories, but everyone can write it's own palette by writing
 * one's own XML document similar to data/palette/standard.xml.
 * 
 * @author Uros Krcadinac email: uros@krcadinac.com
 * @version 1.0
 */
public class SynesketchPalette {

	private int[] fearColors;

	private int[] angerColors;

	private int[] disgustColors;

	private int[] happinessColors;

	private int[] sadnessColors;

	private int[] surpriseColors;

	private int[][] allColors;

	private final Random randomiser = new Random();

	public static final int[] neutralColors = {
		0x606060, 0x909090, 0x9b9b9b, 0x585858, 0x8f8f8f, 0x696969, 0xa5a5a5,
		0x5f5f5f, 0x7f7f7f, 0x828282, 0x9e9e9e, 0x828282, 0x797979, 0x5c5c5c,
		0x4e4e4e, 0x686868, 0xa9a9a9, 0x777777, 0xadadad, 0x9b9b9b, 0x696969,
		0x979797, 0x575757, 0x727272, 0x9a9a9a, 0x797979, 0x999999, 0xa5a5a5,
		0x585858, 0x848484, 0x949494, 0x8a8a8a, 0xa7a7a7, 0x7a7a7a, 0x646464,
		0x9e9e9e, 0x7a7a7a, 0xa6a6a6
	};

	/**
	 * Class contructor which sets six palettes -- one for each emotion type,
	 * happiness, sadness, anger, fear, disgust, and surprise -- by taking data
	 * from a XML file defined by palette's name.
	 *
	 * @param paletteName
	 *            {@link String} name of the six-part palette -- XML file with
	 *            the color codes for each emotion type
	 */
	public SynesketchPalette(String paletteName) {
		PropertiesManager pm = new PropertiesManager("/data/palette/"
			+ paletteName.toLowerCase() + ".xml");
		happinessColors = pm.getIntArrayProperty("happiness.palette");
		sadnessColors = pm.getIntArrayProperty("sadness.palette");
		angerColors = pm.getIntArrayProperty("anger.palette");
		fearColors = pm.getIntArrayProperty("fear.palette");
		disgustColors = pm.getIntArrayProperty("disgust.palette");
		surpriseColors = pm.getIntArrayProperty("surprise.palette");

		allColors = new int[7][];
		allColors[Emotion.NEUTRAL + 1] = neutralColors;
		allColors[Emotion.HAPPINESS + 1] = happinessColors;
		allColors[Emotion.SADNESS + 1] = sadnessColors;
		allColors[Emotion.ANGER + 1] = angerColors;
		allColors[Emotion.FEAR + 1] = fearColors;
		allColors[Emotion.DISGUST + 1] = disgustColors;
		allColors[Emotion.SURPRISE + 1] = surpriseColors;
	}

	public int[] getColors(Emotion e) {
		int type = e.getType() + 1;
		return (type >= 0 && type < allColors.length) ? allColors[type] : null;
	}

	public int getRandomColor(Emotion e) {
		int[] palette = getColors(e);
		return palette[randomiser.nextInt(palette.length)];
	}

	/**
	 * Getter for the palette for the emotion of anger.
	 * 
	 * @return array of integers representing the palette for the emotion of anger
	 */
	public int[] getAngerColors() {
		return angerColors;
	}

	/**
	 * Getter for the palette for the emotion of disgust.
	 * 
	 * @return array of integers representing the palette for the emotion of disgust
	 */
	public int[] getDisgustColors() {
		return disgustColors;
	}

	/**
	 * Getter for the palette for the emotion of fear.
	 * 
	 * @return array of integers representing the palette for the emotion of fear
	 */
	public int[] getFearColors() {
		return fearColors;
	}

	/**
	 * Getter for the palette for the emotion of happiness.
	 * 
	 * @return array of integers representing the palette for the emotion of happiness
	 */
	public int[] getHappinessColors() {
		return happinessColors;
	}

	/**
	 * Getter for the palette for the emotion of sadness.
	 * 
	 * @return array of integers representing the palette for the emotion of sadness
	 */
	public int[] getSadnessColors() {
		return sadnessColors;
	}

	/**
	 * Getter for the palette for the emotion of surprise
	 * 
	 * @return array of integers representing the palette for the emotion of surprise.
	 */
	public int[] getSurpriseColors() {
		return surpriseColors;
	}

	/**
	 * Returns a random color from the hapiness palette.
	 * 
	 * @return integer which represents the color
	 */
	public int getRandomHappinessColor() {
		return happinessColors[randomiser.nextInt(happinessColors.length)];
	}

	/**
	 * Returns a random color from the sadness palette.
	 * 
	 * @return integer which represents the color
	 */
	public int getRandomSadnessColor() {
		return sadnessColors[randomiser.nextInt(sadnessColors.length)];
	}

	/**
	 * Returns a random color from the anger palette.
	 * 
	 * @return integer which represents the color
	 */
	public int getRandomAngerColor() {
		return angerColors[randomiser.nextInt(angerColors.length)];
	}

	/**
	 * Returns a random color from the fear palette.
	 * 
	 * @return integer which represents the color
	 */
	public int getRandomFearColor() {
		return fearColors[randomiser.nextInt(fearColors.length)];
	}

	/**
	 * Returns a random color from the disgust palette.
	 * 
	 * @return integer which represents the color
	 */
	public int getRandomDisgustColor() {
		return disgustColors[randomiser.nextInt(disgustColors.length)];
	}

	/**
	 * Returns a random color from the surprise palette.
	 * 
	 * @return integer which represents the color
	 */
	public int getRandomSurpriseColor() {
		return surpriseColors[randomiser.nextInt(surpriseColors.length)];
	}

}

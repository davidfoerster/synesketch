/*
 * Synesketch 
 * Copyright (C) 2008  Uros Krcadinac
 *               2015  David Foerster
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
package synesketch.emotion;

/**
 * Represents one emotion, with it's type and weight.
 * <p>
 * Emotion types are the ones defined by Ekman: happiness, sadness, fear, anger,
 * disgust, surprise. These types are defines by the static attributes of this
 * class.
 * 
 * @author Uros Krcadinac email: uros@krcadinac.com
 * @version 1.0
 */

public class Emotion implements Comparable<Emotion> {

	public static final int NEUTRAL = -1;

	public static final int HAPPINESS = 0;

	public static final int SADNESS = 1;

	public static final int FEAR = 2;

	public static final int ANGER = 3;

	public static final int DISGUST = 4;

	public static final int SURPRISE = 5;

	private static final String[] NAMES = {
		"neutral", "happiness", "sadness", "fear", "anger", "disgust", "surprise"
	};

	private double weight;

	private int type;

	/**
	 * Class constructor which sets weight and type of the emotion.
	 * 
	 * @param weight
	 *            double which represents the intensity of the emotion (can take
	 *            values between 0 and 1)
	 * @param type
	 *            type of the emotion (happiness, sadness, fear, anger, disgust,
	 *            or surprise)
	 */

	public Emotion(double weight, int type) {
		this.weight = weight;
		this.type = type;
	}

	/**
	 * Compares weights of current object and the one from the argument.
	 * 
	 * @param arg0   which is to compared to the current one
	 * @return integer representing the result
	 */
	@SuppressWarnings("SubtractionInCompareTo")
	@Override
	public int compareTo(Emotion arg0)
	{
		return
			Math.abs(this.weight - arg0.weight) < 1e-2 ?
				0 :
				Double.compare(this.weight, arg0.weight);
	}

	/**
	 * Getter for the emotion type
	 * 
	 * @return emotion type (integer constant defined by this class)
	 */
	public int getType() {
		return type;
	}

	public String getTypeName() {
		return NAMES[type + 1];
	}

	/**
	 * Setter for the emotion type
	 * 
	 * @param type
	 *            emotion type (integer constant defined by this class)
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Getter for the emotional weight
	 * 
	 * @return double representing the emotional weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Setter for the emotional weight
	 * 
	 * @param value
	 *            double representing the emotional weight
	 */
	public void setWeight(double value) {
		this.weight = value;
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return "Type: " + getTypeName() + ", weight: " + weight;
	}

}

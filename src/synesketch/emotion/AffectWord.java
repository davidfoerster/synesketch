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
package synesketch.emotion;

import java.util.Collections;
import java.util.Comparator;


/**
 * Represents one unit from the Synesketch Lexicon: a word associated with
 * emotional meaning, and it's emotional weights and valence.
 * <p>
 * Synesketch Lexicon, which consits of several thousand words (with emoticons),
 * associates these atributes to a word:
 * <ul>
 * <li>General emotional weight
 * <li>General emotional valence
 * <li>Happiness weight
 * <li>Sadness weight
 * <li>Fear weight
 * <li>Anger weight
 * <li>Disgust weight
 * <li>Surprise weight
 * </ul>
 * 
 * @author Uros Krcadinac email: uros@krcadinac.com
 * @version 1.0
 */
public class AffectWord implements Cloneable
{
	private String word;

	private double generalWeight = 0.0, generalValence = 0.0;

	private double[] weights = new double[6];

	private boolean startsWithEmoticon = false;

	/**
	 * Class constructor which sets the affect word
	 * 
	 * @param word
	 *            String representing the word
	 */
	public AffectWord(String word) {
		this.word = word;
	}

	/**
	 * Class constructor which sets the affect word and it's weights. Valence is
	 * calculated as a function of different emotion type weights.
	 * 
	 * @param word
	 *            {@link String} representing the word
	 * @param generalWeight
	 *            double representing the general emotional weight
	 * @param happinessWeight
	 *            double representing the happiness weight
	 * @param sadnessWeight
	 *            double representing the sadness weight
	 * @param angerWeight
	 *            double representing the anger weight
	 * @param fearWeight
	 *            double representing the fear weight
	 * @param disgustWeight
	 *            double representing the disgust weight
	 * @param surpriseWeight
	 *            double representing the surprise weight
	 */
	public AffectWord(String word, double generalWeight,
			double happinessWeight, double sadnessWeight, double angerWeight,
			double fearWeight, double disgustWeight, double surpriseWeight)
  {
		this.word = word;
		this.generalWeight = generalWeight;
    weights[Emotion.HAPPINESS] = happinessWeight;
    weights[Emotion.SADNESS] = sadnessWeight;
    weights[Emotion.ANGER] = angerWeight;
    weights[Emotion.FEAR] = fearWeight;
    weights[Emotion.DISGUST] = disgustWeight;
    weights[Emotion.SURPRISE] = surpriseWeight;
		this.generalValence = getValenceSum();
	}

	/**
	 * Class constructor which sets the affect word and it's weights, adjusted
	 * by the quoeficient. Valence is calculated as a function of different
	 * emotion type weights.
	 * 
	 * @param word
	 *            {@link String} representing the word
	 * @param generalWeight
	 *            double representing the general emotional weight
	 * @param happinessWeight
	 *            double representing the happiness weight
	 * @param sadnessWeight
	 *            double representing the sadness weight
	 * @param angerWeight
	 *            double representing the anger weight
	 * @param fearWeight
	 *            double representing the fear weight
	 * @param disgustWeight
	 *            double representing the disgust weight
	 * @param surpriseWeight
	 *            double representing the surprise weight
	 * @param coefficient
	 *            double representing the coefficient for adjusting the weights
	 */
	public AffectWord(String word, double generalWeight,
			double happinessWeight, double sadnessWeight, double angerWeight,
			double fearWeight, double disgustWeight, double surpriseWeight,
			double coefficient)
  {
		this.word = word;
		this.generalWeight = generalWeight * coefficient;
    weights[Emotion.HAPPINESS] = happinessWeight * coefficient;
    weights[Emotion.SADNESS] = sadnessWeight * coefficient;
    weights[Emotion.ANGER] = angerWeight * coefficient;
    weights[Emotion.FEAR] = fearWeight * coefficient;
    weights[Emotion.DISGUST] = disgustWeight * coefficient;
    weights[Emotion.SURPRISE] = surpriseWeight * coefficient;
		this.generalValence = getValenceSum();
	}

	/**
	 * Adjusts weights by the certain coefficient.
	 * 
	 * @param coefficient
	 *            double representing the coefficient for adjusting the weights
	 */

	public void adjustWeights(double coefficient)
  {
		generalWeight *= coefficient;
    for (int i = 0; i < weights.length; i++)
      weights[i] *= coefficient;
		normalise();
	}

	private void normalise()
  {
		if (generalWeight > 1)
			generalWeight = 1;
    for (int i = 0; i < weights.length; i++) {
      if (weights[i] > 1)
        weights[i] = 1;
    }
	}

	/**
	 * Flips valence of the word -- calculates change from postive to negative
	 * emotion.
	 */
	public void flipValence()
  {
		generalValence = -generalValence;
		double temp = weights[Emotion.HAPPINESS];
    weights[Emotion.HAPPINESS] = Math.max(
      Math.max(weights[Emotion.SADNESS], weights[Emotion.ANGER]),
      Math.max(weights[Emotion.FEAR], weights[Emotion.DISGUST]));
    weights[Emotion.SADNESS] = temp;
    temp /= 2;
    weights[Emotion.ANGER] = temp;
    weights[Emotion.FEAR] = temp;
    weights[Emotion.DISGUST] = temp;
	}

	/**
	 * Makes duplicate of the object.
	 * 
	 * @return {@link AffectWord}, new duplicated object
	 */
	public AffectWord clone() {
    try {
      return (AffectWord) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error(e);
    }
  }

	/**
	 * Returns true if the word starts with the emoticon.
	 * 
	 * @return boolean (true if the word starts with the emoticon, false if not)
	 */

	public boolean startsWithEmoticon() {
		return startsWithEmoticon;
	}

	/**
	 * Sets does the word start with emoticon.
	 * 
	 * @param startsWithEmoticon
	 *            boolean (true if the word starts with the emoticon, false if
	 *            not)
	 */
	public void setStartsWithEmoticon(boolean startsWithEmoticon) {
		this.startsWithEmoticon = startsWithEmoticon;
	}

	/**
	 * Getter for the anger weight.
	 * 
	 * @return double which represents the anger weight
	 */
	public double getAngerWeight() {
		return weights[Emotion.ANGER];
	}

	/**
	 * Getter for the anger weight.
	 * 
	 * @param angerWeight
	 *            double which represents the anger weight
	 */
	public void setAngerWeight(double angerWeight) {
    weights[Emotion.ANGER] = angerWeight;
	}

	/**
	 * Getter for the disgust weight.
	 * 
	 * @return double which represents the disgust weight
	 */
	public double getDisgustWeight() {
		return weights[Emotion.DISGUST];
	}

	/**
	 * Setter for the disgust weight.
	 * 
	 * @param disgustWeight
	 *            double which represents the disgust weight
	 */
	public void setDisgustWeight(double disgustWeight) {
    weights[Emotion.DISGUST] = disgustWeight;
	}

	/**
	 * Getter for the fear weight.
	 * 
	 * @return double which represents the fear weight
	 */
	public double getFearWeight() {
		return weights[Emotion.FEAR];
	}

	/**
	 * Getter for the fear weight.
	 * 
	 * @param fearWeight
	 *            double which represents the fear weight
	 */
	public void setFearWeight(double fearWeight) {
    weights[Emotion.FEAR] = fearWeight;
	}

	/**
	 * Getter for the happiness weight.
	 * 
	 * @return double which represents the happiness weight
	 */
	public double getHappinessWeight() {
		return weights[Emotion.HAPPINESS];
	}

	/**
	 * Setter for the happiness weight.
	 * 
	 * @param happinessWeight
	 *            double which represents the happiness weight
	 */
	public void setHappinessWeight(double happinessWeight) {
    weights[Emotion.HAPPINESS] = happinessWeight;
	}

	/**
	 * Getter for the sadness weight.
	 * 
	 * @return double which represents the sadness weight
	 */
	public double getSadnessWeight() {
		return weights[Emotion.SADNESS];
	}

	/**
	 * Setter for the sadness weight.
	 * 
	 * @param sadnessWeight
	 *            double which represents the sadness weight
	 */
	public void setSadnessWeight(double sadnessWeight) {
    weights[Emotion.SADNESS] = sadnessWeight;
	}

	/**
	 * Getter for the surprise weight.
	 * 
	 * @return double which represents the surprise weight
	 */
	public double getSurpriseWeight() {
		return weights[Emotion.SURPRISE];
	}

	/**
	 * Setter for the surprise weight.
	 * 
	 * @param surpriseWeight
	 *            double which represents the surprise weight
	 */
	public void setSurpriseWeight(double surpriseWeight) {
    weights[Emotion.SURPRISE] = surpriseWeight;
	}

	/**
	 * Getter for the word.
	 * 
	 * @return {@link String} which represents the word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Getter for the general weight.
	 * 
	 * @return double which represents the general weight
	 */
	public double getGeneralWeight() {
		return generalWeight;
	}

	/**
	 * Setter for the general weight.
	 * 
	 * @param generalWeight
	 *            double which represents the general weight
	 */
	public void setGeneralWeight(double generalWeight) {
		this.generalWeight = generalWeight;
	}

	/**
	 * Getter for the general valence.
	 * 
	 * @return double which represents the general valence
	 */
	public double getGeneralValence() {
		return generalValence;
	}

	/**
	 * Setter for the general valence
	 * 
	 * @param generalValence
	 *            double which represents the general valence
	 */
	public void setGeneralValence(int generalValence) {
		this.generalValence = generalValence;
	}

	/**
	 * Sets the boolean value which determines does a word have specific
	 * emotional weight for emotion types defined by Ekman: happiness, sadness,
	 * fear, anger, disgust, and surprise.
	 * 
	 * @return boolean value, true if all specific emotional weight have the
	 *         value of zero
	 */
	public boolean isZeroEkman() {
		return getWeightSum() == 0;
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	public String toString()
  {
    StringBuilder sb = new StringBuilder(word + 7 * 6);
    sb.append(word).append(' ').append(generalWeight);
    for (double w: weights)
      sb.append(' ').append(w);
		return sb.toString();
	}

	private double getValenceSum()
  {
		return weights[Emotion.HAPPINESS] - (
      (weights[Emotion.SADNESS] + weights[Emotion.ANGER]) +
      (weights[Emotion.FEAR] + weights[Emotion.DISGUST]));
	}

	private double getWeightSum()
  {
    double sum = 0;
    for (double w: weights)
      sum += w;
    return sum;
	}

	private double getSquareWeightSum()
  {
    double sum = 0;
    for (double w: weights)
      sum += w * w;
    return sum;
	}


	public static class WeightSumComparator implements Comparator<AffectWord>
	{
		private static Comparator<AffectWord> instance = null,
      reverseInstance = null;

		public static Comparator<AffectWord> getInstance()
		{
			if (instance == null)
				instance = new WeightSumComparator();
			return instance;
		}

    public static Comparator<AffectWord> getReverseInstance()
    {
      if (reverseInstance == null)
        reverseInstance = Collections.reverseOrder(getInstance());
      return reverseInstance;
    }

		private WeightSumComparator() { }

		@Override
		public int compare( AffectWord o1, AffectWord o2 )
		{
			return Double.compare(o1.getWeightSum(), o2.getWeightSum());
		}
	}


  public static class SquareWeightSumComparator implements Comparator<AffectWord>
  {
    public static final Comparator<AffectWord>
      INSTANCE = new WeightSumComparator(),
      REVERSE_INSTANCE = Collections.reverseOrder(INSTANCE);

    protected SquareWeightSumComparator() { }

    @Override
    public int compare( AffectWord o1, AffectWord o2 )
    {
      return Double.compare(o1.getSquareWeightSum(), o2.getSquareWeightSum());
    }
  }
}

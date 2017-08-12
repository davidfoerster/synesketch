/*
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
package synesketch.art.sketch;

import processing.core.PApplet;
import synesketch.SynesketchState;
import synesketch.art.util.SynesketchPalette;
import synesketch.emotion.Emotion;
import synesketch.emotion.EmotionalState;


public class Hooloovoo extends PApplet
{
	SynesketchPalette palette = new SynesketchPalette("standard");

	int[] currentPalette;

	int[] bwPalette = { -10461088, -7303024, -6579301, -10987432, -7368817,
	    -9868951,
	    -5921371, -10526881, -8421505, -8224126, -6381922, -8224126, -8816263,
	    -10724260, -11645362, -9934744, -5658199, -8947849, -5395027, -6579301,
	    -9868951, -6842473, -11053225, -9276814, -6645094, -8816263, -6710887,
	    -5921371, -10987432, -8092540, -7039852, -7697782, -5789785, -8750470,
	    -10197916, -6381922, -8750470, -5855578 };

  int[] emotionTypeDelays = { 400, 1500, 300, 800, 800, 200 };

	int dim = 400;
	int size = 40;
	int delay = 1500;
	float sat = 1.0f;


	public Hooloovoo(int dim)
	{
		super();
		this.dim = dim;
	}


	@Override
	public void settings()
	{
		size(dim, dim);
		//smooth();
	}


	@Override
	public void setup()
	{
		colorMode(HSB, 1.0f);
		noStroke();
		//noLoop();
		currentPalette = bwPalette;
	}


	@Override
	public void draw()
	{
		//colorMode(RGB);
		for (int i = 0; i < dim / size + 1; i++) {
			for (int j = 0; j < dim / size + 1; j++) {
				int color = getRandomColor();
				noStroke();
				color =
				    color(hue(color), saturation(color) * sat * 0.3f, brightness(color));
				colorMode(RGB);
				fill(red(color), green(color), blue(color), 1);
				//fill(hue(color), saturation(color), brightness(color), trans);
				rect(i * size, j * size, size, size);
				colorMode(HSB, 1.0f);
			}
		}
		//colorMode(HSB, 1.0f);
		delay(delay);
	}


	public void synesketchUpdate(SynesketchState state)
	{
		colorMode(HSB, 1.0f);
		EmotionalState currentState = (EmotionalState) state;
		System.out.println(currentState);
		Emotion emo = currentState.getStrongestEmotion();
		setSize(emo.getWeight());

		if (emo.getType() != Emotion.NEUTRAL) {
			currentPalette = palette.getColors(emo);
			delay = emotionTypeDelays[emo.getType()];
			sat = (float) Math.sqrt(emo.getWeight());
		} else {
			currentPalette = bwPalette;
			delay = 1500;
			sat = 0.5f;
		}

		//printCurrentPalette();
	}


	public void printCurrentPalette () {
		colorMode(RGB, 255);
		for (int color: currentPalette) {
			System.out.format("(%d, %d, %d)%n", (int) red(color), (int) green(color), (int) blue(color));
		}
	}


	public void setSize(double w)
	{
		if (w > 0.75) {
			size = 100;
		} else if (w > 0.5) {
			size = 80;
		} else if (w > 0.25) {
			size = 60;
		} else {
			size = 40;
		}
	}


	public int getRandomColor()
	{
		return currentPalette[(int) random(currentPalette.length)];
	}

}

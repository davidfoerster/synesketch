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
package synesketch.emotion.util;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Utility class for some text parsing algorithms
 * 
 * @author Uros Krcadinac email: uros@krcadinac.com
 * @version 1.0
 */
public final class ParsingUtility
{
	private ParsingUtility() { }


	/**
	 * Pareses text into sentences.
	 * 
	 * @param text
	 *            {@link String} which represents the text
	 * @return List of {@link String} instances representing the sentences
	 */
	public static List<String> parseSentences(String text)
	{
		List<String> value = new ArrayList<>();

		BreakIterator boundary = BreakIterator.getSentenceInstance();
		boundary.setText(text);
		int start = boundary.first();
		for (int end = boundary.next(); end != BreakIterator.DONE;
			start = end, end = boundary.next())
		{
			String word = text.substring(start, end);
			value.add(word);
		}
		return value;
	}

	/**
	 * Pareses sentences into words.
	 * 
	 * @param text
	 *            {@link String} which represents the sentence
	 * @return List of {@link String} instances representing the words
	 */
	public static List<String> parseWords(String text)
	{
		List<String> value = new ArrayList<>();

		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(text);
		int start = boundary.first();
		for (int end = boundary.next(); end != BreakIterator.DONE;
			start = end, end = boundary.next())
		{
			String word = text.substring(start, end);
			value.add(word);
		}
		return value;
	}

	/**
	 * Splits words by a given {@link String} argument (' ' or '-', to name two
	 * examples).
	 * 
	 * @param text
	 *            {@link String} which represents the text
	 * @param splitter
	 *            {@link String} which represents the splitting mark (' ' or
	 *            '-', to name two examples)
	 * @return {@link List} of {@link String} instances representing the split
	 *         words
	 */
	public static List<String> splitWords(String text, String splitter) {
		return Arrays.asList(text.split(splitter));
	}

	public static List<String> splitWords(String text, Pattern splitter) {
		return Arrays.asList(splitter.split(text));
	}
}

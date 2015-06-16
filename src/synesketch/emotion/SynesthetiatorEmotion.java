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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import synesketch.Synesthetiator;
import synesketch.UpdateHandler;


/**
 * Defines behavior for transferring textual affect information -- emotional
 * manifestations recognised in text -- into visual output and notifying
 * Processing applet ({@link PApplet}) about that new information.
 * 
 * @author Uros Krcadinac email: uros@krcadinac.com
 * @version 1.0
 */
public class SynesthetiatorEmotion extends Synesthetiator {

	private List<EmotionalState> emotionalStates = new ArrayList<EmotionalState>();

	private Empathyscope empathyscope = Empathyscope.getInstance();

  public SynesthetiatorEmotion() throws IOException
  {
    super();
  }

	/**
	 * Class constructor that sets parent Processing applet ({@link PApplet}).
	 * Parent applet is to be notified about the text event -- the recognition
	 * of a current emotion in text.
	 * 
	 * @param parent
	 * @throws Exception
	 */
	public SynesthetiatorEmotion(PApplet parent)
    throws IOException, NoSuchMethodException, IllegalAccessException
  {
		super(parent);
	}

	public SynesthetiatorEmotion( UpdateHandler handler ) throws IOException
  {
		super(handler);
	}

	/**
	 * Defines behaviour of transferring affective textual information into
	 * visual information (defines the synesthetic abilities).
	 * 
	 * @param text  contains the text which is to be analyzed.
   * @return  The result of the synesthetic analysis
	 * @throws IOException
	 */
	@Override
	public EmotionalState synesthetiseDirect( String text ) throws IOException
	{
		EmotionalState current = empathyscope.feel(text);
		if (!emotionalStates.isEmpty())
			current
					.setPrevious(emotionalStates
							.get(emotionalStates.size() - 1));
		emotionalStates.add(current);
		return current;
	}

}

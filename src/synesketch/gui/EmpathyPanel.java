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
package synesketch.gui;

import java.awt.*;
import java.lang.reflect.Constructor;

import javax.swing.*;

import processing.core.PApplet;

import synesketch.Synesthetiator;

/**
 * A Swing component, subclass of a JPanel, which embeds the Processing apllet
 * (PApplet). EmpathyPanel can be used to add Synesketch to a GUI application.
 * <p>
 * It can work for different Processing visualizing artwork, and for different
 * synesthetic alghoritms.
 * 
 * @author Uros Krcadinac email: uros@krcadinac.com
 * @version 1.0
 */
public class EmpathyPanel extends JPanel {

	private final Synesthetiator synesthetiator;

	private static final long serialVersionUID = 1L;

	private final PApplet embed;

	private static final String appletClassNamePrefix = "synesketch.art.sketch.";

	/**
	 * Class contructor that creates a Processing applet and embeds it.
	 *
	 * @param appletSize
	 *            dimensions of the Processing applet (applet is square)
	 * @param artType
	 *            name od the visualization type, Processing artwork; in other
	 *            words, name of the PApplet class representing the
	 *            visualizaton, which is located in synesketch.art.sketch
	 *            package
	 * @param synesthetiatorType
	 *            name of the synesthetiator type; in other words, name of the
	 *            subclass of the Synesthetiator which defines the synesthetic
	 *            behavior.
	 * @throws Exception
	 */
	public EmpathyPanel(int appletSize, String artType,
			String synesthetiatorType) throws Exception
	{
		String appletClassName = appletClassNamePrefix + artType;

		@SuppressWarnings("unchecked")
		Class<? extends PApplet> appletDefinition =
			(Class<? extends PApplet>) Class.forName(appletClassName);
		Constructor<? extends PApplet> appletConstructor = appletDefinition
				.getConstructor(int.class);
		embed = appletConstructor.newInstance(appletSize);

		@SuppressWarnings("unchecked")
		Class<? extends Synesthetiator> syneDefinition =
			(Class<? extends Synesthetiator>) Class.forName(synesthetiatorType);
		Constructor<? extends Synesthetiator> syneConstructor = syneDefinition
				.getConstructor(PApplet.class);
		synesthetiator = syneConstructor.newInstance(embed);

		PApplet.runSketch(new String[]{ appletClassName }, embed);

		//add(embed, BorderLayout.CENTER);
	}

	/**
	 * Notifies the Synesthetiator about the new text. It should be called from
	 * the GUI.
	 *
	 * @param text
	 *            String which represents the text to be analysed
	 * @throws Exception
	 */
	public void fireSynesthesiator(String text) throws Exception {
		synesthetiator.synesthetise(text);
	}

	/**
	 * Getter for the embeded Processing Applet, so it can be accessed from the
	 * GUI.
	 *
	 * @return embeded Processing applet
	 */
	public PApplet getProcessingApplet() {
		return embed;
	}

}

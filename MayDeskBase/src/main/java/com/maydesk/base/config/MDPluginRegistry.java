/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/
package com.maydesk.base.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @author chrismay
 */
public class MDPluginRegistry {

	private static MDPluginRegistry INSTANCE;
	
	public static MDPluginRegistry getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MDPluginRegistry();
		}
		return INSTANCE;
	}
	
	private XMLDesktopConfig configuration;
	
	private MDPluginRegistry() {
	}
	
	public XMLDesktopConfig getConfiguration() {
		if (configuration == null) {
			try {
				InputStream configStream = getClass().getResourceAsStream("config.xml");
				if (configStream == null) {
					//just for local development
					String pathName = "../MayDeskDemoProject";
					File file = new File(pathName + "/src/resource/config.xml");
					if (!file.exists()) {
						throw new IllegalArgumentException("File " + file.getAbsolutePath() + " not found!");
					}
					configStream = new FileInputStream(file);
				}
				JAXBContext context = JAXBContext.newInstance(XMLDesktopConfig.class);
				Unmarshaller m = context.createUnmarshaller();
				configuration = (XMLDesktopConfig) m.unmarshal(configStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return configuration;
	}
	
	public static void main(String... args) {
		
		MDPluginRegistry r = new MDPluginRegistry();
		//loadControls();
		
		r.saveDiagram();
	}
	
	public  boolean saveDiagram() {

		XMLDesktopConfig config  = new XMLDesktopConfig();
		XmlMenu w = new XmlMenu();
		w.setTextEN("Menu 1");		
		config.setMenu(w);
		
		XmlMenu w2 = new XmlMenu();
		w2.setTextEN("p2");
		w.getMenuEntries().add(w2);
		
		XmlMenuItem w3 = new XmlMenuItem();
		w3.setTextEN("Just do it!");
		w3.setCommandClass("com.maydesk.base.command.SopCommands");
		w3.setCommandId("justDoIt");
		w2.getMenuItems().add(w3);
		
		XmlDesktopItem di = new XmlDesktopItem();
		di.setLeft(50);
		di.setTop(120);
		di.setClassName("com.maydesk.base.widgets.PDRecycleBin");
		config.getDesktopEntries().add(di);
		
		File file = new File("test.xml");
		try {
			JAXBContext context = JAXBContext.newInstance(XMLDesktopConfig.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(config, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}

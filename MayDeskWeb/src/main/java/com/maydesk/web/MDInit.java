package com.maydesk.web;

import java.util.Hashtable;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.model.MDataLink;
import com.maydesk.base.model.MPlug;
import com.maydesk.base.model.MTask;
import com.maydesk.base.model.MTaskAssign;
import com.maydesk.base.model.MWire;
import com.maydesk.base.sop.plug.Plugable;
import com.maydesk.dvratio.sop.SopDVRDefaultWires;
import com.maydesk.dvratio.sop.SopDVRPlugs;

public class MDInit {
	
	
	public static Hashtable<String, MPlug> addNewPlugs(Plugable[] plugs2) {
		Session s = PDHibernateFactory.getSession();
		
//		Criteria c = PDHibernateFactory.getSession().createCriteria(MPlug.class);
//		List<MPlug> plugs = c.list();
		Hashtable<String, MPlug> plugSet = new Hashtable<String, MPlug>();
//		for (MPlug plug : plugs) {
//			plugSet.put(plug.getName(), plug);
//		}
		
		for (Plugable p : plugs2) {
			String name = ((Enum)p).name();
			if ("NULL".equals(name)) {
				continue;
			} else if (plugSet.containsKey(name)) {
				MPlug plug = plugSet.get(name);
				plug.setClassNames(p);
				s.update(plug);
				continue;
			}
			
			MPlug plug = new MPlug();
			plug.setName(name);
			plug.setCaption(p.plugTitle());
			plug.setClassNames(p);
			plug.setPlugType(p.plugType());
			plug.setIcon(p.icon());
			s.save(plug);
			plugSet.put(name, plug);
		}
		return plugSet;
	}	
	
	public static void resetAll() {
		Session s = PDHibernateFactory.getSession();

		cleanTable(s, MWire.class);
		cleanTable(s, MPlug.class);
		
		Hashtable<String, MPlug> plugs = new Hashtable<String, MPlug>();
		
		//plugs.putAll(addNewPlugs(SopPlugs.values()));
		Hashtable<String, MPlug> xx = addNewPlugs(SopDVRPlugs.values());
		plugs.putAll(xx);

		Hashtable<String, MWire> wires = new Hashtable<String, MWire>();
		for (SopDVRDefaultWires defaultWire : SopDVRDefaultWires.values()) {
			if (defaultWire.plug() == null) continue;
			MPlug plug = plugs.get(defaultWire.plug().name());
			if (plug == null) continue;
			String parentName = defaultWire.parent().name();
			MWire parentWire = wires.get(parentName);
			
			MWire wire = new MWire();
			wire.setPlug(plug);
			wire.setParentWire(parentWire);
			wire.setCaption(defaultWire.textEN());
			wire.setParameter(defaultWire.name());  //???
			s.save(wire);
			wires.put(defaultWire.name(), wire);
		}					
		
		cleanTable(s, MTaskAssign.class);
		cleanTable(s, MTask.class);
		cleanTable(s, MDataLink.class);

		s.flush();		
	}
	
	private static void cleanTable(Session s, Class clazz) {
		Criteria c = PDHibernateFactory.getSession().createCriteria(clazz);
		List list = c.list();
		for (Object dl : list) {
			s.delete(dl);
		}
	}
}

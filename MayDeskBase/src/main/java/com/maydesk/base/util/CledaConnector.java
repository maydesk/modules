/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.util;

import java.io.File;
import java.io.Serializable;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Entity;
import javax.sql.DataSource;

import org.hibernate.EmptyInterceptor;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.type.Type;

import com.maydesk.base.model.MBaseWithTitle;

/**
 * @author Angel Perez
 */
public class CledaConnector {

	private static CledaConnector instance = new CledaConnector();

	private SessionFactory sessionFactory;

	private CledaConnector() {
		createSessionFactory();
	}

	public static CledaConnector getInstance() {
		if (instance == null) {
			instance = new CledaConnector();
		}
		return instance;
	}

	private void createSessionFactory() {
		Properties props = new Properties();
		
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/maydesk_db");
			props.put("hibernate.connection.datasource", ds);
		} catch (NamingException e) {
			e.printStackTrace();
		}

		props.put("hibernate.cglib.use_reflection_optimizer", true);
		props.put("hibernate.show_sql", false);
		props.put("hibernate.hbm2ddl.auto", "update");
		props.put("transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");

		props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

		// Create config
		Configuration config = new Configuration();
		config.setProperties(props);

		config.setInterceptor(new AuditInterceptor());
		config.setNamingStrategy(new ImprovedNamingStrategy());
		config.setProperties(props);

		// registering model
		registerModels(config, "com.maydesk.base.model");
		registerModels(config, "com.maydesk.social.model");

		ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();

		ServiceRegistry serviceRegistry = serviceRegistryBuilder.applySettings(props).buildServiceRegistry();
		sessionFactory = config.buildSessionFactory(serviceRegistry);
	}

	private void registerModels(Configuration config, String pckgname) {
		// see also http://www.javaworld.com/javaworld/javatips/jw-javatip113.html?page=2
		File directory = null;
		URL url = null;
		try {
			String pack = '/' + pckgname.replace('.', '/');
			url = getClass().getClassLoader().getResource(pack);
			directory = new File(url.getFile());
		} catch (NullPointerException x) {
			throw new IllegalArgumentException(pckgname + " does not appear to be a valid package");
		}
		List<String> files = new Vector<String>();

		if (directory.exists()) {
			for (String s : directory.list()) {
				files.add(pckgname + "." + s);
			}
		} else {
			try {
				// It does not work with the filesystem: we must
				// be in the case of a package contained in a jar file.
				JarURLConnection conn = (JarURLConnection) url.openConnection();
				// String starts = conn.getEntryName();
				JarFile jfile = conn.getJarFile();
				Enumeration e = jfile.entries();
				while (e.hasMoreElements()) {
					ZipEntry entry = (ZipEntry) e.nextElement();
					String entryname = entry.getName();
					entryname = entryname.replace('/', '.');
					if (!entryname.startsWith(pckgname))
						continue;
					if (!entryname.endsWith(".class"))
						continue;
					files.add(entryname);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		// Get the list of the files contained in the package
		for (String file : files) {
			// we are only interested in .class files
			if (file.endsWith(".class")) {
				// removes the .class extension
				String className = file.substring(0, file.length() - 6);
				try {
					Class clazz = Class.forName(className);
					if (clazz.getAnnotation(Entity.class) != null) {
						config.addAnnotatedClass(clazz);
					}
				} catch (ClassNotFoundException cnfe) {
					System.out.println("Class " + className + " mot found");
				}
			}
		}
	}

	public class AuditInterceptor extends EmptyInterceptor {
		@Override
		public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
			if (entity instanceof MBaseWithTitle) {
				MBaseWithTitle bwt = (MBaseWithTitle) entity;
				bwt.setCachedTitle(bwt.createCachedTitle());
				bwt.setCachedDescription(bwt.createCachedDescription());
			}
			return false;
		}
	}

	public Session createSession() {
		Session session = sessionFactory.openSession();
		session.setFlushMode(FlushMode.COMMIT);
		session.beginTransaction();
		return session;
	}
}
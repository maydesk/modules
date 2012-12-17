/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
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

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
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

	public static CledaConnector getInstance()  {
		if (instance == null) {
			instance = new CledaConnector();
		}
		return instance;
	}

	private void createSessionFactory() {
		Properties props = new Properties();

		
		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/maydesk_dev");
			props.put("hibernate.connection.datasource", ds);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
		//props.put("hibernate.connection.datasource", getDataSource());
		
		props.put("hibernate.cglib.use_reflection_optimizer", true);
		props.put("hibernate.show_sql", false);
		props.put("hibernate.hbm2ddl.auto", "update");
		props.put("transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");		

		props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		
		// Create config
		Configuration config = new Configuration();
		config.setProperties(props);

		//AnnotationConfiguration config = new AnnotationConfiguration();
		config.setInterceptor(new AuditInterceptor());
		config.setNamingStrategy(new ImprovedNamingStrategy());
		config.setProperties(props);
		
		// registering model
		registerModels(config, "com.maydesk.base.model");
		registerModels(config, PDUtil.getProperty("model.path"));
		if (!PDUtil.isEmpty(PDUtil.getProperty("model.path2"))) {
			registerModels(config, PDUtil.getProperty("model.path2"));
		}
		String s3 = PDUtil.getProperty("model.path3");
		if (!PDUtil.isEmpty(s3)) {
			registerModels(config, s3);
		}
		if (!PDUtil.isEmpty(PDUtil.getProperty("model.path4"))) {
			registerModels(config, PDUtil.getProperty("model.path4"));
		}
		
		ServiceRegistryBuilder serviceRegistryBuilder = new ServiceRegistryBuilder();

		ServiceRegistry serviceRegistry =serviceRegistryBuilder.applySettings(props).buildServiceRegistry();
		sessionFactory = config.buildSessionFactory(serviceRegistry);
	}

	// Create the Tomcat JDBC Connection Pool Datasource
	private DataSource getDataSource() {
		PoolProperties poolProps = new PoolProperties();

		// database connection
		String host = PDUtil.getProperty("db.host");
		if (PDUtil.isEmpty(host)) {
			host = "localhost";
		}
		
		poolProps.setUrl("jdbc:mysql://" + host + "/" + PDUtil.getProperty("db.database"));
		poolProps.setDriverClassName("com.mysql.jdbc.Driver");
		
		poolProps.setUsername(PDUtil.getProperty("db.username"));
		poolProps.setPassword(PDUtil.getProperty("db.password"));

		poolProps.setJmxEnabled(true);
		poolProps.setTestWhileIdle(false);
		poolProps.setTestOnBorrow(true);
		poolProps.setValidationQuery("SELECT 1");
		poolProps.setTestOnReturn(false);
		poolProps.setValidationInterval(30000);
		poolProps.setTimeBetweenEvictionRunsMillis(30000);

		poolProps.setMaxActive(75);
		poolProps.setMaxIdle(25);
		poolProps.setInitialSize(3);
		poolProps.setMaxWait(10000);
		poolProps.setRemoveAbandonedTimeout(60);
		poolProps.setMinEvictableIdleTimeMillis(30000);
		poolProps.setMinIdle(10);

		poolProps.setLogAbandoned(true);
		poolProps.setRemoveAbandoned(true);

		poolProps.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

		DataSource dataSource = new DataSource();
		dataSource.setPoolProperties(poolProps);
		return dataSource;
	}

	
	
	private void registerModels(Configuration config, String pckgname) {
		// see also
		// http://www.javaworld.com/javaworld/javatips/jw-javatip113.html?page=2
		File directory = null;
		URL url = null;
		try {
			String pack = '/' + pckgname.replace('.', '/');
			//System.out.println(pack);
			url = getClass().getResource(pack);
			//System.out.println(url);
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
				//String starts = conn.getEntryName();
				JarFile jfile = conn.getJarFile();
				Enumeration e = jfile.entries();
				while (e.hasMoreElements()) {
					ZipEntry entry = (ZipEntry) e.nextElement();
					String entryname = entry.getName();
					entryname = entryname.replace('/', '.');
					if (!entryname.startsWith(pckgname)) continue;
					if (!entryname.endsWith(".class")) continue;
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
				//String className = pckgname + '.' + file.substring(0, file.length() - 6);
				String className = file.substring(0, file.length() - 6);
				try {
					Class clazz = Class.forName(className);
					if (clazz.getAnnotation(Entity.class) != null) {
						config.addAnnotatedClass(clazz);
						
//						PersistentClass persistentClass = config.getClassMapping(clazz.getCanonicalName());
//				         SimpleValue simpleValue = new SimpleValue();
//				         simpleValue.addColumn(new Column("fld_" + "test"));
//				         simpleValue.setTypeName(String.class.getName());
//
//				         simpleValue.setTable(persistentClass.getTable());
//
//				         Property property = new Property();
//				         property.setName("test");
//				         property.setValue(simpleValue);
//			             Property property2 = persistentClass.getProperty(clazz.getCanonicalName());
//			             Component customProperties = (Component) property2.getValue();
//			             customProperties.addProperty(property);
						
					}
				} catch (ClassNotFoundException cnfe) {
					System.out.println("Class " + className + " mot found");
				}
			}
		}
	}

	public void dropDB() {
		// TODO Auto-generated method stub
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

	// public String executeDBScripts() {
	//		
	// String sqlScriptFilePath = "busstop23_testbase.sql";
	// Session session = createSession();
	// try {
	// Connection con = session.connection();
	// con.setAutoCommit(false);
	// ScriptRunner runner = new ScriptRunner(con, false, true);
	// URL url = getClass().getClassLoader().getResource(sqlScriptFilePath);
	//			
	// FileInputStream fis = new FileInputStream(url.getFile());
	// InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
	//			
	// runner.runScript(isr);
	// //con.getc
	// } catch (Exception e) {
	// System.err.println("Failed to Execute " + sqlScriptFilePath + ". The
	// error
	// is: " + e.getMessage());
	// return e.getMessage();
	// }
	// return null;
	// }
}
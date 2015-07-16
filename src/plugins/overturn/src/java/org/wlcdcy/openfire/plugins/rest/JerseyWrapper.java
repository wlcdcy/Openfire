package org.wlcdcy.openfire.plugins.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.jivesoftware.admin.AuthCheckFilter;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * The Class JerseyWrapper.
 */
public class JerseyWrapper extends ServletContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/** The Constant SCAN_PACKAGE_DEFAULT. */
	private static final String SCAN_PACKAGE_DEFAULT = JerseyWrapper.class.getPackage().getName();
	
	/** The Constant AUTHFILTER. */
	private static final String AUTHFILTER = "org.wlcdcy.openfire.plugins.AuthFilter";

	/** The Constant CONTAINER_REQUEST_FILTERS. */
	private static final String CONTAINER_REQUEST_FILTERS = "com.sun.jersey.spi.container.ContainerRequestFilters";
	
	/** The Constant RESOURCE_CONFIG_CLASS_KEY. */
	private static final String RESOURCE_CONFIG_CLASS_KEY = "com.sun.jersey.config.property.resourceConfigClass";

	/** The Constant RESOURCE_CONFIG_CLASS. */
	private static final String RESOURCE_CONFIG_CLASS = "com.sun.jersey.api.core.PackagesResourceConfig";

	/** The config. */
	private static Map<String, Object> config;
	/** The prc. */
	private static PackagesResourceConfig prc;
	
	private static final String SERVER_URL="overturn/*";
	
	

	static {
		config = new HashMap<String, Object>();
		config.put(RESOURCE_CONFIG_CLASS_KEY, RESOURCE_CONFIG_CLASS);
		prc = new PackagesResourceConfig(SCAN_PACKAGE_DEFAULT);
		
		prc.setPropertiesAndFeatures(config);
		prc.getProperties().put(CONTAINER_REQUEST_FILTERS, AUTHFILTER);

		prc.getClasses().add(HelloService.class);
		prc.getClasses().add(WebhookService.class);
	}
	
	/**
	 * Instantiates a new jersey wrapper.
	 */
	public JerseyWrapper() {
		super(prc);
	}

	@Override
	public void destroy() {
		AuthCheckFilter.removeExclude(SERVER_URL);
		super.destroy();
	}

	@Override
	public void init(ServletConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		AuthCheckFilter.addExclude(SERVER_URL);
	}
	
}

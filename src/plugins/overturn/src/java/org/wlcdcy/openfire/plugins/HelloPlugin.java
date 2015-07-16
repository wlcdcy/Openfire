package org.wlcdcy.openfire.plugins;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.jivesoftware.openfire.XMPPServer;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.roster.RosterManager;
import org.jivesoftware.openfire.user.UserManager;
import org.jivesoftware.util.JiveGlobals;
import org.jivesoftware.util.PropertyEventDispatcher;
import org.jivesoftware.util.PropertyEventListener;
import org.jivesoftware.util.StringUtils;

public class HelloPlugin implements Plugin, PropertyEventListener {
	
	private UserManager userManager;
	private RosterManager rosterManager;
	private XMPPServer server;

	private static PluginManager plugin_manager;
	
	
	/** The enabled. */
	private boolean enabled = true;

	@Override
	public void initializePlugin(PluginManager manager, File pluginDirectory) {
		server = XMPPServer.getInstance();
		userManager = server.getUserManager();
		rosterManager = server.getRosterManager();
		plugin_manager = manager;
		
		PropertyEventDispatcher.addListener(this);
	}

	@Override
	public void destroyPlugin() {
		userManager = null;
		PropertyEventDispatcher.removeListener(this);
	}

	@Override
	public void propertySet(String property, Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void propertyDeleted(String property, Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void xmlPropertySet(String property, Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void xmlPropertyDeleted(String property, Map<String, Object> params) {
		// TODO Auto-generated method stub
		
	}

	public boolean isEnabled() {
		return enabled;
	}	
	
}

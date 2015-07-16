package org.wlcdcy.openfire.plugins;

import javax.annotation.PostConstruct;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.jivesoftware.openfire.XMPPServer;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class AuthFilter implements ContainerRequestFilter {
	
	private HelloPlugin plugin ;
	
	@PostConstruct
	public void init(){
		plugin = (HelloPlugin) XMPPServer.getInstance().getPluginManager().getPlugin("myplugin");
	}

	@Override
	public ContainerRequest filter(ContainerRequest containerRequest) {
		if (!plugin.isEnabled()) {
			throw new WebApplicationException(Status.FORBIDDEN);
		}
		
		// Get the authentification passed in HTTP headers parameters
		String auth = containerRequest.getHeaderValue("authorization");

//		if (auth == null) {
//			throw new WebApplicationException(Status.UNAUTHORIZED);
//		}
		return containerRequest;
	}

}

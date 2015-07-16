package org.wlcdcy.openfire.plugins.rest;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wlcdcy.openfire.plugins.api.TeambitionApi;

@Path("overturn/hello")
public class HelloService {
	
	private static Logger logger = LoggerFactory.getLogger(HelloService.class);

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public String sayhello(@PathParam("name") String name) {
		logger.info("request data : "+name);
		return "welcome : " + name;
	}
	
	
	@GET
	@Path("/teambition/project")
	public List<Map<String,Object>> listProjects(@HeaderParam("sid") String sid){
		String access_token = getTeambitionAccessToken(sid);
		return TeambitionApi.listProjects(access_token);
	}
	


	@GET
	@Path("/teambition/project/event")
	public List<String> listWebhookEvent4Projects(@HeaderParam("sid") String sid){
		String access_token = getTeambitionAccessToken(sid);
		return TeambitionApi.listWebhookEvent4Projects(access_token);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@POST
	@Path("/teambition/project")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createWebhook4Project(@HeaderParam("sid") String sid, Map jsonData){
		
		String access_token = getTeambitionAccessToken(sid);
		String _token = createHiworkisAccessToken(sid);
		
		String p_id = (String) jsonData.get("_id");
		String callbackURL =String.format("s?token=%s", TeambitionApi.hook_project,_token) ;
		List<String> events = (List<String>) jsonData.get("events");
		
		String resp_result = TeambitionApi.createWebhook4Project(access_token,p_id, callbackURL, events);
		if(resp_result!=null){
			//持久化数据库
			
			String app_name = TeambitionApi.APP_NAME;
			String channel_id = (String) jsonData.get("channel");
			String nickname = (String) jsonData.get("nickname");
			String jid;
			String gid;
			String token = _token;
			String state = "1";
			Date create_date = new Date();
			
			// extend is json: include [webhook_id,project_id,webhook_url,notify_event]
			String extend;
			
			String webhook_id = resp_result;
			String project_id = p_id;
			String webhook_url = callbackURL;
			List<String> notify_events =  events;
			
			
			
			return "200";
		}
		
		return "200-500";
	}
	
	

	private String getTeambitionAccessToken(String sid) {
		return null;
	}


	private String createHiworkisAccessToken(String session_id) {
		return null;
	}
}

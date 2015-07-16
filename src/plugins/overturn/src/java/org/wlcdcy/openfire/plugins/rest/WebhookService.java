package org.wlcdcy.openfire.plugins.rest;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wlcdcy.openfire.plugins.api.TeambitionApi;

@Path("overturn/webhook")
public class WebhookService {
	private static Logger logger = LoggerFactory
			.getLogger(WebhookService.class);

	// **********************teambition
	// service************************************

	/**
	 * 重定向到认证授权地址
	 * 
	 * @param req
	 * @return
	 */
	@Path("/teambition/auth")
	@GET
	public Response oauth2Auth(@Context HttpServletRequest req) {
		String redirect_url = TeambitionApi.getOAuthUrl();
		return Response.seeOther(UriBuilder.fromUri(redirect_url).build())
				.build();
	}

	/**
	 * 认证授权成功回调地址
	 * 
	 * @param code
	 * @param state
	 * @return
	 */
	@Path("/teambition/authback")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String auth_back(@QueryParam("code") String auth_code,
			@QueryParam("state") String state) {

		logger.info(String.format(
				"【teambition】auth code is : %s	res state is : %s", auth_code,
				state));

		// fetch access_token request
		String resp_data = TeambitionApi.fetchAccessToken(auth_code);

		try {
			String access_token = (String) new ObjectMapper().readValue(
					resp_data, Map.class).get("access_token");
			// check access_token
			if (TeambitionApi.checkAccessToken(access_token)) {

				// 持久化用户的 auth_code&access_code
				// TeambitionApi.auth_code = auth_code;
				// TeambitionApi.access_token = access_token;

				return access_token;
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 工程事件变化通知地址
	 * 
	 * @param req
	 * @param jsonData
	 * @return
	 */
	@Path("/teambition/project")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String project(@Context HttpServletRequest req,
			@QueryParam("token") String token, Map<String, Object> jsonData) {
		String content_type = req.getContentType();
		logger.info(content_type);

		return content_type;
	}

	/**
	 * 组织机构事件变化通知地址
	 * 
	 * @param req
	 * @param jsonData
	 * @return
	 */
	@Path("/teambition/organization")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String organization(@Context HttpServletRequest req,
			@QueryParam("token") String token, Map<String, Object> jsonData) {
		String content_type = req.getContentType();
		logger.info(content_type);

		return content_type;
	}
}

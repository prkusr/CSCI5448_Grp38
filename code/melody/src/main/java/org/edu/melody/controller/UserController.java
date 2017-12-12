package org.edu.melody.controller;

import org.apache.logging.log4j.LogManager;
import org.edu.melody.model.User;
import org.edu.melody.response.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.dao.PaymentDao;
import org.edu.melody.manager.PaymentManager;
import org.edu.melody.manager.PlanManager;
import org.edu.melody.manager.UserManager;
import org.edu.melody.model.Artist;
import org.edu.melody.model.Customer;
import org.edu.melody.model.UserInfoDTO;
import org.edu.melody.model.PlaylistDTO;
import org.edu.melody.model.SetupDDDTO;
import org.edu.melody.model.ModActivationStatusDTO;
import org.edu.melody.model.TextMessageHandler;
import org.edu.melody.model.SessionInfoDTO;
import org.edu.melody.model.PlanEnrollDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController extends Controller {

	static final Logger logger = LogManager.getLogger(UserController.class);
	UserManager userManager = new UserManager();
	PlanManager planManager = new PlanManager();
	PaymentManager paymentManager = new PaymentManager();

	public static Map<Long, Long> uiotp = new HashMap<>();
	public static final String saveDDAndSendOtp = "SSDO";

	@RequestMapping(value = "login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Response login(@RequestBody UserInfoDTO userSessionInfo) {

		Response resp = Response.builder().errorCode(0).errorStr("").message("").build();
				
		String sessionId = userManager.signIn(userSessionInfo.getName(), userSessionInfo.getPwd(), userSessionInfo.getType(), resp);
		if (resp.getErrorCode() == 0)
			resp.setMessage("SessionId: {"+sessionId+"}");
		return resp;

	}

	@RequestMapping(value = "signup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Response signUp(@RequestBody UserInfoDTO userSessionInfo) {
		
		Response resp = Response.builder().errorCode(0).errorStr("").message("").build();		
		userManager.signUp(userSessionInfo.getName(), userSessionInfo.getPwd(), userSessionInfo.getEmail(), userSessionInfo.getCellNum(), userSessionInfo.getType(), resp);		
		
		if(resp.getErrorCode() == 0)
			resp.setMessage("User Signup successfull.");
		return resp;
	}

	
	@RequestMapping(value = "signout", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Response signOut(@RequestBody SessionInfoDTO sessionInfo) {
		
		Response resp = Response.builder().errorCode(0).errorStr("").message("").build();
		userManager.signOutUser(sessionInfo.getSessionId());
		resp.setMessage("User signed out successfully");
		return resp;
	}

	@RequestMapping(value = "getuser", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Response getUserInfo(@RequestBody SessionInfoDTO sessionInfo) {
				
		Response resp = Response.builder().errorCode(0).errorStr("").message("").build();
		User usr = userManager.getUserInfo(sessionInfo.getSessionId());
		if (usr == null)
			resp.setError(1, "User not logged In.");
		else
			if (usr instanceof Customer)
				resp.setCustomer((Customer)usr);
			else if (usr instanceof Artist)
				resp.setArtist((Artist)usr);
			else
				resp.setUser(usr);
		return resp;
	}

	@RequestMapping(value = "plans")
	public Response getPlans() {
			
		Response resp = Response.builder().errorCode(0).errorStr("").message("").build();
		logger.debug("Fetching plans....");		
		resp.setPlans(planManager.getPlans());
		return resp;
	}

	@RequestMapping(value = "enrollplan", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Response enrollInPlan(@RequestBody PlanEnrollDTO planEnroll) {
				
		Response resp = Response.builder().errorCode(0).errorStr("").message("").build();
		
		try {	
			Customer cust;
			int paymentId = -1;
			User usr = userManager.getUserInfo(planEnroll.getSessionId());
			if (usr == null)
				resp.setError(1,"User not logged In.");
			else if( usr instanceof Customer) {
				cust = (Customer) userManager.getUserInfo(planEnroll.getSessionId());
				
				if (!planManager.checkEnrollment(cust, resp)) {
					
					boolean enrollmentRequiresPayment = planManager.requiresPayment(planEnroll.getPlanId(), resp);		
					
					if(resp.getErrorCode() > 0)
						return resp;
					if (enrollmentRequiresPayment){
						// Do Payment Processing
					}					
					planManager.planEnrollment(cust, planEnroll.getPlanId(), paymentId, resp);					
					cust.setPlanEnrollmentDate(new Date());				
					if(resp.getErrorCode() == 0)
						resp.setMessage("Enrolled user ["+cust.getUserName()+"] to plan.");
				}else{
					resp.setMessage("User already enrolled in a plan");
				}
			} else
				resp.setError(1, "Operation not valid for this user."); 			
		} catch(Exception e) {
			logger.error("Failed to enroll user to plan: "+e.getClass().getName() + ": " + e.getMessage()+"\n"+e.getStackTrace());
			resp.setError(1, "Failed to enroll user to plan: "+e.getClass().getName() + ": " + e.getMessage());
		}
		
		return resp;
		
	}
	
	@RequestMapping(value = "changeacc", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Response changeAccDetForArtist(@RequestBody SetupDDDTO dDDetails) {
		
		Response resp = Response.builder().errorCode(0).errorStr("").message("").build();
		
		User usr = userManager.getUserInfo(dDDetails.getSessionId());
		if (usr != null){			
			userManager.updateAccDetails(usr, dDDetails.getAccountNo(), dDDetails.getRoutingNumber(), dDDetails.getBankName(), dDDetails.getBankAddress(), resp);			
		}else
			resp.setError(1,"User not logged In.");
		
		return resp;
	}

	
	@RequestMapping(value = "modactivationstatus", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public Response changeActivationStatus(@RequestBody ModActivationStatusDTO activationStatus) {
		
		Response resp = Response.builder().errorCode(0).errorStr("").message("").build();
		
		User usr = userManager.getUserInfo(activationStatus.getSessionId());
		if (usr != null){			
			userManager.changeActivationStatusForUser(usr, activationStatus.getUserName(), resp);		
			if (resp.getErrorCode() == 0)
				resp.setMessage("Activation status changed successfully for user ["+activationStatus.getUserName()+"]");
		}else
			resp.setError(1,"User not logged In.");
		
		
		return resp;
	}
	
	@RequestMapping(value = "makepay", method = RequestMethod.GET)
	public String makePayment(@RequestParam("sessionId") String sessionId, @RequestParam("songid") int songid) {

		User wds = userManager.getUserInfo(sessionId);
		if (wds == null) {
			return "User not logged in";
		}
		PaymentDao ad = new PaymentDao();

		return "You've been charged $" + ad.debitSong(wds, songid);
		// return "Attempting: "+wds.getUserId();
	}

	@RequestMapping(value = "createPlaylist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String createPlaylist(@RequestBody PlaylistDTO playlist) {

		if (userManager.isUserLoggedIn(playlist.getSessionId())) {
			int playlistId = userManager.createPlaylist(playlist.getName(), playlist.getSongIds(),
					userManager.getUserInfo(playlist.getSessionId()).getUserId());
			logger.info(playlistId);
			return "{playlistId : " + playlistId + "}";
		}
		return "{ 'Error' : 'User not logged in'}";
	}

	@RequestMapping(value = "sendOTP", method = RequestMethod.GET)
	public String sentOTP(@RequestParam("sessionId") String sessionId, @RequestParam("number") long rcnum) {

		User wds = userManager.getUserInfo(sessionId);
		if (wds == null) {
			return "User not logged in";
		}
		TextMessageHandler ad = new TextMessageHandler();
		ad.setReceiverNumber(rcnum);
		boolean sms = ad.send();

		if (sms == true) {
			uiotp.put(wds.getUserId(), ad.getOtp());
			return "A message has been sent to : " + rcnum + " :  " + uiotp;

		}
		return "Error in sending SMS";
		// return "You've been charged $"+ad.debitSong(wds,songid);
		// return "Attempting: "+wds.getUserId();
	}

	@RequestMapping(value = "updatePlaylist", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String updatePlaylist(@RequestBody PlaylistDTO playlist) {

		if (userManager.isUserLoggedIn(playlist.getSessionId()) && playlist.getId() > 0) {
			userManager.updatePlaylist(playlist.getName(), playlist.getSongIds(),
					userManager.getUserInfo(playlist.getSessionId()).getUserId(), playlist.getId());
			logger.info("Updated the playlist");
			return "{'response' : 'Updated the playlist'}";
		}
		return "{ 'Error' : 'User not logged in'}";
	}

	@RequestMapping(value = saveDDAndSendOtp, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public String saveDirectDepositAndSendOtp(@RequestBody SetupDDDTO setup) {
		if (userManager.isUserLoggedIn(setup.getSessionId())) {
			paymentManager.saveDDandSendOtp(userManager.getUserInfo(setup.getSessionId()), setup.getAccountNo(),
					setup.getRoutingNumber(), setup.getBankName(), setup.getBankAddress());
		}
		return "{saved details}";
	}

	@RequestMapping(value = "validateOTP", method = RequestMethod.GET)
	public String validateOTP(@RequestParam("sessionId") String sessionId, @RequestParam("otp") long entotp) {
		if (userManager.isUserLoggedIn(sessionId)) {
			paymentManager.insertDDFromOTP(userManager.getUserInfo(sessionId), entotp);
		}
		return "Test";
	}

}

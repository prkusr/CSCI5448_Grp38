package org.edu.melody.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.dao.PaymentDao;
import org.edu.melody.manager.PlanManager;
import org.edu.melody.manager.UserManager;
import org.edu.melody.model.Artist;
import org.edu.melody.model.Customer;
import org.edu.melody.model.Plan;
import org.edu.melody.model.PlaylistDTO;
import org.edu.melody.model.User;
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

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(@RequestParam("name") String userName, @RequestParam("pwd") String password,
			@RequestParam(value = "type", defaultValue = "c", required = true) String userType) {

		Class usrType;
		if (userType.equals("c"))
			usrType = Customer.class;
		else
			usrType = Artist.class;
		return userManager.signIn(userName, password, usrType);
	}

	@RequestMapping(value = "signup", method = RequestMethod.GET)
	public void signUp(@RequestParam("name") String userName, @RequestParam("pwd") String password,
			@RequestParam(value = "type", required = true) String userType,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "cellnum", required = true) long cellNum) {

		Class usrType;
		if (userType.equals("c"))
			usrType = Customer.class;
		else
			usrType = Artist.class;
		userManager.signUp(userName, password, email, cellNum, usrType);
	}

	@RequestMapping(value = "signout", method = RequestMethod.GET)
	public void signOut(@RequestParam("sessionId") String sessionId) {

		userManager.signOutUser(sessionId);
	}

	@RequestMapping(value = "getuser", method = RequestMethod.GET)
	public String getUserInfo(@RequestParam("sessionId") String sessionId) {

		User usr = userManager.getUserInfo(sessionId);
		if (usr == null)
			return "User not logged In.";
		return usr.toString();

	}

	@RequestMapping(value = "plans")
	public List<Plan> getPlans() {

		logger.debug("Fetching plans....");
		System.out.println(planManager.getPlans());
		return planManager.getPlans();

	}

	@RequestMapping(value = "enrollplan", method = RequestMethod.GET)
	public String enrollInPlan(@RequestParam("sessionId") String sessionId, @RequestParam("planid") int planId) {

		String message;

		try {

			Customer cust;
			int paymentId = -1;
			User usr = userManager.getUserInfo(sessionId);
			if (usr == null)
				message = "User not logged In.";
			else if (usr instanceof Customer) {
				cust = (Customer) userManager.getUserInfo(sessionId);

				if (!planManager.checkEnrollment(cust)) {

					boolean enrollmentRequiresPayment = planManager.requiresPayment(planId);
					if (enrollmentRequiresPayment) {
						// Do Payment Processing
					}

					planManager.planEnrollment(cust, planId, paymentId);
					cust.setPlanEnrollmentDate(new Date());
					message = "Enrolled user [" + cust.getUserName() + "] to plan.";
				} else {
					message = "User already enrolled in a plan";
				}
			} else
				message = "Operation not valid for this user.";
		} catch (Exception e) {
			logger.error("Failed to enroll user to plan: " + e.getClass().getName() + ": " + e.getMessage() + "\n"
					+ e.getStackTrace());
			message = "Failed to enroll user to plan: " + e.getClass().getName() + ": " + e.getMessage();
		}

		return message;

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
}

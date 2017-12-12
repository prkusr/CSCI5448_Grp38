package org.edu.melody.manager;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.dao.PlanDao;
import org.edu.melody.model.*;
import org.edu.melody.response.Response;

public class PlanManager {
	
	private static final Logger logger = LogManager.getLogger(PlanManager.class);
	
	List<Plan> plans = new ArrayList<Plan>();
	
	public PlanManager(){
		plans = new ArrayList<Plan>();	
		PlanDao.getPlans(plans);
	}
	
	public List<Plan> getPlans(){		
		return plans;
	}
	
	private Plan isValidPlanId(int planId){
		for(int i=0;i<plans.size();i++){
			if(plans.get(i).getId() == planId)
				return plans.get(i);
		}
		return null;
	}
	public boolean requiresPayment(int planId, Response resp) {
		
		Plan plan = isValidPlanId(planId);
		if (plan == null)
			resp.setError(1, "Invalid Plan Id.");			
		if (plan.getCost() > 0)
				return true;		
		return false;
	}
	
	public boolean checkEnrollment(Customer usr, Response resp){
		return PlanDao.isUserAlreadyEnrolledInPlan(usr, resp);
	}
	
	public void planEnrollment(Customer usr, int planId, int paymentId, Response resp) {
				
		Plan plan = isValidPlanId(planId);
		PlanDao.saveUserPlanEnrollment(usr, plan, paymentId, resp);
		
	}
}

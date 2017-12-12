package org.edu.melody.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.edu.melody.model.Plan;
import org.edu.melody.model.User;
import org.edu.melody.response.Response;


public class PlanDao extends AbstractDAO {
	private static final Logger logger = LogManager.getLogger(PlanDao.class);
	
	public static void getPlans(List<Plan> plans) {
		
		try {

			Statement stmt = null;
			stmt = getConnection().createStatement();
			String query = "SELECT * FROM Plans";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {				 
				Plan plan = Plan.builder().id(rs.getInt("planId")).planName(rs.getString("planName")).cost(rs.getFloat("cost")).durationInDays(rs.getInt("planDuration")).build();
				
				plans.add(plan);
			}
			logger.debug("Loaded all plans from Database.");
		} catch (Exception e) {
			String errorStr = "Failed to load plans from Database. "+e.getClass().getName() + ": " + e.getMessage() ;
			logger.error(errorStr+"\n"+e.getStackTrace());			
		}
	}
	
	public static boolean isUserAlreadyEnrolledInPlan(User usr, Response resp) {
		
		boolean flag = true;
		try {
			Statement stmt = null;
			stmt = getConnection().createStatement();
			String query = "SELECT * FROM UserPlanAssociation U "
					+ "INNER JOIN Plans P ON U.planId = P.planId "
					+ "WHERE userId = "+String.valueOf(usr.getUserId())+" ORDER BY enrollmentDate DESC";
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {				 
				Date enrollmentDate = rs.getDate("enrollmentDate");
				Date currDate = new Date();
				int planDuration = rs.getInt("planDuration");
				int dateDiff = (int)( (enrollmentDate.getTime() - currDate.getTime())/(1000 * 60 * 60 * 24));
				if (dateDiff > planDuration) {
					logger.debug("Plan expired for user: ["+usr.getUserName()+"]");
					flag =  false;
				}
			} else {
				logger.debug("User: ["+usr.getUserName()+"] not enrolled in any plan.");
				flag = false;
			}			
		} catch (Exception e) {
			String errorStr = "Failed to load plan for user ["+usr.getUserName()+"] - "+e.getClass().getName() + ": " + e.getMessage();
			logger.error(errorStr +"\n"+e.getStackTrace());
			resp.setError(1, errorStr);
			flag = false;
		}
		return flag;
	}
	
	public static void saveUserPlanEnrollment(User usr, Plan plan, int paymentId, Response resp) {
		
		try {

			Statement stmt = null;
			stmt = getConnection().createStatement();
			String query = "INSERT INTO UserPlanAssociation(userId, planId, enrollmentDate, paymentId) VALUES(" 
							+ String.valueOf(usr.getUserId())
							+ ", " + String.valueOf(plan.getId())
							+ ", " + String.valueOf(new Date());
			
			if (paymentId > 0)
				query += ", " + String.valueOf(paymentId);
			else
				query += ", NULL";
			
			query += ")";
			
			stmt.executeUpdate(query);			
			logger.debug("Enrolled user ["+usr.getUserName()+"] to plan ["+plan.getPlanName()+"].");
		} catch (Exception e) {
			String errorStr = "Failed to enroll user ["+usr.getUserName()+"] to plan ["+plan.getPlanName()+"]: "+e.getClass().getName();
			logger.error(errorStr + ": " + e.getMessage());
			resp.setError(1, errorStr);
		}
	}
}

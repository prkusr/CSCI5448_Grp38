package org.edu.melody.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.edu.melody.model.Customer.CustomerBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

	
	private int id;
	private String planName;
	private int durationInDays;
	private float cost;
	
	
	public void modifyPlan(Plan plan){
		this.id = plan.getId();
		this.planName = plan.getPlanName();
		this.durationInDays = plan.getDurationInDays();
		this.cost = plan.getCost();
	}
	
	
	@Override
	public String toString() {
		return new StringBuilder("Plan: ").append(" {"+planName+"} ").append(" Duration: {"+String.valueOf(durationInDays)+"} ")
				.append(" Cost: {"+String.valueOf(cost)+"} ").toString();
	}
}

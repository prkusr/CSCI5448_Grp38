package org.edu.melody.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.edu.melody.model.PlanEnrollDTO.PlanEnrollDTOBuilder;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionInfoDTO {
	String sessionsId;
}

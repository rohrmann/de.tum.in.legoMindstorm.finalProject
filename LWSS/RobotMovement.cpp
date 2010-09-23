/*
 * RobotMovement.cpp
 *
 *  Created on: Sep 21, 2010
 *      Author: rohrmann
 */

#include "RobotMovement.h"
#include "GameState.h"
#include <sstream>
#include "Helper.h"

std::string RobotMovement::toString(){
	std::stringstream ss;

	ss << (type == RPUSHER? "pusher " : "puller ") << Helper::pair2Str(this->dest);

	if(next != NULL){
		ss << std::endl << next->toString();
	}

	return ss.str();
}

std::string RobotMovement::toConvertedString(int dim){
	std::stringstream ss;

	ss << (type == RPUSHER? "pusher " : "puller ") << Helper::pair2Str(std::make_pair<int,int>(dest.second,dim-1-dest.first));

	if(next != NULL){
		ss << std::endl << next->toConvertedString(dim);
	}

	return ss.str();
}

RobotMovement::RobotMovement(Robot type, point dest, RobotMovement* next){
	this->type = type;
	this->dest = dest;
	this->next = next;
}

RobotMovement::~RobotMovement(){
	if(next != NULL){
		delete next;
	}
}

point RobotMovement::getPuller(point def){
	if(type == RPULLER && next == NULL){
		return dest;
	}
	else if(next == NULL)
		return def;
	else if(type == RPULLER){
		return next->getPuller(dest);
	}
	else
		return next->getPuller(def);
}

point RobotMovement::getPusher(point def){
	if(type ==RPUSHER && next ==NULL){
		return dest;
	}
	else if(next ==NULL){
		return def;
	}
	else if(type == RPUSHER){
		return next->getPusher(dest);
	}
	else
		return next->getPusher(def);
}

void RobotMovement::insert(RobotMovement* movement){
	if(next == NULL){
		next = movement;
	}
	else
		next->insert(movement);
}

unsigned int RobotMovement::count(){
	if(next == NULL)
		return 1;
	else
		return next->count()+1;
}

void RobotMovement::doMove(GameState* state){
	state->moveBot(this->type,this->dest);
	if(next != NULL)
		next->doMove(state);
}


/*
 * RobotMove.cpp
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#include "RobotMove.h"
#include "AbstractGameMap.h"
#include <utility>

RobotMove::RobotMove(std::pair<int,int> src, std::pair<int,int> dest, Robot type, Move* innerMove, int addEstimatedCosts){
	this->src = src;
	this->dest = dest;
	this->type = type;
	this->estimatedCosts = addEstimatedCosts;
	this->innerMove = innerMove;

	if(innerMove != NULL){
		setPrev(innerMove->removePrev());
		innerMove->setPrev(this);
		this->estimatedCosts += innerMove->getEstimatedCosts();
	}
}

RobotMove::~RobotMove() {
}


bool RobotMove::doMove(AbstractGameMap & map){
	map.setBot(type,dest);

	if(innerMove !=NULL){
		innerMove->doMove(map);
	}

	return true;
}

bool RobotMove::undoMove(AbstractGameMap& map){

	if(innerMove != NULL){
		innerMove->undoMove(map);
	}

	map.setBot(type,src);

	return true;
}

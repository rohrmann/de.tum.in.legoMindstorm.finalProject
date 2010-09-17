/*
 * RobotMove.cpp
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#include "RobotMove.h"
#include "GameMap.h"
#include "ZobristHashing.h"

RobotMove::RobotMove(std::pair<int,int> src, std::pair<int,int> dest, unsigned int botNum, Move* innerMove, int addEstimatedCosts){
	this->src = src;
	this->dest = dest;
	this->botNum = botNum;
	this->estimatedCosts = addEstimatedCosts;
	this->innerMove = innerMove;

	if(innerMove != NULL){
		setPrev(innerMove->getPrev());
		innerMove->removePrev();
		innerMove->setPrev(this);
		this->estimatedCosts += innerMove->getEstimatedCosts();
	}
}

RobotMove::~RobotMove() {
}


bool RobotMove::doMove(GameMap & map,const ZobristHashing<HASHLENGTH>& hashing){
	map.setBot(botNum,dest);

	if(innerMove !=NULL){
		innerMove->doMove(map,hashing);
	}

	return true;
}

bool RobotMove::undoMove(GameMap& map,const ZobristHashing<HASHLENGTH>& hashing){

	if(innerMove != NULL){
		innerMove->undoMove(map,hashing);
	}

	map.setBot(botNum,src);

	return true;
}

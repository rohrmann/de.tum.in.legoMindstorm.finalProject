/*
 * BoxMove.cpp
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#include "BoxMove.h"
#include "AbstractGameMap.h"

BoxMove::BoxMove(std::pair<int,int> sourceBox, std::pair<int,int> destBox,std::pair<int,int> sourceBot, std::pair<int,int> destBot,int botNum, Move* prev, int estimatedCosts):Move(prev,estimatedCosts) {
	this->sourceBox = sourceBox;
	this->destBox = destBox;
	this->sourceBot = sourceBot;
	this->destBot = destBot;
	this->botNum = botNum;
}

BoxMove::~BoxMove() {
}

bool BoxMove::doMove(AbstractGameMap & map){
	map.set(sourceBox,EMPTY);
	map.set(destBox,BOX);
	map.setBot(botNum,destBot);

	return true;
}

bool BoxMove::undoMove(AbstractGameMap& map){
	map.set(destBox,EMPTY);
	map.set(sourceBox,BOX);
	map.setBot(botNum,sourceBot);

	return true;
}



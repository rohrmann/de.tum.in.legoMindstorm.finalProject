/*
 * BoxMove.cpp
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#include "BoxMove.h"
#include "GameMap.h"
#include "ZobristHashing.h"

BoxMove::BoxMove(std::pair<int,int> sourceBox, std::pair<int,int> destBox,std::pair<int,int> sourceBot, std::pair<int,int> destBot,unsigned int botNum, Move* prev, int estimatedCosts):Move(prev,estimatedCosts) {
	this->sourceBox = sourceBox;
	this->destBox = destBox;
	this->sourceBot = sourceBot;
	this->destBot = destBot;
	this->botNum = botNum;
}

BoxMove::BoxMove(std::pair<int,int> sourceBox, std::pair<int,int> destBox,std::pair<int,int> sourceBot, std::pair<int,int> destBot,unsigned int botNum):Move(NULL,0) {
}


BoxMove::~BoxMove() {
}

bool BoxMove::doMove(GameMap & map,const ZobristHashing<HASHLENGTH>& hashing){
	map.hashValue ^= hashing.getField(sourceBox,map.at(sourceBox));
	map.hashValue ^= hashing.getField(destBox,map.at(destBox));
	map.set(sourceBox,GEMPTY);
	map.set(destBox,GBOX);
	map.setBot(botNum, destBot);
	return true;
}

bool BoxMove::undoMove(GameMap& map, const ZobristHashing<HASHLENGTH>& hashing){
	map.hashValue ^= hashing.getField(sourceBox,map.at(sourceBox));
	map.hashValue ^= hashing.getField(destBox,map.at(destBox));
	map.set(destBox,GEMPTY);
	map.set(sourceBox,GBOX);
	map.setBot(botNum, sourceBot);

	return true;
}



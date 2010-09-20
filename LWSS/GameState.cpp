/*
 * GameMap.cpp
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#include "GameState.h"
#include <utility>
#include "ZobristHashing.h"
#include <stack>
#include <vector>
#include "MapUtils.h"
#include <string.h>
#include "ComponentTester.h"
#include "MapAlgorithms.h"


unsigned int GameState::numTargets =0;
std::pair<dimension,dimension>* GameState::targets = NULL;
std::unordered_set< std::pair<dimension,dimension>, PairHasher, PairEqual > GameState::targetsHash;
int GameState::instanceCounter=0;

GameState::~GameState(){
	delete [] boxes;

	if(prev!=NULL){
		prev->children--;
	}
}

GameState::GameState(){
	init(make_point(0,0),make_point(0,0),make_point(0,0),make_point(0,0),NULL,0,0,1,NULL);
}

GameState::GameState(field* map,std::pair<dimension,dimension> dims) : Map<field>(map,dims){
	init(make_point(0,0),make_point(0,0),make_point(0,0),make_point(0,0),NULL,0,0,1,NULL);
}

GameState::GameState(point puller,point pullerTL,point pusher,point pusherTL,point* boxes, unsigned int numBoxes,int estimatedCosts,field componentValue,GameState* prev,field* map,point dims) : Map<field>(map,dims){
	init(puller,pullerTL,pusher,pusherTL,boxes,numBoxes,estimatedCosts,componentValue,prev);
}

void GameState::init(point puller,point pullerTL,point pusher,point pusherTL,point* boxes, unsigned int numBoxes,int estimatedCosts,field componentValue,GameState* prev){
	this->puller = puller;
	this->pullerTL = pullerTL;
	this->pusher = pusher;
	this->pusherTL = pusherTL;
	this->boxes = boxes;
	this->numBoxes = numBoxes;
	this->estimatedCosts = estimatedCosts;
	this->componentValue = componentValue;

	this->prev = prev;
	this->children = 0;

	if(prev != NULL){
		prev->children++;
		this->level = prev->level +1;
	}
	else{
		this->level = 0;
	}

	instanceCounter++;
}

bool GameState::solved(){
	for(unsigned int i= 0; i< numBoxes;i++){
		if(GameState::targetsHash.find(boxes[i]) == GameState::targetsHash.end())
			return false;
	}
	return true;
}

void GameState::setPrev(GameState* prev){
	if(this->prev !=NULL){
		this->prev->children--;
	}

	this->prev = prev;
	this->level = this->prev->level + 1;
	this->prev->children++;
}

GameState* GameState::copy(){
	point* newBoxes = new point[numBoxes];
	field* newMap = new field[dims.first*dims.second];

	memcpy(newBoxes,boxes,sizeof(point)*numBoxes);
	memcpy(newMap,map,sizeof(field)*dims.first*dims.second);

	return new GameState(puller,pullerTL,pusher,pusherTL,newBoxes,numBoxes,estimatedCosts,componentValue,prev,newMap,dims);
}

void GameState::updateTL(){
	bool pullerFound = false;
	bool pusherFound = false;
	field pullerField = this->at(puller);
	field pusherField = this->at(pusher);

	for(dimension i = 0 ; i< dims.first;i++){
		for(dimension j =0; j<dims.second;j++){
			if(!pullerFound && this->at(i,j) == pullerField){
				pullerTL = make_point(i,j);
				pullerFound = true;
			}

			if(!pusherFound && this->at(i,j) == pusherField){
				pusherTL = make_point(i,j);
				pusherFound = true;
			}

			if(pusherFound && pullerFound)
				break;
		}
	}
}

void GameState::moveBot(Robot type, point pos){
	switch(type){
	case RPUSHER:
		return movePusher(pos);
	case RPULLER:
		return movePuller(pos);
	}
}

void GameState::movePuller(point pos){
	puller = pos;
}

void GameState::movePusher(point pos){
	pusher = pos;
}

void GameState::moveBox(unsigned int boxNum, point pos){
	this->set(boxes[boxNum],1);
	this->boxes[boxNum] = pos;
	this->set(pos,0);
	updateComponents();
}

HashValue<HASHLENGTH> GameState::getHashValue(ZobristHashing<HASHLENGTH>* hashing){
	HashValue<HASHLENGTH> result;

	for(unsigned int i = 0; i< numBoxes;i++){
		result ^= hashing->getBox(boxes[i]);
	}

	updateTL();

	result ^= hashing->getPuller(pullerTL);
	result ^= hashing->getPusher(pusherTL);

	return result;
}

void GameState::updateComponents(){

	for(unsigned int x =0 ; x < dims.first*dims.second;x++){
		if(map[x] != 0){
			map[x] = 255;
		}
	}

	field component =1;
	ComponentTester tester(this);

	for(dimension x = 0; x< dims.first;x++){
		for(dimension y = 0; y < dims.second;y++){
			if(at(x,y) == 255){
				dfs(make_point(x,y),map,component++,tester);
			}
		}
	}
}

void GameState::calcEstimatedCosts(){
	estimatedCosts= 0;
	bool* used = new bool[GameState::numTargets];
	for(unsigned int i =0; i< GameState::numTargets;i++){
		used[i] = false;
	}

	for(unsigned int i = 0; i< this->numBoxes;i++){
		unsigned int index = -1;
		int dist = 255;
		for(unsigned int j = 0; j< this->numTargets;j++){
			if(!used[j] && calcManhattanDist(this->boxes[i], this->targets[j]) < dist){
				dist = calcManhattanDist(this->boxes[i],this->targets[j]);
				index = j;
			}
		}

		used[index] = true;

		estimatedCosts += dist;
	}

	delete [] used;
}

unsigned char GameState::getFingerPrint(point pos){
	unsigned char result = 0;
	unsigned int x = pos.first-1;
	unsigned int y = pos.second-1;

	for(unsigned int i =0; i< 3;i++){
		if(this->at(x,y+i)==0){
			result |= 1<<i;
		}
	}

	if(this->at(x+1,y)==0){
		result |= 1<<3;
	}

	if(this->at(x+1,y+2)==0){
		result |= 1 << 4;
	}

	for(unsigned int i =0 ; i< 3;i++){
		if(this->at(x+2,y+i)==0){
			result |= 1 << (5 + i);
		}
	}

	return result;
}


field GameState::getNextComponentValue(){
	return componentValue++;
}

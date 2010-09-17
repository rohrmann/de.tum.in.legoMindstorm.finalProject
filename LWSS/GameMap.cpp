/*
 * GameMap.cpp
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#include "GameMap.h"
#include <utility>
#include "ZobristHashing.h"
#include <stack>
#include <vector>
#include "NotWall.h"
#include "Empty.h"
#include "MapUtils.h"

GameMap::~GameMap(){
	delete [] bots;
	delete [] types;
	delete [] targets;
}

GameMap::GameMap(){
	bots =NULL;
	numBots= 0;
	types= NULL;
	targets =NULL;
	numTargets = 0;
}

GameMap::GameMap(std::pair<int,int>* bots, Robot* types,unsigned int numBots, std::pair<int,int>* targets,unsigned int numTargets,Game* map, std::pair<int,int> dims):Map<Game>(map,dims){
	this->bots = bots;
	this->types = types;
	this->numBots = numBots;
	this->targets = targets;
	this->numTargets = numTargets;
}

void GameMap::init(const ZobristHashing<HASHLENGTH>& hashing){
	for(int i =0; i < dims.first;i++){
		for(int j =0; j< dims.second;j++){
			if(at(i,j) != GWALL)
				hashValue ^= hashing.getField(i,j,at(i,j));
		}
	}

	for(unsigned int i =0; i< numTargets;i++ ){
		targetsHash.insert(targets[i]);
	}

	clipToAccessibleArea();
}

void GameMap::clipToAccessibleArea(){

	bool *data = new bool[dims.first*dims.second];

	for(int i =0; i< dims.first*dims.second; i++){
		data[i] = false;
	}

	for(unsigned int i =0; i< numBots;i++){
		dfs<NotWall>(bots[i],data,true,false);
	}

	for(int i = 0; i < dims.first*dims.second;i++){
		if(!data[i]){
			map[i] = GWALL;
		}
	}

	delete [] data;

}

Game GameMap::getField(int x, int y) const{
	if(!validCoords(x,y))
		return GWALL;

	return this->map[addr(x,y)];
}

template<typename U,typename T >
void GameMap::dfs(std::pair<int,int> coordinates,T* data,T value, T notVisited){
	std::stack<std::pair<int,int> > nodes;
	U test;

	nodes.push(coordinates);
	data[addr(coordinates)] = value;

	while(!nodes.empty()){
		std::pair<int,int> node = nodes.top();
		nodes.pop();

		if(data[addr(Helper::north(node))] == notVisited && test(getField(Helper::north(node)))){
			nodes.push(Helper::north(node));
			data[addr(Helper::north(node))] = value;
		}

		if(data[addr(Helper::west(node))]==notVisited && test(getField(Helper::west(node)))){
			nodes.push(Helper::west(node));
			data[addr(Helper::west(node))] = value;
		}

		if(data[addr(Helper::south(node))]==notVisited && test(getField(Helper::south(node)))){
			nodes.push(Helper::south(node));
			data[addr(Helper::south(node))] = value;
		}

		if(data[addr(Helper::east(node))] == notVisited && test(getField(Helper::east(node)))){
			nodes.push(Helper::east(node));
			data[addr(Helper::east(node))]= value;
		}
	}
}

HashValue<HASHLENGTH> GameMap::getHash(const ZobristHashing<HASHLENGTH>& hashing){
	std::pair<int,int>* components = getBotComponents();

	HashValue<HASHLENGTH> result = hashValue;

	for(unsigned int i =0; i< numBots;i++){
		result ^= hashing.getBot(components[i],i);
	}

	delete [] components;

	return result;
}

std::pair<int,int>* GameMap::getBotComponents(){
	int* data = new int[dims.first*dims.second];
	Empty empty;

	for(int i =0; i< dims.first*dims.second;i++){
		data[i] = -1;
	}

	int component=0;

	for(int i=0;i<dims.first;i++){
		for(int j =0; j < dims.second;j++){
			if(data[i*dims.second+j]==-1 && empty(getField(i,j)))
				dfs<Empty>(std::make_pair(i,j),data,component++,-1);
		}
	}


	std::pair<int,int>* componentIDs = new std::pair<int,int>[component];

	component = -1;
	for(int i=0; i<dims.first;i++){
		for(int j =0;j<dims.second;j++){
			if(data[i*dims.second+j] > component){
				componentIDs[component+1] = std::make_pair(i,j);
				component = data[i*dims.second+j];
			}
		}
	}

	std::pair<int,int>* result= new std::pair<int,int>[numBots];

	for(unsigned int i=0; i< numBots;i++){
		result[i] = componentIDs[data[addr(bots[i])]];
	}

	delete [] componentIDs;

	return result;
}

bool GameMap::solved(){
	for(unsigned int i= 0; i< numTargets;i++){
		if(at(targets[i])!= GBOX)
			return false;
	}

	return true;
}


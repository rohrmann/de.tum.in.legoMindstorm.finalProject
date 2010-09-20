/*
 * MapUtils.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#include "MapUtils.h"
#include <utility>
#include <stack>
#include <vector>
#include "PrintableMap.h"
#include "GameState.h"
#include "ZobristHashing.h"
#include "Helper.h"
#include "IsAccessible.h"
#include "IsComponent.h"
#include "MapAlgorithms.h"

bool MapUtils::write(const PrintableMap& map, std::ostream & output){

	for(dimension i = 0; i< map.dims.first;i++){
		for(dimension j = 0; j< map.dims.second;j++){
			output << MapUtils::xsb2Ch(map.at(i,j));
		}
		output << std::endl;
	}

	return true;
}

XSB MapUtils::ch2XSB(char ch){
	switch(ch){
	case ' ':
		return EMPTY;
	case '@':
		return PUSHER;
	case '!':
		return PULLER;
	case '.':
		return TARGET;
	case '*':
		return BOXTARGET;
	case '$':
		return BOX;
	case '#':
		return WALL;
	default:
		return UNDEFINED;
	}
}

char MapUtils::xsb2Ch(XSB xsb){
	switch(xsb){
	case EMPTY:
		return ' ';
	case PUSHER:
		return '@';
	case PULLER:
		return '!';
	case TARGET:
		return '.';
	case BOXTARGET:
		return '*';
	case BOX:
		return '$';
	default:
		return '#';
	}
}


PrintableMap* MapUtils::read(std::istream& input){
	std::vector<std::string> map;
	unsigned int lineLength = 0;
	int size = 256;
	char charLine[size];
	std::string line = "";
	while(!input.eof()){
		input.getline(charLine,256);
		line = std::string(charLine);

		unsigned int counter = 0;

		while(counter < line.length() && line[counter] == ' ')
			counter++;

		if(counter >= line.length() || ch2XSB(line[counter])==UNDEFINED)
			continue;

		if(lineLength < line.length()){
			lineLength = line.length();
		}
		map.push_back(line);
	}

	XSB* ptr = new XSB[lineLength*map.size()];

	for(unsigned int i =0; i < lineLength*map.size();i++){
		ptr[i] = WALL;
	}

	for(unsigned int i = 0; i< map.size(); i++){
		line = map[i];
		for(unsigned int j =0;j < line.length();j++){
			ptr[i*lineLength +j] = ch2XSB(line[j]);
		}
	}

	PrintableMap* printableMap = new PrintableMap(ptr,std::pair<dimension,dimension>((dimension)map.size(),(dimension)lineLength));

	return printableMap;
}

PrintableMap* MapUtils::convert(const GameState& map){

	XSB* xsb = new XSB[map.dims.first*map.dims.second];

	for(unsigned int i =0; i< map.dims.first*map.dims.second;i++){
		xsb[i] = WALL;
	}

	for(dimension i =0; i< map.dims.first; i++){
		for(dimension j =0; j< map.dims.second;j++){
			if(map.at(i,j)!=0)
				xsb[i*map.dims.second+j] = EMPTY;
		}
	}

	std::pair<dimension,dimension> * targets = map.targets;

	for(unsigned int i =0; i< map.numTargets;i++){
		xsb[map.addr(targets[i])] = TARGET;
	}

	std::pair<dimension,dimension> * boxes = map.boxes;

	for(unsigned int i =0; i< map.numBoxes;i++){
		xsb[map.addr(boxes[i])] = xsb[map.addr(boxes[i])] == TARGET ? BOXTARGET: BOX;
	}

	xsb[map.addr(map.puller)] = PULLER;
	xsb[map.addr(map.pusher)] = PUSHER;

	return new PrintableMap(xsb,map.dims);
}

void MapUtils::printMap(const GameState & map, std::ostream & output){
	PrintableMap* pMap= convert(map);
	write(*pMap,output);

	delete pMap;
}

GameState* MapUtils::buildGameState(const PrintableMap& map,const ZobristHashing<HASHLENGTH> & hashing){
	field* componentMap = new field[map.dims.first*map.dims.second];

	for( unsigned int i =0; i< map.dims.first*map.dims.second;i++){
		componentMap[i] = 0;
	}

	IsAccessible accessible(map);
	field component=1;

	for(dimension i = 0; i< map.dims.first;i++){
		for(dimension j = 0;j<map.dims.second;j++){
			if(componentMap[map.addr(i,j)]==0 && accessible(i,j)){
				dfs(std::make_pair(i,j),componentMap,component++,accessible);
			}
		}
	}

	GameState* state = new GameState(componentMap,map.dims);

	unsigned int numTargets =0;
	unsigned int numBoxes = 0;

	for( dimension i=0; i < map.dims.first; i++){
		for(dimension j =0; j < map.dims.second;j++){
			if(map.at(i,j)==TARGET || map.at(i,j) == BOXTARGET)
				numTargets++;
			else if(map.at(i,j) == BOX || map.at(i,j) == BOXTARGET)
				numBoxes++;
		}
	}

	std::pair<dimension,dimension>* targets = new std::pair<dimension,dimension>[numTargets];
	numTargets = 0;
	std::pair<dimension,dimension>* boxes = new std::pair<dimension,dimension>[numBoxes];
	numBoxes =0;

	for( dimension i =0; i < map.dims.first;i++){
		for(dimension j=0; j< map.dims.second;j++){
			switch(map.at(i,j)){
			case TARGET:
				targets[numTargets++] = std::make_pair(i,j);
				break;
			case BOXTARGET:
				targets[numTargets++] = std::make_pair(i,j);
				boxes[numBoxes++] = std::make_pair(i,j);
				state->hashValue ^= hashing.getBox(i,j);
				break;
			case BOX:
				boxes[numBoxes++] = std::make_pair(i,j);
				state->hashValue ^= hashing.getBox(i,j);
				break;
			case EMPTY:
				break;
			case PULLER:
				state->puller = std::make_pair(i,j);
				break;
			case PUSHER:
				state->pusher = std::make_pair(i,j);
				break;
			default:
				break;
			}
		}
	}

	state->boxes = boxes;
	state->numBoxes = numBoxes;
	state->targets = targets;
	state->numTargets = numTargets;

	for(unsigned int i=0 ;i < numTargets;i++){
		state->targetsHash.insert(targets[i]);
	}

	bool* visited = new bool[map.dims.first*map.dims.second];

	for(unsigned int i =0; i< map.dims.first*map.dims.second;i++){
		visited[i] = false;
	}

	IsComponent isComponent(state);

	std::pair<dimension,dimension> topLeft = dfs(state->puller,visited,true,isComponent);

	state->pullerTL = topLeft;
	state->hashValue ^= hashing.getPuller(topLeft);

	if(state->at(state->puller)!=state->at(state->pusher))
		topLeft = dfs(state->pusher,visited,true,isComponent);

	state->pusherTL = topLeft;
	state->hashValue ^= hashing.getPusher(topLeft);
	state->componentValue = component;

	state->calcEstimatedCosts();

	delete [] visited;

	return state;
}



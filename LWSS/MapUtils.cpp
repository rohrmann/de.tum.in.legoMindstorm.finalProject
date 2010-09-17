/*
 * MapUtils.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#include "MapUtils.h"
#include <utility>
#include "PrintableMap.h"
#include "GameMap.h"
#include <vector>
#include "Helper.h"

bool MapUtils::write(const PrintableMap& map, std::ostream & output){

	for(int i = 0; i< map.dims.first;i++){
		for(int j = 0; j< map.dims.second;j++){
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

XSB MapUtils::game2XSB(Game game){
	switch(game){
	case GBOX:
		return BOX;
	case GEMPTY:
		return EMPTY;
	default:
		return WALL;
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

	PrintableMap* printableMap = new PrintableMap(ptr,std::pair<int,int>(map.size(),lineLength));

	return printableMap;
}

PrintableMap* MapUtils::convert(const GameMap& map){

	XSB* xsb = new XSB[map.dims.first*map.dims.second];

	for(int i =0; i< map.dims.first*map.dims.second;i++){
		xsb[i] = WALL;
	}

	for(int i =0; i< map.dims.first; i++){
		for(int j =0; j< map.dims.second;j++){
			xsb[i*map.dims.second+j] = game2XSB(map.at(i,j));
		}
	}

	std::pair<int,int> * targets = map.targets;

	for(unsigned int i =0; i< map.numTargets;i++){
		int addr = targets[i].first*map.dims.second + targets[i].second;
		xsb[addr] = xsb[addr] == BOX ? BOXTARGET: TARGET;
	}

	std::pair<int,int> * bots = map.bots;

	for(unsigned int i =0; i< map.numBots; i++){
		switch(map.types[i]){
		case RPULLER:
			xsb[map.addr(bots[i])] = PULLER;
			break;
		case RPUSHER:
			xsb[map.addr(bots[i])] = PUSHER;
			break;
		}

	}

	return new PrintableMap(xsb,map.dims);
}

void MapUtils::printMap(const GameMap & map, std::ostream & output){
	PrintableMap* pMap= convert(map);
	write(*pMap,output);

	delete pMap;
}

GameMap* MapUtils::buildGameMap(const PrintableMap& map){
	Game* game = new Game[map.dims.first*map.dims.second];

	for( int i =0; i< map.dims.first*map.dims.second;i++){
		game[i] = GWALL;
	}

	unsigned int numBots = 0;
	unsigned int numTargets =0;

	for( int i=0; i < map.dims.first; i++){
		for(int j =0; j < map.dims.second;j++){
			if(map.at(i,j)==TARGET)
				numTargets++;
			else if(map.at(i,j)==PULLER || map.at(i,j) == PUSHER)
				numBots++;
		}
	}

	std::pair<int,int>* bots = new std::pair<int,int>[numBots];
	Robot * types=  new Robot[numBots];
	std::pair<int,int>* targets = new std::pair<int,int>[numTargets];
	numBots =0;
	numTargets = 0;

	for( int i =0; i < map.dims.first;i++){
		for(int j=0; j< map.dims.second;j++){
			switch(map.at(i,j)){
			case TARGET:
				targets[numTargets++] = std::make_pair(i,j);
				game[map.addr(i,j)] = GEMPTY;
				break;
			case BOXTARGET:
				targets[numTargets++] = std::make_pair(i,j);
				game[map.addr(i,j)] = GBOX;
				break;
			case BOX:
				game[map.addr(i,j)] = GBOX;
				break;
			case EMPTY:
				game[map.addr(i,j)] = GEMPTY;
				break;
			case PULLER:
				bots[numBots] = std::make_pair(i,j);
				types[numBots++] = RPULLER;
				game[map.addr(i,j)]= GEMPTY;
				break;
			case PUSHER:
				bots[numBots] = std::make_pair(i,j);
				types[numBots++] = RPUSHER;
				game[map.addr(i,j)] = GEMPTY;
				break;
			default:
				break;
			}
		}
	}

	GameMap* gameMap = new GameMap(bots,types,numBots,targets,numTargets,game,map.dims);
	return gameMap;
}




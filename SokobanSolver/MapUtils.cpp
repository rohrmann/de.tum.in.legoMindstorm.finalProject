/*
 * MapUtils.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#include "MapUtils.h"
#include <utility>
#include <iostream>
#include "Helper.h"
#include "PrintableMap.h"
#include "AbstractGameMap.h"

bool MapUtils::write(const PrintableMap& map, std::ostream & output){
	std::pair<int,int> dims = map.dimensions();

	for(int i = 0; i< dims.first;i++){
		for(int j = 0; j< dims.second;j++){
			output << Helper::field2Ch(map.at(i,j));
		}
		output << std::endl;
	}

	return true;
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

		if(counter >= line.length() || line[counter] == ';')
			continue;

		if(lineLength < line.length()){
			lineLength = line.length();
		}
		map.push_back(line);
	}

	Field* ptr = new Field[lineLength*map.size()];

	for(unsigned int i = 0; i< map.size(); i++){
		line = map[i];
		for(unsigned int j =0;j < lineLength;j++){
			if(line.length() <= j){
				ptr[i*lineLength + j] = UNDEFINED;
			}
			else
				ptr[i*lineLength +j] = Helper::ch2Field(line[j]);
		}
	}

	PrintableMap* printableMap = new PrintableMap(ptr,std::pair<int,int>(map.size(),lineLength));

	return printableMap;
}

PrintableMap* MapUtils::convert(const AbstractGameMap& map){
	std::pair<int,int> dims = map.dimensions();

	Field* field = new Field[dims.first*dims.second];

	for(int i =0; i< dims.first; i++){
		for(int j =0; j< dims.second;j++){
			field[i*dims.second+j] = map.at(i,j);
		}
	}

	const std::vector<std::pair<int,int> >& targets = map.getTargets();

	for(std::vector<std::pair<int,int> >::const_iterator it = targets.begin(); it != targets.end();++it){
		int coord = (*it).first*dims.second + (*it).second;

		field[coord] = field[coord] ==BOX ? BOXONTARGET: TARGET;
	}

	for(unsigned int i =0; i< map.getBotSize();i++){
		std::pair<int,int> bot = map.getBot(i);
		switch(map.getType(i)){
		case PULLER:
			field[bot.first*dims.second+bot.second] = PULLERSTART;
			break;
		case PUSHER:
			field[bot.first*dims.second+bot.second] = PUSHERSTART;
			break;
		}

	}



	return new PrintableMap(field,dims);
}

void MapUtils::printMap(const AbstractGameMap & map, std::ostream & output){
	PrintableMap* pMap= convert(map);

	write(*pMap,output);

	delete pMap;
}




/*
 * ArrayMapUtils.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#include "ArrayMapUtils.h"
#include "ArrayMap.h"
#include "Helper.h"
#include <vector>
#include <string>

AbstractMap* ArrayMapUtils::read(std::istream& input){
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

	ArrayMap* arrayMap = new ArrayMap(ptr,std::pair<int,int>(map.size(),lineLength));

	return arrayMap;
}


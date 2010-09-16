/*
 * ArrayMapFactory.cpp
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#include "ArrayMapFactory.h"
#include "PrintableMap.h"
#include "ZobristHashing.h"
#include <utility>

ArrayMap* ArrayMapFactory::createMap(const PrintableMap& map){
	std::pair<int,int> dims = map.dimensions();

	Field * field = new Field[dims.first*dims.second];

	ArrayMap * result = new ArrayMap();
	result->map = field;
	result->dims = dims;

	for(int i=0; i< dims.first;i++){
		for(int j =0;j<dims.second;j++){
			switch(map.at(i,j)){
			case PULLERSTART:
				result->bots.push_back(std::pair<int,int>(i,j));
				result->types.push_back(PULLER);
				result->map[i*dims.second+j] = EMPTY;
				break;
			case PUSHERSTART:
				result->bots.push_back(std::pair<int,int>(i,j));
				result->types.push_back(PUSHER);
				result->map[i*dims.second+j]=EMPTY;
				break;
			case BOX:
				result->map[i*dims.second+j] = BOX;
				break;
			case TARGET:
				result->targets.push_back(std::pair<int,int>(i,j));
				break;
			case BOXONTARGET:
				result->targets.push_back(std::pair<int,int>(i,j));
				result->map[i*dims.second+j]= BOX;
				break;
			case EMPTY:
				result->map[i*dims.second+j] = EMPTY;
				break;
			case WALL:
				result->map[i*dims.second+j] = WALL;
				break;
			case UNDEFINED:
				result->map[i*dims.second+j] =UNDEFINED;
				break;
			default:
				break;
			}
		}
	}

	result->hashing = new ZobristHashing<ZOBRISTHASHLENGTH>(dims,result->bots.size());
	result->clipToReachable();
	result->updateComponents();

	return result;
}

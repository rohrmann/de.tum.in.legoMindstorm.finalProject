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
#include "AbstractMap.h"

bool MapUtils::write(const AbstractMap& map, std::ostream & output){
	std::pair<int,int> dims = map.dimensions();

	for(int i = 0; i< dims.first;i++){
		for(int j = 0; j< dims.second;j++){
			output << Helper::field2Ch(map.at(i,j));
		}
		output << std::endl;
	}

	return true;
}


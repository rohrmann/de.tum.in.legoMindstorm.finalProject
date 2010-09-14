/*
 * GraphMap.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#include "GraphMap.h"
#include <utility>

Field& GraphMap::get(int x, int y) throw(InvalidArgumentException){
	return field;
}

void GraphMap::set(int x, int y, Field type) throw(InvalidArgumentException){

}

std::pair<int,int> GraphMap::dimensions(){
	return std::pair<int,int>(0,0);
}

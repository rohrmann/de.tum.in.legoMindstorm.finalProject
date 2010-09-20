/*
 * MapAlgorithms.cpp
 *
 *  Created on: Sep 20, 2010
 *      Author: rohrmann
 */

#include "MapAlgorithms.h"

int calcManhattanDist(point a, point b){
	return abs(a.first-b.first)+abs(a.second-b.second);
}


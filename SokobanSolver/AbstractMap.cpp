/*
 * AbstractMap.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */


#include "AbstractMap.h"

void AbstractMap::clear(){
	boxes.clear();
	targets.clear();
	puller = std::pair<int,int>(0,0);
	pusher = std::pair<int,int>(0,0);
}

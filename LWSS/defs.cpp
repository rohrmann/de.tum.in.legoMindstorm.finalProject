/*
 * defs.cpp
 *
 *  Created on: Sep 19, 2010
 *      Author: rohrmann
 */

#include "defs.h"

point make_point(dimension x, dimension y){
	return std::make_pair<dimension,dimension>(x,y);
}

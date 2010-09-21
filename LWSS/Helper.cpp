/*
 * Helper.cpp
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */


#include "Helper.h"
#include <iostream>
#include <sstream>

std::string Helper::pair2Str(point pair){
	std::stringstream ss;

	ss<< "(" << (int)pair.first << "," << (int)pair.second << ")";

	return ss.str();
}

std::string Helper::pair2Str(std::pair<int,int> pair){
	std::stringstream ss;

	ss<< "(" << pair.first << "," << pair.second << ")";

	return ss.str();
}

std::string Helper::pair2Str(dimension x, dimension y){
	return pair2Str(std::make_pair(x,y));
}

point Helper::north(point origin, int dist){
	return make_point(origin.first-dist,origin.second);
}

point Helper::south(point origin, int dist){
	return make_point(origin.first+dist,origin.second);
}

point Helper::east(point origin, int dist){
	return make_point(origin.first,origin.second+dist);

}

point Helper::west(point origin, int dist){
	return make_point(origin.first,origin.second-dist);
}

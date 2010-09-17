/*
 * Helper.cpp
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */


#include "Helper.h"
#include <iostream>
#include <sstream>

std::string Helper::pair2Str(std::pair<int,int> pair){
	std::stringstream ss;

	ss<< "(" << pair.first << "," << pair.second << ")";

	return ss.str();
}

std::string Helper::pair2Str(int x, int y){
	return pair2Str(std::make_pair(x,y));
}

std::pair<int,int> Helper::north(std::pair<int,int> origin, int dist){
	return std::make_pair(origin.first-dist,origin.second);
}

std::pair<int,int> Helper::south(std::pair<int,int> origin, int dist){
	return std::make_pair(origin.first+dist,origin.second);
}

std::pair<int,int> Helper::east(std::pair<int,int> origin, int dist){
	return std::make_pair(origin.first,origin.second+dist);

}

std::pair<int,int> Helper::west(std::pair<int,int> origin, int dist){
	return std::make_pair(origin.first,origin.second-dist);
}

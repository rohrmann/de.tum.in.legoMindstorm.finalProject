/*
 * main.cpp
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#include <fstream>
#include <iostream>
#include "PrintableMap.h"
#include "MapUtils.h"
#include "GameState.h"
#include "ZobristHashing.h"
#include "Solver.h"
#include "IDAStar.h"

int main(int argc, char** argv){
	std::string str;
	if(argc >1){
		str = argv[1];
	}
	else{
		std::cin >> str;
	}

	std::ifstream ifs(str.c_str(),std::ios::in);
	PrintableMap* pMap = MapUtils::read(ifs);

	Solver solver(*pMap);

	solver.solve();
	return 0;
}

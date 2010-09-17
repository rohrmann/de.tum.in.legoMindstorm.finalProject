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
#include "GameMap.h"
#include "ZobristHashing.h"
#include "Solver.h"

int main(int argc, char** argv){

	std::ifstream ifs("sokoban.xsb",std::ios::in);
	PrintableMap* pMap = MapUtils::read(ifs);
	GameMap * gMap = MapUtils::buildGameMap(*pMap);
	ZobristHashing<HASHLENGTH> hashing(gMap->dims,gMap->numBots);
	gMap->init(hashing);

	Solver solver;

	solver.solve(gMap,hashing);


	return 0;
}
